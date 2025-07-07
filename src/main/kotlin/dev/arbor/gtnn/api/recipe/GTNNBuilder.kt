package dev.arbor.gtnn.api.recipe

import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.fluids.FluidBuilder
import com.gregtechceu.gtceu.api.fluids.attribute.FluidAttributes
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys
import net.minecraft.resources.ResourceLocation

class GTNNBuilder(resourceLocation: ResourceLocation?) : Material.Builder(resourceLocation) {

    fun acid(): Material.Builder {
        fluid(FluidStorageKeys.LIQUID, FluidBuilder().attribute(FluidAttributes.ACID))
        return this
    }
}
