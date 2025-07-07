package dev.arbor.gtnn.extension

import com.gregtechceu.gtceu.api.pattern.BlockPattern
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo
import dev.arbor.gtnn.api.pattern.NNBlockPattern
import java.util.*

object StructureUtil {
    fun getMatchingShapes(
        blockPattern: NNBlockPattern,
        maxIndex: Int
    ): List<MultiblockShapeInfo> {
        val aisleRepetitions = blockPattern.aisleRepetitions
        val pages = repetitionCandidates(blockPattern, ArrayList(), aisleRepetitions, Stack())
        if (pages.size < maxIndex) {
            val repetition = IntArray(aisleRepetitions.size) { i ->
                aisleRepetitions[i][1]
            }
            for (i in pages.size until maxIndex) {
                pages.add(MultiblockShapeInfo(blockPattern.getPreview(repetition, i)))
            }
        }
        return pages
    }

    private fun repetitionDFS(
        pattern: BlockPattern,
        pages: MutableList<MultiblockShapeInfo>,
        aisleRepetitions: Array<IntArray>,
        repetitionStack: Stack<Int>
    ): List<MultiblockShapeInfo> {
        if (repetitionStack.size == aisleRepetitions.size) {
            val repetition = IntArray(repetitionStack.size) { repetitionStack[it] }
            pages.add(MultiblockShapeInfo(pattern.getPreview(repetition)))
        } else {
            val currentLevel = repetitionStack.size
            for (i in aisleRepetitions[currentLevel][0]..aisleRepetitions[currentLevel][1]) {
                repetitionStack.push(i)
                repetitionDFS(pattern, pages, aisleRepetitions, repetitionStack)
                repetitionStack.pop()
            }
        }
        return pages
    }

    private fun repetitionDFS(
        pattern: NNBlockPattern,
        pages: MutableList<MultiblockShapeInfo>,
        aisleRepetitions: Array<IntArray>,
        repetitionStack: Stack<Int>,
        index: Int
    ): List<MultiblockShapeInfo> {
        if (repetitionStack.size == aisleRepetitions.size) {
            val repetition = IntArray(repetitionStack.size) { repetitionStack[it] }
            pages.add(MultiblockShapeInfo(pattern.getPreview(repetition, index)))
        } else {
            val currentLevel = repetitionStack.size
            for (i in aisleRepetitions[currentLevel][0]..aisleRepetitions[currentLevel][1]) {
                repetitionStack.push(i)
                repetitionDFS(pattern, pages, aisleRepetitions, repetitionStack, index)
                repetitionStack.pop()
            }
        }
        return pages
    }

    private fun repetitionCandidates(
        pattern: NNBlockPattern,
        pages: MutableList<MultiblockShapeInfo>,
        aisleRepetitions: Array<IntArray>,
        repetitionStack: Stack<Int>
    ): MutableList<MultiblockShapeInfo> {
        if (repetitionStack.size == aisleRepetitions.size) {
            val repetition = IntArray(repetitionStack.size) { repetitionStack[it] }
            pages.add(MultiblockShapeInfo(pattern.getPreview(repetition, pages.size)))
        } else {
            val currentLevel = repetitionStack.size
            for (i in aisleRepetitions[currentLevel][0]..aisleRepetitions[currentLevel][1]) {
                repetitionStack.push(i)
                repetitionCandidates(pattern, pages, aisleRepetitions, repetitionStack)
                repetitionStack.pop()
            }
        }
        return pages
    }
}