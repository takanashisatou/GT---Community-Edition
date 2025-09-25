package dev.arbor.gtnn.api.machine.feature

import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine
import com.gregtechceu.gtceu.api.pattern.MultiblockState
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import dev.arbor.gtnn.api.machine.trait.MultiblockTrait

interface IParallelMachine : IMachineFeature {
    var maxParallel: Int
    var parallelNumber: Int

    class ParallelStats(
        machine: MetaMachine,
        private val parallelCalculator: (IParallelMachine) -> Int
    ) : MultiblockTrait(machine), IParallelMachine {
        @Persisted
        override var parallelNumber: Int = 0
            get() = maxOf(1, field)
            set(value) {
                val multiblock = multiblock ?: return
                if (!multiblock.isFormed) return

                if (multiblock is WorkableMultiblockMachine) {
                    field = value.coerceIn(1, maxParallel)
                    multiblock.recipeLogic.markLastRecipeDirty()
                }
            }

        override var maxParallel: Int = 0
            get() = (multiblock as? IParallelMachine)?.let(parallelCalculator) ?: 1

        override fun onStructureFormed(state: MultiblockState) {
            if (parallelNumber == 0) parallelNumber = maxParallel
        }

        override fun onStructureInvalid() {
            parallelNumber = 0
        }

        override fun getFieldHolder(): ManagedFieldHolder {
            return MANAGED_FIELD_HOLDER
        }

        companion object {
            val MANAGED_FIELD_HOLDER = ManagedFieldHolder(
                ParallelStats::class.java,
                MultiblockTrait.MANAGED_FIELD_HOLDER
            )
        }
    }
}