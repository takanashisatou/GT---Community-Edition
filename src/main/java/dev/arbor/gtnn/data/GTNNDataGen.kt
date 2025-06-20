package dev.arbor.gtnn.data

import com.tterrag.registrate.providers.ProviderType
import dev.arbor.gtnn.GTNNRegistries.REGISTRATE
import dev.arbor.gtnn.api.registry.NNLangProvider
import dev.arbor.gtnn.data.lang.NNLangHandler

object GTNNDataGen {
    val NN_LANG: ProviderType<NNLangProvider> = ProviderType.register("nn_lang") { p, e ->
        NNLangProvider(p, e.generator.packOutput)
    }

    fun init() {
        REGISTRATE.addDataGenerator(NN_LANG, NNLangHandler::init)
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, GTNNTags::initBlock)
    }
}
