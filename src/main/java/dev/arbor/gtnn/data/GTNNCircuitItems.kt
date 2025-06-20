package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.common.data.GTItems.*
import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.entry.ItemEntry
import com.tterrag.registrate.util.nullness.NonNullFunction
import dev.arbor.gtnn.GTNN
import dev.arbor.gtnn.GTNNRegistries.REGISTRATE
import dev.arbor.gtnn.api.item.EtchedItem
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

object GTNNCircuitItems {
    val ETCHED_CIRCUIT_MAP = Object2ObjectOpenHashMap<ItemEntry<out Item>, ItemEntry<EtchedItem>>()
    val ETCHED_CIRCUIT_BOARD_MAP = Object2ObjectOpenHashMap<ItemEntry<out Item>, ItemEntry<EtchedItem>>()
    val ETCHED_CIRCUIT_BASE_MAP = Object2ObjectOpenHashMap<ItemEntry<out Item>, ItemEntry<EtchedItem>>()

    init {
        REGISTRATE.creativeModeTab { GTNNCreativeModeTabs.GTNN_CIRCUIT_REFORM }
    }

    // region Micro Circuit Items
    val ETCHED_MICRO_PROCESSOR = registerEtchedCircuit(
        PROCESSOR_MV,
        GTNN.id("item/etched/micro"),
        "processor",
        "mv"
    )
    val ETCHED_MICRO_PROCESSOR_ASSEMBLY = registerEtchedCircuit(
        PROCESSOR_ASSEMBLY_HV,
        GTNN.id("item/etched/micro"),
        "processor_assembly",
        "hv"
    )
    val ETCHED_MICRO_COMPUTER = registerEtchedCircuit(
        WORKSTATION_EV,
        GTNN.id("item/etched/micro"),
        "computer",
        "ev"
    )
    val ETCHED_MICRO_MAINFRAME = registerEtchedCircuit(
        MAINFRAME_IV,
        GTNN.id("item/etched/micro"),
        "mainframe",
        "iv"
    )

    val ETCHED_MICRO_PROCESSOR_BOARD = registerEtchedCircuitBoard(
        PROCESSOR_MV,
        GTNN.id("item/etched_board/micro"),
        "processor",
        "mv"
    )
    val ETCHED_MICRO_PROCESSOR_ASSEMBLY_BOARD = registerEtchedCircuitBoard(
        PROCESSOR_ASSEMBLY_HV,
        GTNN.id("item/etched_board/micro"),
        "processor_assembly",
        "hv"
    )
    val ETCHED_MICRO_COMPUTER_BOARD = registerEtchedCircuitBoard(
        WORKSTATION_EV,
        GTNN.id("item/etched_board/micro"),
        "computer",
        "ev"
    )
    val ETCHED_MICRO_MAINFRAME_BOARD = registerEtchedCircuitBoard(
        MAINFRAME_IV,
        GTNN.id("item/etched_board/micro"),
        "mainframe",
        "iv"
    )

    val ETCHED_MICRO_PROCESSOR_BASE = registerEtchedCircuitBase(
        PROCESSOR_MV,
        GTNN.id("item/etched_base/micro"),
        "processor",
        "mv"
    )
    val ETCHED_MICRO_PROCESSOR_ASSEMBLY_BASE = registerEtchedCircuitBase(
        PROCESSOR_ASSEMBLY_HV,
        GTNN.id("item/etched_base/micro"),
        "processor_assembly",
        "hv"
    )
    val ETCHED_MICRO_COMPUTER_BASE = registerEtchedCircuitBase(
        WORKSTATION_EV,
        GTNN.id("item/etched_base/micro"),
        "computer",
        "ev"
    )
    val ETCHED_MICRO_MAINFRAME_BASE = registerEtchedCircuitBase(
        MAINFRAME_IV,
        GTNN.id("item/etched_base/micro"),
        "mainframe",
        "iv"
    )
    // endregion

    // region Nano Circuit Items
    val ETCHED_NANO_PROCESSOR = registerEtchedCircuit(
        NANO_PROCESSOR_HV,
        GTNN.id("item/etched/nano"),
        "processor",
        "hv"
    )
    val ETCHED_NANO_PROCESSOR_ASSEMBLY = registerEtchedCircuit(
        NANO_PROCESSOR_ASSEMBLY_EV,
        GTNN.id("item/etched/nano"),
        "processor_assembly",
        "ev"
    )
    val ETCHED_NANO_COMPUTER = registerEtchedCircuit(
        NANO_COMPUTER_IV,
        GTNN.id("item/etched/nano"),
        "computer",
        "iv"
    )
    val ETCHED_NANO_MAINFRAME = registerEtchedCircuit(
        WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched/nano"),
        "mainframe",
        "luv"
    )

    val ETCHED_NANO_PROCESSOR_BOARD = registerEtchedCircuitBoard(
        NANO_PROCESSOR_HV,
        GTNN.id("item/etched_board/nano"),
        "processor",
        "hv"
    )
    val ETCHED_NANO_PROCESSOR_ASSEMBLY_BOARD = registerEtchedCircuitBoard(
        NANO_PROCESSOR_ASSEMBLY_EV,
        GTNN.id("item/etched_board/nano"),
        "processor_assembly",
        "ev"
    )
    val ETCHED_NANO_COMPUTER_BOARD = registerEtchedCircuitBoard(
        NANO_COMPUTER_IV,
        GTNN.id("item/etched_board/nano"),
        "computer",
        "iv"
    )
    val ETCHED_NANO_MAINFRAME_BOARD = registerEtchedCircuitBoard(
        WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_board/nano"),
        "mainframe",
        "luv"
    )

    val ETCHED_NANO_PROCESSOR_BASE = registerEtchedCircuitBase(
        NANO_PROCESSOR_HV,
        GTNN.id("item/etched_base/nano"),
        "processor",
        "hv"
    )
    val ETCHED_NANO_PROCESSOR_ASSEMBLY_BASE = registerEtchedCircuitBase(
        NANO_PROCESSOR_ASSEMBLY_EV,
        GTNN.id("item/etched_base/nano"),
        "processor_assembly",
        "ev"
    )
    val ETCHED_NANO_COMPUTER_BASE = registerEtchedCircuitBase(
        NANO_COMPUTER_IV,
        GTNN.id("item/etched_base/nano"),
        "computer",
        "iv"
    )
    val ETCHED_NANO_MAINFRAME_BASE = registerEtchedCircuitBase(
        WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_base/nano"),
        "mainframe",
        "luv"
    )
    // endregion

    // region Quantum Circuit Items
    val ETCHED_QUANTUM_PROCESSOR = registerEtchedCircuit(
        QUANTUM_PROCESSOR_EV,
        GTNN.id("item/etched/quantum"),
        "processor",
        "ev"
    )
    val ETCHED_QUANTUM_PROCESSOR_ASSEMBLY = registerEtchedCircuit(
        QUANTUM_ASSEMBLY_IV,
        GTNN.id("item/etched/quantum"),
        "processor_assembly",
        "iv"
    )
    val ETCHED_QUANTUM_COMPUTER = registerEtchedCircuit(
        WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched/quantum"),
        "computer",
        "luv"
    )
    val ETCHED_QUANTUM_MAINFRAME = registerEtchedCircuit(
        QUANTUM_MAINFRAME_ZPM,
        GTNN.id("item/etched/quantum"),
        "mainframe",
        "zpm"
    )

    val ETCHED_QUANTUM_PROCESSOR_BOARD = registerEtchedCircuitBoard(
        QUANTUM_PROCESSOR_EV,
        GTNN.id("item/etched_board/quantum"),
        "processor",
        "ev"
    )
    val ETCHED_QUANTUM_PROCESSOR_ASSEMBLY_BOARD = registerEtchedCircuitBoard(
        QUANTUM_ASSEMBLY_IV,
        GTNN.id("item/etched_board/quantum"),
        "processor_assembly",
        "iv"
    )
    val ETCHED_QUANTUM_COMPUTER_BOARD = registerEtchedCircuitBoard(
        WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_board/quantum"),
        "computer",
        "luv"
    )
    val ETCHED_QUANTUM_MAINFRAME_BOARD = registerEtchedCircuitBoard(
        QUANTUM_MAINFRAME_ZPM,
        GTNN.id("item/etched_board/quantum"),
        "mainframe",
        "zpm"
    )

    val ETCHED_QUANTUM_PROCESSOR_BASE = registerEtchedCircuitBase(
        QUANTUM_PROCESSOR_EV,
        GTNN.id("item/etched_base/quantum"),
        "processor",
        "ev"
    )
    val ETCHED_QUANTUM_PROCESSOR_ASSEMBLY_BASE = registerEtchedCircuitBase(
        QUANTUM_ASSEMBLY_IV,
        GTNN.id("item/etched_base/quantum"),
        "processor_assembly",
        "iv"
    )
    val ETCHED_QUANTUM_COMPUTER_BASE = registerEtchedCircuitBase(
        WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_base/quantum"),
        "computer",
        "luv"
    )
    val ETCHED_QUANTUM_MAINFRAME_BASE = registerEtchedCircuitBase(
        QUANTUM_MAINFRAME_ZPM,
        GTNN.id("item/etched_base/quantum"),
        "mainframe",
        "zpm"
    )
    // endregion

    // region Crystal Circuit Items
    val ETCHED_CRYSTAL_PROCESSOR = registerEtchedCircuit(
        CRYSTAL_PROCESSOR_IV,
        GTNN.id("item/etched/crystal"),
        "processor",
        "iv"
    )
    val ETCHED_CRYSTAL_PROCESSOR_ASSEMBLY = registerEtchedCircuit(
        WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched/crystal"),
        "processor_assembly",
        "luv"
    )
    val ETCHED_CRYSTAL_COMPUTER = registerEtchedCircuit(
        CRYSTAL_COMPUTER_ZPM,
        GTNN.id("item/etched/crystal"),
        "computer",
        "zpm"
    )
    val ETCHED_CRYSTAL_MAINFRAME = registerEtchedCircuit(
        CRYSTAL_MAINFRAME_UV,
        GTNN.id("item/etched/crystal"),
        "mainframe",
        "uv"
    )

    val ETCHED_CRYSTAL_PROCESSOR_BOARD = registerEtchedCircuitBoard(
        CRYSTAL_PROCESSOR_IV,
        GTNN.id("item/etched_board/crystal"),
        "processor",
        "iv"
    )
    val ETCHED_CRYSTAL_PROCESSOR_ASSEMBLY_BOARD = registerEtchedCircuitBoard(
        WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_board/crystal"),
        "processor_assembly",
        "luv"
    )
    val ETCHED_CRYSTAL_COMPUTER_BOARD = registerEtchedCircuitBoard(
        CRYSTAL_COMPUTER_ZPM,
        GTNN.id("item/etched_board/crystal"),
        "computer",
        "zpm"
    )
    val ETCHED_CRYSTAL_MAINFRAME_BOARD = registerEtchedCircuitBoard(
        CRYSTAL_MAINFRAME_UV,
        GTNN.id("item/etched_board/crystal"),
        "mainframe",
        "uv"
    )

    val ETCHED_CRYSTAL_PROCESSOR_BASE = registerEtchedCircuitBase(
        CRYSTAL_PROCESSOR_IV,
        GTNN.id("item/etched_base/crystal"),
        "processor",
        "iv"
    )
    val ETCHED_CRYSTAL_PROCESSOR_ASSEMBLY_BASE = registerEtchedCircuitBase(
        WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_base/crystal"),
        "processor_assembly",
        "luv"
    )
    val ETCHED_CRYSTAL_COMPUTER_BASE = registerEtchedCircuitBase(
        CRYSTAL_COMPUTER_ZPM,
        GTNN.id("item/etched_base/crystal"),
        "computer",
        "zpm"
    )
    val ETCHED_CRYSTAL_MAINFRAME_BASE = registerEtchedCircuitBase(
        CRYSTAL_MAINFRAME_UV,
        GTNN.id("item/etched_base/crystal"),
        "mainframe",
        "uv"
    )
    // endregion

    // region Wetware Circuit Items
    val ETCHED_WETWARE_PROCESSOR = registerEtchedCircuit(
        WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched/wetware"),
        "processor",
        "luv"
    )
    val ETCHED_WETWARE_PROCESSOR_ASSEMBLY = registerEtchedCircuit(
        WETWARE_PROCESSOR_ASSEMBLY_ZPM,
        GTNN.id("item/etched/wetware"),
        "processor_assembly",
        "zpm"
    )
    val ETCHED_WETWARE_COMPUTER = registerEtchedCircuit(
        WETWARE_SUPER_COMPUTER_UV,
        GTNN.id("item/etched/wetware"),
        "computer",
        "uv"
    )
    val ETCHED_WETWARE_MAINFRAME = registerEtchedCircuit(
        WETWARE_MAINFRAME_UHV,
        GTNN.id("item/etched/wetware"),
        "mainframe",
        "uhv"
    )

    val ETCHED_WETWARE_PROCESSOR_BOARD = registerEtchedCircuitBoard(
        WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_board/wetware"),
        "processor",
        "luv"
    )
    val ETCHED_WETWARE_PROCESSOR_ASSEMBLY_BOARD = registerEtchedCircuitBoard(
        WETWARE_PROCESSOR_ASSEMBLY_ZPM,
        GTNN.id("item/etched_board/wetware"),
        "processor_assembly",
        "zpm"
    )
    val ETCHED_WETWARE_COMPUTER_BOARD = registerEtchedCircuitBoard(
        WETWARE_SUPER_COMPUTER_UV,
        GTNN.id("item/etched_board/wetware"),
        "computer",
        "uv"
    )
    val ETCHED_WETWARE_MAINFRAME_BOARD = registerEtchedCircuitBoard(
        WETWARE_MAINFRAME_UHV,
        GTNN.id("item/etched_board/wetware"),
        "mainframe",
        "uhv"
    )

    val ETCHED_WETWARE_PROCESSOR_BASE = registerEtchedCircuitBase(
        WETWARE_PROCESSOR_LuV,
        GTNN.id("item/etched_base/wetware"),
        "processor",
        "luv"
    )
    val ETCHED_WETWARE_PROCESSOR_ASSEMBLY_BASE = registerEtchedCircuitBase(
        WETWARE_PROCESSOR_ASSEMBLY_ZPM,
        GTNN.id("item/etched_base/wetware"),
        "processor_assembly",
        "zpm"
    )
    val ETCHED_WETWARE_COMPUTER_BASE = registerEtchedCircuitBase(
        WETWARE_SUPER_COMPUTER_UV,
        GTNN.id("item/etched_base/wetware"),
        "computer",
        "uv"
    )
    val ETCHED_WETWARE_MAINFRAME_BASE = registerEtchedCircuitBase(
        WETWARE_MAINFRAME_UHV,
        GTNN.id("item/etched_base/wetware"),
        "mainframe",
        "uhv"
    )
    // endregion

    fun init() {
    }

    private fun <T : Item?> createItem(name: String, factory: NonNullFunction<Item.Properties, T>): ItemBuilder<T, Registrate> {
        return REGISTRATE.item(name, factory)
    }

    private fun registerEtchedCircuit(
        circuit: ItemEntry<*>,
        baseTexture: ResourceLocation,
        circuitType: String,
        voltage: String
    ): ItemEntry<EtchedItem> {
        val entry = createItem("etched_${circuit.id.path}") {
                props -> EtchedItem(props, { circuit }, "etched")
            }
            .model(GTNNModels.simpleCustomModel(
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
            .model(GTNNModels.simpleCustomModel(
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
            .model(GTNNModels.simpleCustomModel(
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