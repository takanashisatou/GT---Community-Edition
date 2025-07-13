package dev.arbor.gtnn.common.machine

import com.gregtechceu.gtceu.api.capability.ICoverable
import com.gregtechceu.gtceu.api.capability.recipe.IO
import com.gregtechceu.gtceu.api.cover.CoverDefinition
import com.gregtechceu.gtceu.api.cover.filter.FilterHandler
import com.gregtechceu.gtceu.api.cover.filter.FilterHandlers
import com.gregtechceu.gtceu.api.cover.filter.ItemFilter
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget
import com.gregtechceu.gtceu.api.misc.virtualregistry.EntryTypes
import com.gregtechceu.gtceu.api.misc.virtualregistry.VirtualEnderRegistry
import com.gregtechceu.gtceu.api.misc.virtualregistry.VirtualEntry
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler
import com.gregtechceu.gtceu.common.cover.ender.AbstractEnderLinkCover
import com.gregtechceu.gtceu.utils.GTTransferUtils
import com.lowdragmc.lowdraglib.gui.widget.Widget
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import dev.arbor.gtnn.GTNN.getServerConfig
import dev.arbor.gtnn.extension.StringExtension.nn
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.IItemHandlerModifiable

class EnderItemLinkCover(definition: CoverDefinition, coverHolder: ICoverable, attachedSide: Direction) :
    AbstractEnderLinkCover<EnderItemLinkCover.VirtualBox>(definition, coverHolder, attachedSide) {
    @Persisted
    @DescSynced
    @get: JvmName("getFilterHandlerKt")
    val filterHandler: FilterHandler<ItemStack, ItemFilter> = FilterHandlers.item(this)

    @Persisted
    @DescSynced
    var virtualBox: VirtualBox? = null
    private var itemsLeftToTransferLastSecond = 0

    init {
        if (!isRemote) this.virtualBox = VirtualEnderRegistry.getInstance()
            .getOrCreateEntry(getOwner(), VirtualBox.ENDER_ITEM, this.channelName)
    }

    override fun getFilterHandler(): FilterHandler<*, *> {
        return filterHandler
    }

    override fun canAttach(): Boolean {
        if (!getServerConfig().isTurnOnEnderItemCover) return false
        return this.coverHolder.getItemHandlerCap(this.attachedSide, false) != null
    }

    protected override fun identifier(): String {
        return "EILink#"
    }

    override fun getEntry(): VirtualEntry? {
        return virtualBox
    }

    protected override fun setEntry(entry: VirtualEntry) {
        this.virtualBox = entry as VirtualBox
    }

    override fun getEntryType(): EntryTypes<VirtualBox> {
        return VirtualBox.ENDER_ITEM
    }

    protected override fun transfer() {
        val timer: Long = this.coverHolder.getOffsetTimer()
        if (itemsLeftToTransferLastSecond > 0) {
            val platformTransferredItem = doTransferItems(itemsLeftToTransferLastSecond)
            this.itemsLeftToTransferLastSecond -= platformTransferredItem
        }

        if (timer % 20L == 0L) {
            this.itemsLeftToTransferLastSecond = TRANSFER_RATE
        }
    }

    private fun doTransferItems(itemsLeftToTransferLastSecond: Int): Int {
        val ownItemHandler = this.ownItemHandler
        if (ownItemHandler != null && virtualBox != null) {
            return when (io) {
                IO.IN -> GTTransferUtils.transferItemsFiltered(
                    ownItemHandler, virtualBox!!.itemHandler,
                    filterHandler.getFilter(), itemsLeftToTransferLastSecond
                )

                IO.OUT -> GTTransferUtils.transferItemsFiltered(
                    virtualBox!!.itemHandler, ownItemHandler,
                    filterHandler.getFilter(), itemsLeftToTransferLastSecond
                )

                else -> 0
            }
        }
        return 0
    }

    val ownItemHandler: IItemHandlerModifiable?
        get() = this.coverHolder.getItemHandlerCap(this.attachedSide, false)

    protected override fun addVirtualEntryWidget(
        entry: VirtualEntry,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        canClick: Boolean
    ): Widget {
        val slotWidget = SlotWidget((entry as VirtualBox).itemHandler, 0, x, y, false, false)
        slotWidget.setSize(width, height)
        return slotWidget
    }

    override fun getUITitle(): String? {
        return "cover.ender_item_link.title"
    }

    companion object {
        val fieldHolder: ManagedFieldHolder =
            ManagedFieldHolder(EnderItemLinkCover::class.java, MANAGED_FIELD_HOLDER)
        const val TRANSFER_RATE: Int = 64 // item/s
    }

    class VirtualBox : VirtualEntry() {
        val itemHandler: CustomItemStackHandler = CustomItemStackHandler()

        fun setStack(stack: ItemStack) {
            itemHandler.setStackInSlot(0, stack)
        }

        override fun getType(): EntryTypes<out VirtualEntry> {
            return ENDER_ITEM
        }

        override fun equals(o: Any?): Boolean {
            if (o !is VirtualBox) return false
            return this.itemHandler === o.itemHandler
        }

        override fun serializeNBT(): CompoundTag? {
            val tag = super.serializeNBT()

            if (itemHandler.getStackInSlot(0) != ItemStack.EMPTY) tag.put(
                ITEM_KEY,
                itemHandler.getStackInSlot(0).save(CompoundTag())
            )
            return tag
        }

        override fun deserializeNBT(nbt: CompoundTag) {
            super.deserializeNBT(nbt)
            if (nbt.contains(ITEM_KEY)) setStack(ItemStack.of(nbt.getCompound(ITEM_KEY)))
        }

        override fun canRemove(): Boolean {
            return super.canRemove() && itemHandler.getStackInSlot(0).isEmpty()
        }

        companion object {
            private const val ITEM_KEY: String = "item"
            lateinit var ENDER_ITEM: EntryTypes<VirtualBox>

            fun init() {
                ENDER_ITEM = EntryTypes.addEntryType("ender_item".nn(), ::VirtualBox)
            }
        }

        override fun hashCode(): Int {
            return itemHandler.hashCode()
        }
    }
}