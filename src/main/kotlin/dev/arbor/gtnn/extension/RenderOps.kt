package dev.arbor.gtnn.extension

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.InventoryMenu
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

/**
 * Common rendering operations.
 *
 * @author GateGuardian
 * @date : 2024/8/30
 */
@Suppress("unused")
@OnlyIn(Dist.CLIENT)
object RenderOps {
    /**
     * Binds a texture for rendering
     *
     * @param location the ResourceLocation for the texture
     */
    fun bindTexture(location: ResourceLocation) {
        RenderSystem.setShaderTexture(0, location)
    }

    /**
     * Binds the block texture atlas for rendering
     */
    fun bindBlockAtlas() {
        bindTexture(InventoryMenu.BLOCK_ATLAS)
    }

    fun setPositionShader() {
        RenderSystem.setShader { GameRenderer.getPositionShader() }
    }

    fun setPositionColorShader() {
        RenderSystem.setShader { GameRenderer.getPositionColorShader() }
    }

    val positionColorTexShader: Unit
        get() {
            RenderSystem.setShader { GameRenderer.getPositionColorTexShader() }
        }

    fun setPositionTexShader() {
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
    }

    fun setPositionTexColorShader() {
        RenderSystem.setShader { GameRenderer.getPositionTexColorShader() }
    }

    fun setColor(argb: Int) {
        val r: Float = ColorUtils.ARGB32.red(argb)
        val g: Float = ColorUtils.ARGB32.green(argb)
        val b: Float = ColorUtils.ARGB32.blue(argb)
        val a: Float = ColorUtils.ARGB32.alpha(argb)
        RenderSystem.setShaderColor(r, g, b, a)
    }
}