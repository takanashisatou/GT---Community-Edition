package dev.arbor.gtnn.data.recipes.handler

import com.gregtechceu.gtceu.api.GTValues.*
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper
import com.gregtechceu.gtceu.api.data.chemical.material.MarkerMaterials
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.*
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys
import com.gregtechceu.gtceu.api.recipe.RecipeHelper
import com.gregtechceu.gtceu.api.recipe.ResearchRecipeBuilder
import com.gregtechceu.gtceu.api.recipe.ingredient.IntCircuitIngredient
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient
import com.gregtechceu.gtceu.common.data.GTItems
import com.gregtechceu.gtceu.common.data.GTMachines
import com.gregtechceu.gtceu.common.data.GTMaterials.*
import com.gregtechceu.gtceu.common.data.GTRecipeTypes.*
import com.gregtechceu.gtceu.core.mixins.IngredientAccessor
import com.gregtechceu.gtceu.core.mixins.TagValueAccessor
import com.gregtechceu.gtceu.data.recipe.CustomTags
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder
import com.tterrag.registrate.util.entry.ItemEntry
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.data.GTNNMaterials.Cerrobase140
import dev.arbor.gtnn.data.GTNNRecipeTypes.CIRCUIT_ASSEMBLY_LINE_RECIPES
import dev.arbor.gtnn.data.GTPPMachines
import dev.arbor.gtnn.data.item.GTNNCircuitItems
import dev.arbor.gtnn.data.item.GTNNWrapItem
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import java.util.function.Consumer

object CircuitAssemblyLineMachineRecipeHandler {
    private val recipeBuilders = mutableListOf<GTRecipeBuilder>()

    fun init(recipeBuilder: GTRecipeBuilder) {
        val outputItems = RecipeHelper.getOutputItems(recipeBuilder)
        if (outputItems.isEmpty()) return

        val item = outputItems[0].item
        GTNNCircuitItems.ETCHED_CIRCUIT_MAP.keys.firstOrNull { it.`is`(item) }?.let { entry ->
            handleComponent(recipeBuilder, entry, outputItems[0].count)
        }
    }

    fun finish(provider: Consumer<FinishedRecipe>) {
        recipeBuilders.forEach { it.save(provider) }

        ASSEMBLY_LINE_RECIPES
            .recipeBuilder(GTNN.id("circuit_assembly_line"))
            .inputItems(GTMachines.CIRCUIT_ASSEMBLER[LuV])
            .inputItems(GTItems.ROBOT_ARM_LuV, 4)
            .inputItems(GTItems.ELECTRIC_MOTOR_LuV, 4)
            .inputItems(GTItems.FIELD_GENERATOR_LuV)
            .inputItems(GTItems.EMITTER_LuV)
            .inputItems(GTItems.SENSOR_LuV)
            .inputItems(plate, Rhodium, 8)
            .inputFluids(Cerrobase140.getFluid(FluidStorageKeys.MOLTEN, 1440))
            .outputItems(GTPPMachines.CIRCUIT_ASSEMBLY_LINE)
            .scannerResearch { b: ResearchRecipeBuilder.ScannerRecipeBuilder ->
                b.researchStack(GTMachines.CIRCUIT_ASSEMBLER[LuV].asStack())
                    .duration(1200 * 20)
                    .EUt(VA[LV])
            }
            .duration(1200 * 20)
            .EUt(VA[LuV].toLong())
            .save(provider)

        GTNNCircuitItems.ETCHED_CIRCUIT_MAP.forEach { (entry, etchedCircuitUnknow) ->
            val etchedCircuit = etchedCircuitUnknow.get()
            val etchedCircuitBoard = GTNNCircuitItems.ETCHED_CIRCUIT_BOARD_MAP[entry]!!.get()
            val etchedCircuitBase = GTNNCircuitItems.ETCHED_CIRCUIT_BASE_MAP[entry]!!.get()
            val va = VA.map { it.toLong() }

            when {
                entry.id.path.contains("micro") -> {
                    LASER_ENGRAVER_RECIPES.recipeBuilder(etchedCircuit.descriptionId)
                        .inputItems(entry)
                        .notConsumable(lens, MarkerMaterials.Color.Yellow)
                        .outputItems(etchedCircuit)
                        .EUt(va[MV])
                        .duration(30 * 20)
                        .save(provider)

                    FORMING_PRESS_RECIPES.recipeBuilder(etchedCircuitBase.descriptionId)
                        .inputItems(etchedCircuitBoard)
                        .inputItems(foil, WroughtIron, 4)
                        .outputItems(etchedCircuitBase)
                        .EUt(va[LV])
                        .duration(15 * 20)
                        .save(provider)
                }
                entry.id.path.contains("nano") -> {
                    LASER_ENGRAVER_RECIPES.recipeBuilder(etchedCircuit.descriptionId)
                        .inputItems(entry)
                        .notConsumable(lens, MarkerMaterials.Color.Cyan)
                        .outputItems(etchedCircuit)
                        .EUt(va[HV])
                        .duration(30 * 20)
                        .save(provider)

                    FORMING_PRESS_RECIPES.recipeBuilder(etchedCircuitBase.descriptionId)
                        .inputItems(etchedCircuitBoard)
                        .inputItems(foil, Silver, 4)
                        .outputItems(etchedCircuitBase)
                        .EUt(va[LV])
                        .duration(15 * 20)
                        .save(provider)
                }
                entry.id.path.contains("quantum") -> {
                    LASER_ENGRAVER_RECIPES.recipeBuilder(etchedCircuit.descriptionId)
                        .inputItems(entry)
                        .notConsumable(lens, MarkerMaterials.Color.Lime)
                        .outputItems(etchedCircuit)
                        .EUt(va[EV])
                        .duration(30 * 20)
                        .save(provider)

                    FORMING_PRESS_RECIPES.recipeBuilder(etchedCircuitBase.descriptionId)
                        .inputItems(etchedCircuitBoard)
                        .inputItems(foil, Electrum, 4)
                        .outputItems(etchedCircuitBase)
                        .EUt(va[LV])
                        .duration(15 * 20)
                        .save(provider)
                }
                entry.id.path.contains("crystal") -> {
                    LASER_ENGRAVER_RECIPES.recipeBuilder(etchedCircuit.descriptionId)
                        .inputItems(entry)
                        .notConsumable(lens, MarkerMaterials.Color.Blue)
                        .outputItems(etchedCircuit)
                        .EUt(va[IV])
                        .duration(30 * 20)
                        .save(provider)

                    FORMING_PRESS_RECIPES.recipeBuilder(etchedCircuitBase.descriptionId)
                        .inputItems(etchedCircuitBoard)
                        .inputItems(foil, Osmium, 4)
                        .outputItems(etchedCircuitBase)
                        .EUt(va[LV])
                        .duration(15 * 20)
                        .save(provider)
                }
                entry.id.path.contains("wetware") -> {
                    LASER_ENGRAVER_RECIPES.recipeBuilder(etchedCircuit.descriptionId)
                        .inputItems(entry)
                        .notConsumable(lens, MarkerMaterials.Color.Red)
                        .outputItems(etchedCircuit)
                        .EUt(va[LuV])
                        .duration(30 * 20)
                        .save(provider)

                    FORMING_PRESS_RECIPES.recipeBuilder(etchedCircuitBase.descriptionId)
                        .inputItems(etchedCircuitBoard)
                        .inputItems(foil, HSSG, 4)
                        .outputItems(etchedCircuitBase)
                        .EUt(va[LV])
                        .duration(15 * 20)
                        .save(provider)
                }
            }

            ASSEMBLER_RECIPES.recipeBuilder(etchedCircuitBoard.descriptionId)
                .inputItems(etchedCircuit)
                .inputItems(GTItems.PLASTIC_BOARD)
                .inputFluids(Glue.getFluid(100))
                .outputItems(etchedCircuitBoard)
                .EUt(va[LV])
                .duration(15 * 20)
                .save(provider)
        }
    }

    private fun handleComponent(
        builder: GTRecipeBuilder,
        outputItem: ItemEntry<*>,
        outputCount: Int
    ) {
        if (!builder.id.path.contains("soldering_alloy")) return

        val inputItems = RecipeHelper.getInputContents(builder, ItemRecipeCapability.CAP)
        val inputFluids = RecipeHelper.getInputContents(builder, FluidRecipeCapability.CAP)

        val newBuilder = CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder(GTNN.id(builder.id.path))

        inputItems.forEach { input ->
            when (input) {
                is IntCircuitIngredient -> {} // 忽略电路
                is SizedIngredient -> {
                    val count = input.amount
                    (input.inner as IngredientAccessor).values.forEach { value ->
                        when (value) {
                            is Ingredient.ItemValue -> {
                                value.items.forEach { stack ->
                                    val entry = ChemicalHelper.getMaterialEntry(stack.item)
                                    if (entry != null && entry !== MaterialEntry.NULL_ENTRY) {
                                        handleMaterialEntry(newBuilder, entry, count)
                                    } else {
                                        GTNNWrapItem.WRAP_ITEM_MAP.entries
                                            .firstOrNull { it.key.asItem() == stack.item }
                                            ?.value
                                            ?.let { wrapItem ->
                                                newBuilder.inputItems(ItemStack(wrapItem, count))
                                            } ?: run {
                                            newBuilder.inputItems(stack.copyWithCount(minOf(64, count * 16)))
                                        }
                                    }
                                }
                            }
                            is Ingredient.TagValue -> {
                                val tag = (value as TagValueAccessor).tag
                                val entry = ChemicalHelper.getMaterialEntry(tag)
                                if (entry != null && entry !== MaterialEntry.NULL_ENTRY) {
                                    handleMaterialEntry(newBuilder, entry, count)
                                } else {
                                    when (tag) {
                                        CustomTags.TRANSISTORS -> newBuilder.inputItems(GTNNWrapItem.WRAP_SMD_TRANSISTOR, count)
                                        CustomTags.RESISTORS -> newBuilder.inputItems(GTNNWrapItem.WRAP_SMD_RESISTOR, count)
                                        CustomTags.CAPACITORS -> newBuilder.inputItems(GTNNWrapItem.WRAP_SMD_CAPACITOR, count)
                                        CustomTags.DIODES -> newBuilder.inputItems(GTNNWrapItem.WRAP_SMD_DIODE, count)
                                        CustomTags.INDUCTORS -> newBuilder.inputItems(GTNNWrapItem.WRAP_SMD_INDUCTOR, count)
                                        else -> newBuilder.inputItems(tag, minOf(64, count * 16))
                                    }
                                }
                            }
                            else -> {
                                value.items.forEach { stack ->
                                    newBuilder.inputItems(stack.copyWithCount(minOf(64, count * 16)))
                                }
                            }
                        }
                    }
                }
                else -> {
                    input.items.forEach { stack ->
                        newBuilder.inputItems(stack.copyWithCount(minOf(64, stack.count * 16)))
                    }
                }
            }
        }

        inputFluids.forEach(newBuilder::inputFluids)

        newBuilder.outputItems(outputItem, outputCount * 16)
            .EUt(builder.EUt().totalEU)
            .duration(builder.duration * 3)
            .scannerResearch { b ->
                b.researchStack(GTNNCircuitItems.ETCHED_CIRCUIT_BASE_MAP[outputItem]!!.asStack())
                    .EUt(VA[IV])
                    .duration(30 * 20)
            }

        recipeBuilders.add(newBuilder)
    }

    private fun handleMaterialEntry(
        newBuilder: GTRecipeBuilder,
        entry: MaterialEntry,
        count: Int
    ) {
        val material = entry.material
        val prefix = entry.tagPrefix

        when (prefix) {
            wireFine -> newBuilder.inputItems(wireGtQuadruple, material, count)
            wireGtSingle -> newBuilder.inputItems(wireGtHex, material, count)
            else -> newBuilder.inputItems(prefix, material, minOf(64, count * 16))
        }
    }
}