package dev.arbor.gtnn.api.gui

import com.gregtechceu.gtceu.api.gui.fancy.IFancyConfigurator
import com.gregtechceu.gtceu.api.gui.widget.IntInputWidget
import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture
import com.lowdragmc.lowdraglib.gui.widget.Widget
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup
import dev.arbor.gtnn.api.machine.feature.IParallelMachine
import dev.arbor.gtnn.data.GTNNGuiTextures
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class ParallelFancyConfigurator(private var machine: IParallelMachine) : IFancyConfigurator {

    override fun getTitle(): Component {
        return Component.translatable("gui.gtnn.machine_parallel.title")
    }

    override fun getIcon(): IGuiTexture {
        return GTNNGuiTextures.OVERLAY_PARALLEL_CONFIGURATOR
    }

    override fun createConfigurator(): Widget {
        val parallelAmountGroup = WidgetGroup(0, 0, 100, 20)
        parallelAmountGroup.addWidget(
            object : IntInputWidget(machine::parallelNumber, { machine.parallelNumber = it}) {
                override fun writeInitialData(buffer: FriendlyByteBuf) {
                    super.writeInitialData(buffer)
                    buffer.writeVarInt(machine.maxParallel)
                    setMax(machine.maxParallel)
                }

                @OnlyIn(Dist.CLIENT)
                override fun readInitialData(buffer: FriendlyByteBuf) {
                    super.readInitialData(buffer)
                    setMax(buffer.readVarInt())
                }

                override fun detectAndSendChanges() {
                    super.detectAndSendChanges()
                    val newMaxParallel: Int = machine.maxParallel
                    if (newMaxParallel != max) {
                        setMax(newMaxParallel)
                        writeUpdateInfo(0) { buf: FriendlyByteBuf? -> buf!!.writeVarInt(newMaxParallel) }
                    }
                }

                @OnlyIn(Dist.CLIENT)
                override fun readUpdateInfo(id: Int, buffer: FriendlyByteBuf) {
                    super.readUpdateInfo(id, buffer)
                    if (id == 0) {
                        setMax(buffer.readVarInt())
                    }
                }
            }.setMin(1)
        )
        return parallelAmountGroup
    }

    override fun getTooltips(): MutableList<Component?> {
        val tooltip: MutableList<Component?> = ArrayList()
        tooltip.add(Component.translatable("gui.gtnn.change_parallel.desc"))
        return tooltip
    }
}