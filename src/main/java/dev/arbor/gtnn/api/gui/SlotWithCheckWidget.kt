package dev.arbor.gtnn.api.gui

import com.gregtechceu.gtceu.api.gui.widget.SlotWidget
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler
import kotlinx.coroutines.Runnable

class SlotWithCheckWidget(inventory: CustomItemStackHandler, index: Int, x: Int, y: Int,val check: Runnable)
    : SlotWidget(inventory, index, x, y) {

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        check.run()
        return super.mouseClicked(mouseX, mouseY, button)
    }
}