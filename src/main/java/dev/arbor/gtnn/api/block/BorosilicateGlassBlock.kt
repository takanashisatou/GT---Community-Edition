package dev.arbor.gtnn.api.block

import com.gregtechceu.gtceu.api.GTValues.*
import dev.arbor.gtnn.GTNN
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.GlassBlock
import java.util.*

class BorosilicateGlassBlock(
    properties: Properties
) : GlassBlock(properties), ITierBlock {

    override fun appendHoverText(
        stack: ItemStack,
        level: BlockGetter?,
        tooltip: MutableList<Component>,
        flag: TooltipFlag
    ) {
        tooltip += Component.translatable("${stack.descriptionId}.desc")
        super.appendHoverText(stack, level, tooltip, flag)
    }

    enum class Type(override val tier: Int) : ITierGlassType {
        BOROSILICATE(HV),
        TITANIUM_BOROSILICATE(EV),
        TUNGSTEN_BOROSILICATE(IV),
        IRIDIUM_BOROSILICATE(LuV),
        OSMIUM_BOROSILICATE(ZPM),
        DURANIUM_BOROSILICATE(UV),
        NEUTRONIUM_BOROSILICATE(UHV),
        COSMIC_NEUTRONIUM_BOROSILICATE(UEV),
        INFINITY_BOROSILICATE(UIV),
        TRANSCENDENT_METAL_BOROSILICATE(UXV),
        WHITE_DWARF_MATTER_BOROSILICATE(OpV);

        override val texture: ResourceLocation
            get() = GTNN.id("block/casings/transparent/%s".format(typeName))

        override val isOpticalGlass: Boolean
            get() = false

        override val typeName: String
            get() = "${name.lowercase(Locale.ROOT)}_glass"

        override fun toString() = typeName
    }
}