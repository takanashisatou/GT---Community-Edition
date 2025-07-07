package dev.arbor.gtnn.api.machine.feature

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart
import net.minecraft.world.level.block.state.BlockState

interface IGTPPRenderMachine: IMachineFeature {
    fun getAppearance(): BlockState
    fun getPartAppearance(part: IMultiPart): BlockState = getAppearance()
}
