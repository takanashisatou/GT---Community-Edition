package dev.arbor.gtnn.data.item

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.item.ComponentItem
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate
import com.gregtechceu.gtceu.common.data.GTItems
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.entry.ItemEntry
import com.tterrag.registrate.util.nullness.NonNullFunction
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.GTNNRegistries
import dev.arbor.gtnn.client.renderer.item.SuperscriptItemBehavior
import dev.arbor.gtnn.data.GTNNCreativeModeTabs
import dev.arbor.gtnn.data.GTNNModels
import dev.arbor.gtnn.data.tags.CIRCustomTags
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike

object GTNNWrapItem {
    val WRAP_ITEM_MAP = Object2ObjectOpenHashMap<ItemLike, ItemLike>()
    val WRAP_CIRCUIT_MAP = Object2ObjectOpenHashMap<TagKey<Item>, ItemLike>()

    init {
        GTNNRegistries.REGISTRATE.creativeModeTab { GTNNCreativeModeTabs.GTNN_CIRCUIT_REFORM }
    }

    // region Wrap Circuits
    val WRAP_CIRCUIT_ULV = regWrpCir("ulv_wrap_circuit", GTCEu.id("item/nand_chip"), GTValues.ULV)
    val WRAP_CIRCUIT_LV = regWrpCir("lv_wrap_circuit", GTCEu.id("item/microchip_processor"), GTValues.LV)
    val WRAP_CIRCUIT_MV = regWrpCir("mv_wrap_circuit", GTCEu.id("item/micro_processor"), GTValues.MV)
    val WRAP_CIRCUIT_HV = regWrpCir("hv_wrap_circuit", GTCEu.id("item/nano_processor"), GTValues.HV)
    val WRAP_CIRCUIT_EV = regWrpCir("ev_wrap_circuit", GTCEu.id("item/quantum_processor"), GTValues.EV)
    val WRAP_CIRCUIT_IV = regWrpCir("iv_wrap_circuit", GTCEu.id("item/crystal_processor"), GTValues.IV)
    val WRAP_CIRCUIT_LuV = regWrpCir("luv_wrap_circuit", GTCEu.id("item/wetware_processor"), GTValues.LuV)
    val WRAP_CIRCUIT_ZPM = regWrpCir("zpm_wrap_circuit", GTNN.id("item/gooware_processor"), GTValues.ZPM)
    val WRAP_CIRCUIT_UV = regWrpCir("uv_wrap_circuit", GTNN.id("item/optical_processor"), GTValues.UV)
    val WRAP_CIRCUIT_UHV = regWrpCir("uhv_wrap_circuit", GTNN.id("item/spintronic_processor"), GTValues.UHV)
    val WRAP_CIRCUIT_UEV = regWrpCir("uev_wrap_circuit", GTNN.id("item/cosmic_processor"), GTValues.UEV)
    val WRAP_CIRCUIT_UIV = regWrpCir("uiv_wwrap_circuit", GTNN.id("item/supracausal_processor"), GTValues.UIV)
    val WRAP_CIRCUIT_UXV = regWrpCir("uxv_wrap_circuit", GTNN.id("item/supracausal_assembly"), GTValues.UXV)
    val WRAP_CIRCUIT_OpV = regWrpCir("opv_wrap_circuit", GTNN.id("item/supracausal_computer"), GTValues.OpV)
    val CIRCUIT_MAX = regWrpCir("max_wrap_circuit", GTNN.id("item/supracausal_mainframe"), GTValues.MAX)
    // endregion

    // region Wrap Boards
    val WRAP_BOARD_COATED = regWrpItem("resin_wrap_circuit_board", GTItems.COATED_BOARD, 1)
    val WRAP_BOARD_PHENOLIC = regWrpItem("phenolic_wrap_circuit_board", GTItems.PHENOLIC_BOARD, 2)
    val WRAP_BOARD_PLASTIC = regWrpItem("plastic_wrap_circuit_board", GTItems.PLASTIC_BOARD, 3)
    val WRAP_BOARD_EPOXY = regWrpItem("epoxy_wrap_circuit_board", GTItems.EPOXY_BOARD, 4)
    val WRAP_BOARD_FIBER = regWrpItem("fiber_reinforced_wrap_circuit_board", GTItems.FIBER_BOARD, 5)
    val WRAP_BOARD_MULTILAYER_FIBER =
        regWrpItem("multilayer_fiber_reinforced_wrap_circuit_board", GTItems.MULTILAYER_FIBER_BOARD, 6)
    val WRAP_BOARD_WETWARE = regWrpItem("wetware_wrap_circuit_board", GTItems.WETWARE_BOARD, 7)
    val WRAP_BOARD_GOOWARE = regWrpItem("gooware_wrap_circuit_board", EPMItems.GOOWARE_BOARD, 8)
    val WRAP_BOARD_OPTICAL = regWrpItem("optical_wrap_circuit_board", EPMItems.OPTICAL_BOARD, 9)
    val WRAP_BOARD_SPINTRONIC = regWrpItem("spintronic_wrap_circuit_board", EPMItems.SPINTRONIC_BOARD, 10)
    // endregion

    // region Wrap Printed Circuit Boards
    val WRAP_CIRCUIT_BOARD_BASIC = regWrpItem("resin_wrap_printed_circuit_board", GTItems.BASIC_CIRCUIT_BOARD, 1)
    val WRAP_CIRCUIT_BOARD_GOOD = regWrpItem("phenolic_wrap_printed_circuit_board", GTItems.GOOD_CIRCUIT_BOARD, 2)
    val WRAP_CIRCUIT_BOARD_PLASTIC = regWrpItem("plastic_wrap_printed_circuit_board", GTItems.PLASTIC_CIRCUIT_BOARD, 3)
    val WRAP_CIRCUIT_BOARD_ADVANCED = regWrpItem("epoxy_wrap_printed_circuit_board", GTItems.ADVANCED_CIRCUIT_BOARD, 4)
    val WRAP_CIRCUIT_BOARD_EXTREME = regWrpItem("fiber_reinforced_wrap_printed_circuit_board",
        GTItems.EXTREME_CIRCUIT_BOARD, 5)
    val WRAP_CIRCUIT_BOARD_ELITE =
        regWrpItem("multilayer_fiber_reinforced_wrap_printed_circuit_board", GTItems.ELITE_CIRCUIT_BOARD, 6)
    val WRAP_CIRCUIT_BOARD_WETWARE = regWrpItem("wetware_wrap_printed_circuit_board", GTItems.WETWARE_CIRCUIT_BOARD, 7)
    val WRAP_CIRCUIT_BOARD_GOOWARE =
        regWrpItem("gooware_wrap_printed_circuit_board", EPMItems.GOOWARE_PRINTED_CIRCUIT_BOARD, 8)
    val WRAP_CIRCUIT_BOARD_OPTICAL =
        regWrpItem("optical_wrap_printed_circuit_board", EPMItems.OPTICAL_PRINTED_CIRCUIT_BOARD, 9)
    val WRAP_CIRCUIT_BOARD_SPINTRONIC =
        regWrpItem("spintronic_wrap_printed_circuit_board", EPMItems.SPINTRONIC_PRINTED_CIRCUIT_BOARD, 10)
    // endregion

    // region Basic SMD Components
    val WRAP_SMD_TRANSISTOR = regWrpItem("wrap_smd_transistor", GTItems.SMD_TRANSISTOR, 1)
    val WRAP_SMD_RESISTOR = regWrpItem("wrap_smd_resistor", GTItems.SMD_RESISTOR, 1)
    val WRAP_SMD_CAPACITOR = regWrpItem("wrap_smd_capacitor", GTItems.SMD_CAPACITOR, 1)
    val WRAP_SMD_DIODE = regWrpItem("wrap_smd_diode", GTItems.SMD_DIODE, 1)
    val WRAP_SMD_INDUCTOR = regWrpItem("wrap_smd_inductor", GTItems.SMD_INDUCTOR, 1)
    // endregion

    // region Advanced SMD Components
    val WRAP_SMD_TRANSISTOR_ADVANCED = regWrpItem("wrap_advanced_smd_transistor", GTItems.ADVANCED_SMD_TRANSISTOR, 2)
    val WRAP_SMD_RESISTOR_ADVANCED = regWrpItem("wrap_advanced_smd_resistor", GTItems.ADVANCED_SMD_RESISTOR, 2)
    val WRAP_SMD_CAPACITOR_ADVANCED = regWrpItem("wrap_advanced_smd_capacitor", GTItems.ADVANCED_SMD_CAPACITOR, 2)
    val WRAP_SMD_DIODE_ADVANCED = regWrpItem("wrap_advanced_smd_diode", GTItems.ADVANCED_SMD_DIODE, 2)
    val WRAP_SMD_INDUCTOR_ADVANCED = regWrpItem("wrap_advanced_smd_inductor", GTItems.ADVANCED_SMD_INDUCTOR, 2)
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
    val WRAP_CPU_CHIP = regWrpItem("warp_cpu_chip", GTItems.CENTRAL_PROCESSING_UNIT)
    val WRAP_RAM_CHIP = regWrpItem("warp_ram_chip", GTItems.RANDOM_ACCESS_MEMORY)
    val WRAP_ILC_CHIP = regWrpItem("wrap_ilc_chip", GTItems.INTEGRATED_LOGIC_CIRCUIT)
    val WARP_NANO_CPU_CHIP = regWrpItem("warp_nano_cpu_chip", GTItems.NANO_CENTRAL_PROCESSING_UNIT)
    val WARP_QBIT_CPU_CHIP = regWrpItem("warp_qbit_cpu_chip", GTItems.QUBIT_CENTRAL_PROCESSING_UNIT)
    val WARP_SIMPLE_SOC = regWrpItem("warp_simple_soc", GTItems.SIMPLE_SYSTEM_ON_CHIP)
    val WARP_SOC = regWrpItem("warp_soc", GTItems.SYSTEM_ON_CHIP)
    val WARP_ADVANCED_SOC = regWrpItem("warp_advanced_soc", GTItems.ADVANCED_SYSTEM_ON_CHIP)
    val WARP_HIGHLY_ADVANCED_SOC = regWrpItem("warp_highly_advanced_soc", GTItems.HIGHLY_ADVANCED_SOC)
    val WARP_NAND_MEMORY_CHIP = regWrpItem("warp_nand_memory_chip", GTItems.NAND_MEMORY_CHIP)
    val WARP_NOR_MEMORY_CHIP = regWrpItem("warp_nor_memory_chip", GTItems.NOR_MEMORY_CHIP)
    // endregion

    fun init() {
    }

    private fun <T : Item> createItem(
        name: String, factory: NonNullFunction<Item.Properties, T>
    ): ItemBuilder<T, GTRegistrate> {
        return GTNNRegistries.REGISTRATE.item(name, factory)
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