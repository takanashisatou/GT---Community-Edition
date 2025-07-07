package dev.arbor.gtnn.data;

import com.gregtechceu.gtceu.api.cover.CoverDefinition
import com.gregtechceu.gtceu.client.renderer.cover.SimpleCoverRenderer
import com.gregtechceu.gtceu.common.data.GTCovers
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.extension.StringExtension.nn
import dev.arbor.gtnn.common.machine.EnderItemLinkCover
import java.util.function.Supplier


object GTNNCovers {

    val ENDER_ITEM_LINK: CoverDefinition = GTCovers.register(
        "ender_item_link".nn(),
        ::EnderItemLinkCover,
    ) {
        Supplier { SimpleCoverRenderer(GTNN.id("block/cover/overlay_ender_item_link")) }
    }

    fun init() {
    }
}

