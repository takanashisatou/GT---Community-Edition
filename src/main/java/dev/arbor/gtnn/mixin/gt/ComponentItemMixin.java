package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.ICustomRenderer;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;

import dev.arbor.gtnn.api.item.INNItemRendererProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(value = ComponentItem.class, remap = false)
public abstract class ComponentItemMixin extends Item implements INNItemRendererProvider {
    @Shadow
    protected List<IItemComponent> components;

    public ComponentItemMixin(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public ICustomRenderer getRenderInfo(@NotNull ItemStack itemStack) {
        for (IItemComponent component : components) {
            if (component instanceof ICustomRenderer customRenderer) {
                return customRenderer;
            }
        }
        return null;
    }
}
