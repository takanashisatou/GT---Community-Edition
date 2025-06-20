package dev.arbor.gtnn.api.extension

import java.util.function.BooleanSupplier
import java.util.function.Supplier

object NNUtils {
    fun <T> getOrDefault(value: T, defaultSupplier: Supplier<T>): T {
        if (value == null) return defaultSupplier.get()
        return value
    }

    fun <T> getOrDefault(canGet: BooleanSupplier, getter: ()->T, defaultValue: T): T {
        return if (canGet.asBoolean) getter() else defaultValue
    }

    fun <T> getOrLast(canGet: Array<T>, index: Int): T {
        return getOrDefault(canGet, index, canGet[canGet.size - 1])
    }

    fun <T> getOrDefault(canGet: Array<T>, index: Int, defaultValue: T): T {
        return if (index < canGet.size) canGet[index] else defaultValue
    }
}