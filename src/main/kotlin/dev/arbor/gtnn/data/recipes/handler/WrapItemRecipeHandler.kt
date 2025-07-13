package dev.arbor.gtnn.data.recipes.handler

import com.gregtechceu.gtceu.common.data.GTMaterials
import com.gregtechceu.gtceu.common.data.GTRecipeTypes
import dev.arbor.gtnn.data.item.GTNNWrapItem
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

object WrapItemRecipeHandler {
    fun init(provider: Consumer<FinishedRecipe>) {
        GTNNWrapItem.WRAP_CIRCUIT_MAP.object2ObjectEntrySet().fastForEach({ entry ->
            val item = entry.value.asItem()
            val tag = entry.key
            GTRecipeTypes.ASSEMBLER_RECIPES
                .recipeBuilder("wrap_" + tag.location().getPath().replace('/', '_'))
                .inputItems(tag, 16)
                .inputFluids(GTMaterials.Polyethylene.getFluid(72))
                .circuitMeta(16)
                .outputItems(item, 1)
                .duration(600)
                .EUt(30)
                .save(provider)
        })

        GTNNWrapItem.WRAP_ITEM_MAP.object2ObjectEntrySet().fastForEach({ entry ->
            val item = entry.value.asItem()
            val wrappedItem = entry.key.asItem()
            GTRecipeTypes.ASSEMBLER_RECIPES
                .recipeBuilder("wrap_" + wrappedItem.asItem().getDescriptionId())
                .inputItems(wrappedItem, 16)
                .inputFluids(GTMaterials.Polyethylene.getFluid(72))
                .circuitMeta(16)
                .outputItems(item, 1)
                .duration(600)
                .EUt(30)
                .save(provider)
        })
    }
}