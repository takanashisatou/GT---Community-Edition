package dev.arbor.gtnn.common.block

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.utils.GTUtil
import dev.arbor.gtnn.api.block.ITierType
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.BlockGetter

class SimpleTierBlock(
    properties: Properties,
    public val data: ITierType,
) : CasingBlock(properties) {

    private val useNumberTier = true

    override fun appendHoverText(
        stack: ItemStack,
        level: BlockGetter?,
        tooltip: MutableList<Component>,
        flag: TooltipFlag
    ) {
        super.appendHoverText(stack, level, tooltip, flag)

        if (GTUtil.isShiftDown()) {
            if (useNumberTier) {
                tooltip.add(Component.translatable("gtnn.universal.tier", data.tier))
            } else {
                tooltip.add(
                    Component.translatable("gtnn.universal.tier", GTValues.VNF[data.tier])
                )
            }
        } else {
            tooltip.add(Component.translatable("gtnn.shift_info"))
        }
    }
}