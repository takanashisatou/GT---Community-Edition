package dev.arbor.gtnn.data

import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateItemModelProvider
import com.tterrag.registrate.util.nullness.NonNullBiConsumer
import com.tterrag.registrate.util.nullness.NonNullSupplier
import dev.arbor.gtnn.GTNN
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile

object GTNNModels {
    fun <T : Item> wrapItemModel(wrapped: ResourceLocation):
            NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> {
        return NonNullBiConsumer { ctx: DataGenContext<Item, T>, prov: RegistrateItemModelProvider ->
            prov.getBuilder("item/" + prov.name { ctx.getEntry() })
                .parent(UncheckedModelFile(GTNN.id("item/wrap_item")))
                .texture("layer0", wrapped)
                .texture("layer4", GTNN.id("item/wrap_overlay"))
        }
    }

    fun <T : Item> simpleCustomModel(modelLocation: ResourceLocation, vararg textureLocations: ResourceLocation
    ): NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> {
        return NonNullBiConsumer { ctx: DataGenContext<Item, T>, prov: RegistrateItemModelProvider ->
            val builder = prov.getBuilder("item/" + prov.name { ctx.getEntry() })
                .parent(UncheckedModelFile(modelLocation))
            for (i in textureLocations.indices) {
                builder.texture("layer%s".format(i), textureLocations[i])
            }
        }
    }

    fun simpleCustomBlockItemModel(context: DataGenContext<Item, BlockItem>, provider: RegistrateItemModelProvider) {
        provider.generated(context, provider.modLoc("block/" + provider.name(context)))
    }

    fun captureToolModel(
        ctx: DataGenContext<Item, out Item>, prov: RegistrateItemModelProvider
    ) {
        // empty model
        prov.getBuilder("item/" + prov.name(NonNullSupplier { ctx.getEntry() }) + "_empty")
            .parent(UncheckedModelFile("item/generated"))
            .texture("layer0", prov.modLoc("item/%s/empty".format(prov.name(ctx))))

        // filled model
        prov.getBuilder("item/" + prov.name(NonNullSupplier { ctx.getEntry() }) + "_filled")
            .parent(UncheckedModelFile("item/generated"))
            .texture("layer0", prov.modLoc("item/%s/filled".format(prov.name(ctx))))

        // root model
        prov.generated(NonNullSupplier { ctx.getEntry() }, prov.modLoc("item/%s/empty".format(prov.name(ctx))))
            .override()
            .predicate(GTNN.id("organism_capture_tool"), 0f)
            .model(UncheckedModelFile(
                prov.modLoc("item/%s_empty".format(prov.name(ctx)))))
            .end()
            .override()
            .predicate(GTNN.id("organism_capture_tool"), 1f)
            .model(UncheckedModelFile(
                prov.modLoc("item/%s_filled".format(prov.name(ctx)))))
            .end()
    }
}