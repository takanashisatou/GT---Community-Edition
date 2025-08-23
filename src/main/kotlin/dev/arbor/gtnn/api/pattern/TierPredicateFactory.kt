package dev.arbor.gtnn.api.pattern

import com.gregtechceu.gtceu.api.pattern.MultiblockState
import com.gregtechceu.gtceu.api.pattern.error.PatternStringError
import com.lowdragmc.lowdraglib.utils.BlockInfo
import dev.arbor.gtnn.api.block.ITierType
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.Block
import java.util.function.Predicate
import java.util.function.Supplier

class TierPredicateFactory (val name: String) {
    var strict = false
    var map: Object2ObjectOpenHashMap<ITierType, Supplier<Block>>? = null
    var candidatesMap: Object2ObjectOpenHashMap<ITierType, Supplier<Block>>? = null
    var errorKey: Component? = null
    var comparator: Comparator<ITierType>? = null
    var predicate: Predicate<ITierType>? = null
    var container: Supplier<IValueContainer<*>>? = null

    fun build(): TraceabilityPredicateEx = if (strict) {
        TraceabilityPredicateEx(
            NNPredicate(
                getStrictPredicate(
                    name,
                    map ?: Object2ObjectOpenHashMap(),
                    container ?: Supplier { IValueContainer.noop() },
                    errorKey ?: Component.translatable("structure.multiblock.pattern.error.casing")
                ), getCandidates(
                    name,
                    candidatesMap ?: map ?: Object2ObjectOpenHashMap(),
                    comparator ?: Comparator.comparingInt(ITierType::tier),
                    predicate ?: Predicate { true })
            ).setPreviewCandidates(true), name
        )
    } else {
        TraceabilityPredicateEx(
            NNPredicate(
                getPredicate(
                    name,
                    candidatesMap ?: map ?: Object2ObjectOpenHashMap(),
                    container ?: Supplier { IValueContainer.noop() }), getCandidates(
                    name,
                    map ?: Object2ObjectOpenHashMap(),
                    comparator ?: Comparator.comparingInt(ITierType::tier),
                    predicate ?: Predicate { true })
            ).setPreviewCandidates(true), name
        )
    }

    private fun getPredicate(
        name: String,
        map: Object2ObjectOpenHashMap<ITierType, Supplier<Block>>,
        containerSupplier: Supplier<IValueContainer<*>>
    ): Predicate<MultiblockState> {
        return Predicate { blockWorldState: MultiblockState ->
            val blockState = blockWorldState.getBlockState()
            val objectIterator = map.object2ObjectEntrySet().fastIterator()
            while (objectIterator.hasNext()) {
                val entry = objectIterator.next()
                if (blockState.`is`(entry.value!!.get())) {
                    val currentContainer: IValueContainer<*> =
                        blockWorldState.matchContext.getOrPut(name + "Value", containerSupplier.get())!!
                    currentContainer.operate(blockState.block, entry.key!!)
                    return@Predicate true
                }
            }
            false
        }
    }

    private fun getStrictPredicate(
        name: String,
        map: Object2ObjectOpenHashMap<ITierType, Supplier<Block>>,
        containerSupplier: Supplier<IValueContainer<*>>,
        errorKey: Component
    ): Predicate<MultiblockState> {
        return Predicate { blockWorldState: MultiblockState ->
            val blockState = blockWorldState.getBlockState()
            val objectIterator = map.object2ObjectEntrySet().fastIterator()
            while (objectIterator.hasNext()) {
                val entry = objectIterator.next()
                if (blockState.`is`(entry.value!!.get())) {
                    val stats = entry.key
                    val currentStats: Any = blockWorldState.matchContext.getOrPut(name, stats)
                    if (currentStats != stats) {
                        blockWorldState.setError(PatternStringError(errorKey.string))
                        return@Predicate false
                    }
                    val currentContainer: IValueContainer<*> =
                        blockWorldState.matchContext.getOrPut(name + "Value", containerSupplier.get())!!
                    currentContainer.operate(blockState.block, stats)
                    return@Predicate true
                }
            }
            false
        }
    }

    private fun getCandidates(
        name: String,
        map: Object2ObjectOpenHashMap<ITierType, Supplier<Block>>,
        comparator: Comparator<ITierType>,
        predicate: Predicate<ITierType>
    ): Supplier<Array<BlockInfo>> {
        return Supplier {
            CACHE.computeIfAbsent(name) { _: String ->
                map.keys
                    .filter { predicate.test(it)}
                    .sortedWith { o1, o2 -> comparator.compare(o1, o2) }
                    .map { type -> BlockInfo.fromBlock(map[type]!!.get()) }
                .toTypedArray()
            }
        }
    }

    companion object {
        private val CACHE: MutableMap<String, Array<BlockInfo>> = HashMap<String, Array<BlockInfo>>()
    }
}

interface IValueContainer<T> {
    fun operate(block: Block, data: Any)
    val value: T?

    companion object {
        fun noop(): IValueContainer<Any> = NOOP()
    }
    class NOOP(override val value: Any? = null) : IValueContainer<Any> {
        override fun operate(block: Block, data: Any) = Unit
    }
}