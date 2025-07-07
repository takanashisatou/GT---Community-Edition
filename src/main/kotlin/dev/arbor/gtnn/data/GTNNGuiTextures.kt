package dev.arbor.gtnn.data

import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture
import dev.arbor.gtnn.GTNN

object GTNNGuiTextures {
    val OVERLAY_INVENTORY_CONFIGURATOR: ResourceTexture = createTexture("overlay/inventory_configurator.png")
    val OVERLAY_PARALLEL_CONFIGURATOR: ResourceTexture = createTexture("overlay/parallel_configurator.png")

    @Suppress("SameParameterValue")
    private fun createTexture(imageLocation: String): ResourceTexture {
        return ResourceTexture("%s:textures/gui/%s".format(GTNN.MOD_ID, imageLocation))
    }
}