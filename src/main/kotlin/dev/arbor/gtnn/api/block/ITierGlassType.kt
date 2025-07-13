package dev.arbor.gtnn.api.block

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.client.util.TooltipHelper
import dev.arbor.gtnn.GTNN
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import kotlin.math.floor

interface ITierGlassType : ITierType {
    val isOpticalGlass: Boolean

    val texture: ResourceLocation

    val tireVoltage: Long
        get() = GTValues.V[tier]

    val tireName: String
        get() = GTValues.VN[tier]

    val tireNameColored: String
        get() = GTValues.VNF[tier]

    val opticalGlassTier: Int
        get() = (floor(tier / 2.0) + tier % 2 - 2).toInt()

    val opticalTierName: Component
        get() = Component.translatable("gtnn.optical_glass_tier.desc." + this.opticalGlassTier)
            .withStyle(TooltipHelper.BLINKING_CYAN.current)

    @JvmRecord
    data class SimpleTierGlassType(
        override val typeName: String,
        override val tier: Int,
        override val isOpticalGlass: Boolean
    ) : ITierGlassType {
        override fun toString(): String {
            return this.typeName
        }

        override val texture: ResourceLocation
            get() = GTNN.id("block/casings/transparent/$typeName")
    }
}