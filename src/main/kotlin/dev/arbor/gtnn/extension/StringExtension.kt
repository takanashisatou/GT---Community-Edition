package dev.arbor.gtnn.extension

import dev.arbor.gtnn.GTNN
import net.minecraft.resources.ResourceLocation

object StringExtension {
    fun String.rl(): ResourceLocation {
        return ResourceLocation.tryParse(this) ?: ResourceLocation.fromNamespaceAndPath("minecraft", this)
    }

    fun String.rl(namespace: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(namespace, this)
    }

    fun String.gt(): ResourceLocation {
        return this.rl("gtceu")
    }

    fun String.nn(): ResourceLocation {
        return this.rl(GTNN.MOD_ID)
    }
}