package dev.arbor.gtnn.api.pattern

import com.gregtechceu.gtceu.api.pattern.Predicates
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection

class NNFactoryPattern private constructor(
    charDir: RelativeDirection,
    stringDir: RelativeDirection,
    aisleDir: RelativeDirection
) {
    private val depth = mutableListOf<Array<out String>>()
    private val aisleRepetitions = mutableListOf<IntArray>()
    private val symbolMap = mutableMapOf<Char, TraceabilityPredicate?>()
    private val structureDir = arrayOf(charDir, stringDir, aisleDir)
    private var aisleHeight = 0
    private var rowWidth = 0

    init {
        var flags = 0
        structureDir.forEach { direction ->
            flags = when (direction) {
                RelativeDirection.UP, RelativeDirection.DOWN -> flags or 0x1
                RelativeDirection.LEFT, RelativeDirection.RIGHT -> flags or 0x2
                RelativeDirection.FRONT, RelativeDirection.BACK -> flags or 0x4
            }
        }
        require(flags == 0x7) { "Must have 3 different axes!" }
        symbolMap[' '] = Predicates.any()
    }

    fun aisleRepeatable(minRepeat: Int, maxRepeat: Int, vararg aisle: String): NNFactoryPattern {
        require(aisle.isNotEmpty() && aisle[0].isNotEmpty()) { "Empty pattern for aisle" }

        if (depth.isEmpty()) {
            aisleHeight = aisle.size
            rowWidth = aisle[0].length
        }

        require(aisle.size == aisleHeight) {
            "Expected aisle with height of $aisleHeight, but was given one with a height of ${aisle.size}"
        }

        aisle.forEach { row ->
            require(row.length == rowWidth) {
                "Not all rows in the given aisle are the correct width (expected $rowWidth, found one with ${row.length})"
            }
            row.forEach { char ->
                symbolMap.putIfAbsent(char, null)
            }
        }

        depth.add(aisle)
        require(minRepeat <= maxRepeat) {
            "Lower bound of repeat counting must smaller than upper bound!"
        }
        aisleRepetitions.add(intArrayOf(minRepeat, maxRepeat))
        return this
    }

    fun aisle(vararg aisle: String): NNFactoryPattern {
        return aisleRepeatable(1, 1, *aisle)
    }

    fun setRepeatable(minRepeat: Int, maxRepeat: Int): NNFactoryPattern {
        require(minRepeat <= maxRepeat) {
            "Lower bound of repeat counting must smaller than upper bound!"
        }
        aisleRepetitions[aisleRepetitions.size - 1] = intArrayOf(minRepeat, maxRepeat)
        return this
    }

    fun setRepeatable(repeatCount: Int): NNFactoryPattern {
        return setRepeatable(repeatCount, repeatCount)
    }

    fun where(symbol: String, blockMatcher: TraceabilityPredicate): NNFactoryPattern {
        return where(symbol[0], blockMatcher)
    }

    fun where(symbol: Char, blockMatcher: TraceabilityPredicate): NNFactoryPattern {
        symbolMap[symbol] = if (blockMatcher.isAny || blockMatcher.isAir) {
            blockMatcher
        } else {
            TraceabilityPredicate(blockMatcher).sort()
        }
        return this
    }

    fun build(): NNBlockPattern {
        return build(0)
    }

    fun build(tier: Int): NNBlockPattern {
        checkMissingPredicates()
        var centerOffset = IntArray(5)
        val repetitionsArray = aisleRepetitions.toTypedArray()
        val predicate = Array(depth.size) { Array(aisleHeight) { arrayOfNulls<TraceabilityPredicate>(rowWidth) } }

        var minZ = 0
        var maxZ = 0
        for (i in depth.indices) {
            for (j in 0 until aisleHeight) {
                for (k in 0 until rowWidth) {
                    val cell = depth[i][j][k]
                    predicate[i][j][k] = symbolMap[cell]!!
                    if (predicate[i][j][k]!!.isController) {
                        centerOffset = intArrayOf(k, j, i, minZ, maxZ)
                    }
                }
            }
            minZ += aisleRepetitions[i][0]
            maxZ += aisleRepetitions[i][1]
        }

        return NNBlockPattern(tier, predicate, structureDir, repetitionsArray, centerOffset)
    }

    private fun makePredicateArray(): Array<Array<Array<TraceabilityPredicate>>> {
        checkMissingPredicates()
        return Array(depth.size) { i ->
            Array(aisleHeight) { j ->
                Array(rowWidth) { k ->
                    symbolMap[depth[i][j][k]]!!
                }
            }
        }
    }

    private fun checkMissingPredicates() {
        val missing = symbolMap.filter { it.value == null }.keys
        require(missing.isEmpty()) {
            "Predicates for character(s) ${missing.joinToString(",")} are missing"
        }
    }

    companion object {
        fun start(): NNFactoryPattern {
            return NNFactoryPattern(
                RelativeDirection.LEFT,
                RelativeDirection.UP,
                RelativeDirection.FRONT
            )
        }

        fun start(
            charDir: RelativeDirection,
            stringDir: RelativeDirection,
            aisleDir: RelativeDirection
        ): NNFactoryPattern {
            return NNFactoryPattern(charDir, stringDir, aisleDir)
        }
    }
}
