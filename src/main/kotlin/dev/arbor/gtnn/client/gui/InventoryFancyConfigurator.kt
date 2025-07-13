package dev.arbor.gtnn.client.gui

import com.gregtechceu.gtceu.api.gui.GuiTextures
import com.gregtechceu.gtceu.api.gui.fancy.IFancyConfigurator
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler
import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture
import com.lowdragmc.lowdraglib.gui.widget.Widget
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup
import com.lowdragmc.lowdraglib.jei.IngredientIO
import dev.arbor.gtnn.data.GTNNGuiTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import kotlin.math.sqrt

class InventoryFancyConfigurator(
    private val inventory: CustomItemStackHandler,
    private val title: Component
) : IFancyConfigurator {

    @get:JvmName("tooltips_kt")
    var tooltips: List<Component> = emptyList()

    lateinit var lockItemGetter: () -> ItemStack
    lateinit var slot: SlotWidget

    override fun getTooltips(): List<Component> {
        return tooltips
    }

    override fun getTitle(): Component {
        return title
    }

    override fun getIcon(): IGuiTexture {
        return GTNNGuiTextures.OVERLAY_INVENTORY_CONFIGURATOR
    }

    fun update() {
        if (!lockItemGetter.invoke().isEmpty)
            slot.setCanTakeItems(false)
    }

    override fun createConfigurator(): Widget {
        val rowSize = if (inventory.slots == 8) 4 else sqrt(inventory.slots.toDouble()).toInt()
        val colSize = if (inventory.slots == 8) 2 else rowSize

        val group = WidgetGroup(0, 0, 18 * rowSize + 16, 18 * colSize + 16)
        val container = WidgetGroup(4, 4, 18 * rowSize + 8, 18 * colSize + 8)

        var index = 0
        for (y in 0 until colSize) {
            for (x in 0 until rowSize) {
                if (index >= inventory.slots) break
                slot = SlotWithCheckWidget(inventory, index++, 4 + x * 18, 4 + y * 18) { update() }
                    .setBackgroundTexture(GuiTextures.SLOT)
                    .setIngredientIO(IngredientIO.INPUT)
                container.addWidget(slot)
            }
        }

        container.setBackground(GuiTextures.BACKGROUND_INVERSE)
        group.addWidget(container)

        return group
    }
}