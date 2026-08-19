package dev.arbor.gtnn.client.renderer.item

import com.google.common.collect.ImmutableList
import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.lowdragmc.lowdraglib.LDLib
import com.lowdragmc.lowdraglib.client.model.ModelFactory
import dev.arbor.gtnn.mixin.mc.ItemModelGeneratorAccessor
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.block.model.*
import net.minecraft.client.renderer.texture.SpriteContents
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.client.resources.model.Material
import net.minecraft.client.resources.model.ModelBaker
import net.minecraft.client.resources.model.ModelState
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.ForgeRenderTypes
import net.minecraftforge.client.RenderTypeGroup
import net.minecraftforge.client.model.CompositeModel
import net.minecraftforge.client.model.ForgeFaceData
import net.minecraftforge.client.model.geometry.IGeometryBakingContext
import net.minecraftforge.client.model.geometry.IGeometryLoader
import net.minecraftforge.client.model.geometry.IUnbakedGeometry
import net.minecraftforge.client.model.geometry.UnbakedGeometryHelper
import org.joml.Vector3f
import java.util.function.Function
import kotlin.math.abs

class ItemCustomLayerModel private constructor(
    private var textures: ImmutableList<Material>?,
    private val layerData: Int2ObjectMap<ForgeFaceData>,
    private val layerPos: Array<FloatArray>?,
    private val renderTypeNames: Int2ObjectMap<ResourceLocation>
) : IUnbakedGeometry<ItemCustomLayerModel> {

    override fun bake(
        context: IGeometryBakingContext,
        baker: ModelBaker,
        spriteGetter: Function<Material, TextureAtlasSprite>,
        modelState: ModelState,
        overrides: ItemOverrides,
        modelLocation: ResourceLocation
    ): BakedModel {
        var finalModelState = modelState
        var localTextures = textures
        if (localTextures == null) {
            val builder = ImmutableList.builder<Material>()
            var i = 0
            while (context.hasMaterial("layer$i")) {
                builder.add(context.getMaterial("layer$i"))
                i++
            }
            localTextures = builder.build()
            textures = localTextures
        }

        val particle = spriteGetter.apply(
            if (context.hasMaterial("particle")) context.getMaterial("particle") else localTextures!![0]
        )
        val rootTransform = context.rootTransform
        if (!rootTransform.isIdentity) {
            finalModelState = UnbakedGeometryHelper.composeRootTransformIntoModelState(modelState, rootTransform)
        }

        val normalRenderTypes = RenderTypeGroup(
            RenderType.cutout(), ForgeRenderTypes.ITEM_LAYERED_CUTOUT.get()
        )
        val builder = CompositeModel.Baked.builder(
            context, particle, overrides, context.transforms
        )
        for (i in localTextures!!.indices) {
            val sprite = spriteGetter.apply(localTextures[i])
            val unbaked = createUnbakedCustomItemElements(i,
                layerPos?.get(i) ?: DEFAULT_LAYER_POS, sprite.contents(), layerData[i])
            val quads = UnbakedGeometryHelper.bakeElements(
                unbaked, { sprite }, finalModelState, modelLocation)
            val renderTypeName = renderTypeNames[i]
            val renderTypes = renderTypeName?.let { context.getRenderType(it) }
            builder.addQuads(renderTypes ?: normalRenderTypes, quads)
        }

        textures = null
        return builder.build()
    }

    private fun createUnbakedCustomItemElements(
        layerIndex: Int,
        pos: FloatArray,
        spriteContents: SpriteContents,
        faceData: ForgeFaceData?
    ): List<BlockElement> {
        val texture = "layer$layerIndex"
        val map = Maps.newHashMap<Direction, BlockElementFace>()
        map[Direction.SOUTH] = BlockElementFace(
            null, layerIndex, texture, BlockFaceUV(floatArrayOf(0.0f, 0.0f, 16.0f, 16.0f), 0)
        )
        map[Direction.NORTH] = BlockElementFace(
            null, layerIndex, texture, BlockFaceUV(floatArrayOf(16.0f, 0.0f, 0.0f, 16.0f), 0)
        )
        val elements = Lists.newArrayList<BlockElement>()
        val zOffset = 7.5f - 0.001f * layerIndex
        val zOffsetEnd = 8.5f + 0.001f * layerIndex
        elements.add(
            BlockElement(
                Vector3f(pos[0], pos[1], zOffset),
                Vector3f(pos[2], pos[3], zOffsetEnd),
                map, null, false
            )
        )
        elements.addAll(createSideElements(spriteContents, texture, pos, layerIndex))
        faceData?.let {
            elements.forEach { element -> element.faceData = it }
        }
        return elements
    }

    private fun createSideElements(
        sprite: SpriteContents,
        texture: String,
        pos: FloatArray,
        tintIndex: Int
    ): List<BlockElement> {
        val generator = ModelFactory.ITEM_MODEL_GENERATOR
        val spriteWidth = sprite.width().toFloat()
        val spriteHeight = sprite.height().toFloat()
        val list = Lists.newArrayList<BlockElement>()
        val spans = getSpans(generator, sprite)

        for (span in spans) {
            var h: Float
            var i: Float
            var j: Float
            var k: Float
            var l: Float
            var m: Float
            var n: Float
            var o: Float
            val p = abs(pos[0] - pos[2]) / spriteWidth
            val q = abs(pos[1] - pos[3]) / spriteWidth
            val r = span.min.toFloat()
            val s = span.max.toFloat()
            val t = span.anchor.toFloat()

            val spanFacing = span.facing
            when (spanFacing) {
                ItemModelGenerator.SpanFacing.UP -> {
                    l = r
                    h = r
                    j = s + 1.0f
                    m = j
                    n = t
                    i = t
                    k = t
                    o = t + 1.0f
                }
                ItemModelGenerator.SpanFacing.DOWN -> {
                    n = t
                    o = t + 1.0f
                    l = r
                    h = r
                    j = s + 1.0f
                    m = j
                    i = t + 1.0f
                    k = i
                }
                ItemModelGenerator.SpanFacing.LEFT -> {
                    l = t
                    h = t
                    j = t
                    m = t + 1.0f
                    o = r
                    i = r
                    k = s + 1.0f
                    n = k
                }
                ItemModelGenerator.SpanFacing.RIGHT -> {
                    l = t
                    m = t + 1.0f
                    h = t + 1.0f
                    j = h
                    o = r
                    i = r
                    k = s + 1.0f
                    n = k
                }
            }

            h *= p
            j *= p
            i *= q
            k *= q
            i = 16.0f - i
            k = 16.0f - k
            val pScale = 16.0f / spriteHeight
            val qScale = 16.0f / spriteHeight
            l *= pScale
            m *= pScale
            n *= qScale
            o *= qScale

            val map = Maps.newHashMap<Direction, BlockElementFace>()
            map[spanFacing.direction] = BlockElementFace(
                null, tintIndex, texture, BlockFaceUV(floatArrayOf(l, n, m, o), 0)
            )
            when (spanFacing) {
                ItemModelGenerator.SpanFacing.UP -> list.add(
                    BlockElement(
                        Vector3f(h + pos[0], i - pos[0], 7.5f),
                        Vector3f(j + pos[0], i - pos[0], 8.5f),
                        map, null, true
                    )
                )
                ItemModelGenerator.SpanFacing.DOWN -> list.add(
                    BlockElement(
                        Vector3f(h + pos[0], k - pos[0], 7.5f),
                        Vector3f(j + pos[0], k - pos[0], 8.5f),
                        map, null, true
                    )
                )
                ItemModelGenerator.SpanFacing.LEFT -> list.add(
                    BlockElement(
                        Vector3f(h + pos[0], i - pos[0], 7.5f),
                        Vector3f(h + pos[0], k - pos[0], 8.5f),
                        map, null, true
                    )
                )
                ItemModelGenerator.SpanFacing.RIGHT -> list.add(
                    BlockElement(
                        Vector3f(j + pos[0], i - pos[0], 7.5f),
                        Vector3f(j + pos[0], k - pos[0], 8.5f),
                        map, null, true
                    )
                )
            }
        }
        return list
    }

    private fun getSpans(
        generator: ItemModelGenerator,
        sprite: SpriteContents
    ): List<ItemModelGenerator.Span> {
        if (generator is ItemModelGeneratorAccessor) {
            return generator.callGetSpans(sprite)
        }

        val method = generator.javaClass.declaredMethods.firstOrNull { candidate ->
            candidate.parameterCount == 1 &&
                candidate.parameterTypes[0] == SpriteContents::class.java &&
                List::class.java.isAssignableFrom(candidate.returnType)
        } ?: throw IllegalStateException("Unable to find ItemModelGenerator.getSpans")

        method.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        return method.invoke(generator, sprite) as List<ItemModelGenerator.Span>
    }

    class Loader : IGeometryLoader<ItemCustomLayerModel> {
        override fun read(
            jsonObject: JsonObject,
            deserializationContext: JsonDeserializationContext
        ): ItemCustomLayerModel {
            val renderTypeNames = Int2ObjectOpenHashMap<ResourceLocation>()
            jsonObject.getAsJsonObject("render_types")?.let { renderTypes ->
                for ((key, element) in renderTypes.entrySet()) {
                    val renderType = ResourceLocation(key)
                    for (layerElement in element.asJsonArray) {
                        val layer = layerElement.asInt
                        if (renderTypeNames.put(layer, renderType) != null) {
                            throw JsonParseException("Registered duplicate render type for layer $layer")
                        }
                    }
                }
            }

            val emissiveLayers = Int2ObjectArrayMap<ForgeFaceData>()
            jsonObject.getAsJsonObject("forge_data")?.let { forgeData ->
                readLayerData(forgeData, renderTypeNames, emissiveLayers, false)
            }

            val list = jsonObject.getAsJsonArray("layer_pos")?.let {
                LDLib.GSON.fromJson(it, Array<FloatArray>::class.java)
            }
            return ItemCustomLayerModel(null, emissiveLayers, list, renderTypeNames)
        }

        private fun readLayerData(
            jsonObject: JsonObject,
            renderTypeNames: Int2ObjectOpenHashMap<ResourceLocation>,
            layerData: Int2ObjectMap<ForgeFaceData>,
            logWarning: Boolean
        ) {
            jsonObject.getAsJsonObject("layers")?.let { fullBrightLayers ->
                for ((key, value) in fullBrightLayers.entrySet()) {
                    val layer = key.toInt()
                    val data = ForgeFaceData.read(value, ForgeFaceData.DEFAULT)
                    layerData[layer] = data
                }
            }
        }
    }

    companion object {
        private val DEFAULT_LAYER_POS = floatArrayOf(0.0f, 0.0f, 16.0f, 16.0f)
        val INSTANCE = Loader()
    }
}
