package dev.arbor.gtnn.api.registry

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateProvider
import dev.arbor.gtnn.data.GTNNDataGen

class NNRegistrate(modId: String): GTRegistrate(modId) {
    override fun <T : RegistrateProvider> genData(type: ProviderType<out T>, gen: T) {
        if (type === ProviderType.LANG) return

        if (type === GTNNDataGen.NN_LANG) {
            super.genData(ProviderType.LANG, gen)
            super.genData(type, gen)
        } else {
            super.genData(type, gen)
        }
    }
}