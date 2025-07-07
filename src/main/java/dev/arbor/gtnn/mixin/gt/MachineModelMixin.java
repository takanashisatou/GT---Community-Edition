package dev.arbor.gtnn.mixin.gt;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.client.model.machine.MachineModel;
import com.llamalad7.mixinextras.sugar.Local;
import dev.arbor.gtnn.api.machine.feature.IGTPPRenderMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mixin(value = MachineModel.class, remap = false)
public abstract class MachineModelMixin {
    @Shadow
    protected abstract List<BakedQuad> renderPartOverrides(MachineModel controllerModel, IMultiController controller, List<BakedQuad> originalQuads, IMultiPart part, Direction frontFacing, @Nullable Direction side, RandomSource rand, ModelData modelData, @Nullable RenderType renderType);

    @Inject(method = "renderMachine", at = @At(value = "INVOKE", target = "Lcom/gregtechceu/gtceu/client/model/machine/MachineModel;renderBaseModel(Ljava/util/List;Lcom/gregtechceu/gtceu/client/model/machine/MachineRenderState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/util/RandomSource;Lnet/minecraftforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V", shift = At.Shift.AFTER))
    private void renderMachine(@Nullable MetaMachine machine, @Nullable BlockState blockState, Direction frontFacing, @Nullable Direction side, RandomSource rand, @NotNull ModelData modelData, @Nullable RenderType renderType, CallbackInfoReturnable<List<BakedQuad>> cir, @Local List<BakedQuad> quads) {
        if (machine instanceof IGTPPRenderMachine && blockState != null) {
            quads.clear();
        }
    }
}
