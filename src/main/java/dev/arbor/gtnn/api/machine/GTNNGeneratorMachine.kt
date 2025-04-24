package dev.arbor.gtnn.api.machine

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.SimpleGeneratorMachine
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction
import it.unimi.dsi.fastutil.ints.Int2IntFunction

class GTNNGeneratorMachine(
    holder: IMachineBlockEntity, tier: Int, name: String, tankScalingFunction: Int2IntFunction, vararg args: Any
) : SimpleGeneratorMachine(holder, tier, tankScalingFunction, args) {
    private val efficiency = getEfficiency(tier, name)

    companion object {
        fun getEfficiency(tier: Int, name: String): Int {
            return when (name) {
                "naquadah_reactor" -> if (tier == 4) 80 else (tier - 5) * 50 + 100
                "rocket_engine" -> 80 - (tier - 4) * 10
                else -> tier * 20 + 100
            }
        }

        fun nonParallel(machine: MetaMachine, recipe: GTRecipe): ModifierFunction {
            if (machine is GTNNGeneratorMachine) {
                return ModifierFunction {
                    val dur = recipe.duration
                    val recipeModified = recipe.copy()
                    recipeModified.duration = dur * machine.efficiency / 100
                    return@ModifierFunction recipeModified
                }
            }
            return ModifierFunction.NULL
        }

        fun parallel(machine: MetaMachine, recipe: GTRecipe): ModifierFunction {
            val recipeModifier = nonParallel(machine, recipe)
            return recipeModifier.apply(recipe)?.let { recipeModifier(machine, it) } ?: ModifierFunction.NULL
        }
    }

}
