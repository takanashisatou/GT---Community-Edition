package dev.arbor.gtnn

import com.gregtechceu.gtceu.api.addon.GTAddon
import com.gregtechceu.gtceu.api.addon.IGTAddon
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate
import dev.arbor.gtnn.api.registry.GTRecipeManager
import dev.arbor.gtnn.data.*
import dev.arbor.gtnn.data.block.GTNNBlocks
import dev.arbor.gtnn.data.item.GTNNItems
import dev.arbor.gtnn.data.recipes.AdAstraRecipes
import dev.arbor.gtnn.data.recipes.DefaultRecipes
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.resources.ResourceLocation
import java.util.function.Consumer

@GTAddon
class GTNNAddon : IGTAddon {
    override fun getRegistrate(): GTRegistrate {
        return GTNNRegistries.REGISTRATE
    }

    override fun initializeAddon() {
        GTNNMaterialIconTypes.init()
        GTRecipeManager.onGTPostInitialization()
        GTNNItems.init()
        GTNNBlocks.init()
        GTNN.LOGGER.info("GTNN Loaded!")
    }

    override fun addonModId(): String {
        return GTNN.MOD_ID
    }

    override fun registerTagPrefixes() {
        GTNNTagPrefix.init()
    }

    override fun registerElements() {
        GTNNElement.init()
    }

    override fun addRecipes(provider: Consumer<FinishedRecipe>) {
        GTRecipeManager.onGTRecipeAddition(provider)
        GTNNRecipes.init(provider)
    }

    override fun removeRecipes(consumer: Consumer<ResourceLocation>) {
        DefaultRecipes.Misc.removeRecipes(consumer)
        if (GTNNIntegration.isAdAstraLoaded()) AdAstraRecipes.remove(consumer)
    }

    override fun registerOreVeins() {
        GTNNOres.init()
    }

    override fun registerWorldgenLayers() {
        GTNNWorld.GTNNWorldGenLayers.init()
    }

    override fun registerCovers() {
        GTNNCovers.init()
    }
}
