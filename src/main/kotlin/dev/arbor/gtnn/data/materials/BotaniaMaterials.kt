package dev.arbor.gtnn.data.materials

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet
import com.gregtechceu.gtceu.common.data.GTMaterials
import dev.arbor.gtnn.data.GTNNMaterials.*


object BotaniaMaterials {
    fun init() {
        ManaSteel =
            Builder("mana_steel").ingot().fluid().color(0x2177b8).iconSet(MaterialIconSet.SHINY)
                .appendFlags(
                    GTMaterials.EXT2_METAL,
                    MaterialFlags.GENERATE_FINE_WIRE,
                    MaterialFlags.GENERATE_GEAR,
                    MaterialFlags.GENERATE_FRAME
                ).buildAndRegister()
        TerraSteel =
            Builder("terra_steel").ingot().fluid().color(0x5dbe8a).iconSet(MaterialIconSet.SHINY)
                .appendFlags(
                    GTMaterials.EXT2_METAL,
                    MaterialFlags.GENERATE_FINE_WIRE,
                    MaterialFlags.GENERATE_GEAR,
                    MaterialFlags.GENERATE_FRAME
                ).buildAndRegister()
        Elementium =
            Builder("elementium").ingot().fluid().color(0xFFB3D6).iconSet(MaterialIconSet.SHINY)
                .appendFlags(
                    GTMaterials.EXT2_METAL,
                    MaterialFlags.GENERATE_FINE_WIRE,
                    MaterialFlags.GENERATE_GEAR,
                    MaterialFlags.GENERATE_FRAME
                ).buildAndRegister()
    }
}
