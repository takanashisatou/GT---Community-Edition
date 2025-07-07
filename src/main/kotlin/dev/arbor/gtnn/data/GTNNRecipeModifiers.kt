package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.RecipeHelper
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier
import dev.arbor.gtnn.api.machine.feature.IGTPPMachine
import kotlin.math.max
import kotlin.math.min

object GTNNRecipeModifiers {

    val GTPP_MODIFIER: RecipeModifier = RecipeModifier { machine: MetaMachine?, recipe: GTRecipe? ->
        if (machine !is IMultiController || !machine.isFormed) return@RecipeModifier ModifierFunction.IDENTITY
        val machineData = machine as? IGTPPMachine
        val speedMultiplier = 100.0 / (100.0 + (machineData?.speedMultiplier?.toDouble() ?: 0.0))
        val energyConsumeMultiplier = 100 / (100.0 + (machineData?.energyConsumeMultiplier?.toDouble() ?: 0.0))
        var parallels = machineData?.maxParallel ?: 1
        if (machine is WorkableElectricMultiblockMachine) {
            parallels = min(parallels,
                max(machine.maxVoltage / RecipeHelper.getInputEUt(recipe), 1).toInt())
        }
        if (parallels == 1 && speedMultiplier == 1.0 && energyConsumeMultiplier == 1.0)
            return@RecipeModifier ModifierFunction.IDENTITY
        return@RecipeModifier ModifierFunction.builder()
            .modifyAllContents(ContentModifier.multiplier(parallels.toDouble()))
            .eutMultiplier(parallels * energyConsumeMultiplier)
            .durationMultiplier(speedMultiplier)
            .parallels(parallels)
            .build()
    }
}
