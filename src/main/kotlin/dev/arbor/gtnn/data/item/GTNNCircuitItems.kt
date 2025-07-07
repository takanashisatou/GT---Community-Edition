package dev.arbor.gtnn.data.item

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate
import com.gregtechceu.gtceu.common.data.GTItems
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.entry.ItemEntry
import com.tterrag.registrate.util.nullness.NonNullFunction
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.GTNNRegistries
import dev.arbor.gtnn.common.item.EtchedItem
import dev.arbor.gtnn.data.GTNNCreativeModeTabs
import dev.arbor.gtnn.data.GTNNModels
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

object GTNNCircuitItems {
    val ETCHED_CIRCUIT_MAP = Object2ObjectOpenHashMap<ItemEntry<out Item>, ItemEntry<EtchedItem>>()
    val ETCHED_CIRCUIT_BOARD_MAP = Object2ObjectOpenHashMap<ItemEntry<out Item>, ItemEntry<EtchedItem>>()
    val ETCHED_CIRCUIT_BASE_MAP = Object2ObjectOpenHashMap<ItemEntry<out Item>, ItemEntry<EtchedItem>>()

    init {
        GTNNRegistries.REGISTRATE.creativeModeTab { GTNNCreativeModeTabs.GTNN_CIRCUIT_REFORM }
    }

    // region Micro Circuit Items
    val ETCHED_MICRO_PROCESSOR = registerEtchedCircuit(
        GTItems.PROCESSOR_MV,
        GTNN.id("item/etched/micro"),
        "processor",
        "mv"
    )
    val ETCHED_MICRO_PROCESSOR_ASSEMBLY = registerEtchedCircuit(
        GTItems.PROCESSOR_ASSEMBLY_HV,
        GTNN.id("item/etched/micro"),
        "processor_assembly",
        "hv"
    )
    val ETCHED_MICRO_COMPUTER = registerEtchedCircuit(
        GTItems.WORKSTATION_EV,
        GTNN.id("item/etched/micro"),
        "computer",
        "ev"
    )
    val ETCHED_MICRO_MAINFRAME = registerEtchedCircuit(
        GTItems.MAINFRAME_IV,
        GTNN.id("item/etched/micro"),
        "mainframe",
        "iv"
    )

    val ETCHED_MICRO_PROCESSOR_BOARD = registerEtchedCircuitBoard(
        GTItems.PROCESSOR_MV,
        GTNN.id("item/etched_board/micro"),
        "processor",
        "mv"
    )
    val ETCHED_MICRO_PROCESSOR_ASSEMBLY_BOARD = registerEtchedCircuitBoard(
        GTItems.PROCESSOR_ASSEMBLY_HV,
        GTNN.id("item/etched_board/micro"),
        "processor_assembly",
        "hv"
    )
    val ETCHED_MICRO_COMPUTER_BOARD = registerEtchedCircuitBoard(
        GTItems.WORKSTATION_EV,
        GTNN.id("item/etched_board/micro"),
        "computer",
        "ev"
    )
    val ETCHED_MICRO_MAINFRAME_BOARD = registerEtchedCircuitBoard(
        GTItems.MAINFRAME_IV,
        GTNN.id("item/etched_board/micro"),
        "mainframe",
        "iv"
    )

    val ETCHED_MICRO_PROCESSOR_BASE = registerEtchedCircuitBase(
        GTItems.PROCESSOR_MV,
        GTNN.id("item/etched_base/micro"),
        "processor",
        "mv"
    )
    val ETCHED_MICRO_PROCESSOR_ASSEMBLY_BASE = registerEtchedCircuitBase(
        GTItems.PROCESSOR_ASSEMBLY_HV,
        GTNN.id("item/etched_base/micro"),
        "processor_assembly",
        "hv"
    )
    val ETCHED_MICRO_COMPUTER_BASE = registerEtchedCircuitBase(
        GTItems.WORKSTATION_EV,
        GTNN.id("item/etched_base/micro"),
        "computer",
        "ev"
    )
    val ETCHED_MICRO_MAINFRAME_BASE = registerEtchedCircuitBase(
        GTItems.MAINFRAME_IV,
        GTNN.id("item/etched_base/micro"),
        "mainframe",
        "iv"
    )
    // endregion

    // region Nano Circuit Items
    val ETCHED_NANO_PROCESSOR = registerEtchedCircuit(
        GTItems.NANO_PROCESSOR_HV,
        GTNN.id("item/etched/nano"),
        "processor",
        "hv"
    )
    val ETCHED_NANO_PROCESSOR_ASSEMBLY = registerEtchedCircuit(
        GTItems.NANO_PROCESSOR_ASSEMBLY_EV,
        GTNN.id("item/etched/nano"),
        "processor_assembly",
        "ev"
    )
    val ETCHED_NANO_COMPUTER = registerEtchedCircuit(
        GTItems.NANO_COMPUTER_IV,
        GTNN.id("item/etched/nano"),
        "computer",
        "iv"
    )
    val ETCHED_NANO_MAINFRAME = registerEtchedCircuit(
        GTItems.WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched/nano"),
        "mainframe",
        "luv"
    )

    val ETCHED_NANO_PROCESSOR_BOARD = registerEtchedCircuitBoard(
        GTItems.NANO_PROCESSOR_HV,
        GTNN.id("item/etched_board/nano"),
        "processor",
        "hv"
    )
    val ETCHED_NANO_PROCESSOR_ASSEMBLY_BOARD = registerEtchedCircuitBoard(
        GTItems.NANO_PROCESSOR_ASSEMBLY_EV,
        GTNN.id("item/etched_board/nano"),
        "processor_assembly",
        "ev"
    )
    val ETCHED_NANO_COMPUTER_BOARD = registerEtchedCircuitBoard(
        GTItems.NANO_COMPUTER_IV,
        GTNN.id("item/etched_board/nano"),
        "computer",
        "iv"
    )
    val ETCHED_NANO_MAINFRAME_BOARD = registerEtchedCircuitBoard(
        GTItems.WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_board/nano"),
        "mainframe",
        "luv"
    )

    val ETCHED_NANO_PROCESSOR_BASE = registerEtchedCircuitBase(
        GTItems.NANO_PROCESSOR_HV,
        GTNN.id("item/etched_base/nano"),
        "processor",
        "hv"
    )
    val ETCHED_NANO_PROCESSOR_ASSEMBLY_BASE = registerEtchedCircuitBase(
        GTItems.NANO_PROCESSOR_ASSEMBLY_EV,
        GTNN.id("item/etched_base/nano"),
        "processor_assembly",
        "ev"
    )
    val ETCHED_NANO_COMPUTER_BASE = registerEtchedCircuitBase(
        GTItems.NANO_COMPUTER_IV,
        GTNN.id("item/etched_base/nano"),
        "computer",
        "iv"
    )
    val ETCHED_NANO_MAINFRAME_BASE = registerEtchedCircuitBase(
        GTItems.WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_base/nano"),
        "mainframe",
        "luv"
    )
    // endregion

    // region Quantum Circuit Items
    val ETCHED_QUANTUM_PROCESSOR = registerEtchedCircuit(
        GTItems.QUANTUM_PROCESSOR_EV,
        GTNN.id("item/etched/quantum"),
        "processor",
        "ev"
    )
    val ETCHED_QUANTUM_PROCESSOR_ASSEMBLY = registerEtchedCircuit(
        GTItems.QUANTUM_ASSEMBLY_IV,
        GTNN.id("item/etched/quantum"),
        "processor_assembly",
        "iv"
    )
    val ETCHED_QUANTUM_COMPUTER = registerEtchedCircuit(
        GTItems.WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched/quantum"),
        "computer",
        "luv"
    )
    val ETCHED_QUANTUM_MAINFRAME = registerEtchedCircuit(
        GTItems.QUANTUM_MAINFRAME_ZPM,
        GTNN.id("item/etched/quantum"),
        "mainframe",
        "zpm"
    )

    val ETCHED_QUANTUM_PROCESSOR_BOARD = registerEtchedCircuitBoard(
        GTItems.QUANTUM_PROCESSOR_EV,
        GTNN.id("item/etched_board/quantum"),
        "processor",
        "ev"
    )
    val ETCHED_QUANTUM_PROCESSOR_ASSEMBLY_BOARD = registerEtchedCircuitBoard(
        GTItems.QUANTUM_ASSEMBLY_IV,
        GTNN.id("item/etched_board/quantum"),
        "processor_assembly",
        "iv"
    )
    val ETCHED_QUANTUM_COMPUTER_BOARD = registerEtchedCircuitBoard(
        GTItems.WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_board/quantum"),
        "computer",
        "luv"
    )
    val ETCHED_QUANTUM_MAINFRAME_BOARD = registerEtchedCircuitBoard(
        GTItems.QUANTUM_MAINFRAME_ZPM,
        GTNN.id("item/etched_board/quantum"),
        "mainframe",
        "zpm"
    )

    val ETCHED_QUANTUM_PROCESSOR_BASE = registerEtchedCircuitBase(
        GTItems.QUANTUM_PROCESSOR_EV,
        GTNN.id("item/etched_base/quantum"),
        "processor",
        "ev"
    )
    val ETCHED_QUANTUM_PROCESSOR_ASSEMBLY_BASE = registerEtchedCircuitBase(
        GTItems.QUANTUM_ASSEMBLY_IV,
        GTNN.id("item/etched_base/quantum"),
        "processor_assembly",
        "iv"
    )
    val ETCHED_QUANTUM_COMPUTER_BASE = registerEtchedCircuitBase(
        GTItems.WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_base/quantum"),
        "computer",
        "luv"
    )
    val ETCHED_QUANTUM_MAINFRAME_BASE = registerEtchedCircuitBase(
        GTItems.QUANTUM_MAINFRAME_ZPM,
        GTNN.id("item/etched_base/quantum"),
        "mainframe",
        "zpm"
    )
    // endregion

    // region Crystal Circuit Items
    val ETCHED_CRYSTAL_PROCESSOR = registerEtchedCircuit(
        GTItems.CRYSTAL_PROCESSOR_IV,
        GTNN.id("item/etched/crystal"),
        "processor",
        "iv"
    )
    val ETCHED_CRYSTAL_PROCESSOR_ASSEMBLY = registerEtchedCircuit(
        GTItems.WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched/crystal"),
        "processor_assembly",
        "luv"
    )
    val ETCHED_CRYSTAL_COMPUTER = registerEtchedCircuit(
        GTItems.CRYSTAL_COMPUTER_ZPM,
        GTNN.id("item/etched/crystal"),
        "computer",
        "zpm"
    )
    val ETCHED_CRYSTAL_MAINFRAME = registerEtchedCircuit(
        GTItems.CRYSTAL_MAINFRAME_UV,
        GTNN.id("item/etched/crystal"),
        "mainframe",
        "uv"
    )

    val ETCHED_CRYSTAL_PROCESSOR_BOARD = registerEtchedCircuitBoard(
        GTItems.CRYSTAL_PROCESSOR_IV,
        GTNN.id("item/etched_board/crystal"),
        "processor",
        "iv"
    )
    val ETCHED_CRYSTAL_PROCESSOR_ASSEMBLY_BOARD = registerEtchedCircuitBoard(
        GTItems.WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_board/crystal"),
        "processor_assembly",
        "luv"
    )
    val ETCHED_CRYSTAL_COMPUTER_BOARD = registerEtchedCircuitBoard(
        GTItems.CRYSTAL_COMPUTER_ZPM,
        GTNN.id("item/etched_board/crystal"),
        "computer",
        "zpm"
    )
    val ETCHED_CRYSTAL_MAINFRAME_BOARD = registerEtchedCircuitBoard(
        GTItems.CRYSTAL_MAINFRAME_UV,
        GTNN.id("item/etched_board/crystal"),
        "mainframe",
        "uv"
    )

    val ETCHED_CRYSTAL_PROCESSOR_BASE = registerEtchedCircuitBase(
        GTItems.CRYSTAL_PROCESSOR_IV,
        GTNN.id("item/etched_base/crystal"),
        "processor",
        "iv"
    )
    val ETCHED_CRYSTAL_PROCESSOR_ASSEMBLY_BASE = registerEtchedCircuitBase(
        GTItems.WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_base/crystal"),
        "processor_assembly",
        "luv"
    )
    val ETCHED_CRYSTAL_COMPUTER_BASE = registerEtchedCircuitBase(
        GTItems.CRYSTAL_COMPUTER_ZPM,
        GTNN.id("item/etched_base/crystal"),
        "computer",
        "zpm"
    )
    val ETCHED_CRYSTAL_MAINFRAME_BASE = registerEtchedCircuitBase(
        GTItems.CRYSTAL_MAINFRAME_UV,
        GTNN.id("item/etched_base/crystal"),
        "mainframe",
        "uv"
    )
    // endregion

    // region Wetware Circuit Items
    val ETCHED_WETWARE_PROCESSOR = registerEtchedCircuit(
        GTItems.WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched/wetware"),
        "processor",
        "luv"
    )
    val ETCHED_WETWARE_PROCESSOR_ASSEMBLY = registerEtchedCircuit(
        GTItems.WETWARE_PROCESSOR_ASSEMBLY_ZPM,
        GTNN.id("item/etched/wetware"),
        "processor_assembly",
        "zpm"
    )
    val ETCHED_WETWARE_COMPUTER = registerEtchedCircuit(
        GTItems.WETWARE_SUPER_COMPUTER_UV,
        GTNN.id("item/etched/wetware"),
        "computer",
        "uv"
    )
    val ETCHED_WETWARE_MAINFRAME = registerEtchedCircuit(
        GTItems.WETWARE_MAINFRAME_UHV,
        GTNN.id("item/etched/wetware"),
        "mainframe",
        "uhv"
    )

    val ETCHED_WETWARE_PROCESSOR_BOARD = registerEtchedCircuitBoard(
        GTItems.WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_board/wetware"),
        "processor",
        "luv"
    )
    val ETCHED_WETWARE_PROCESSOR_ASSEMBLY_BOARD = registerEtchedCircuitBoard(
        GTItems.WETWARE_PROCESSOR_ASSEMBLY_ZPM,
        GTNN.id("item/etched_board/wetware"),
        "processor_assembly",
        "zpm"
    )
    val ETCHED_WETWARE_COMPUTER_BOARD = registerEtchedCircuitBoard(
        GTItems.WETWARE_SUPER_COMPUTER_UV,
        GTNN.id("item/etched_board/wetware"),
        "computer",
        "uv"
    )
    val ETCHED_WETWARE_MAINFRAME_BOARD = registerEtchedCircuitBoard(
        GTItems.WETWARE_MAINFRAME_UHV,
        GTNN.id("item/etched_board/wetware"),
        "mainframe",
        "uhv"
    )

    val ETCHED_WETWARE_PROCESSOR_BASE = registerEtchedCircuitBase(
        GTItems.WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_base/wetware"),
        "processor",
        "luv"
    )
    val ETCHED_WETWARE_PROCESSOR_ASSEMBLY_BASE = registerEtchedCircuitBase(
        GTItems.WETWARE_PROCESSOR_ASSEMBLY_ZPM,
        GTNN.id("item/etched_base/wetware"),
        "processor_assembly",
        "zpm"
    )
    val ETCHED_WETWARE_COMPUTER_BASE = registerEtchedCircuitBase(
        GTItems.WETWARE_SUPER_COMPUTER_UV,
        GTNN.id("item/etched_base/wetware"),
        "computer",
        "uv"
    )
    val ETCHED_WETWARE_MAINFRAME_BASE = registerEtchedCircuitBase(
        GTItems.WETWARE_MAINFRAME_UHV,
        GTNN.id("item/etched_base/wetware"),
        "mainframe",
        "uhv"
    )
    // endregion

    fun init() {
    }

    private fun <T : Item> createItem(name: String, factory: NonNullFunction<Item.Properties, T>)
    : ItemBuilder<T, GTRegistrate> {
        return GTNNRegistries.REGISTRATE.item(name, factory)
    }

    private fun registerEtchedCircuit(
        circuit: ItemEntry<*>,
        baseTexture: ResourceLocation,
        circuitType: String,
        voltage: String
    ): ItemEntry<EtchedItem> {
        val entry = createItem("etched_${circuit.id.path}") {
                props ->
            EtchedItem(props, { circuit }, "etched")
            }
            .model(
                GTNNModels.simpleCustomModel(
                    ResourceLocation("item/generated"),
                baseTexture,
                GTNN.id("item/voltage_overlay/$voltage"),
                GTNN.id("item/circuit_type_overlay/$circuitType")))
            .register()

        ETCHED_CIRCUIT_MAP[circuit] = entry
        return entry
    }

    private fun registerEtchedCircuitBoard(
        circuit: ItemEntry<*>,
        baseTexture: ResourceLocation,
        circuitType: String,
        voltage: String
    ): ItemEntry<EtchedItem> {
        val entry = createItem("etched_${circuit.id.path}_board") { props : Item.Properties ->
            EtchedItem(props, circuit, "etched_board")
            }
            .model(
                GTNNModels.simpleCustomModel(
                    ResourceLocation("item/generated"),
                baseTexture,
                GTNN.id("item/voltage_overlay/$voltage"),
                GTNN.id("item/circuit_type_overlay/$circuitType")
            ))
            .register()

        ETCHED_CIRCUIT_BOARD_MAP[circuit] = entry
        return entry
    }

    private fun registerEtchedCircuitBase(
        circuit: ItemEntry<*>,
        baseTexture: ResourceLocation,
        circuitType: String,
        voltage: String
    ): ItemEntry<EtchedItem> {
        val entry = createItem("etched_${circuit.id.path}_base") { props : Item.Properties ->
            EtchedItem(props, circuit, "etched_base")
            }
            .model(
                GTNNModels.simpleCustomModel(
                    ResourceLocation("item/generated"),
                baseTexture,
                GTNN.id("item/voltage_overlay/$voltage"),
                GTNN.id("item/circuit_type_overlay/$circuitType")
            ))
            .register()

        ETCHED_CIRCUIT_BASE_MAP[circuit] = entry
        return entry
    }
}