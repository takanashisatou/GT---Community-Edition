package dev.arbor.gtnn.client.renderer.item

import com.gregtechceu.gtceu.api.item.component.ICustomRenderer
import com.lowdragmc.lowdraglib.client.renderer.IRenderer
import dev.arbor.gtnn.config.GTNNConfigHandler

class SuperscriptItemBehavior private constructor() {
    @JvmRecord
    data class Number(override val tier: Int) : INumberSuperscriptEffect {
        override val isRoma: Boolean
            get() = GTNNConfigHandler.INSTANCE.Client.items.perfRomanSubscript
    }

    @JvmRecord
    data class Voltage(override val tier: Int) : IVoltageSuperscriptEffect
}

interface INumberSuperscriptEffect : ICustomRenderer {
    val tier: Int

    val isRoma: Boolean

    override fun getRenderer(): IRenderer {
        return GTNNItemRenderers.SUPERSCRIPT_ITEM_RENDERER
    }
}

interface IVoltageSuperscriptEffect : ICustomRenderer {
    val tier: Int

    override fun getRenderer(): IRenderer {
        return GTNNItemRenderers.SUPERSCRIPT_ITEM_RENDERER
    }
}