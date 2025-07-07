package dev.arbor.gtnn.api.machine.multiblock.part

import com.gregtechceu.gtceu.api.item.tool.GTToolType
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine
import net.minecraft.MethodsReturnNonnullByDefault
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

import javax.annotation.ParametersAreNonnullByDefault

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
class HighSpeedPipeBlock(holder: IMachineBlockEntity) : MultiblockPartMachine(holder) {
    override fun shouldOpenUI(player: Player, hand: InteractionHand, hit: BlockHitResult): Boolean {
        return false
    }

    override fun scheduleRenderUpdate() {
    }

    override fun shouldRenderGrid(
        player: Player,
        pos: BlockPos,
        state: BlockState,
        held: ItemStack,
        toolTypes: MutableSet<GTToolType>
    ): Boolean {
        return false
    }
}
