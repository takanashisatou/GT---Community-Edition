package dev.arbor.gtnn.api.machine.feature

import com.gregtechceu.gtceu.api.gui.fancy.ConfiguratorPanel
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine
import dev.arbor.gtnn.api.gui.ParallelFancyConfigurator

interface IEnhanceFancyUIMachine : IFancyUIMachine {
    override fun attachConfigurators(configuratorPanel: ConfiguratorPanel) {
        super.attachConfigurators(configuratorPanel)
        if (this is IParallelMachine) {
            configuratorPanel.attachConfigurators(ParallelFancyConfigurator(this))
        }
    }
}