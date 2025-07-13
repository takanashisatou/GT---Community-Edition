package dev.arbor.gtnn.common.item

import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import java.util.function.Supplier

class EtchedItem(properties: Properties, private val itemSupplier: Supplier<out ItemLike>, private val type: String) :
    Item(properties) {
    override fun getDescription(): Component {
        return Component.translatable(
            "gtnn.item.$type", itemSupplier.get().asItem().description
        )
    }

    override fun getName(stack: ItemStack): Component {
        return description
    }
}