package dev.arbor.gtnn.api.pattern

import com.gregtechceu.gtceu.api.pattern.MultiblockState
import com.gregtechceu.gtceu.api.pattern.predicates.SimplePredicate
import com.lowdragmc.lowdraglib.utils.BlockInfo
import java.util.function.Predicate
import java.util.function.Supplier

class NNPredicate : SimplePredicate {
    @set:JvmName("setPc")
    var previewCandidates = false

    fun setPreviewCandidates(previewCandidates: Boolean): NNPredicate {
        this.previewCandidates = previewCandidates
        return this
    }

    constructor()

    constructor(type: String) : super(type)

    constructor(
        predicate: Predicate<MultiblockState>,
        candidates: Supplier<Array<BlockInfo>>
    ) : super(predicate, candidates)

    constructor(
        type: String,
        predicate: Predicate<MultiblockState>,
        candidates: Supplier<Array<BlockInfo>>
    ) : super(type, predicate, candidates)
}