package dev.arbor.gtnn.api.block

import net.minecraft.resources.ResourceLocation
import javax.annotation.Nonnull

interface IChemicalPlantCasing: ITierType {
    val texture: ResourceLocation

    data class CPCasingType(override val typeName: String, override val tier: Int, override val texture: ResourceLocation) :
        IChemicalPlantCasing {

        @Nonnull
        override fun toString(): String {
            return typeName
        }
    }
}