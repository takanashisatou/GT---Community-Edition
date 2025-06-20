package dev.arbor.gtnn.api.extension

import net.minecraft.util.FastColor
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

@Suppress("unused")
object ColorUtils {
    fun mkHSBtoRGB(hue: Float, saturation: Float, brightness: Float): Int {
        var r = 0
        var g = 0
        var b = 0
        if (saturation == 0f) {
            b = (brightness * 255.0f + 0.5f).toInt()
            g = b
            r = g
        } else {
            val h = (hue - floor(hue.toDouble()).toFloat()) * 6.0f
            val f = h - floor(h.toDouble()).toFloat()
            val p = brightness * (1.0f - saturation)
            val q = brightness * (1.0f - saturation * f)
            val t = brightness * (1.0f - (saturation * (1.0f - f)))
            when (h.toInt()) {
                0 -> {
                    r = (brightness * 255.0f + 0.5f).toInt()
                    g = (t * 255.0f + 0.5f).toInt()
                    b = (p * 255.0f + 0.5f).toInt()
                }

                1 -> {
                    r = (q * 255.0f + 0.5f).toInt()
                    g = (brightness * 255.0f + 0.5f).toInt()
                    b = (p * 255.0f + 0.5f).toInt()
                }

                2 -> {
                    r = (p * 255.0f + 0.5f).toInt()
                    g = (brightness * 255.0f + 0.5f).toInt()
                    b = (t * 255.0f + 0.5f).toInt()
                }

                3 -> {
                    r = (p * 255.0f + 0.5f).toInt()
                    g = (q * 255.0f + 0.5f).toInt()
                    b = (brightness * 255.0f + 0.5f).toInt()
                }

                4 -> {
                    r = (t * 255.0f + 0.5f).toInt()
                    g = (p * 255.0f + 0.5f).toInt()
                    b = (brightness * 255.0f + 0.5f).toInt()
                }

                5 -> {
                    r = (brightness * 255.0f + 0.5f).toInt()
                    g = (p * 255.0f + 0.5f).toInt()
                    b = (q * 255.0f + 0.5f).toInt()
                }
            }
        }
        return -0x1000000 or (r shl 16) or (g shl 8) or (b)
    }

    /**
     * all components should in [0-1]
     */
    fun mkRGBtoHSB(color: Int): FloatArray {
        val r = ((color shr 16) and 0xff)
        val g = ((color shr 8) and 0xff)
        val b = ((color) and 0xff)

        var hue: Float
        val saturation: Float
        val brightness: Float

        var cmax = max(r, g)
        if (b > cmax) cmax = b
        var cmin = min(r, g)
        if (b < cmin) cmin = b

        brightness = (cmax.toFloat()) / 255.0f
        if (cmax != 0) saturation = ((cmax - cmin).toFloat()) / (cmax.toFloat())
        else saturation = 0f
        if (saturation == 0f) hue = 0f
        else {
            val redc = ((cmax - r).toFloat()) / ((cmax - cmin).toFloat())
            val greenc = ((cmax - g).toFloat()) / ((cmax - cmin).toFloat())
            val bluec = ((cmax - b).toFloat()) / ((cmax - cmin).toFloat())
            if (r == cmax) hue = bluec - greenc
            else if (g == cmax) hue = 2.0f + redc - bluec
            else hue = 4.0f + greenc - redc
            hue /= 6.0f
            if (hue < 0) hue += 1.0f
        }
        return floatArrayOf(hue, saturation, brightness)
    }

    object ARGB32 {
        fun alpha(packedColor: Int): Float {
            return FastColor.ARGB32.alpha(packedColor) / 255.0f
        }

        fun red(packedColor: Int): Float {
            return FastColor.ARGB32.red(packedColor) / 255.0f
        }

        fun green(packedColor: Int): Float {
            return FastColor.ARGB32.green(packedColor) / 255.0f
        }

        fun blue(packedColor: Int): Float {
            return FastColor.ARGB32.blue(packedColor) / 255.0f
        }

        fun color(alpha: Float, red: Float, green: Float, blue: Float): Int {
            return FastColor.ARGB32.color(
                (alpha * 255).toInt(), (red * 255).toInt(), (green * 255).toInt(), (blue * 255).toInt()
            )
        }
    }

    object ABGR32 {
        fun alpha(packedColor: Int): Float {
            return FastColor.ABGR32.alpha(packedColor) / 255.0f
        }

        fun blue(packedColor: Int): Float {
            return FastColor.ABGR32.blue(packedColor) / 255.0f
        }

        fun green(packedColor: Int): Float {
            return FastColor.ABGR32.green(packedColor) / 255.0f
        }

        fun red(packedColor: Int): Float {
            return FastColor.ABGR32.red(packedColor) / 255.0f
        }

        fun color(alpha: Float, blue: Float, green: Float, red: Float): Int {
            return FastColor.ABGR32.color(
                (alpha * 255).toInt(), (blue * 255).toInt(), (green * 255).toInt(), (red * 255).toInt()
            )
        }
    }
}