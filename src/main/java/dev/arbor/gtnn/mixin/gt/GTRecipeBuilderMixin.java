package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import dev.arbor.gtnn.api.registry.GTRecipeManager;
import net.minecraft.data.recipes.FinishedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(value = GTRecipeBuilder.class, remap = false)
public class GTRecipeBuilderMixin {
    @Shadow
    public GTRecipeType recipeType;

    @Inject(
            method = "save(Ljava/util/function/Consumer;)V",
            at =
            @At(
                    value = "INVOKE",
                    target =
                            "Lcom/gregtechceu/gtceu/data/recipe/builder/GTRecipeBuilder;build()Lnet/minecraft/data/recipes/FinishedRecipe;"),
            cancellable = true)
    private void save(Consumer<FinishedRecipe> consumer, CallbackInfo ci) {
        if (GTRecipeManager.INSTANCE.shouldRemove(recipeType, (GTRecipeBuilder) (Object) this)) ci.cancel();
    }
}
