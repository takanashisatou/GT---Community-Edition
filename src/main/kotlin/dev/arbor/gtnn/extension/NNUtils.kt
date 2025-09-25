package dev.arbor.gtnn.extension

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

    /**
     * 判断第几位是否为1
     *
     * @param num   整数
     * @param index 低位起第几位下标
     * @return 是否为1
     */
    fun getStatusType(num: Int, index: Int): Boolean = (num shr (index - 1) and 1) == 1
}