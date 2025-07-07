package dev.arbor.gtnn.init

import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderManager
import dev.arbor.gtnn.GTNNRegistries
import dev.arbor.gtnn.client.renderer.item.GTNNItemRenderers
import dev.arbor.gtnn.client.renderer.machine.GTPPMachineRender
import dev.arbor.gtnn.common.machine.EnderItemLinkCover
import dev.arbor.gtnn.data.GTNNDataGen
import dev.arbor.gtnn.extension.StringExtension.nn

object CommonProxy {
    fun init() {
        DynamicRenderManager.register("gtpp_machine".nn(), GTPPMachineRender.TYPE)
        GTNNDataGen.init()
        GTNNItemRenderers.init()
        GTNNRegistries.REGISTRATE.registerRegistrate()
        EnderItemLinkCover.VirtualBox.init()
    }
}
