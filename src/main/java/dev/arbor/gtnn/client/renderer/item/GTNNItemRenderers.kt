package dev.arbor.gtnn.client.renderer.item

import com.gregtechceu.gtceu.api.GTValues.VN
import com.lowdragmc.lowdraglib.Platform
import com.lowdragmc.lowdraglib.client.model.ModelFactory
import com.lowdragmc.lowdraglib.client.renderer.IItemRendererProvider
import com.lowdragmc.lowdraglib.client.renderer.IRenderer
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.api.extension.RenderOps
import dev.arbor.gtnn.api.item.INNItemRendererProvider
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.function.Consumer

object GTNNItemRenderers {
    val SUPERSCRIPT_ITEM_RENDERER: SuperscriptItemRenderer = SuperscriptItemRenderer()

    fun init() {
    }
}

abstract class WrappedItemRenderer : IRenderer {
    @Suppress("SameParameterValue")
    @OnlyIn(Dist.CLIENT)
    protected fun getVanillaModel(
        stack: ItemStack,
        level: ClientLevel?,
        entity: LivingEntity?
    ): BakedModel {
        val shaper = Minecraft.getInstance().itemRenderer.itemModelShaper
        val model = shaper.getItemModel(stack.item)
        return model?.overrides?.resolve(model, stack, level, entity, 0)
            ?: shaper.modelManager.missingModel
    }

    @OnlyIn(Dist.CLIENT)
    protected fun vanillaRender(
        stack: ItemStack,
        transformType: ItemDisplayContext,
        leftHand: Boolean,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        combinedLight: Int,
        combinedOverlay: Int,
        model: BakedModel
    ) {
        IItemRendererProvider.disabled.set(true)
        Minecraft.getInstance().itemRenderer.render(
            stack,
            transformType,
            leftHand,
            poseStack,
            buffer,
            combinedLight,
            combinedOverlay,
            model
        )
        IItemRendererProvider.disabled.set(false)
    }
}

@OnlyIn(Dist.CLIENT)
class SuperscriptItemRenderer : WrappedItemRenderer() {
    private val voltageTextures = Int2ObjectOpenHashMap<ResourceLocation>()
    private val numberTextures = Int2ObjectOpenHashMap<ResourceLocation>()
    private val romaNumberTextures = Int2ObjectOpenHashMap<ResourceLocation>()

    init {
        if (Platform.isClient()) {
            registerEvent()
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun renderItem(
        stack: ItemStack,
        transformType: ItemDisplayContext,
        leftHand: Boolean,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        combinedLight: Int,
        combinedOverlay: Int,
        model: BakedModel
    ) {
        val vanillaModel = getVanillaModel(stack, null, null)
        val texture = getTexture(stack)

        if (transformType == ItemDisplayContext.GUI && texture != null) {
            // 渲染基础物品
            val tess = Tesselator.getInstance()
            val immediateBuffer = MultiBufferSource.immediate(tess.builder)
            vanillaRender(stack, transformType, leftHand, poseStack, immediateBuffer, combinedLight, combinedOverlay, vanillaModel)
            immediateBuffer.endBatch()

            // 准备渲染上标纹理
            val buf = tess.builder
            buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX)

            poseStack.pushPose()
            poseStack.translate(-0.5f, -0.5f, -0.5f)

            RenderSystem.setShader { GameRenderer.getPositionTexShader() }
            val sprite = ModelFactory.getBlockSprite(texture)
            RenderOps.bindBlockAtlas()

            val minU = sprite.u0
            val maxU = sprite.u1
            val minV = sprite.v0
            val maxV = sprite.v1

            val posMatrix = poseStack.last().pose()
            buf.vertex(posMatrix, 1f, 1f, 0f).uv(maxU, minV).endVertex()
            buf.vertex(posMatrix, 0f, 1f, 0f).uv(minU, minV).endVertex()
            buf.vertex(posMatrix, 0f, 0f, 0f).uv(minU, maxV).endVertex()
            buf.vertex(posMatrix, 1f, 0f, 0f).uv(maxU, maxV).endVertex()
            tess.end()

            poseStack.popPose()
        } else {
            // 非GUI环境使用普通渲染
            vanillaRender(stack, transformType, leftHand, poseStack, buffer, combinedLight, combinedOverlay, model)
        }
    }

    private fun getTexture(stack: ItemStack): ResourceLocation? {
        if (stack.item is INNItemRendererProvider) {
            val provider = stack.item as INNItemRendererProvider
            val info = provider.getRenderInfo(stack)

            return when (info) {
                is INumberSuperscriptEffect -> {
                    if (info.isRoma) romaNumberTextures[info.tier]
                    else numberTextures[info.tier]
                }
                is IVoltageSuperscriptEffect -> {
                    voltageTextures[info.tier]
                }
                else -> null
            }
        }
        return null
    }

    @OnlyIn(Dist.CLIENT)
    override fun onPrepareTextureAtlas(atlasName: ResourceLocation, register: Consumer<ResourceLocation>) {
        if (atlasName == InventoryMenu.BLOCK_ATLAS) {
            for (i in 0..14) {
                // 电压纹理
                val voltageId = GTNN.id("superscript/voltage/${VN[i].lowercase()}")
                register.accept(voltageId)
                voltageTextures[i] = voltageId

                // 数字纹理
                val numberId = GTNN.id("superscript/number/${i + 1}")
                register.accept(numberId)
                numberTextures[i + 1] = numberId

                // 罗马数字纹理
                val romaNumberId = GTNN.id("superscript/number/roman/${i + 1}")
                register.accept(romaNumberId)
                romaNumberTextures[i + 1] = romaNumberId
            }
        }
    }
}