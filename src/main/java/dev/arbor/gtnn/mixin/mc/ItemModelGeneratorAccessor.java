package dev.arbor.gtnn.mixin.mc;

import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.texture.SpriteContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(ItemModelGenerator.class)
public interface ItemModelGeneratorAccessor {
    @Invoker
    List<ItemModelGenerator.Span> callGetSpans(SpriteContents sprite);
}
