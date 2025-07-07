package dev.arbor.gtnn.api.block

import net.minecraft.world.level.block.state.properties.IntegerProperty

object NNBlockProperties {
    val STRUCTURE_TIER: IntegerProperty = IntegerProperty.create("structure_tier", 0, 15)
}