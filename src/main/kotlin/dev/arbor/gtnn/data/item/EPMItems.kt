package dev.arbor.gtnn.data.item

import com.gregtechceu.gtceu.api.item.ComponentItem
import com.gregtechceu.gtceu.data.recipe.CustomTags
import com.tterrag.registrate.util.entry.ItemEntry
import dev.arbor.gtnn.GTNNRegistries.REGISTRATE
import dev.arbor.gtnn.data.GTNNCreativeModeTabs.GTNN_CIRCUIT_REFORM
import dev.arbor.gtnn.data.item.GTNNItems.registerItemWithTooltip
import net.minecraft.network.chat.Component

object EPMItems {
    init {
        REGISTRATE.creativeModeTab { GTNN_CIRCUIT_REFORM }
    }

    //  Boards
    val GOOWARE_BOARD: ItemEntry<ComponentItem> = registerItemWithTooltip("gooware_circuit_board", 1).register()
    val OPTICAL_BOARD: ItemEntry<ComponentItem> = registerItemWithTooltip("optical_circuit_board", 1).register()
    val SPINTRONIC_BOARD: ItemEntry<ComponentItem> = registerItemWithTooltip("spintronic_circuit_board", 1).register()
    val GOOWARE_PRINTED_CIRCUIT_BOARD: ItemEntry<ComponentItem> =
        registerItemWithTooltip("gooware_printed_circuit_board", 1).register()
    val OPTICAL_PRINTED_CIRCUIT_BOARD: ItemEntry<ComponentItem> =
        registerItemWithTooltip("optical_printed_circuit_board", 1).register()
    val SPINTRONIC_PRINTED_CIRCUIT_BOARD: ItemEntry<ComponentItem> =
        registerItemWithTooltip("spintronic_printed_circuit_board", 1).register()

    //  Circuits

    val GOOWARE_PROCESSOR: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "gooware_processor", 1, Component.translatable("item.gtnn.circuit.zpm.desc")
    ).tag(CustomTags.ZPM_CIRCUITS).register()

    val GOOWARE_ASSEMBLY: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "gooware_assembly", 1, Component.translatable("item.gtnn.circuit.uv.desc")
    ).tag(CustomTags.UV_CIRCUITS).register()

    val GOOWARE_COMPUTER: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "gooware_computer", 1, Component.translatable("item.gtnn.circuit.uhv.desc")
    ).tag(CustomTags.UHV_CIRCUITS).register()

    val GOOWARE_MAINFRAME: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "gooware_mainframe", 1, Component.translatable("item.gtnn.circuit.uev.desc")
    ).tag(CustomTags.UEV_CIRCUITS).register()

    val OPTICAL_PROCESSOR: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "optical_processor", 1, Component.translatable("item.gtnn.circuit.uv.desc")
    ).tag(CustomTags.UV_CIRCUITS).register()

    val OPTICAL_ASSEMBLY: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "optical_assembly", 1, Component.translatable("item.gtnn.circuit.uhv.desc")
    ).tag(CustomTags.UHV_CIRCUITS).register()

    val OPTICAL_COMPUTER: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "optical_computer", 1, Component.translatable("item.gtnn.circuit.uev.desc")
    ).tag(CustomTags.UEV_CIRCUITS).register()

    val OPTICAL_MAINFRAME: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "optical_mainframe", 1, Component.translatable("item.gtnn.circuit.uiv.desc")
    ).tag(CustomTags.UIV_CIRCUITS).register()

    val SPINTRONIC_PROCESSOR: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "spintronic_processor", 1, Component.translatable("item.gtnn.circuit.uhv.desc")
    ).tag(CustomTags.UHV_CIRCUITS).register()

    val SPINTRONIC_ASSEMBLY: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "spintronic_assembly", 1, Component.translatable("item.gtnn.circuit.uev.desc")
    ).tag(CustomTags.UEV_CIRCUITS).register()

    val SPINTRONIC_COMPUTER: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "spintronic_computer", 1, Component.translatable("item.gtnn.circuit.uiv.desc")
    ).tag(CustomTags.UIV_CIRCUITS).register()

    val SPINTRONIC_MAINFRAME: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "spintronic_mainframe", 1, Component.translatable("item.gtnn.circuit.uxv.desc")
    ).tag(CustomTags.UXV_CIRCUITS).register()

    val COSMIC_PROCESSOR: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "cosmic_processor", 1, Component.translatable("item.gtnn.circuit.uev.desc")
    ).tag(CustomTags.UEV_CIRCUITS).register()

    val COSMIC_ASSEMBLY: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "cosmic_assembly", 1, Component.translatable("item.gtnn.circuit.uiv.desc")
    ).tag(CustomTags.UIV_CIRCUITS).register()

    val COSMIC_COMPUTER: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "cosmic_computer", 1, Component.translatable("item.gtnn.circuit.uxv.desc")
    ).tag(CustomTags.UXV_CIRCUITS).register()

    val COSMIC_MAINFRAME: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "cosmic_mainframe", 1, Component.translatable("item.gtnn.circuit.opv.desc")
    ).tag(CustomTags.OpV_CIRCUITS).register()

    val SUPRACAUSAL_PROCESSOR: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "supracausal_processor", 1, Component.translatable("item.gtnn.circuit.uiv.desc")
    ).tag(CustomTags.UIV_CIRCUITS).register()

    val SUPRACAUSAL_ASSEMBLY: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "supracausal_assembly", 1, Component.translatable("item.gtnn.circuit.uxv.desc")
    ).tag(CustomTags.UXV_CIRCUITS).register()

    val SUPRACAUSAL_COMPUTER: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "supracausal_computer", 1, Component.translatable("item.gtnn.circuit.opv.desc")
    ).tag(CustomTags.OpV_CIRCUITS).register()

    val SUPRACAUSAL_MAINFRAME: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "supracausal_mainframe", 1, Component.translatable("item.gtnn.circuit.max.desc")
    ).tag(CustomTags.MAX_CIRCUITS).register()

    //  Components

    val OPTICAL_TRANSISTOR: ItemEntry<ComponentItem> = registerItemWithTooltip("optical_smd_transistor", 1).register()

    val OPTICAL_RESISTOR: ItemEntry<ComponentItem> = registerItemWithTooltip("optical_smd_resistor", 1).register()

    val OPTICAL_CAPACITOR: ItemEntry<ComponentItem> = registerItemWithTooltip("optical_smd_capacitor", 1).register()

    val OPTICAL_DIODE: ItemEntry<ComponentItem> = registerItemWithTooltip("optical_smd_diode", 1).register()

    val OPTICAL_INDUCTOR: ItemEntry<ComponentItem> = registerItemWithTooltip("optical_smd_inductor", 1).register()

    val SPINTRONIC_TRANSISTOR: ItemEntry<ComponentItem> =
        registerItemWithTooltip("spintronic_smd_transistor", 1).register()

    val SPINTRONIC_RESISTOR: ItemEntry<ComponentItem> = registerItemWithTooltip("spintronic_smd_resistor", 1).register()

    val SPINTRONIC_CAPACITOR: ItemEntry<ComponentItem> =
        registerItemWithTooltip("spintronic_smd_capacitor", 1).register()

    val SPINTRONIC_DIODE: ItemEntry<ComponentItem> = registerItemWithTooltip("spintronic_smd_diode", 1).register()

    val SPINTRONIC_INDUCTOR: ItemEntry<ComponentItem> = registerItemWithTooltip("spintronic_smd_inductor", 1).register()

    val COSMIC_TRANSISTOR: ItemEntry<ComponentItem> = registerItemWithTooltip("cosmic_smd_transistor", 1).register()

    val COSMIC_RESISTOR: ItemEntry<ComponentItem> = registerItemWithTooltip("cosmic_smd_resistor", 1).register()

    val COSMIC_CAPACITOR: ItemEntry<ComponentItem> = registerItemWithTooltip("cosmic_smd_capacitor", 1).register()

    val COSMIC_DIODE: ItemEntry<ComponentItem> = registerItemWithTooltip("cosmic_smd_diode", 1).register()

    val COSMIC_INDUCTOR: ItemEntry<ComponentItem> = registerItemWithTooltip("cosmic_smd_inductor", 1).register()

    val SUPRACAUSAL_TRANSISTOR: ItemEntry<ComponentItem> =
        registerItemWithTooltip("supracausal_smd_transistor", 1).register()

    val SUPRACAUSAL_RESISTOR: ItemEntry<ComponentItem> =
        registerItemWithTooltip("supracausal_smd_resistor", 1).register()

    val SUPRACAUSAL_CAPACITOR: ItemEntry<ComponentItem> =
        registerItemWithTooltip("supracausal_smd_capacitor", 1).register()

    val SUPRACAUSAL_DIODE: ItemEntry<ComponentItem> = registerItemWithTooltip("supracausal_smd_diode", 1).register()

    val SUPRACAUSAL_INDUCTOR: ItemEntry<ComponentItem> =
        registerItemWithTooltip("supracausal_smd_inductor", 1).register()

    val MANIFOLD_OSCILLATORY_POWER_CELL: ItemEntry<ComponentItem> =
        registerItemWithTooltip("manifold_oscillatory_power_cell", 1).register()

    val NANO_PIC_WAFER: ItemEntry<ComponentItem> = registerItemWithTooltip("nano_pic_wafer", 1).register()

    val NANO_PIC_CHIP: ItemEntry<ComponentItem> = registerItemWithTooltip("nano_pic_chip", 1).register()

    val PICO_PIC_WAFER: ItemEntry<ComponentItem> = registerItemWithTooltip("pico_pic_wafer", 1).register()

    val PICO_PIC_CHIP: ItemEntry<ComponentItem> = registerItemWithTooltip("pico_pic_chip", 1).register()

    val DUBNIUM_BOULE: ItemEntry<ComponentItem> = registerItemWithTooltip("dubnium_boule", 1).register()

    val DUBNIUM_WAFER: ItemEntry<ComponentItem> = registerItemWithTooltip("dubnium_wafer", 1).register()

    val EUROPIUM_CUBIC_ZIRCONIA_BOULE: ItemEntry<ComponentItem> =
        registerItemWithTooltip("europium_cubic_zirconia_boule", 1).register()

    val EUROPIUM_CUBIC_ZIRCONIA_WAFER: ItemEntry<ComponentItem> =
        registerItemWithTooltip("europium_cubic_zirconia_wafer", 1).register()

    val CRYSTAL_INTERFACE_WAFER: ItemEntry<ComponentItem> =
        registerItemWithTooltip("crystal_interface_wafer", 1).register()

    val INTRAVITAL_SOC: ItemEntry<ComponentItem> = registerItemWithTooltip("intravital_soc", 1).register()

    val BOHRIUM_STRONTIUM_CARBONATE_BOULE: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "bohrium_strontium_carbonate_boule", 1
    ).register()

    val BOHRIUM_STRONTIUM_CARBONATE_WAFER: ItemEntry<ComponentItem> = registerItemWithTooltip(
        "bohrium_strontium_carbonate_wafer", 1
    ).register()

    val STRONTIUM_CARBONATE_WAFER: ItemEntry<ComponentItem> =
        registerItemWithTooltip("strontium_carbonate_wafer", 1).register()

    val OPTICAL_IMC_BOARD: ItemEntry<ComponentItem> = registerItemWithTooltip("optical_imc_board", 1).register()

    val PHOTOELECTRON_SOC: ItemEntry<ComponentItem> = registerItemWithTooltip("photoelectron_soc", 1).register()

    val DIAMOND_CHIP: ItemEntry<ComponentItem> = registerItemWithTooltip("diamond_chip", 1).register()

    val RUBY_CHIP: ItemEntry<ComponentItem> = registerItemWithTooltip("ruby_chip", 1).register()

    val SAPPHIRE_CHIP: ItemEntry<ComponentItem> = registerItemWithTooltip("sapphire_chip", 1).register()

    val DIAMOND_MODULATOR: ItemEntry<ComponentItem> = registerItemWithTooltip("diamond_modulator", 1).register()

    val RUBY_MODULATOR: ItemEntry<ComponentItem> = registerItemWithTooltip("ruby_modulator", 1).register()

    val SAPPHIRE_MODULATOR: ItemEntry<ComponentItem> = registerItemWithTooltip("sapphire_modulator", 1).register()

    fun init() {
    }
}