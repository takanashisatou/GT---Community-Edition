package dev.arbor.gtnn.api.pattern

import com.gregtechceu.gtceu.api.pattern.MultiblockState
import dev.arbor.gtnn.api.block.ITierType
import dev.arbor.gtnn.extension.NNUtils
import net.minecraft.world.level.block.Block

class TierOptionalContainer : IValueContainer<Int?> {
    private var tier = 0
    override var value: Int = 0
        private set

    override fun operate(block: Block, data: Any) {
        val int: Int = when (data) {
            is Int -> data
            is ITierType -> data.tier
            else -> 0
        }

        if (!NNUtils.getStatusType(this.value, tier)) {
            tier++
        }
        this.value = this.value or (1 shl (int - 1))
    }

    companion object {
        fun getTier(multiblockState: MultiblockState, name: String): Int {
            val container = multiblockState.matchContext.getOrCreate(name + "Value") { IValueContainer.noop() }
            if (container is TierOptionalContainer) {
                val tier = container.tier
                if (getAllOnesInt(tier) == container.value) return tier
            }
            return 0
        }

        private fun getAllOnesInt(n: Int): Int {
            require(n in 1..32) { "Invalid number of bits, should be between 1 and 32" }
            var result = 0
            for (i in 0..<n) {
                result = result or (1 shl i)
            }
            return result
        }
    }
}