package dev.arbor.gtnn.data.recipes.handler

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper
import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.api.recipe.RecipeHelper
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient
import com.gregtechceu.gtceu.api.recipe.ingredient.IntCircuitIngredient
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient
import com.gregtechceu.gtceu.common.data.GTMaterials
import com.gregtechceu.gtceu.common.data.GTRecipeTypes
import com.gregtechceu.gtceu.core.mixins.IngredientAccessor
import com.gregtechceu.gtceu.core.mixins.TagValueAccessor
import com.gregtechceu.gtceu.data.recipe.CraftingComponent
import com.gregtechceu.gtceu.data.recipe.GTCraftingComponents
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.common.recipe.TierCasingCondition
import dev.arbor.gtnn.data.GTNNRecipeTypes.COMPONENT_ASSEMBLY_LINE_RECIPES
import dev.arbor.gtnn.data.item.GTNNWrapItem
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Ingredient.ItemValue
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.fluids.FluidStack
import java.util.function.Consumer

object ComponentAssemblyLineRecipeHandler {
    // 需要制作的部件
    var craftComponents: Array<CraftingComponent>
    private val componentItems = Object2IntLinkedOpenHashMap<Item>()

    private val recipeBuilders = ArrayList<GTRecipeBuilder>()

    init {
        GTCraftingComponents.init()
        craftComponents = arrayOf(
            GTCraftingComponents.MOTOR,
            GTCraftingComponents.PUMP,
            GTCraftingComponents.PISTON,
            GTCraftingComponents.CONVEYOR,
            GTCraftingComponents.ROBOT_ARM,
            GTCraftingComponents.FIELD_GENERATOR,
            GTCraftingComponents.EMITTER,
            GTCraftingComponents.SENSOR
        )
        initComponentList()
    }

    fun init(recipeBuilder: GTRecipeBuilder) {
        // throw NotImplementedError()

        val outputItems = RecipeHelper.getOutputItems(recipeBuilder)
        if (outputItems.isEmpty()) return

        val item = outputItems[0]!!.item
        GTNN.LOGGER.warn("Meow Owo" + item.toString());
        if (componentItems.containsKey(item)) {
            if(item.toString() == "lv_electric_motor") {
                GTNN.LOGGER.warn("Owo " + componentItems.getInt(item));
            }
            handleComponent(item, recipeBuilder, componentItems.getInt(item))
        }
    }

    fun finish(provider: Consumer<FinishedRecipe>) {
        recipeBuilders.forEach {
            it.save(provider)
        }
    }

    private fun handleComponent(outputItem: Item, builder: GTRecipeBuilder, tier: Int) {
        val inputItems = RecipeHelper.getInputContents(builder, ItemRecipeCapability.CAP)
        val inputFluids =
            RecipeHelper.getInputContents(builder, FluidRecipeCapability.CAP)
        val inputFluidMap = Object2IntOpenHashMap<Fluid>()

        val newBuilder: GTRecipeBuilder =
            COMPONENT_ASSEMBLY_LINE_RECIPES.recipeBuilder(GTNN.id(builder.id.path))

        for (input in inputItems) {
            when (input) {
                is IntCircuitIngredient -> {
                }

                is SizedIngredient -> {
                    val count = input.getAmount()
                    val inner = input.getInner()
                    if (inner is IngredientAccessor) {
                        for (value in inner.values) {
                            if (value is ItemValue) {
                                // 普通 itemStack 物品
                                for (stack in value.items) {
                                    addItemInput(builder, newBuilder, inputFluidMap, stack, count)
                                }
                            } else if (value is Ingredient.TagValue) {
                                if (value is TagValueAccessor) {
                                    val tag = value.tag
                                    val location = tag.location()
                                    if (location.namespace == GTCEu.MOD_ID
                                        && location.path.contains("circuits")
                                    ) {
                                        // 是 GT 电路板
                                        val warpItem: ItemLike? = GTNNWrapItem.WRAP_CIRCUIT_MAP.get(tag)
                                        if (warpItem != null) {
                                            newBuilder.inputItems(ItemStack(warpItem, count * 3))
                                        }
                                    } else {
                                        val entry = ChemicalHelper.getMaterialEntry(tag)
                                        if (entry != null && entry !== MaterialEntry.NULL_ENTRY) {
                                            handleMaterialEntry(builder, newBuilder, inputFluidMap, entry, count)
                                        } else {
                                            newBuilder.inputItems(tag, count * 48)
                                        }
                                    }
                                } else {
                                    value.items.forEach { stack ->
                                        addItemInput(builder, newBuilder, inputFluidMap, stack, count)
                                    }
                                }
                            } else {
                                value.items.forEach { stack ->
                                    addItemInput(builder, newBuilder, inputFluidMap, stack, count)
                                }
                            }
                        }
                    } else {
                        inner.items.forEach { stack ->
                            addItemInput(builder, newBuilder, inputFluidMap, stack, count)
                        }
                    }
                }

                else -> {
                    for (stack in input.items) {
                        newBuilder.inputItems(stack.copyWithCount(stack.count * 48))
                    }
                }
            }
        }

        inputFluids.forEach(Consumer { fluidIngredient: FluidIngredient? ->
            val copy = fluidIngredient!!.copy()
            copy.setAmount(copy.amount * 48)
            newBuilder.inputFluids(copy)
        })
        inputFluidMap.forEach { (fluid: Fluid, amount: Int) ->
            newBuilder.inputFluids(FluidStack(fluid, amount))
        }

        newBuilder.outputItems(outputItem, 64)
        newBuilder.EUt(GTValues.VA[tier].toLong())
        newBuilder.duration(builder.duration * 48)
        newBuilder.addCondition(TierCasingCondition(tier))

        if (builder.id.path.contains("electric_motor")) {
            newBuilder.circuitMeta(1)
        } else if (builder.id.path.contains("electric_pump")) {
            newBuilder.circuitMeta(2)
        } else if (builder.id.path.contains("conveyor_module")) {
            newBuilder.circuitMeta(3)
        } else if (builder.id.path.contains("electric_piston")) {
            newBuilder.circuitMeta(4)
        } else if (builder.id.path.contains("robot_arm")) {
            newBuilder.circuitMeta(5)
        } else if (builder.id.path.contains("field_generator")) {
            newBuilder.circuitMeta(6)
        } else if (builder.id.path.contains("emitter")) {
            newBuilder.circuitMeta(7)
        } else if (builder.id.path.contains("sensor")) {
            newBuilder.circuitMeta(8)
        }

        recipeBuilders.add(newBuilder)
    }

    private fun addItemInput(
        builder: GTRecipeBuilder,
        newBuilder: GTRecipeBuilder,
        fluidMap: Object2IntOpenHashMap<Fluid>,
        stack: ItemStack,
        count: Int
    ) {
        val entry = ChemicalHelper.getMaterialEntry(stack.item)
        if (entry != null && entry !== MaterialEntry.NULL_ENTRY) {
            handleMaterialEntry(builder, newBuilder, fluidMap, entry, count)
        } else {
            newBuilder.inputItems(stack.copyWithCount(count * 48))
        }
    }

    private fun initComponentList() {
        for (i in 0..<GTValues.TIER_COUNT) {
            for (component in craftComponents) {
                val stack = component.get(i)
                if (stack is ItemStack) {
                    //Using Special Judge For Fix This Bugs
                    if(stack.item.toString().get(0) == 'l' && stack.item.toString().get(1) == 'v'){
                        if(i != 1) continue;
                    }
                    componentItems.put(stack.item, i)
                }
            }
        }
    }

    private fun handleMaterialEntry(
        builder: GTRecipeBuilder,
        newBuilder: GTRecipeBuilder,
        fluidMap: Object2IntOpenHashMap<Fluid>,
        entry: MaterialEntry,
        count: Int
    ) {
        // 是有 mat 物品
        val material = entry.material
        val prefix = entry.tagPrefix
        // 1x线缆 -> 16x线缆
        if (prefix === TagPrefix.cableGtSingle) {
            conventPrefix(newBuilder, TagPrefix.cableGtSingle, TagPrefix.cableGtHex, material, count, 16)
            return
        }
        // 2x线缆 -> 16x线缆
        if (prefix === TagPrefix.cableGtDouble) {
            conventPrefix(newBuilder, TagPrefix.cableGtDouble, TagPrefix.cableGtHex, material, count, 8)
            return
        }
        // 4x线缆 -> 16x线缆
        if (prefix === TagPrefix.cableGtQuadruple) {
            conventPrefix(newBuilder, TagPrefix.cableGtQuadruple, TagPrefix.cableGtHex, material, count, 4)
            return
        }
        // 8x线缆 -> 16x线缆
        if (prefix === TagPrefix.cableGtOctal) {
            conventPrefix(newBuilder, TagPrefix.cableGtOctal, TagPrefix.cableGtHex, material, count, 2)
            return
        }
        if (builder.recipeType === GTRecipeTypes.ASSEMBLY_LINE_RECIPES) {
            conventPrefixToFluid(fluidMap, newBuilder, prefix, material, count)
        } else {
            // 杆 -> 长杆
            if (prefix === TagPrefix.rod) {
                conventPrefix(newBuilder, TagPrefix.rod, TagPrefix.rodLong, material, count, 2)
                return
            }
            // 板 -> 致密板
            if (prefix === TagPrefix.plate) {
                conventPrefix(newBuilder, TagPrefix.plate, TagPrefix.plateDense, material, count, 9)
                return
            }
            // 小齿轮 -> 齿轮
            if (prefix === TagPrefix.gearSmall) {
                conventPrefix(newBuilder, TagPrefix.gearSmall, TagPrefix.gear, material, count, 4)
                return
            }
            if (material === GTMaterials.Rubber || material === GTMaterials.SiliconeRubber || material === GTMaterials.StyreneButadieneRubber) {
                conventPrefixToFluid(fluidMap, newBuilder, prefix, material, count)
                return
            }
            newBuilder.inputItems(prefix, material, count * 48)
        }
    }

    private fun conventPrefix(
        builder: GTRecipeBuilder,
        originalPrefix: TagPrefix,
        newPrefix: TagPrefix,
        material: Material,
        count: Int,
        multiple: Int
    ) {
        val stack = ChemicalHelper.get(newPrefix, material)
        if (stack.isEmpty) {
            builder.inputItems(originalPrefix, material, count * 48)
        } else {
            builder.inputItems(newPrefix, material, count * 48 / multiple)
        }
    }

    private fun conventPrefixToFluid(
        fluidMap: Object2IntOpenHashMap<Fluid>,
        builder: GTRecipeBuilder,
        prefix: TagPrefix,
        material: Material,
        count: Int
    ) {
        if (material.hasFluid() && prefix.materialAmount() != -1L) {
            val fluidCount = prefix.materialAmount().toInt() / 25200
            val fluid: FluidStack = material.getFluid(fluidCount * count * 48)
            fluidMap.addTo(fluid.fluid, fluid.amount)
        } else {
            builder.inputItems(prefix, material, count * 48)
        }
    }
}
