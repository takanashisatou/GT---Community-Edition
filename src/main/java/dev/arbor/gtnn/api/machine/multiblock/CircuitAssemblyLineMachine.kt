package dev.arbor.gtnn.api.machine.multiblock

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.capability.recipe.IO
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability
import com.gregtechceu.gtceu.api.gui.fancy.ConfiguratorPanel
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.feature.IDropSaveMachine
import com.gregtechceu.gtceu.api.machine.feature.IMachineModifyDrops
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.RecipeHelper
import com.gregtechceu.gtceu.common.data.GTBlocks
import com.gregtechceu.gtceu.common.data.GTRecipeTypes
import com.gregtechceu.gtceu.common.machine.multiblock.part.EnergyHatchPartMachine
import com.gregtechceu.gtceu.config.ConfigHolder
import com.gregtechceu.gtceu.utils.GTUtil
import com.gregtechceu.gtceu.utils.ResearchManager
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced
import com.lowdragmc.lowdraglib.syncdata.annotation.DropSaved
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import dev.arbor.gtnn.api.gui.InventoryFancyConfigurator
import dev.arbor.gtnn.api.machine.feature.IGTPPRenderMachine
import dev.arbor.gtnn.data.GTNNRecipeTypes.CIRCUIT_ASSEMBLY_LINE_RECIPES
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Function

class CircuitAssemblyLineMachine(
    holder: IMachineBlockEntity,
    vararg args: Any
) : WorkableElectricMultiblockMachine(holder, *args), IGTPPRenderMachine, IDropSaveMachine, IMachineModifyDrops {

    @Persisted
    val templateInventory: NotifiableItemStackHandler = NotifiableItemStackHandler(this, 1, IO.IN, IO.NONE).apply {
        setFilter(ResearchManager::hasResearchTag)
    }

    @DropSaved
    @Persisted
    @DescSynced
    var lockItem: ItemStack = ItemStack.EMPTY

    override fun beforeWorking(recipe: GTRecipe?): Boolean {
        if (recipe == null) return false
        if (recipe.recipeType == CIRCUIT_ASSEMBLY_LINE_RECIPES) {
            if (!checkResearch(recipe)) return false
            if (ConfigHolder.INSTANCE.machines.orderedAssemblyLineItems) {
                val recipeInputs = recipe.inputs[ItemRecipeCapability.CAP] ?: return false
                val itemInputInventory = getCapabilitiesProxy()
                    .get(IO.IN, ItemRecipeCapability.CAP)
                    ?.filterNot { it.isProxy }?.mapNotNull { container ->
                        container.contents
                            .filterIsInstance<ItemStack>()
                            .takeIf { it.isNotEmpty() }
                    }
                    ?: emptyList()

                if (itemInputInventory.size < recipeInputs.size) return false

                for (i in recipeInputs.indices) {
                    val itemStack = itemInputInventory[i].first()
                    val recipeStack = ItemRecipeCapability.CAP.of(recipeInputs[i].content)
                    if (!recipeStack.test(itemStack)) {
                        return false
                    }
                }
            }
            if (lockItem.isEmpty) {
                lockItem = templateInventory.getStackInSlot(0)
            }
        }
        return super.beforeWorking(recipe)
    }

    override fun getRealRecipe(recipe: GTRecipe): GTRecipe? {
        if (recipe.recipeType == GTRecipeTypes.CIRCUIT_ASSEMBLER_RECIPES) {
            val recipeTier = RecipeHelper.getRecipeEUtTier(recipe)
            if (recipeTier >= GTUtil.getTierByVoltage(getEnergyContainer().inputVoltage)) {
                return null
            }
        }
        return super.getRealRecipe(recipe)
    }

    fun checkResearch(recipe: GTRecipe): Boolean {
        val researchData = ResearchManager.readResearchId(templateInventory.getStackInSlot(0)) ?: return false
        if (researchData.first != CIRCUIT_ASSEMBLY_LINE_RECIPES) return false

        val recipes = researchData.first.getDataStickEntry(researchData.second) ?: return false
        return recipes.contains(recipe)
    }

    override fun onStructureFormed() {
        definition.setPartSorter { part1, part2 ->
            multiblockPartSorter().apply(part1.self().pos)
                .compareTo(multiblockPartSorter().apply(part2.self().pos))
        }
        super.onStructureFormed()
    }

    private fun multiblockPartSorter(): Function<BlockPos, Int> {
        return RelativeDirection.RIGHT.getSorter(frontFacing, upwardsFacing, isFlipped)
    }

    override fun getFieldHolder(): ManagedFieldHolder {
        return MANAGED_FIELD_HOLDER
    }

    override fun attachConfigurators(configuratorPanel: ConfiguratorPanel) {
        if (!lockItem.isEmpty) {
            templateInventory.setStackInSlot(0, lockItem)
        }
        val configurator = InventoryFancyConfigurator(templateInventory.storage, Component.literal(""))
            .apply {
                tooltips = listOf(
                    Component.translatable("gui.gtnn.circuit_assembly_line.template.desc.0")
                )
                lockItemGetter = { lockItem }
            }
        configuratorPanel.attachConfigurators(configurator)
        super.attachConfigurators(configuratorPanel)
    }

    override fun locationGetter(): ResourceLocation {
        return if (isFormed) {
            GTCEu.id("block/casings/pipe/machine_casing_grate")
        } else {
            GTCEu.id("block/casings/solid/machine_casing_solid_steel")
        }
    }

    override fun partLocationGetter(part: IMultiPart?): ResourceLocation {
        return if (part is EnergyHatchPartMachine &&
            part.energyContainer.handlerIO == IO.IN) {
            GTCEu.id("block/casings/pipe/machine_casing_grate")
        } else {
            GTCEu.id("block/casings/solid/machine_casing_solid_steel")
        }
    }

    override fun getAppearance(): BlockState {
        return if (isFormed) {
            GTBlocks.CASING_GRATE.get().defaultBlockState()
        } else {
            GTBlocks.CASING_STEEL_SOLID.get().defaultBlockState()
        }
    }

    override fun getPartAppearance(part: IMultiPart): BlockState {
        return if (part is EnergyHatchPartMachine &&
            part.energyContainer.handlerIO == IO.IN) {
            GTBlocks.CASING_GRATE.get().defaultBlockState()
        } else {
            GTBlocks.CASING_STEEL_SOLID.get().defaultBlockState()
        }
    }

    override fun onDrops(drops: MutableList<ItemStack>) {
        val stack = templateInventory.getStackInSlot(0)
        if (lockItem.isEmpty && !stack.isEmpty) {
            drops.add(stack)
        }
    }

    //////////////////////////////////////
    // ***      Multiblock Render     ***//
    //////////////////////////////////////

    companion object {
        val MANAGED_FIELD_HOLDER = ManagedFieldHolder(
            CircuitAssemblyLineMachine::class.java,
            WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER
        )
    }
}