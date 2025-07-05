package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.item.TagPrefixItem;
import com.gregtechceu.gtceu.api.item.component.ICustomRenderer;

import com.lowdragmc.lowdraglib.Platform;
import com.lowdragmc.lowdraglib.client.renderer.IRenderer;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import dev.arbor.gtnn.api.item.INNItemRendererProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TagPrefixItem.class, remap = false)
public abstract class TagPrefixItemMixin extends Item implements INNItemRendererProvider {

    @Unique
    private ICustomRenderer gtnn$customRenderer;

    private TagPrefixItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "<init>(Lnet/minecraft/world/item/Item$Properties;Lcom/gregtechceu/gtceu/api/data/tag/TagPrefix;Lcom/gregtechceu/gtceu/api/data/chemical/material/Material;)V",
            at = @At(value = "RETURN"))
    private void TagPrefixItem(
                               Properties properties, TagPrefix tagPrefix, Material material, CallbackInfo ci) {
        if (Platform.isClient()) {
            // if (material.getMaterialIconSet() instanceof NNMaterialIconSet iconSet) {
            // this.gtnn$customRenderer = iconSet.getCustomRenderer();
            // }

            // if (tagPrefix instanceof NNTagPrefix prefix) {
            // this.gtnn$customRenderer = prefix.getCustomRenderer();
            // }
        }
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public ICustomRenderer getRenderInfo(@NotNull ItemStack itemStack) {
        return gtnn$customRenderer;
    }

    @Nullable
    @Override
    public IRenderer getRenderer(ItemStack stack) {
        if (gtnn$customRenderer != null) {
            return gtnn$customRenderer.getRenderer();
        }
        return null;
    }
}
