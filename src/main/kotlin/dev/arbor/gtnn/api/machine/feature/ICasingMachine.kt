package dev.arbor.gtnn.api.machine.feature

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature
import com.gregtechceu.gtceu.api.pattern.MultiblockState
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import dev.arbor.gtnn.api.block.ITierType
import dev.arbor.gtnn.api.machine.multiblock.MultiStatsElectricMultiblockMachine
import dev.arbor.gtnn.api.machine.trait.MultiblockTrait

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
            val type = state.matchContext.get<Any?>(typeName)
            if (type is ITierType) {
                this.tierType = type
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