package dev.arbor.gtnn.api.item

import com.gregtechceu.gtceu.api.item.component.ICustomRenderer
import com.lowdragmc.lowdraglib.client.renderer.IItemRendererProvider
import net.minecraft.world.item.ItemStack

interface INNItemRendererProvider: IItemRendererProvider {
    fun getRenderInfo(itemStack: ItemStack): ICustomRenderer?
}