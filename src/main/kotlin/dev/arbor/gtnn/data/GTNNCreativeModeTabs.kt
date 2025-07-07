package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs
import com.tterrag.registrate.util.entry.RegistryEntry
import dev.arbor.gtnn.GTNN.id
import dev.arbor.gtnn.GTNNRegistries.REGISTRATE
import net.minecraft.world.item.CreativeModeTab

object GTNNCreativeModeTabs {
    val MAIN_TAB: RegistryEntry<CreativeModeTab> = REGISTRATE.defaultCreativeTab(
        "main"
    ) { builder: CreativeModeTab.Builder ->
        builder.displayItems(GTCreativeModeTabs.RegistrateDisplayItemsGenerator(
            "main", REGISTRATE))
            .title(REGISTRATE.addLang(
                "itemGroup",
                id("main"),
                "GT--"))
            .icon { GTNNMachines.LargeNaquadahReactor.asStack() }
            .build()
    }.register()

    val GTNN_CIRCUIT_REFORM: RegistryEntry<CreativeModeTab> = REGISTRATE.defaultCreativeTab(
        "circuit_reform"
    ) { builder: CreativeModeTab.Builder -> builder
        .displayItems(GTCreativeModeTabs.RegistrateDisplayItemsGenerator(
            "circuit_reform", REGISTRATE))
            .title(REGISTRATE.addLang(
                "itemGroup",
                id("gtnn_circuit_reform"),
                "GT--" + " | Circuit Reform"))
            .icon { GTNNWrapItem.WRAP_BOARD_GOOWARE.asStack() }
            .build()
    }.register()
}
