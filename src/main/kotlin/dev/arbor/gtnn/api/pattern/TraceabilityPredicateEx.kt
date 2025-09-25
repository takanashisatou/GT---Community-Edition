package dev.arbor.gtnn.api.pattern;

import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate
import com.gregtechceu.gtceu.api.pattern.predicates.SimplePredicate
import com.gregtechceu.gtceu.api.pattern.util.PatternMatchContext
import dev.arbor.gtnn.api.block.ITierType
import dev.arbor.gtnn.api.pattern.IValueContainer.Companion.noop
import dev.arbor.gtnn.extension.NNUtils

class TraceabilityPredicateEx(predicate: SimplePredicate, val name: String) : TraceabilityPredicate(predicate) {
    private fun getContainer(context: PatternMatchContext, withValue: Boolean = false): Any {
        val key = if (withValue) name + "Value" else name
        return context.getOrCreate(key) { noop() }
    }

    fun getInt(context: PatternMatchContext): Int {
        return getContainer(context, true) as? Int ?: 0
    }

    fun getTier(context: PatternMatchContext): Int {
        val container = getContainer(context)
        return NNUtils.getOrDefault({ container is ITierType }, { (container as ITierType).tier }, 0)
    }
}
