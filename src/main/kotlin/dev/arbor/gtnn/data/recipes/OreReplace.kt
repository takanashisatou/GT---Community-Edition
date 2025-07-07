package dev.arbor.gtnn.data.recipes

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper
import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient
import com.gregtechceu.gtceu.common.data.GTMaterials.*
import dev.arbor.gtnn.GTNN.getServerConfig
import dev.arbor.gtnn.api.recipe.RecipeReplacer
import dev.arbor.gtnn.data.GTNNMaterials
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe

object OreReplace {
    private val INSTANCE = OreReplace()
    private val ores = listOf<String>()

    class OreReplace : RecipeReplacer

    @JvmStatic
    fun init(recipe: Recipe<*>) {
        if (getServerConfig().enableHarderPlatinumLine) {
            replace(recipe, Platinum, GTNNMaterials.PlatinumMetal)
            replace(recipe, Palladium, GTNNMaterials.PalladiumMetal)
        }
        if (getServerConfig().enableHarderNaquadahLine) {
            replace(recipe, Naquadah, GTNNMaterials.NaquadahOxideMixture)
            replace(recipe, NaquadahEnriched, GTNNMaterials.EnrichedNaquadahOxideMixture)
            replace(recipe, Naquadria, GTNNMaterials.NaquadriaOxideMixture)
        }
        replace(recipe, Neutronium, GTNNMaterials.NeutroniumMixture)
    }

    private fun replace(recipe: Recipe<*>, material: Material, replacedMaterial: Material) {
        for (string in ores) {
            if (recipe is GTRecipe && recipe.id.path.contains(string) && recipe.id.path.contains(material.name)) {
                INSTANCE.setGTRecipeOutputs(
                    recipe.outputs,
                    dust(material),
                    SizedIngredient.create(dust(replacedMaterial))
                )
            }
        }
    }

    private fun dust(material: Material): ItemStack {
        return ChemicalHelper.get(TagPrefix.dust, material, 1)
    }
}
