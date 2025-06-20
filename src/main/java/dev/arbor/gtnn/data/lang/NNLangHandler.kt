package dev.arbor.gtnn.data.lang

import dev.arbor.gtnn.api.registry.NNLangProvider

object NNLangHandler {
    private lateinit var provider: NNLangProvider

    fun init(provider: NNLangProvider): NNLangHandler {
        this.provider = provider
        MachineLang.init(provider)
        MaterialLang.init(provider)
        MiscLang.init(provider)
        WrapItemLang.init(provider)
        BlockLang.init(provider)
        ItemLang.init(provider)
        return this
    }

    fun tsl(key: String, cn: String) {
        provider.addCN(key, cn)
    }

    fun tsl(key: String, cn: String, en: String) {
        provider.add(key, en, cn)
    }
}
