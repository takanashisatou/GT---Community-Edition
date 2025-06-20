package dev.arbor.gtnn.api.machine.trait

import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController
import com.gregtechceu.gtceu.api.machine.trait.MachineTrait
import com.gregtechceu.gtceu.api.pattern.MultiblockState
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import net.minecraft.network.chat.Component

abstract class MultiblockTrait(machine: MetaMachine) : MachineTrait(machine) {
    open fun onStructureFormed(state: MultiblockState) {
    }

    open fun onStructureInvalid() {
    }

    fun addDisplayText(textList: MutableList<Component>) {
    }

    protected val multiblock: IMultiController?
        get() {
            (machine as? IMultiController)?.let { return it }
            return null
        }

    override fun getFieldHolder(): ManagedFieldHolder {
        return MANAGED_FIELD_HOLDER
    }

    companion object {
        @JvmStatic
        protected val MANAGED_FIELD_HOLDER: ManagedFieldHolder = ManagedFieldHolder(MultiblockTrait::class.java)
    }
}