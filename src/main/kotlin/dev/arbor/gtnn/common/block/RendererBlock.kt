@file:Suppress("DEPRECATION")
package dev.arbor.gtnn.common.block

import com.gregtechceu.gtceu.api.block.AppearanceBlock
import com.lowdragmc.lowdraglib.client.renderer.IBlockRendererProvider
import com.lowdragmc.lowdraglib.client.renderer.IRenderer
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class RendererBlock(properties: Properties, val renderer: IRenderer?) : AppearanceBlock(properties),
    IBlockRendererProvider {
    @OnlyIn(Dist.CLIENT)
    override fun getRenderer(state: BlockState?): IRenderer? {
        return renderer
    }
}