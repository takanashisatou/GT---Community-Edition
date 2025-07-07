package dev.arbor.gtnn.data.tags

import com.gregtechceu.gtceu.api.data.tag.TagUtil
import com.tterrag.registrate.providers.RegistrateTagsProvider
import dev.arbor.gtnn.GTNNIntegration
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block

object GTNNTags {
    fun initBlock(provider: RegistrateTagsProvider<Block?>) {
        if (GTNNIntegration.isAdAstraLoaded()) {
            AdAstraTag.init(provider)
        }
    }

    val AD_ASTRA_STONES: TagKey<Block?> = TagUtil.createBlockTag("ad_astra_stones")
}