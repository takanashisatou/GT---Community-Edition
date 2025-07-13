package dev.arbor.gtnn.api.pattern

import com.gregtechceu.gtceu.api.block.MetaMachineBlock
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController
import com.gregtechceu.gtceu.api.pattern.BlockPattern
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate
import com.gregtechceu.gtceu.api.pattern.predicates.SimplePredicate
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection
import com.lowdragmc.lowdraglib.utils.BlockInfo
import dev.arbor.gtnn.data.block.NNBlockProperties
import dev.arbor.gtnn.extension.NNUtils
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.Property
import java.util.function.BiFunction
import java.util.function.Consumer

class NNBlockPattern(
    private var structureTier: Int,
    predicatesIn: Array<Array<Array<TraceabilityPredicate?>>>,
    structureDir: Array<RelativeDirection>,
    aisleRepetitions: Array<IntArray>,
    centerOffset: IntArray
) : BlockPattern(predicatesIn, structureDir, aisleRepetitions, centerOffset) {
    override fun getPreview(repetition: IntArray): Array<Array<Array<BlockInfo?>>> {
        return getPreview(repetition, -1)
    }

    fun getPreview(repetition: IntArray, index: Int): Array<Array<Array<BlockInfo?>>> {
        val cacheGlobal = mutableMapOf<SimplePredicate, Int>()
        val blocks = mutableMapOf<BlockPos, BlockInfo>()
        var (minX, minY, minZ) = listOf(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)
        var (maxX, maxY, maxZ) = listOf(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)

        var x = 0
        for (l in 0 until fingerLength) {
            (0 until repetition[l]).forEach { _ ->
                val cacheLayer = mutableMapOf<SimplePredicate, Int>()
                for (y in 0 until thumbLength) {
                    for (z in 0 until palmLength) {
                        val predicate = blockMatches[l][y][z]
                        val (infos, preview) = findPredicateInfo(predicate, cacheLayer, cacheGlobal)
                        val info = when {
                            preview && index > -1 -> infos?.let { NNUtils.getOrLast(it, index) } ?: BlockInfo.EMPTY
                            else -> infos?.firstOrNull() ?: BlockInfo.EMPTY
                        }

                        setActualRelativeOffset(z, y, x, Direction.NORTH).let { pos ->
                            blocks[pos] = info
                            minX = minOf(minX, pos.x)
                            minY = minOf(minY, pos.y)
                            minZ = minOf(minZ, pos.z)
                            maxX = maxOf(maxX, pos.x)
                            maxY = maxOf(maxY, pos.y)
                            maxZ = maxOf(maxZ, pos.z)
                        }
                    }
                }
                x++
            }
        }

        val result = Array(maxX - minX + 1) {
            Array(maxY - minY + 1) {
                arrayOfNulls<BlockInfo>(maxZ - minZ + 1)
            }
        }

        blocks.forEach { (pos, info) ->
            resetFacing(
                pos, info.blockState, null,
                BiFunction { p, f -> canResetFacing(blocks, info, p, f) },
                Consumer { state ->
                    info.blockState = if (state.hasProperty(NNBlockProperties.STRUCTURE_TIER))
                        state.setValue(NNBlockProperties.STRUCTURE_TIER, structureTier)
                    else state
                }
            )
            result[pos.x - minX][pos.y - minY][pos.z - minZ] = info
        }
        return result
    }

    private fun findPredicateInfo(
        predicate: TraceabilityPredicate,
        cacheLayer: MutableMap<SimplePredicate, Int>,
        cacheGlobal: MutableMap<SimplePredicate, Int>
    ): Pair<Array<BlockInfo>?, Boolean> {
        // 检查 layer 和 previewCount
        predicate.limited.firstNotNullOfOrNull { limit ->
            if (limit.minLayerCount <= 0) return@firstNotNullOfOrNull null

            val count = cacheLayer.getOrDefault(limit, 0)
            if (count >= limit.minLayerCount) return@firstNotNullOfOrNull null

            cacheLayer[limit] = count + 1
            val globalCount = cacheGlobal.getOrDefault(limit, 0)
            if (globalCount >= limit.previewCount) return@firstNotNullOfOrNull null

            cacheGlobal[limit] = globalCount + 1
            limit.candidates?.get() to shouldPreviewCandidates(limit)
        }?.let { return it }

        // 检查 global 和 previewCount
        predicate.limited.firstNotNullOfOrNull { limit ->
            if (limit.minCount == -1 && limit.previewCount == -1) return@firstNotNullOfOrNull null

            val globalCount = cacheGlobal.getOrDefault(limit, 0)
            when {
                globalCount < limit.previewCount -> cacheGlobal[limit] = globalCount + 1
                limit.minCount > 0 && globalCount < limit.minCount -> cacheGlobal[limit] = globalCount + 1
                else -> return@firstNotNullOfOrNull null
            }
            limit.candidates?.get() to shouldPreviewCandidates(limit)
        }?.let { return it }

        // 检查 common 的 previewCount
        predicate.common.firstNotNullOfOrNull { common ->
            if (common.previewCount <= 0) return@firstNotNullOfOrNull null

            val globalCount = cacheGlobal.getOrDefault(common, 0)
            if (globalCount >= common.previewCount) return@firstNotNullOfOrNull null

            cacheGlobal[common] = globalCount + 1
            common.candidates?.get() to shouldPreviewCandidates(common)
        }?.let { return it }

        // 检查没有 previewCount 的 common
        predicate.common.firstNotNullOfOrNull { common ->
            if (common.previewCount != -1) return@firstNotNullOfOrNull null
            common.candidates?.get() to shouldPreviewCandidates(common)
        }?.let { return it }

        // 检查 max 限制
        predicate.limited.firstNotNullOfOrNull { limit ->
            if (limit.previewCount != -1) return@firstNotNullOfOrNull null

            val globalCount = cacheGlobal.getOrDefault(limit, 0)
            val layerCount = cacheLayer.getOrDefault(limit, 0)

            when {
                limit.maxCount != -1 && globalCount < limit.maxCount ->
                    cacheGlobal[limit] = globalCount + 1
                limit.maxLayerCount != -1 && layerCount < limit.maxLayerCount ->
                    cacheLayer[limit] = layerCount + 1
                else -> return@firstNotNullOfOrNull null
            }
            limit.candidates?.get() to shouldPreviewCandidates(limit)
        }?.let { return it }

        return null to false
    }

    private fun canResetFacing(
        blocks: Map<BlockPos, BlockInfo>,
        info: BlockInfo,
        pos: BlockPos,
        facing: Direction
    ): Boolean {
        val blockInfo = blocks[pos.relative(facing)]
        if (blockInfo == null || blockInfo.blockState.block == Blocks.AIR) {
            if (info.blockState.block is MetaMachineBlock) {
                val machineBlock = info.blockState.block as MetaMachineBlock
                val machineBlockEntity = machineBlock.newBlockEntity(BlockPos.ZERO, machineBlock.defaultBlockState())

                if (machineBlockEntity is IMachineBlockEntity) {
                    val machine = machineBlockEntity.metaMachine
                    return if (machine is IMultiController) false else machine.isFacingValid(facing)
                }
            }
            return true
        }
        return false
    }

    private fun shouldPreviewCandidates(predicate: SimplePredicate): Boolean {
        return (predicate as? NNPredicate)?.previewCandidates ?: false
    }

    @Suppress("SameParameterValue")
    private fun resetFacing(
        pos: BlockPos,
        blockState: BlockState,
        facing: Direction?,
        checker: BiFunction<BlockPos, Direction, Boolean>,
        consumer: Consumer<BlockState>
    ) {
        when {
            blockState.hasProperty(BlockStateProperties.FACING) -> tryFacings(
                blockState,
                pos,
                checker,
                consumer,
                BlockStateProperties.FACING,
                facing?.let { arrayOf(it) + FACINGS } ?: FACINGS
            )

            blockState.hasProperty(BlockStateProperties.HORIZONTAL_FACING) -> tryFacings(
                blockState,
                pos,
                checker,
                consumer,
                BlockStateProperties.HORIZONTAL_FACING,
                when {
                    facing == null -> FACINGS_H
                    facing.axis == Direction.Axis.Y -> FACINGS_H
                    else -> arrayOf(facing) + FACINGS_H
                }
            )
        }
    }

    private fun tryFacings(
        blockState: BlockState,
        pos: BlockPos,
        checker: BiFunction<BlockPos, Direction, Boolean>,
        consumer: Consumer<BlockState>,
        property: Property<Direction>,
        facings: Array<Direction>
    ) {
        var found: Direction? = null
        for (facing in facings) {
            if (checker.apply(pos, facing)) {
                found = facing
                break
            }
        }
        consumer.accept(blockState.setValue(property, found ?: Direction.NORTH))
    }

    @Suppress("SameParameterValue")
    private fun setActualRelativeOffset(x: Int, y: Int, z: Int, facing: Direction): BlockPos {
        val c0 = intArrayOf(x, y, z)
        val c1 = IntArray(3)
        for (i in 0..2) {
            when (structureDir[i].getActualDirection(facing)) {
                Direction.UP -> c1[1] = c0[i]
                Direction.DOWN -> c1[1] = -c0[i]
                Direction.WEST -> c1[0] = -c0[i]
                Direction.EAST -> c1[0] = c0[i]
                Direction.NORTH -> c1[2] = -c0[i]
                Direction.SOUTH -> c1[2] = c0[i]
                else -> throw IllegalArgumentException("Invalid direction")
            }
        }
        return BlockPos(c1[0], c1[1], c1[2])
    }

    companion object {
        val FACINGS = arrayOf(
            Direction.SOUTH,
            Direction.NORTH,
            Direction.WEST,
            Direction.EAST,
            Direction.UP,
            Direction.DOWN
        )
        val FACINGS_H = arrayOf(
            Direction.SOUTH,
            Direction.NORTH,
            Direction.WEST,
            Direction.EAST
        )
    }
}