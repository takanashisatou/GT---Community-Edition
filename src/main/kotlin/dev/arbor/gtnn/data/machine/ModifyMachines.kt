package dev.arbor.gtnn.data.machine

import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifierList
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers
import com.gregtechceu.gtceu.common.data.GTRecipeTypes
import com.gregtechceu.gtceu.common.data.machines.GCYMMachines
import dev.arbor.gtnn.api.machine.feature.IGTPPMachine
import dev.arbor.gtnn.data.GTNNRecipeModifiers
import dev.arbor.gtnn.data.GTNNRecipeTypes
import net.minecraft.network.chat.Component

object ModifyMachines {
    fun init() {
        GTRecipeTypes.ASSEMBLER_RECIPES.setMaxTooltips(4)
        GTRecipeTypes.BREWING_RECIPES.setMaxTooltips(4)
        GTRecipeTypes.FLUID_HEATER_RECIPES.setMaxTooltips(4)
        modifyGTAssembly()
    }

    private fun modifyGTAssembly() {
        val lASB = GCYMMachines.LARGE_ASSEMBLER
        val lASBRecipeTypes = lASB.recipeTypes?.toMutableList()
        lASBRecipeTypes?.add(GTNNRecipeTypes.PRECISION_ASSEMBLY_RECIPES)
        lASB.recipeTypes = lASBRecipeTypes?.toTypedArray()
        lASB.tooltipBuilder = lASB.tooltipBuilder.andThen { _, components ->
            run {
                components.add(Component.translatable("gtnn.precision_assembly.tooltip.1"))
                components.add(Component.translatable("gtnn.precision_assembly.tooltip.2"))
            }
        }
        lASB.setRecipeModifier(::assemblyRecipeModifier)

        // val largeMac = GCYMMachines.LARGE_MACERATION_TOWER
        // largeMac.setRecipeModifier(::largeMacRecipeModifier)
    }

    private fun largeMacRecipeModifier(machine: MetaMachine, gtRecipe: GTRecipe): ModifierFunction {
        return if (machine is WorkableElectricMultiblockMachine && machine.parts.any { it is IGTPPMachine }) {
            RecipeModifierList(GTNNRecipeModifiers.GTPP_MODIFIER,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK).getModifier(machine, gtRecipe)
        } else {
            RecipeModifierList(GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK).getModifier(machine, gtRecipe)
        }
    }

    private fun assemblyRecipeModifier(machine: MetaMachine, gtRecipe: GTRecipe): ModifierFunction {
        return if (gtRecipe.recipeType == GTNNRecipeTypes.PRECISION_ASSEMBLY_RECIPES) {
            GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK).getModifier(machine, gtRecipe)
        } else {
            RecipeModifierList(
                GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK)
            ).getModifier(machine, gtRecipe)
        }
    }
}