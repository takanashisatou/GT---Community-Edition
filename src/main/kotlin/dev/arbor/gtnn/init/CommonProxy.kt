package dev.arbor.gtnn.init

import com.gregtechceu.gtceu.api.machine.MachineDefinition
import com.gregtechceu.gtceu.api.recipe.GTRecipeType
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType
import dev.arbor.gtnn.GTNNRegistries
import dev.arbor.gtnn.common.machine.EnderItemLinkCover
import dev.arbor.gtnn.data.GTNNDataGen
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

open class CommonProxy() {
    @Suppress("DEPRECATION")
    open fun init() {
        val modEventBus = FMLJavaModLoadingContext.get().modEventBus
        val register = Mod.EventBusSubscriber.Bus.MOD.bus().get()
        val events = MinecraftForge.EVENT_BUS
        serverGenericListener(modEventBus)
        serverEventBusSubscriberRegister(register)
        serverEventRegister(events)
        GTNNDataGen.init()
        GTNNRegistries.REGISTRATE.registerRegistrate()
        EnderItemLinkCover.VirtualBox.init()
    }

    companion object {
        @JvmStatic
        fun serverGenericListener(modBus: IEventBus) {
            modBus.addListener(GTNNRegistries::registerGeometryLoaders)
            modBus.addListener(GTNNRegistries::onCommonSetup)
            modBus.addGenericListener(MachineDefinition::class.java, GTNNRegistries::registerMachines)
            modBus.addGenericListener(GTRecipeType::class.java, GTNNRegistries::registerRecipeTypes)
            modBus.addGenericListener(RecipeConditionType::class.java, GTNNRegistries::registerRecipeConditions)
        }

        @JvmStatic
        fun serverEventBusSubscriberRegister(modBus: IEventBus) {
            modBus.addListener(GTNNRegistries::registerMaterials)
            modBus.addListener(GTNNRegistries::registerRecipeHandler)
            modBus.addListener(GTNNRegistries::registerMaterialRegistryEvent)
        }

        @JvmStatic
        fun serverEventRegister(events: IEventBus) {
            events.addListener(GTNNRegistries::fmlCommonSetupEvent)
            events.addListener(GTNNRegistries::onServerStarting)
        }
    }
}
