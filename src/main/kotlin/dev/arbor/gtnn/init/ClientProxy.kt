package dev.arbor.gtnn.init

import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderManager
import dev.arbor.gtnn.GTNNRegistries
import dev.arbor.gtnn.client.renderer.item.GTNNItemRenderers
import dev.arbor.gtnn.client.renderer.machine.GTPPMachineRender
import dev.arbor.gtnn.extension.StringExtension.nn
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.IEventBus

class ClientProxy(): CommonProxy() {
    override fun init() {
        super.init()
        val events = MinecraftForge.EVENT_BUS
        clientEventRegister(events)
        DynamicRenderManager.register("gtpp_machine".nn(), GTPPMachineRender.TYPE)
        GTNNItemRenderers.init()
    }

    companion object {
        @JvmStatic
        fun clientEventRegister(events: IEventBus) {
            events.addListener(GTNNRegistries::onRenderWorldLast)
        }
    }
}