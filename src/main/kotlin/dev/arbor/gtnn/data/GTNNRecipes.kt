package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.common.data.GTRecipeTypes
import dev.arbor.gtnn.GTNN.getServerConfig
import dev.arbor.gtnn.GTNNIntegration.isAdAstraLoaded
import dev.arbor.gtnn.common.recipe.NeutronActivatorCondition
import dev.arbor.gtnn.common.recipe.PlantCasingCondition
import dev.arbor.gtnn.api.registry.GTRecipeEvent
import dev.arbor.gtnn.data.recipes.*
import dev.arbor.gtnn.data.recipes.handler.CircuitAssemblyLineMachineRecipeHandler
import dev.arbor.gtnn.data.recipes.handler.ComponentAssemblyLineRecipeHandler
import dev.arbor.gtnn.data.recipes.handler.WrapItemRecipeHandler
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

object GTNNRecipes {
    fun init(provider: Consumer<FinishedRecipe>) {
        CircuitAssemblyLineMachineRecipeHandler.finish(provider)
        ComponentAssemblyLineRecipeHandler.finish(provider)
        WrapItemRecipeHandler.init(provider)
        DefaultRecipes.init(provider)
        NaquadahReactor.init(provider)
        RocketFuel.init(provider)
        BrineChain.init(provider)
        if (getServerConfig().enableHarderPlatinumLine) PlatinumLine.init(provider)
        if (getServerConfig().enableHarderNaquadahLine) NaquadahLine.init(provider)
        if (isAdAstraLoaded()) AdAstraRecipes.init(provider)
    }

    fun dur(seconds: Double): Int {
        return (seconds * 20.0).toInt()
    }

    @JvmStatic
    fun setNA(max: Int, min: Int): NeutronActivatorCondition {
        return NeutronActivatorCondition(max, min)
    }

    @JvmStatic
    fun setPlantCasing(tier: Int): PlantCasingCondition {
        return PlantCasingCondition(tier)
    }

    fun register(event: GTRecipeEvent.RegisterHandler) {
        event.register(GTRecipeTypes.CIRCUIT_ASSEMBLER_RECIPES) { builder, _ ->
            CircuitAssemblyLineMachineRecipeHandler.init(builder)
        }
        event.register(GTRecipeTypes.ASSEMBLY_LINE_RECIPES) { builder, _ ->
            ComponentAssemblyLineRecipeHandler.init(builder)
        }
        event.register(GTRecipeTypes.ASSEMBLER_RECIPES) { builder, _ ->
            ComponentAssemblyLineRecipeHandler.init(builder)
        }
    }
}
