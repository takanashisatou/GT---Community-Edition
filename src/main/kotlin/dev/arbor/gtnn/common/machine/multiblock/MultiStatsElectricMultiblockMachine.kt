package dev.arbor.gtnn.common.machine.multiblock

import com.gregtechceu.gtceu.api.gui.fancy.ConfiguratorPanel
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import dev.arbor.gtnn.api.machine.feature.IEnhanceFancyUIMachine
import dev.arbor.gtnn.api.machine.trait.MultiblockTrait
import net.minecraft.network.chat.Component

open class MultiStatsElectricMultiblockMachine(holder: IMachineBlockEntity, vararg args: Any) :
    WorkableElectricMultiblockMachine(holder, *args), IEnhanceFancyUIMachine {
    protected var multiblockStats = ArrayList<MultiblockTrait>()

    override fun onStructureFormed() {
        super.onStructureFormed()
        multiblockStats.forEach { it.onStructureFormed(multiblockState) }
    }

    override fun onStructureInvalid() {
        super.onStructureInvalid()
        multiblockStats.forEach(MultiblockTrait::onStructureInvalid)
    }

    override fun addDisplayText(textList: MutableList<Component>) {
        super.addDisplayText(textList)
        multiblockStats.forEach { it.addDisplayText(textList) }
    }

    override fun attachConfigurators(configuratorPanel: ConfiguratorPanel) {
        super<IEnhanceFancyUIMachine>.attachConfigurators(configuratorPanel)
    }

    fun addStats(state: MultiblockTrait) {
        multiblockStats.add(state)
    }

    override fun getFieldHolder(): ManagedFieldHolder {
        return MANAGED_FIELD_HOLDER
    }

    companion object {
        @JvmStatic
        protected val MANAGED_FIELD_HOLDER: ManagedFieldHolder = ManagedFieldHolder(
            MultiStatsElectricMultiblockMachine::class.java,
            WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER
        )
    }
}