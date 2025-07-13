package dev.arbor.gtnn.api.vein;

import com.gregtechceu.gtceu.api.data.worldgen.ores.GeneratedVeinMetadata
import net.minecraft.util.RandomSource
import net.minecraft.world.level.levelgen.XoroshiroRandomSource

@JvmRecord
data class VeinConfiguration(val data: GeneratedVeinMetadata, val random:  RandomSource) {

    fun newRandom(): RandomSource {
        return XoroshiroRandomSource(random.nextLong());
    }
}
