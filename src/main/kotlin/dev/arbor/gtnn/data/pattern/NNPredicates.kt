package dev.arbor.gtnn.data.pattern

import dev.arbor.gtnn.api.pattern.IValueContainer
import dev.arbor.gtnn.api.pattern.TierPredicateFactory
import dev.arbor.gtnn.data.block.GTNNBlocks
import dev.arbor.gtnn.data.block.NNBlockMaps
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.Block
import java.util.function.Supplier

object NNPredicates {
    val coilBlock = TierPredicateFactory("CoilType").apply {
        map = NNBlockMaps.ALL_COIL_BLOCKS
        errorKey = Component.translatable("gtceu.multiblock.pattern.error.coils")
        strict = true
    }.build()

    val plantCasings = TierPredicateFactory("PlantCasing").apply{
        map = NNBlockMaps.ALL_CP_CASINGS
        errorKey = Component.translatable("gtnn.multiblock.pattern.error.plant_casings")
        strict = true
    }.build()

    val pipeBlock = TierPredicateFactory("Pipe").apply{
        map = NNBlockMaps.ALL_CP_TUBES
        errorKey = Component.translatable("gtnn.multiblock.pattern.error.pipe")
        strict = true
    }.build()

    val machineCasing = TierPredicateFactory("MachineCasing").apply{
        map = NNBlockMaps.ALL_MACHINE_CASINGS
        errorKey = Component.translatable("gtnn.multiblock.pattern.error.machine_casing")
        strict = true
    }.build()

    val componentAssemblyBlock = TierPredicateFactory("ComponentAssembly").apply{
        map = NNBlockMaps.ALL_CA_TIRED_CASINGS
        strict = true
    }.build()

    val neutronActivator = TierPredicateFactory("SpeedPipe").apply{
        container = Supplier {object : IValueContainer<Int> {
            var count = 0
            override fun operate(block: Block, data: Any) {
                if (block == GTNNBlocks.HIGH_SPEED_PIPE_BLOCK.get()) {
                    count++
                }
            }
            override val value get() = count
        } }
    }.build()
}
