package dev.arbor.gtnn.api.lang

import com.lowdragmc.lowdraglib.LDLib
import net.minecraft.client.resources.language.I18n
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

interface ILangEntry

class SingleLangEntry(private val key: String) : ILangEntry {

    operator fun invoke(vararg args: Any): MutableComponent {
        return Component.translatable(key, *args)
    }

    fun i18n(vararg args: Any): String {
        return if (LDLib.isRemote()) I18n.get(key, *args) else key
    }
}

class MultiLangEntry(private val keys: Set<String>) : ILangEntry {
    operator fun invoke(vararg args: List<Any>): List<MutableComponent> {
        return keys.mapIndexed { idx, key ->
            Component.translatable(key, *args[idx].toTypedArray())
        }
    }

    fun i18n(vararg args: List<Any>): List<String> {
        return if (LDLib.isRemote()) keys.mapIndexed { idx, key ->
            I18n.get(key, *args[idx].toTypedArray())
        }
        else keys.toList()
    }
}