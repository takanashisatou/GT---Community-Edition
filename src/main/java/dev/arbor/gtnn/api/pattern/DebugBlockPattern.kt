package dev.arbor.gtnn.api.pattern

import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState

class DebugBlockPattern {
    var structureDir: Array<RelativeDirection>
    var pattern: Array<Array<String>>
    var aisleRepetitions: Array<IntArray>
    var symbolMap: MutableMap<Char, MutableSet<String>>

    constructor(){
        structureDir = arrayOf(RelativeDirection.LEFT, RelativeDirection.UP, RelativeDirection.FRONT)
        symbolMap = hashMapOf()
        pattern = arrayOf()
        aisleRepetitions = arrayOf()
    }

    constructor(world: Level, minX: Int, minY: Int, minZ: Int, maxX: Int, maxY: Int, maxZ: Int) : this() {
        val sizeX = 1 + maxX - minX
        val sizeY = 1 + maxY - minY
        pattern = Array(sizeX) { Array(sizeY) { "" } }
        aisleRepetitions = Array(sizeX) { IntArray(2).apply {
            this[0] = 1
            this[1] = 1
        } }

        val stateToChar = mutableMapOf<BlockState, Char>()
        stateToChar[Blocks.AIR.defaultBlockState()] = ' '

        var nextChar = 'A'

        for (x in minX..maxX) {
            for (y in minY..maxY) {
                val builder = StringBuilder()
                for (z in minZ..maxZ) {
                    val pos = BlockPos(x, y, z)
                    val state = world.getBlockState(pos)
                    val charRep = stateToChar.computeIfAbsent(state) {
                        val char = nextChar++
                        symbolMap.computeIfAbsent(char) { HashSet() }.add(char.toString())
                        char
                    }
                    builder.append(charRep)
                }
                pattern[x - minX][y - minY] = builder.toString()
            }
        }

        val dirs = getDir(Direction.NORTH)
        changeDir(dirs[0], dirs[1], dirs[2])
    }

    fun changeDir(charDir: RelativeDirection, stringDir: RelativeDirection, aisleDir: RelativeDirection) {
        if (charDir.isSameAxis(stringDir) ||
            stringDir.isSameAxis(aisleDir) ||
            aisleDir.isSameAxis(charDir)) return

        val newXSize = when {
            structureDir[0].isSameAxis(aisleDir) -> pattern[0][0].length
            structureDir[1].isSameAxis(aisleDir) -> pattern[0].size
            else -> pattern.size
        }

        val newYSize = when {
            structureDir[0].isSameAxis(stringDir) -> pattern[0][0].length
            structureDir[1].isSameAxis(stringDir) -> pattern[0].size
            else -> pattern.size
        }

        val newZSize = when {
            structureDir[0].isSameAxis(charDir) -> pattern[0][0].length
            structureDir[1].isSameAxis(charDir) -> pattern[0].size
            else -> pattern.size
        }

        val newPattern = Array(newXSize) { Array(newYSize) { CharArray(newZSize) } }

        for (i in pattern.indices) {
            for (j in pattern[0].indices) {
                for (k in pattern[0][0].indices) {
                    val c = pattern[i][j][k]
                    var x = 0
                    var y = 0
                    var z = 0

                    // Aisle axis mapping
                    when {
                        structureDir[2].isSameAxis(aisleDir) -> {
                            x = if (structureDir[2] == aisleDir) i else pattern.size - i - 1
                        }
                        structureDir[2].isSameAxis(stringDir) -> {
                            y = if (structureDir[2] == stringDir) i else pattern.size - i - 1
                        }
                        structureDir[2].isSameAxis(charDir) -> {
                            z = if (structureDir[2] == charDir) i else pattern.size - i - 1
                        }
                    }

                    // String axis mapping
                    when {
                        structureDir[1].isSameAxis(aisleDir) -> {
                            x = if (structureDir[1] == aisleDir) j else pattern[0].size - j - 1
                        }
                        structureDir[1].isSameAxis(stringDir) -> {
                            y = if (structureDir[1] == stringDir) j else pattern[0].size - j - 1
                        }
                        structureDir[1].isSameAxis(charDir) -> {
                            z = if (structureDir[1] == charDir) j else pattern[0].size - j - 1
                        }
                    }

                    // Char axis mapping
                    when {
                        structureDir[0].isSameAxis(aisleDir) -> {
                            x = if (structureDir[0] == aisleDir) k else pattern[0][0].length - k - 1
                        }
                        structureDir[0].isSameAxis(stringDir) -> {
                            y = if (structureDir[0] == stringDir) k else pattern[0][0].length - k - 1
                        }
                        structureDir[0].isSameAxis(charDir) -> {
                            z = if (structureDir[0] == charDir) k else pattern[0][0].length - k - 1
                        }
                    }

                    newPattern[x][y][z] = c
                }
            }
        }

        // Convert 3D char array back to string pattern
        pattern = Array(newPattern.size) { i ->
            Array(newPattern[0].size) { j ->
                StringBuilder().apply {
                    for (k in newPattern[i][j].indices) {
                        append(newPattern[i][j][k])
                    }
                }.toString()
            }
        }

        // Reset aisle repetitions
        aisleRepetitions = Array(pattern.size) { IntArray(2).apply {
            this[0] = 1
            this[1] = 1
        } }

        structureDir = arrayOf(charDir, stringDir, aisleDir)
    }

    fun copy(): DebugBlockPattern {
        return DebugBlockPattern().apply{
            structureDir.copyInto(structureDir)

            pattern = Array(pattern.size) { i ->
                pattern[i].copyOf()
            }

            aisleRepetitions = Array(aisleRepetitions.size) { i ->
                aisleRepetitions[i].copyOf()
            }

            symbolMap.forEach { (char, set) ->
                symbolMap[char] = HashSet(set)
            }
        }
    }

    companion object {
        fun getDir(facing: Direction): Array<RelativeDirection> {
            return when (facing) {
                Direction.WEST -> arrayOf(RelativeDirection.LEFT, RelativeDirection.UP, RelativeDirection.BACK)
                Direction.EAST -> arrayOf(RelativeDirection.RIGHT, RelativeDirection.UP, RelativeDirection.FRONT)
                Direction.NORTH -> arrayOf(RelativeDirection.BACK, RelativeDirection.UP, RelativeDirection.RIGHT)
                Direction.SOUTH -> arrayOf(RelativeDirection.FRONT, RelativeDirection.UP, RelativeDirection.LEFT)
                Direction.DOWN -> arrayOf(RelativeDirection.RIGHT, RelativeDirection.BACK, RelativeDirection.UP)
                else -> arrayOf(RelativeDirection.LEFT, RelativeDirection.FRONT, RelativeDirection.UP)
            }
        }
    }
}