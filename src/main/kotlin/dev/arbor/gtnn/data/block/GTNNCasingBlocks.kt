package dev.arbor.gtnn.data.block

import com.gregtechceu.gtceu.api.item.tool.GTToolType
import com.gregtechceu.gtceu.common.data.models.GTModels
import com.tterrag.registrate.util.entry.BlockEntry
import com.tterrag.registrate.util.nullness.NonNullFunction
import com.tterrag.registrate.util.nullness.NonNullSupplier
import dev.arbor.gtnn.GTNN.id
import dev.arbor.gtnn.GTNNRegistries.REGISTRATE
import dev.arbor.gtnn.api.block.ITierType
import dev.arbor.gtnn.common.block.SimpleTierBlock
import dev.arbor.gtnn.data.GTNNCreativeModeTabs
import dev.arbor.gtnn.data.block.NNBlockMaps.ALL_CA_TIRED_CASINGS
import dev.arbor.gtnn.extension.StringExtension.gt
import dev.arbor.gtnn.extension.StringExtension.nn
import net.minecraft.client.renderer.RenderType
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import java.util.function.Supplier

object GTNNCasingBlocks {

    init {
        REGISTRATE.creativeModeTab { GTNNCreativeModeTabs.MAIN_TAB }
    }

    val PROCESS_MACHINE_CASING: BlockEntry<Block> = createCasingBlock(
        "process_machine_casing", id("block/casings/solid/process_machine_casing"))

    val RADIATION_PROOF_MACHINE_CASING: BlockEntry<Block> = createCasingBlock(
        "radiation_proof_machine_casing", id("block/casings/solid/radiation_proof_machine_casing"))

    val MAR_CASING: BlockEntry<Block> = createCasingBlock(
        "field_restriction_casing", id("block/casings/solid/mar_casing"))

    val CASING_POLYBENZIMIDAZOLE_PIPE = createCasingBlock(
        "polybenzimidazole_pipe", id("block/casings/pipe/polybenzimidazole_pipe"))

    val IRIDIUM_CASING: BlockEntry<Block> = createCasingBlock(
        "iridium_casing", id("block/casings/solid/iridium_casing"))

    val ADVANCED_FILTER_CASING = createCasingBlock(
        "advanced_filter_casing", id("block/casings/solid/advanced_filter_casing"))

    //  Component Assembly Line Casings
    
    val COMPONENT_ASSEMBLY_LINE_CASING_LV: BlockEntry<SimpleTierBlock> =
        createComponentAssemblyBlock(ITierType.TierBlockType.LV)
    val COMPONENT_ASSEMBLY_LINE_CASING_MV: BlockEntry<SimpleTierBlock> =
        createComponentAssemblyBlock(ITierType.TierBlockType.MV)
    val COMPONENT_ASSEMBLY_LINE_CASING_HV: BlockEntry<SimpleTierBlock> =
        createComponentAssemblyBlock(ITierType.TierBlockType.HV)
    val COMPONENT_ASSEMBLY_LINE_CASING_EV: BlockEntry<SimpleTierBlock> =
        createComponentAssemblyBlock(ITierType.TierBlockType.EV)
    val COMPONENT_ASSEMBLY_LINE_CASING_IV: BlockEntry<SimpleTierBlock> =
        createComponentAssemblyBlock(ITierType.TierBlockType.IV)
    val COMPONENT_ASSEMBLY_LINE_CASING_LuV: BlockEntry<SimpleTierBlock> =
        createComponentAssemblyBlock(ITierType.TierBlockType.LuV)
    val COMPONENT_ASSEMBLY_LINE_CASING_ZPM: BlockEntry<SimpleTierBlock> =
        createComponentAssemblyBlock(ITierType.TierBlockType.ZPM)
    val COMPONENT_ASSEMBLY_LINE_CASING_UV: BlockEntry<SimpleTierBlock> =
        createComponentAssemblyBlock(ITierType.TierBlockType.UV)
    val COMPONENT_ASSEMBLY_LINE_CASING_UHV: BlockEntry<SimpleTierBlock> =
        createComponentAssemblyBlock(ITierType.TierBlockType.UHV)
    val COMPONENT_ASSEMBLY_LINE_CASING_UEV: BlockEntry<SimpleTierBlock> =
        createComponentAssemblyBlock(ITierType.TierBlockType.UEV)
    val COMPONENT_ASSEMBLY_LINE_CASING_UIV: BlockEntry<SimpleTierBlock> =
        createComponentAssemblyBlock(ITierType.TierBlockType.UIV)
    val COMPONENT_ASSEMBLY_LINE_CASING_UXV: BlockEntry<SimpleTierBlock> =
        createComponentAssemblyBlock(ITierType.TierBlockType.UXV)
    val COMPONENT_ASSEMBLY_LINE_CASING_OpV: BlockEntry<SimpleTierBlock> =
        createComponentAssemblyBlock(ITierType.TierBlockType.OpV)
    val COMPONENT_ASSEMBLY_LINE_CASING_MAX: BlockEntry<SimpleTierBlock> =
        createComponentAssemblyBlock(ITierType.TierBlockType.MAX)

    private fun createCasingBlock(name: String, texture: ResourceLocation): BlockEntry<Block> {
        return createCasingBlock(name,
            { properties: BlockBehaviour.Properties -> Block(properties) },
            texture,
            { Blocks.IRON_BLOCK },
            { Supplier { RenderType.cutoutMipped() } })
    }

    private fun createComponentAssemblyBlock(blockData: ITierType.TierBlockType): BlockEntry<SimpleTierBlock> {
        val bName = blockData.typeName.lowercase()
        val name = "component_assline_casing_$bName"
        val componentAssemblyBlock: BlockEntry<SimpleTierBlock> = REGISTRATE
            .block(name) { SimpleTierBlock(it, blockData) }
            .initialProperties { Blocks.IRON_BLOCK }
            .addLayer { Supplier { RenderType.cutoutMipped() } }
            .blockstate{ ctx, prov ->
                prov.simpleBlock(ctx.getEntry(), prov.models()
                    .withExistingParent(name, "block/cube_2_layer/all".gt())
                    .texture("bot_all", "block/casings/solid/iridium_casing".nn())
                    .texture("top_all", "block/casings/component_assline_casing/$bName".nn()))
            }
            .tag(GTToolType.WRENCH.harvestTags[0], BlockTags.MINEABLE_WITH_PICKAXE)
            .onRegister { it.addTooltip(Component.translatable(it.descriptionId + ".desc")) }
            .item(::BlockItem)
            .build()
            .register()
        ALL_CA_TIRED_CASINGS[blockData] = Supplier { componentAssemblyBlock.get() }
        return componentAssemblyBlock
    }

    private fun createCasingBlock(
        name: String,
        blockSupplier: NonNullFunction<BlockBehaviour.Properties, Block>,
        texture: ResourceLocation,
        properties: NonNullSupplier<out Block>,
        type: Supplier<Supplier<RenderType>>
    ): BlockEntry<Block> {
        return REGISTRATE.block(name, blockSupplier)
                .initialProperties(properties)
                .properties { p: BlockBehaviour.Properties -> p.isValidSpawn { _, _, _, _ -> false } }
                .addLayer(type)
                .exBlockstate(GTModels.cubeAllModel(texture))
                .tag(GTToolType.WRENCH.harvestTags[0], BlockTags.MINEABLE_WITH_PICKAXE)
                .item(::BlockItem)
                .build()
                .register()
    }

    fun init() {
    }
}
