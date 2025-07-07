package dev.arbor.gtnn.data.tags

import com.gregtechceu.gtceu.data.recipe.CustomTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

object CIRCustomTags {
    val CIRCUITS: Array<TagKey<Item>> = arrayOf(
        CustomTags.ULV_CIRCUITS,
        CustomTags.LV_CIRCUITS,
        CustomTags.MV_CIRCUITS,
        CustomTags.HV_CIRCUITS,
        CustomTags.EV_CIRCUITS,
        CustomTags.IV_CIRCUITS,
        CustomTags.LuV_CIRCUITS,
        CustomTags.ZPM_CIRCUITS,
        CustomTags.UV_CIRCUITS,
        CustomTags.UHV_CIRCUITS,
        CustomTags.UEV_CIRCUITS,
        CustomTags.UIV_CIRCUITS,
        CustomTags.UXV_CIRCUITS,
        CustomTags.OpV_CIRCUITS,
        CustomTags.MAX_CIRCUITS
    )

    val BATTERIES: Array<TagKey<Item>> = arrayOf(
        CustomTags.ULV_BATTERIES,
        CustomTags.LV_BATTERIES,
        CustomTags.MV_BATTERIES,
        CustomTags.HV_BATTERIES,
        CustomTags.EV_BATTERIES,
        CustomTags.IV_BATTERIES,
        CustomTags.LuV_BATTERIES,
        CustomTags.ZPM_BATTERIES,
        CustomTags.UV_BATTERIES,
        CustomTags.UHV_BATTERIES
    )
}