package dev.arbor.gtnn.api.item.behaviors

import com.google.common.base.Joiner
import com.gregtechceu.gtceu.api.gui.GuiTextures
import com.gregtechceu.gtceu.api.item.ComponentItem
import com.gregtechceu.gtceu.api.item.component.IItemUIFactory
import com.lowdragmc.lowdraglib.LDLib
import com.lowdragmc.lowdraglib.gui.factory.HeldItemUIFactory
import com.lowdragmc.lowdraglib.gui.modular.ModularUI
import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup
import com.lowdragmc.lowdraglib.gui.texture.TextTexture
import com.lowdragmc.lowdraglib.gui.widget.ButtonWidget
import com.lowdragmc.lowdraglib.gui.widget.ImageWidget
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup
import com.lowdragmc.lowdraglib.utils.LocalizationUtils
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.api.pattern.DebugBlockPattern
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

object StructureWriteBehavior : IItemUIFactory {

    override fun createUI(
        playerInventoryHolder: HeldItemUIFactory.HeldItemHolder,
        entityPlayer: Player
    ): ModularUI {
        val container = WidgetGroup(8, 8, 160, 54)
            .addWidget(ImageWidget(4, 4, 152, 46, GuiTextures.DISPLAY))
            .addWidget(
                LabelWidget(7, 7) {
                    var x = 0
                    var y = 0
                    var z = 0
                    getPos(playerInventoryHolder.held)?.let { blockPos ->
                        x = 1 + blockPos[1].x - blockPos[0].x
                        y = 1 + blockPos[1].y - blockPos[0].y
                        z = 1 + blockPos[1].z - blockPos[0].z
                    }
                    LocalizationUtils.format(
                        "item.gtnn.debug.structure_writer.structural_scale",
                        x, y, z
                    )
                }.setTextColor(0xFAF9F6)
            )
            .addWidget(
                LabelWidget(7, 20) {
                    val direction = getDir(playerInventoryHolder.held)
                    val dirs = DebugBlockPattern.getDir(direction)
                    LocalizationUtils.format(
                        "item.gtnn.debug.structure_writer.export_order",
                        dirs[0].name,
                        dirs[1].name,
                        dirs[2].name
                    )
                }.setTextColor(0xFAF9F6)
            )
            .setBackground(GuiTextures.BACKGROUND_INVERSE)

        return ModularUI(176, 120, playerInventoryHolder, entityPlayer)
            .background(GuiTextures.BACKGROUND)
            .widget(container)
            .widget(
                ButtonWidget(
                    9, 68, 77, 20,
                    GuiTextureGroup(
                        GuiTextures.BUTTON,
                        TextTexture("item.gtnn.debug.structure_writer.export_to_log")
                    )
                ) { exportLog(playerInventoryHolder) }
            )
            .widget(
                ButtonWidget(
                    90, 68, 77, 20,
                    GuiTextureGroup(
                        GuiTextures.BUTTON,
                        TextTexture("item.gtnn.debug.structure_writer.export_to_msg")
                    )
                ) { exportMsg(playerInventoryHolder) }
            )
            .widget(
                ButtonWidget(
                    9, 91, 77, 20,
                    GuiTextureGroup(
                        GuiTextures.BUTTON,
                        TextTexture("item.gtnn.debug.structure_writer.rotate_along_x_axis")
                    )
                ) { changeDirX(playerInventoryHolder) }
            )
            .widget(
                ButtonWidget(
                    90, 91, 77, 20,
                    GuiTextureGroup(
                        GuiTextures.BUTTON,
                        TextTexture("item.gtnn.debug.structure_writer.rotate_along_y_axis")
                    )
                ) { changeDirY(playerInventoryHolder) }
            )
    }

    private fun export(playerInventoryHolder: HeldItemUIFactory.HeldItemHolder): String? {
        val pos = getPos(playerInventoryHolder.held) ?: return null
        if (playerInventoryHolder.player !is ServerPlayer) return null

        val direction = getDir(playerInventoryHolder.held)
        val builder = StringBuilder()
        val blockPattern = DebugBlockPattern(
            playerInventoryHolder.player.level(), pos[0].x, pos[0].y, pos[0].z, pos[1].x, pos[1].y, pos[1].z
        )
        val dirs = DebugBlockPattern.getDir(direction)
        blockPattern.changeDir(dirs[0], dirs[1], dirs[2])
        for (strings in blockPattern.pattern) {
            builder.append(".aisle(\"%s\")\n".format(Joiner.on("\", \"").join(strings)))
        }
        return builder.toString()
    }

    private fun exportMsg(playerInventoryHolder: HeldItemUIFactory.HeldItemHolder) {
        val message = export(playerInventoryHolder)
        message ?: return
        if (!LDLib.isRemote()) {
            playerInventoryHolder.player.sendSystemMessage(Component.literal(message))
        }
    }

    private fun exportLog(playerInventoryHolder: HeldItemUIFactory.HeldItemHolder) {
        val message = export(playerInventoryHolder)
        message ?: return
        GTNN.LOGGER.info(message)
    }

    private fun changeDirX(playerInventoryHolder: HeldItemUIFactory.HeldItemHolder) {
        val itemStack = playerInventoryHolder.held
        if (getPos(itemStack) != null && playerInventoryHolder.player is ServerPlayer) {
            setDir(itemStack, getDir(itemStack).getClockWise(Direction.Axis.X))
        }
    }

    private fun changeDirY(playerInventoryHolder: HeldItemUIFactory.HeldItemHolder) {
        val itemStack = playerInventoryHolder.held
        if (getPos(itemStack) != null && playerInventoryHolder.player is ServerPlayer) {
            setDir(itemStack, getDir(itemStack).getClockWise(Direction.Axis.Y))
        }
    }

    fun isItemStructureWriter(stack: ItemStack): Boolean {
        if (stack.isEmpty) return false
        return (stack.item as? ComponentItem)?.components?.contains(this) ?: false
    }

    fun getDir(stack: ItemStack): Direction {
        val tag = stack.getOrCreateTagElement("structure_writer")
        return if (!tag.contains("dir")) Direction.WEST else Direction.byName(tag.getString("dir"))!!
    }

    fun setDir(stack: ItemStack, dir: Direction) {
        val tag = stack.getOrCreateTagElement("structure_writer")
        tag.putString("dir", dir.name)
    }

    fun getPos(stack: ItemStack): Array<BlockPos>? {
        val tag = stack.getOrCreateTagElement("structure_writer")
        if (!tag.contains("minX")) return null
        return arrayOf(
            BlockPos(tag.getInt("minX"), tag.getInt("minY"), tag.getInt("minZ")),
            BlockPos(tag.getInt("maxX"), tag.getInt("maxY"), tag.getInt("maxZ"))
        )
    }

    fun addPos(stack: ItemStack, pos: BlockPos) {
        val tag = stack.getOrCreateTagElement("structure_writer")
        if (!tag.contains("minX") || tag.getInt("minX") > pos.x) tag.putInt("minX", pos.x)
        if (!tag.contains("maxX") || tag.getInt("maxX") < pos.x) tag.putInt("maxX", pos.x)
        if (!tag.contains("minY") || tag.getInt("minY") > pos.y) tag.putInt("minY", pos.y)
        if (!tag.contains("maxY") || tag.getInt("maxY") < pos.y) tag.putInt("maxY", pos.y)
        if (!tag.contains("minZ") || tag.getInt("minZ") > pos.z) tag.putInt("minZ", pos.z)
        if (!tag.contains("maxZ") || tag.getInt("maxZ") < pos.z) tag.putInt("maxZ", pos.z)
    }

    fun removePos(stack: ItemStack) {
        val tag = stack.getOrCreateTagElement("structure_writer")
        tag.remove("minX")
        tag.remove("maxX")
        tag.remove("minY")
        tag.remove("maxY")
        tag.remove("minZ")
        tag.remove("maxZ")
    }

    override fun onItemUseFirst(stack: ItemStack, context: UseOnContext): InteractionResult {
        val player = context.player ?: return InteractionResult.SUCCESS
        val itemStack = player.getItemInHand(context.hand)
        if (!player.isShiftKeyDown) {
            addPos(itemStack, context.clickedPos)
        } else {
            removePos(itemStack)
        }
        return InteractionResult.SUCCESS
    }

    override fun use(
        item: Item, level: Level, player: Player, usedHand: InteractionHand
    ): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(usedHand)
        return if (player.isShiftKeyDown) {
            removePos(stack)
            InteractionResultHolder.success(stack)
        } else {
            if (player is ServerPlayer) {
                HeldItemUIFactory.INSTANCE.openUI(player, usedHand)
            }
            InteractionResultHolder.success(stack)
        }
    }
}