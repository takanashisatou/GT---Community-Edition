package dev.arbor.gtnn.common.block

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block

open class CasingBlock(properties: Properties) : Block(properties) {
    private val tooltips = ArrayList<Component>()

    fun addTooltip(vararg components: Component) {
        this.tooltips.addAll(listOf(*components))
    }

    fun addTooltip(components: MutableList<MutableComponent>) {
        this.tooltips.addAll(components)
    }

    override fun appendHoverText(
        stack: ItemStack, level: BlockGetter?, tooltip: MutableList<Component>, flag: TooltipFlag
    ) {
        super.appendHoverText(stack, level, tooltip, flag)
        if (!this.tooltips.isEmpty()) {
            tooltip.addAll(this.tooltips)
        }
    }
}