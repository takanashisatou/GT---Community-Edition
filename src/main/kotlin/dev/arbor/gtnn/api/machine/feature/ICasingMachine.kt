package dev.arbor.gtnn.api.machine.feature

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature
import com.gregtechceu.gtceu.api.pattern.MultiblockState
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.api.block.ITierType
import dev.arbor.gtnn.common.machine.multiblock.MultiStatsElectricMultiblockMachine
import dev.arbor.gtnn.api.machine.trait.MultiblockTrait
import dev.arbor.gtnn.common.block.SimpleTierBlock
import net.minecraft.core.BlockPos

interface ICasingMachine : IMachineFeature {
    val tierType: ITierType?

    val casingTier: Int

    class CasingTierStats(machine: MultiStatsElectricMultiblockMachine, private val typeName: String?) :
        MultiblockTrait(machine), ICasingMachine {

        override var tierType: ITierType = ITierType.TierBlockType.ULV
        override val casingTier: Int
            get() = tierType.tier


        init {
            machine.addStats(this)
        }

        override fun onStructureFormed(state: MultiblockState) {
            //For Future
            if (true) {
                val casingPositions = state.cache.longStream()
                    .mapToObj { BlockPos.of(it) }
                    .filter { pos ->
                        val block = state.world.getBlockState(pos).block
                        block is SimpleTierBlock
                    }
                    .toList()
                val maxTier = casingPositions.mapNotNull { pos ->
                    val block = state.world.getBlockState(pos).block
                    (block as? SimpleTierBlock)?.data;
                }.maxByOrNull { it.tier } ?: ITierType.TierBlockType.ULV
                this.tierType = maxTier
            }
        }

        override fun onStructureInvalid() {
            this.tierType = ITierType.TierBlockType.ULV
        }

        override fun getFieldHolder(): ManagedFieldHolder {
            return MANAGED_FIELD_HOLDER
        }

        companion object {
            val MANAGED_FIELD_HOLDER: ManagedFieldHolder =
                ManagedFieldHolder(CasingTierStats::class.java, MultiblockTrait.MANAGED_FIELD_HOLDER)
        }
    }
}