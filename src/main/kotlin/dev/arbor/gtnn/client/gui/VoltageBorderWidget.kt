package dev.arbor.gtnn.client.gui

import com.lowdragmc.lowdraglib.gui.widget.Widget
import dev.arbor.gtnn.GTNN
import net.minecraft.client.gui.GuiGraphics

class VoltageBorderWidget(
    x: Int, y: Int,
    width: Int, height: Int,
    private val color: Int
) : Widget(x, y, width, height) {
    init { setClientSideWidget() }

    override fun drawOverlay(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (!GTNN.getClientConfig().enabledVoltageBorder) return

        with(position) {
            val margin = 4
            val (x, y) = x - margin to y - margin
            val (w, h) = size.run { width + 2 * margin to height + 2 * margin }

            val outer = color.darken(0.6f)
            val inner = color.withAlpha(0xE0)
            val glow = color.withAlpha(0x40)

            // Outer border
            graphics.drawBorders(x, y, w, h, 1, outer)
            // Inner border
            graphics.drawBorders(x + 1, y + 1, w - 2, h - 2, 1, inner)
            // Glow effect
            graphics.fill(x + 4, y + 3, x + w - 4, y + 4, glow)          // Top
            graphics.fill(x + 4, y + h - 4, x + w - 4, y + h - 3, glow)  // Bottom
        }
    }

    private fun Int.withAlpha(alpha: Int) = (alpha shl 24) or (this and 0x00FFFFFF)
    private fun Int.darken(factor: Float): Int {
        fun processComponent(value: Int) = (value * factor).toInt().coerceIn(0, 255)
        val r = processComponent((this shr 16) and 0xFF)
        val g = processComponent((this shr 8) and 0xFF)
        val b = processComponent(this and 0xFF)
        return 0xFF000000.toInt() or (r shl 16) or (g shl 8) or b
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) = false
}

private fun GuiGraphics.drawBorders(x: Int, y: Int, w: Int, h: Int, thickness: Int, color: Int) {
    fill(x, y, x + w, y + thickness, color)                                    // Top
    fill(x, y + h - thickness, x + w, y + h, color)                            // Bottom
    fill(x, y + thickness, x + thickness, y + h - thickness, color)            // Left
    fill(x + w - thickness, y + thickness, x + w, y + h - thickness, color)    // Right
}