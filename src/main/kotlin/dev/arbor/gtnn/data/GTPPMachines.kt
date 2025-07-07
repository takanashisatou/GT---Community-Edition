package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.data.RotationState
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern
import com.gregtechceu.gtceu.api.pattern.Predicates.*
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection
import com.gregtechceu.gtceu.common.data.GTBlocks
import com.gregtechceu.gtceu.common.data.GTMachines
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers
import com.gregtechceu.gtceu.common.data.GTRecipeTypes
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.GTNNRegistries.REGISTRATE
import dev.arbor.gtnn.api.block.NNBlockMaps
import dev.arbor.gtnn.api.extension.StringExtension.gt
import dev.arbor.gtnn.api.extension.StructureUtil
import dev.arbor.gtnn.api.machine.multiblock.CircuitAssemblyLineMachine
import dev.arbor.gtnn.api.machine.multiblock.ComponentAssemblyLinePattern
import dev.arbor.gtnn.api.machine.multiblock.FactoryMacerationMachine
import dev.arbor.gtnn.api.machine.multiblock.TierCasingElectricMultiblockMachine
import dev.arbor.gtnn.api.pattern.NNBlockPattern
import dev.arbor.gtnn.client.renderer.machine.GTPPMachineRenderer
import net.minecraft.network.chat.Component

object GTPPMachines {
    val FactoryMaceration: MultiblockMachineDefinition = REGISTRATE
        .multiblock("maceration_stack_controller", ::FactoryMacerationMachine)
        .langValue("Maceration Stack Controller")
        .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
            Component.translatable("gtceu.macerator")),
            *multiLang("block.gtnn.maceration_stack_controller.desc", 4))
        .rotationState(RotationState.ALL)
        .recipeModifiers(GTNNRecipeModifiers.GTPP_MODIFIER, GTRecipeModifiers.OC_NON_PERFECT_SUBTICK)
        .recipeTypes(GTRecipeTypes.MACERATOR_RECIPES)
        .appearanceBlock(GTBlocks.CASING_TITANIUM_STABLE)
        .pattern(FactoryMacerationMachine.Pattern)
        .workableCasingRenderer(
            "block/casings/solid/machine_casing_stable_titanium".gt(),
            "block/multiblock/gcym/large_maceration_tower".gt())
        .register()

    val CIRCUIT_ASSEMBLY_LINE: MultiblockMachineDefinition = REGISTRATE
        .multiblock("circuit_assembly_line", ::CircuitAssemblyLineMachine)
        .langValue("Circuit Assembly Line")
        .tooltips(
            Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
            Component.translatable("gtceu.circuit_assembly_line"),
                Component.translatable("gtceu.circuit_assembler")
            ),
            *multiLang("block.gtnn.circuit_assembly_line.desc", 6))
        .rotationState(RotationState.ALL)
        .recipeTypes(GTNNRecipeTypes.CIRCUIT_ASSEMBLY_LINE_RECIPES, GTRecipeTypes.CIRCUIT_ASSEMBLER_RECIPES)
        .recipeModifiers(GTRecipeModifiers.OC_PERFECT_SUBTICK)
        .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
        .pattern { definition ->
            FactoryBlockPattern.start(RelativeDirection.BACK, RelativeDirection.UP, RelativeDirection.RIGHT)
                .aisle("FIF", "RTR", "SED")
                .aisle("FIF", "RTR", "DDD")
                .setRepeatable(2, 5)
                .aisle("FOF", "RTR", "DDD")
                .where('S', controller(blocks(definition.get())))
                .where(
                    'F',
                    blocks(GTBlocks.CASING_STEEL_SOLID.get())
                        .or(abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(1))
                )
                .where(
                    'O',
                    abilities(PartAbility.EXPORT_ITEMS)
                        .addTooltips(Component.translatable("gtceu.multiblock.pattern.location_end"))
                )
                .where('I', blocks(GTMachines.ITEM_IMPORT_BUS[0].get()))
                .where('R', blocks(GTNNBlocks.OSMIUM_BOROSILICATE_GLASS.get()))
                .where('T', blocks(GTBlocks.CASING_ASSEMBLY_LINE.get()))
                .where('E', abilities(PartAbility.INPUT_ENERGY))
                .where('D', blocks(GTBlocks.CASING_GRATE.get()))
                .build()
        }
        .renderer({
            GTPPMachineRenderer(
                GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                GTCEu.id("block/multiblock/assembly_line"),
                false
            )
        })
        .partAppearance { iMultiController, iMultiPart, direction ->
            iMultiController as CircuitAssemblyLineMachine
            return@partAppearance iMultiController.getPartAppearance(iMultiPart)
        }
        .register()

    val COMPONENT_ASSEMBLY_LINE: MultiblockMachineDefinition = REGISTRATE
        .multiblock("component_assembly_line") { holder -> TierCasingElectricMultiblockMachine(holder, "CACasing") }
        .langValue("Component Assembly Line")
        .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
            Component.translatable("gtceu.assembly_line")),
            *multiLang("block.gtnn.component_assembly_line.desc", 4))
        .rotationState(RotationState.ALL)
        .recipeType(GTNNRecipeTypes.COMPONENT_ASSEMBLY_LINE_RECIPES)
        .appearanceBlock(GTNNCasingBlocks.IRIDIUM_CASING)
        .pattern(ComponentAssemblyLinePattern.Pattern)
        .shapeInfos {
            StructureUtil.getMatchingShapes(
                it.patternFactory.get() as NNBlockPattern, NNBlockMaps.ALL_CA_TIRED_CASINGS.size)
        }
        .partSorter(Comparator.comparingInt { a -> a.self().pos.y })
        .workableCasingRenderer(GTNN.id("block/casings/solid/iridium_casing"),
            GTNN.id("block/multiblock/component_assembly_line"), false)
        .register()

    fun init() {
        REGISTRATE.creativeModeTab { GTNNCreativeModeTabs.MAIN_TAB }
    }

    private fun multiLang(key: String, rows: Int): Array<Component?> {
        val components = arrayOfNulls<Component>(rows)
        for (i in 0 until rows) {
            components[i] = Component.translatable("$key.$i")
        }
        return components
    }
}