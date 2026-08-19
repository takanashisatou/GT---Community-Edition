package dev.arbor.gtnn.api.registry

import com.google.common.collect.ImmutableMap
import com.gregtechceu.gtceu.api.recipe.GTRecipeType
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.ModLoader
import org.jetbrains.annotations.ApiStatus
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Predicate

object GTRecipeManager {
    private val filterMap: MutableMap<GTRecipeType, MutableList<Predicate<GTRecipeBuilder>>> = hashMapOf()

    private var removed = false

    fun shouldRemove(type: GTRecipeType, builder: GTRecipeBuilder): Boolean {
        if (removed) return false

        val list = filterMap[type]
        if (list != null) {
            for (filter in list) {
                if (filter.test(builder)) return true
            }
        }

        return false
    }

    @ApiStatus.Internal
    fun onGTRecipeAddition(provider: Consumer<FinishedRecipe>) {
        removed = true
        MinecraftForge.EVENT_BUS.post(GTRecipeEvent.AddRecipe(provider))
        removed = false
    }

    @ApiStatus.Internal
    fun onGTPostInitialization() {
        val map: MutableMap<GTRecipeType, MutableList<BiConsumer<GTRecipeBuilder, Consumer<FinishedRecipe>>>> = hashMapOf()
        ModLoader.get().postEvent(GTRecipeEvent.RegisterHandler(map))
        val handlerList = ImmutableMap.copyOf(map)

        handlerList.forEach { (type, list) ->
            val save = type.recipeBuilder(type.registryName.withSuffix("_gtnn_handler_probe")).onSave
            if (save != null) list.add(save)
            type.onRecipeBuild { builder, consumer ->
                list.forEach { c ->
                    c.accept(builder, consumer)
                }
            }
        }
    }

    @ApiStatus.Internal
    fun onCommonSetup() {
        ModLoader.get().postEvent(GTRecipeEvent.RemoveRecipe(filterMap))
    }
}
