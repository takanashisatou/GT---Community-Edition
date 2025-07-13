package dev.arbor.gtnn.common.machine.multiblock

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility
import com.gregtechceu.gtceu.api.pattern.Predicates
import com.gregtechceu.gtceu.common.data.GTBlocks
import com.gregtechceu.gtceu.common.data.GTMaterials
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import dev.arbor.gtnn.api.block.ITierType
import dev.arbor.gtnn.api.machine.feature.ICasingMachine
import dev.arbor.gtnn.api.pattern.NNFactoryPattern
import dev.arbor.gtnn.data.pattern.NNPredicates
import dev.arbor.gtnn.data.block.GTNNBlocks
import dev.arbor.gtnn.data.block.GTNNCasingBlocks
import dev.arbor.gtnn.data.GTNNMaterials

open class TierCasingElectricMultiblockMachine(holder: IMachineBlockEntity, typeName: String) :
    MultiStatsElectricMultiblockMachine(holder), ICasingMachine {
    protected val casingTierStats: ICasingMachine.CasingTierStats = ICasingMachine.CasingTierStats(this, typeName)

    override val tierType: ITierType
        get() = casingTierStats.tierType

    override val casingTier: Int
        get() = casingTierStats.casingTier

    override fun getFieldHolder(): ManagedFieldHolder {
        return MANAGED_FIELD_HOLDER
    }

    companion object {
        val MANAGED_FIELD_HOLDER: ManagedFieldHolder = ManagedFieldHolder(
            TierCasingElectricMultiblockMachine::class.java,
            MultiStatsElectricMultiblockMachine.Companion.MANAGED_FIELD_HOLDER
        )
    }
}

object ComponentAssemblyLinePattern {
    val Pattern = { definition: MultiblockMachineDefinition ->
        NNFactoryPattern.start()
            .aisle(
                "HHHHHHHHH",
                "H  KKK  H",
                "H       H",
                "H       H",
                "H       H",
                "H       H",
                "HH     HH",
                " HHHHHHH ",
                "         ",
                "         "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " ELHHHLE ",
                "         "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A D   D A",
                "AC     CA",
                "AC     CA",
                "HC     CH",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A  n n  A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "AG     GA",
                "AG HHH GA",
                "AG     GA",
                "AG     GA",
                "AG  C  GA",
                "HGG D GGH",
                "E GGDGG E",
                " EL   LE ",
                "   BBB   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A D   D A",
                "AC     CA",
                "AC     CA",
                "HC     CH",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "HG     GH",
                "HG HHH GH",
                "HG     GH",
                "HG     GH",
                "HG  C  GH",
                "HGG D GGH",
                "E GGDGG E",
                " EL   LE ",
                "   BBB   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A D   D A",
                "AC     CA",
                "AC     CA",
                "HC     CH",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "AG     GA",
                "AG HHH GA",
                "AG     GA",
                "AG     GA",
                "AG  C  GA",
                "HGG D GGH",
                "E GGDGG E",
                " EL   LE ",
                "   BBB   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A D   D A",
                "AC     CA",
                "AC     CA",
                "HC     CH",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "HG     GH",
                "HG HHH GH",
                "HG     GH",
                "HG     GH",
                "HG  C  GH",
                "HGG D GGH",
                "E GGDGG E",
                " EL   LE ",
                "   BBB   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A D   D A",
                "AC     CA",
                "AC     CA",
                "HC     CH",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "AG     GA",
                "AG HHH GA",
                "AG     GA",
                "AG     GA",
                "AG  C  GA",
                "HGG D GGH",
                "E GGDGG E",
                " EL   LE ",
                "   BBB   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A D   D A",
                "AC     CA",
                "AC     CA",
                "HC     CH",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "HG     GH",
                "HG HHH GH",
                "HG     GH",
                "HG     GH",
                "HG  C  GH",
                "HGG D GGH",
                "E GGDGG E",
                " EL   LE ",
                "   BBB   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A D   D A",
                "AC     CA",
                "AC     CA",
                "HC     CH",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "AG     GA",
                "AG HHH GA",
                "AG     GA",
                "AG     GA",
                "AG  C  GA",
                "HGG D GGH",
                "E GGDGG E",
                " EL   LE ",
                "   BBB   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A D   D A",
                "AC     CA",
                "AC     CA",
                "HC     CH",
                "E       E",
                " EL   LE ",
                "   HBH   "
            )
            .aisle(
                "MHHHHHHHM",
                "A       A",
                "A  HHH  A",
                "A       A",
                "A       A",
                "A       A",
                "H       H",
                "E       E",
                " ELHHHLE ",
                "         "
            )
            .aisle(
                "HHHHHHHHH",
                "H  N N  H",
                "H  JJJ  H",
                "H  JJJ  H",
                "H       H",
                "H       H",
                "HH III HH",
                " HHI~IHH ",
                "   III   ",
                "         "
            )
            .where('~', Predicates.controller(Predicates.blocks(definition.block)))
            .where('A', Predicates.blocks(GTNNBlocks.OSMIUM_BOROSILICATE_GLASS.get()))
            .where('H', Predicates.blocks(GTNNCasingBlocks.IRIDIUM_CASING.get()))
            .where('C', Predicates.blocks(GTBlocks.CASING_ASSEMBLY_LINE.get()))
            .where('D', Predicates.frames(GTNNMaterials.MARM200Steel))
            .where('G', Predicates.blocks(GTNNCasingBlocks.CASING_POLYBENZIMIDAZOLE_PIPE.get()))
            .where('E', Predicates.blocks(GTNNCasingBlocks.ADVANCED_FILTER_CASING.get()))
            .where('B', NNPredicates.componentAssemblyBlock)
            .where(
                'J',
                Predicates.blocks(GTNNCasingBlocks.IRIDIUM_CASING.get())
                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(6))
                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(6))
            )
            .where(
                'N',
                Predicates.frames(GTMaterials.TungstenSteel)
                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(2))
                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(2))
            )
            .where(
                'K',
                Predicates.blocks(GTNNCasingBlocks.IRIDIUM_CASING.get())
                    .or(
                        Predicates.abilities(PartAbility.EXPORT_ITEMS)
                            .setMaxGlobalLimited(3)
                            .setPreviewCount(1)
                    )
            )
            .where(
                'L',
                Predicates.blocks(GTNNCasingBlocks.IRIDIUM_CASING.get())
                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
            )
            .where(
                'I',
                Predicates.blocks(GTNNCasingBlocks.IRIDIUM_CASING.get())
                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
            )
            .where(
                'M',
                Predicates.blocks(GTNNCasingBlocks.IRIDIUM_CASING.get())
                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(6))
                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(6))
            )
            .where('n', Predicates.frames(GTMaterials.TungstenSteel))
            .build()
    }
}
