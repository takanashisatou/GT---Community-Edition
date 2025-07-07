package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.api.item.tool.GTToolType
import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.util.entry.BlockEntry
import com.tterrag.registrate.util.nullness.NonNullFunction
import dev.arbor.gtnn.GTNNRegistries.REGISTRATE
import dev.arbor.gtnn.api.block.BorosilicateGlassBlock
import dev.arbor.gtnn.api.block.NNBlockMaps
import net.minecraft.client.renderer.RenderType
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import java.util.function.Supplier

object GTNNBlocks {
    init {
        REGISTRATE.creativeModeTab { GTNNCreativeModeTabs.MAIN_TAB }
     }

    val OSMIUM_BOROSILICATE_GLASS: BlockEntry<BorosilicateGlassBlock> =
        createBorosilicateGlass(BorosilicateGlassBlock.Type.OSMIUM_BOROSILICATE)

    private fun <T : Block?> createBlock(
        name: String, factory: NonNullFunction<BlockBehaviour.Properties, T>
    ): BlockBuilder<T, Registrate> {
        return REGISTRATE.block(name, factory)
    }

    @Suppress("SameParameterValue")
    private fun createBorosilicateGlass(
        glassType: BorosilicateGlassBlock.Type
    ): BlockEntry<BorosilicateGlassBlock> {
        val glassBlock: BlockEntry<BorosilicateGlassBlock> =
            createBlock(glassType.typeName, ::BorosilicateGlassBlock)
                .initialProperties { Blocks.GLASS }
                .properties { p -> p.isValidSpawn { _, _, _, _ -> false } }
                .addLayer { Supplier { RenderType.translucent() } }
                .blockstate { ctx, prov ->
                    prov.simpleBlock(ctx.getEntry(),
                        prov.models().cubeAll(glassType.typeName, glassType.texture))
                }
                .tag(GTToolType.WRENCH.harvestTags[0], BlockTags.MINEABLE_WITH_PICKAXE)
                .item(::BlockItem)
                .build()
                .register()

        NNBlockMaps.ALL_GLASSES.put(glassType, glassBlock::get)
        return glassBlock
    }

    fun init() {
    }
}