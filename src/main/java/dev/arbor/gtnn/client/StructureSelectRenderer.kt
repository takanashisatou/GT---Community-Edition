package dev.arbor.gtnn.client

import com.lowdragmc.lowdraglib.client.utils.RenderBufferUtils
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import dev.arbor.gtnn.api.item.behaviors.StructureWriteBehavior
import net.minecraft.client.Camera
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GameRenderer
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
object StructureSelectRenderer {
    fun renderStructureSelect(poseStack: PoseStack, camera: Camera) {
        val mc = Minecraft.getInstance()
        mc.level ?: return
        val player = mc.player ?: return

        val held = player.mainHandItem
        if (!StructureWriteBehavior.isItemStructureWriter(held)) return

        val poses = StructureWriteBehavior.getPos(held) ?: return
        val pos = camera.position

        poseStack.pushPose()
        poseStack.translate(-pos.x, -pos.y, -pos.z)

        RenderSystem.disableDepthTest()
        RenderSystem.enableBlend()
        RenderSystem.disableCull()
        RenderSystem.blendFunc(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA
        )

        val tesselator = Tesselator.getInstance()
        val buffer = tesselator.builder

        // 渲染立方体面
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)
        RenderSystem.setShader { GameRenderer.getPositionColorShader() }

        RenderBufferUtils.renderCubeFace(
            poseStack,
            buffer,
            poses[0].x.toFloat(),
            poses[0].y.toFloat(),
            poses[0].z.toFloat(),
            (poses[1].x + 1).toFloat(),
            (poses[1].y + 1).toFloat(),
            (poses[1].z + 1).toFloat(),
            0.2f, 0.2f, 1f, 0.25f,
            true
        )

        tesselator.end()

        // 渲染立方体框架
        buffer.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL)
        RenderSystem.setShader { GameRenderer.getRendertypeLinesShader() }
        RenderSystem.lineWidth(3f)

        RenderBufferUtils.drawCubeFrame(
            poseStack,
            buffer,
            poses[0].x.toFloat(),
            poses[0].y.toFloat(),
            poses[0].z.toFloat(),
            (poses[1].x + 1).toFloat(),
            (poses[1].y + 1).toFloat(),
            (poses[1].z + 1).toFloat(),
            0f, 0f, 1f, 0.5f
        )

        tesselator.end()

        // 恢复渲染状态
        RenderSystem.enableCull()
        RenderSystem.disableBlend()
        RenderSystem.enableDepthTest()

        poseStack.popPose()
    }
}