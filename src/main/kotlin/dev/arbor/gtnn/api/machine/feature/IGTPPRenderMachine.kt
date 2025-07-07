package dev.arbor.gtnn.api.machine.feature

import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState

interface IGTPPRenderMachine {
    fun locationGetter(): ResourceLocation
    fun partLocationGetter(part: IMultiPart?): ResourceLocation = locationGetter()
    fun getAppearance(): BlockState
    fun getPartAppearance(part: IMultiPart): BlockState = getAppearance()
}
