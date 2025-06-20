package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.GTValues.*
import com.gregtechceu.gtceu.api.item.ComponentItem
import com.gregtechceu.gtceu.common.data.GTItems.*
import com.gregtechceu.gtceu.data.recipe.CustomTags
import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.entry.ItemEntry
import com.tterrag.registrate.util.nullness.NonNullFunction
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.GTNNRegistries.REGISTRATE
import dev.arbor.gtnn.client.renderer.item.SuperscriptItemBehavior
import dev.arbor.gtnn.data.GTNNCreativeModeTabs.GTNN_CIRCUIT_REFORM
import dev.arbor.gtnn.data.GTNNItems.registerItemWithTooltip
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike

object GTNNWrapItem {
    val WRAP_ITEM_MAP = Object2ObjectOpenHashMap<ItemLike, ItemLike>()
    val WRAP_CIRCUIT_MAP = Object2ObjectOpenHashMap<TagKey<Item>, ItemLike>()

    init {
        REGISTRATE.creativeModeTab { GTNN_CIRCUIT_REFORM }
    }

    // region Wrap Circuits
    val WRAP_CIRCUIT_ULV = regWrpCir("ulv_wrap_circuit", GTCEu.id("item/nand_chip"), ULV)
    val WRAP_CIRCUIT_LV = regWrpCir("lv_wrap_circuit", GTCEu.id("item/microchip_processor"), LV)
    val WRAP_CIRCUIT_MV = regWrpCir("mv_wrap_circuit", GTCEu.id("item/micro_processor"), MV)
    val WRAP_CIRCUIT_HV = regWrpCir("hv_wrap_circuit", GTCEu.id("item/nano_processor"), HV)
    val WRAP_CIRCUIT_EV = regWrpCir("ev_wrap_circuit", GTCEu.id("item/quantum_processor"), EV)
    val WRAP_CIRCUIT_IV = regWrpCir("iv_wrap_circuit", GTCEu.id("item/crystal_processor"), IV)
    val WRAP_CIRCUIT_LuV = regWrpCir("luv_wrap_circuit", GTCEu.id("item/wetware_processor"), LuV)
    val WRAP_CIRCUIT_ZPM = regWrpCir("zpm_wrap_circuit", GTNN.id("item/gooware_processor"), ZPM)
    val WRAP_CIRCUIT_UV = regWrpCir("uv_wrap_circuit", GTNN.id("item/optical_processor"), UV)
    val WRAP_CIRCUIT_UHV = regWrpCir("uhv_wrap_circuit", GTNN.id("item/spintronic_processor"), UHV)
    val WRAP_CIRCUIT_UEV = regWrpCir("uev_wrap_circuit", GTNN.id("item/cosmic_processor"), UEV)
    val WRAP_CIRCUIT_UIV = regWrpCir("uiv_wwrap_circuit", GTNN.id("item/supracausal_processor"), UIV)
    val WRAP_CIRCUIT_UXV = regWrpCir("uxv_wrap_circuit", GTNN.id("item/supracausal_assembly"), UXV)
    val WRAP_CIRCUIT_OpV = regWrpCir("opv_wrap_circuit", GTNN.id("item/supracausal_computer"), OpV)
    val CIRCUIT_MAX = regWrpCir("max_wrap_circuit", GTNN.id("item/supracausal_mainframe"), MAX)
    // endregion

    // region Wrap Boards
    val WRAP_BOARD_COATED = regWrpItem("resin_wrap_circuit_board", COATED_BOARD, 1)
    val WRAP_BOARD_PHENOLIC = regWrpItem("phenolic_wrap_circuit_board", PHENOLIC_BOARD, 2)
    val WRAP_BOARD_PLASTIC = regWrpItem("plastic_wrap_circuit_board", PLASTIC_BOARD, 3)
    val WRAP_BOARD_EPOXY = regWrpItem("epoxy_wrap_circuit_board", EPOXY_BOARD, 4)
    val WRAP_BOARD_FIBER = regWrpItem("fiber_reinforced_wrap_circuit_board", FIBER_BOARD, 5)
    val WRAP_BOARD_MULTILAYER_FIBER =
        regWrpItem("multilayer_fiber_reinforced_wrap_circuit_board", MULTILAYER_FIBER_BOARD, 6)
    val WRAP_BOARD_WETWARE = regWrpItem("wetware_wrap_circuit_board", WETWARE_BOARD, 7)
    val WRAP_BOARD_GOOWARE = regWrpItem("gooware_wrap_circuit_board", EPMItems.GOOWARE_BOARD, 8)
    val WRAP_BOARD_OPTICAL = regWrpItem("optical_wrap_circuit_board", EPMItems.OPTICAL_BOARD, 9)
    val WRAP_BOARD_SPINTRONIC = regWrpItem("spintronic_wrap_circuit_board", EPMItems.SPINTRONIC_BOARD, 10)
    // endregion

    // region Wrap Printed Circuit Boards
    val WRAP_CIRCUIT_BOARD_BASIC = regWrpItem("resin_wrap_printed_circuit_board", BASIC_CIRCUIT_BOARD, 1)
    val WRAP_CIRCUIT_BOARD_GOOD = regWrpItem("phenolic_wrap_printed_circuit_board", GOOD_CIRCUIT_BOARD, 2)
    val WRAP_CIRCUIT_BOARD_PLASTIC = regWrpItem("plastic_wrap_printed_circuit_board", PLASTIC_CIRCUIT_BOARD, 3)
    val WRAP_CIRCUIT_BOARD_ADVANCED = regWrpItem("epoxy_wrap_printed_circuit_board", ADVANCED_CIRCUIT_BOARD, 4)
    val WRAP_CIRCUIT_BOARD_EXTREME = regWrpItem("fiber_reinforced_wrap_printed_circuit_board", EXTREME_CIRCUIT_BOARD, 5)
    val WRAP_CIRCUIT_BOARD_ELITE =
        regWrpItem("multilayer_fiber_reinforced_wrap_printed_circuit_board", ELITE_CIRCUIT_BOARD, 6)
    val WRAP_CIRCUIT_BOARD_WETWARE = regWrpItem("wetware_wrap_printed_circuit_board", WETWARE_CIRCUIT_BOARD, 7)
    val WRAP_CIRCUIT_BOARD_GOOWARE =
        regWrpItem("gooware_wrap_printed_circuit_board", EPMItems.GOOWARE_PRINTED_CIRCUIT_BOARD, 8)
    val WRAP_CIRCUIT_BOARD_OPTICAL =
        regWrpItem("optical_wrap_printed_circuit_board", EPMItems.OPTICAL_PRINTED_CIRCUIT_BOARD, 9)
    val WRAP_CIRCUIT_BOARD_SPINTRONIC =
        regWrpItem("spintronic_wrap_printed_circuit_board", EPMItems.SPINTRONIC_PRINTED_CIRCUIT_BOARD, 10)
    // endregion

    // region Basic SMD Components
    val WRAP_SMD_TRANSISTOR = regWrpItem("wrap_smd_transistor", SMD_TRANSISTOR, 1)
    val WRAP_SMD_RESISTOR = regWrpItem("wrap_smd_resistor", SMD_RESISTOR, 1)
    val WRAP_SMD_CAPACITOR = regWrpItem("wrap_smd_capacitor", SMD_CAPACITOR, 1)
    val WRAP_SMD_DIODE = regWrpItem("wrap_smd_diode", SMD_DIODE, 1)
    val WRAP_SMD_INDUCTOR = regWrpItem("wrap_smd_inductor", SMD_INDUCTOR, 1)
    // endregion

    // region Advanced SMD Components
    val WRAP_SMD_TRANSISTOR_ADVANCED = regWrpItem("wrap_advanced_smd_transistor", ADVANCED_SMD_TRANSISTOR, 2)
    val WRAP_SMD_RESISTOR_ADVANCED = regWrpItem("wrap_advanced_smd_resistor", ADVANCED_SMD_RESISTOR, 2)
    val WRAP_SMD_CAPACITOR_ADVANCED = regWrpItem("wrap_advanced_smd_capacitor", ADVANCED_SMD_CAPACITOR, 2)
    val WRAP_SMD_DIODE_ADVANCED = regWrpItem("wrap_advanced_smd_diode", ADVANCED_SMD_DIODE, 2)
    val WRAP_SMD_INDUCTOR_ADVANCED = regWrpItem("wrap_advanced_smd_inductor", ADVANCED_SMD_INDUCTOR, 2)
    // endregion

    // region Optical SMD Components
    val WRAP_SMD_TRANSISTOR_OPTICAL = regWrpItem("wrap_optical_smd_transistor", EPMItems.OPTICAL_TRANSISTOR, 3)
    val WRAP_SMD_RESISTOR_OPTICAL = regWrpItem("wrap_optical_smd_resistor", EPMItems.OPTICAL_RESISTOR, 3)
    val WRAP_SMD_CAPACITOR_OPTICAL = regWrpItem("wrap_optical_smd_capacitor", EPMItems.OPTICAL_CAPACITOR, 3)
    val WRAP_SMD_DIODE_OPTICAL = regWrpItem("wrap_optical_smd_diode", EPMItems.OPTICAL_DIODE, 3)
    val WRAP_SMD_INDUCTOR_OPTICAL = regWrpItem("wrap_optical_smd_inductor", EPMItems.OPTICAL_INDUCTOR, 3)
    // endregion

    // region Spintronic SMD Components
    val WRAP_SMD_TRANSISTOR_SPINTRONIC = regWrpItem("wrap_spintronic_smd_transistor", EPMItems.SPINTRONIC_TRANSISTOR, 4)
    val WRAP_SMD_RESISTOR_SPINTRONIC = regWrpItem("wrap_spintronic_smd_resistor", EPMItems.SPINTRONIC_RESISTOR, 4)
    val WRAP_SMD_CAPACITOR_SPINTRONIC = regWrpItem("wrap_spintronic_smd_capacitor", EPMItems.SPINTRONIC_CAPACITOR, 4)
    val WRAP_SMD_DIODE_SPINTRONIC = regWrpItem("wrap_spintronic_smd_diode", EPMItems.SPINTRONIC_DIODE, 4)
    val WRAP_SMD_INDUCTOR_SPINTRONIC = regWrpItem("wrap_spintronic_smd_inductor", EPMItems.SPINTRONIC_INDUCTOR, 4)
    // endregion

    // region Cosmic SMD Components
    val WRAP_SMD_TRANSISTOR_COSMIC = regWrpItem("wrap_cosmic_smd_transistor", EPMItems.COSMIC_TRANSISTOR, 5)
    val WRAP_SMD_RESISTOR_COSMIC = regWrpItem("wrap_cosmic_smd_resistor", EPMItems.COSMIC_RESISTOR, 5)
    val WRAP_SMD_CAPACITOR_COSMIC = regWrpItem("wrap_cosmic_smd_capacitor", EPMItems.COSMIC_CAPACITOR, 5)
    val WRAP_SMD_DIODE_COSMIC = regWrpItem("wrap_cosmic_smd_diode", EPMItems.COSMIC_DIODE, 5)
    val WRAP_SMD_INDUCTOR_COSMIC = regWrpItem("wrap_cosmic_smd_inductor", EPMItems.COSMIC_INDUCTOR, 5)
    // endregion

    // region Chip Components
    val WRAP_CPU_CHIP = regWrpItem("warp_cpu_chip", CENTRAL_PROCESSING_UNIT)
    val WRAP_RAM_CHIP = regWrpItem("warp_ram_chip", RANDOM_ACCESS_MEMORY)
    val WRAP_ILC_CHIP = regWrpItem("wrap_ilc_chip", INTEGRATED_LOGIC_CIRCUIT)
    val WARP_NANO_CPU_CHIP = regWrpItem("warp_nano_cpu_chip", NANO_CENTRAL_PROCESSING_UNIT)
    val WARP_QBIT_CPU_CHIP = regWrpItem("warp_qbit_cpu_chip", QUBIT_CENTRAL_PROCESSING_UNIT)
    val WARP_SIMPLE_SOC = regWrpItem("warp_simple_soc", SIMPLE_SYSTEM_ON_CHIP)
    val WARP_SOC = regWrpItem("warp_soc", SYSTEM_ON_CHIP)
    val WARP_ADVANCED_SOC = regWrpItem("warp_advanced_soc", ADVANCED_SYSTEM_ON_CHIP)
    val WARP_HIGHLY_ADVANCED_SOC = regWrpItem("warp_highly_advanced_soc", HIGHLY_ADVANCED_SOC)
    val WARP_NAND_MEMORY_CHIP = regWrpItem("warp_nand_memory_chip", NAND_MEMORY_CHIP)
    val WARP_NOR_MEMORY_CHIP = regWrpItem("warp_nor_memory_chip", NOR_MEMORY_CHIP)
    // endregion

    fun init() {
    }

    private fun <T : Item?> createItem(
        name: String, factory: NonNullFunction<Item.Properties, T>
    ): ItemBuilder<T, Registrate> {
        return REGISTRATE.item(name, factory)
    }

    private fun regWrpItem(id: String, wrappedItem: ItemEntry<*>): ItemEntry<Item> {
        return createItem(id, ::Item).model(GTNNModels.wrapItemModel(wrappedItem.id.withPrefix("item/"))).register()
            .also { WRAP_ITEM_MAP[wrappedItem] = it }
    }

    private fun regWrpItem(id: String, wrappedItem: ItemEntry<*>, tier: Int): ItemEntry<ComponentItem> {
        return createItem(id, ComponentItem::create).model(GTNNModels.wrapItemModel(wrappedItem.id.withPrefix("item/")))
            .onRegister(GTNNItems.attachRenderer(SuperscriptItemBehavior.Number(tier))).register()
            .also { WRAP_ITEM_MAP[wrappedItem] = it }
    }

    private fun regWrpCir(
        id: String, wrappedTexture: ResourceLocation, voltage: Int
    ): ItemEntry<ComponentItem> {
        return createItem(id, ComponentItem::create).model(GTNNModels.wrapItemModel(wrappedTexture))
            .onRegister(GTNNItems.attachRenderer(SuperscriptItemBehavior.Voltage(voltage))).register()
            .also { WRAP_CIRCUIT_MAP[CIRCustomTags.CIRCUITS[voltage]] = it }
    }
}

object CIRCustomTags {
    val CIRCUITS: Array<TagKey<Item>> = arrayOf(
        CustomTags.ULV_CIRCUITS,
        CustomTags.LV_CIRCUITS,
        CustomTags.MV_CIRCUITS,
        CustomTags.HV_CIRCUITS,
        CustomTags.EV_CIRCUITS,
        CustomTags.IV_CIRCUITS,
        CustomTags.LuV_CIRCUITS,
        CustomTags.ZPM_CIRCUITS,
        CustomTags.UV_CIRCUITS,
        CustomTags.UHV_CIRCUITS,
        CustomTags.UEV_CIRCUITS,
        CustomTags.UIV_CIRCUITS,
        CustomTags.UXV_CIRCUITS,
        CustomTags.OpV_CIRCUITS,
        CustomTags.MAX_CIRCUITS
    )

    val BATTERIES: Array<TagKey<Item>> = arrayOf(
        CustomTags.ULV_BATTERIES,
        CustomTags.LV_BATTERIES,
        CustomTags.MV_BATTERIES,
        CustomTags.HV_BATTERIES,
        CustomTags.EV_BATTERIES,
        CustomTags.IV_BATTERIES,
        CustomTags.LuV_BATTERIES,
        CustomTags.ZPM_BATTERIES,
        CustomTags.UV_BATTERIES,
        CustomTags.UHV_BATTERIES
    )
}

object EPMItems {
    init {
        REGISTRATE.creativeModeTab { GTNNCreativeModeTabs.GTNN_CIRCUIT_REFORM }
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