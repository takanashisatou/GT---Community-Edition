package dev.arbor.gtnn.api.registry

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability
import com.gregtechceu.gtceu.api.recipe.GTRecipeType
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fml.event.IModBusEvent
import org.jetbrains.annotations.ApiStatus
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Predicate

abstract class GTRecipeEvent @ApiStatus.Internal protected constructor() : Event() {
    class AddRecipe @ApiStatus.Internal constructor(private val provider: Consumer<FinishedRecipe>) :
        GTRecipeEvent() {
        fun register(registry: Consumer<Consumer<FinishedRecipe>>) {
            registry.accept(provider)
        }
    }

    class RemoveRecipe @ApiStatus.Internal constructor(
        private val map: MutableMap<GTRecipeType, MutableList<Predicate<GTRecipeBuilder>>>
    ) : GTRecipeEvent(), IModBusEvent {
        fun removeRecipesByInputs(
            type: GTRecipeType, itemInputs: Array<out ItemStack>, fluidInputs: Array<out FluidStack>
        ) {
            this.map.computeIfAbsent(type) { t: GTRecipeType -> ArrayList() }.add(
                Predicate { b: GTRecipeBuilder ->
                    val listItem = b.input[ItemRecipeCapability.CAP]
                    if (listItem != null) {
                        val list = b.input[ItemRecipeCapability.CAP]!!.stream()
                            .map { it.getContent() }
                            .map { ItemRecipeCapability.CAP.of(it) }
                            .toList()

                        val allItemsMatch = checkItemsMatch(itemInputs, list)
                        if (!allItemsMatch) return@Predicate false
                    }

                    val listFluid = b.input[ItemRecipeCapability.CAP]
                    if (listFluid != null) {
                        val list = b.input[FluidRecipeCapability.CAP]!!.stream()
                            .map { it.getContent() }
                            .map { FluidRecipeCapability.CAP.of(it) }
                            .toList()

                        return@Predicate checkFluidsMatch(fluidInputs, list)
                    }
                    true
                })
        }

        fun removeRecipesByInputs(type: GTRecipeType, vararg itemInputs: ItemStack) {
            removeRecipesByInputs(type, itemInputs, arrayOf())
        }

        fun removeRecipesByInputs(type: GTRecipeType, vararg fluidInputs: FluidStack) {
            removeRecipesByInputs(type, arrayOf(), fluidInputs)
        }

        fun removeRecipesByOutputs(
            type: GTRecipeType, itemOutputs: Array<out ItemStack>, fluidOutputs: Array<out FluidStack>
        ) {
            this.map.computeIfAbsent(type) { arrayListOf() }.add(
                Predicate { b: GTRecipeBuilder ->
                    val listItem = b.output[ItemRecipeCapability.CAP]
                    if (listItem != null) {
                        val list = listItem.stream()
                            .map { it.getContent() }
                            .map { ItemRecipeCapability.CAP.of(it) }
                            .toList()

                        val allItemsMatch = checkItemsMatch(itemOutputs, list)

                        if (!allItemsMatch) return@Predicate false
                    }

                    val listFluid = b.output[FluidRecipeCapability.CAP]

                    if (listFluid != null) {
                        val list = listFluid.stream()
                            .map { it.getContent() }
                            .map { FluidRecipeCapability.CAP.of(it) }
                            .toList()

                        return@Predicate checkFluidsMatch(fluidOutputs, list)
                    }
                    true
                })
        }

        fun removeRecipesByOutputs(type: GTRecipeType, vararg itemOutputs: ItemStack) {
            removeRecipesByOutputs(type, itemOutputs, arrayOf())
        }

        fun removeRecipesByOutputs(type: GTRecipeType, vararg fluidOutputs: FluidStack) {
            removeRecipesByOutputs(type, arrayOf(), fluidOutputs)
        }

        fun removeAllRecipes(type: GTRecipeType) {
            this.map.computeIfAbsent(type) { arrayListOf() }.add(Predicate { true })
        }

        private fun checkItemsMatch(itemStacks: Array<out ItemStack>, list: MutableList<Ingredient>): Boolean {
            return itemStacks.all { list.any { item -> item.test(it) } }
        }

        private fun checkFluidsMatch(fluidStacks: Array<out FluidStack>, list: MutableList<FluidIngredient>): Boolean {
            return fluidStacks.all { list.any { fluid -> fluid.test(it) } }
        }
    }

    class RegisterHandler @ApiStatus.Internal constructor(
        private val map: MutableMap<GTRecipeType, MutableList<BiConsumer<GTRecipeBuilder, Consumer<FinishedRecipe>>>>
    ) : GTRecipeEvent(), IModBusEvent {
        fun register(type: GTRecipeType, handler: BiConsumer<GTRecipeBuilder, Consumer<FinishedRecipe>>
        ) {
            this.map.computeIfAbsent(type) { arrayListOf() }.add(handler)
        }
    }
}