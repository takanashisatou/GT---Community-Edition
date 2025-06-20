package dev.arbor.gtnn.client.renderer.machine

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.capability.IWorkable
import com.gregtechceu.gtceu.api.machine.MachineDefinition
import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine
import com.gregtechceu.gtceu.client.model.WorkableOverlayModel
import com.gregtechceu.gtceu.client.renderer.machine.IControllerRenderer
import com.gregtechceu.gtceu.client.renderer.machine.MachineRenderer
import com.lowdragmc.lowdraglib.client.bakedpipeline.FaceQuad
import com.lowdragmc.lowdraglib.client.model.ModelFactory
import dev.arbor.gtnn.api.machine.feature.IGTPPRenderMachine
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.resources.model.ModelState
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.RandomSource
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.function.Consumer
import java.util.function.Supplier


@OnlyIn(Dist.CLIENT)
class GTPPMachineRenderer(baseCasing: ResourceLocation, workableModel: ResourceLocation, tint: Boolean) :
    MachineRenderer(if (tint) GTCEu.id("block/cube/tinted/all") else GTCEu.id("block/cube/all")), IControllerRenderer {
    private val overlayModel = WorkableOverlayModel(workableModel)

    init {
        setTextureOverride(mapOf("all" to baseCasing))
    }

    @OnlyIn(Dist.CLIENT)
    private fun render(
        side: Direction?,
        modelFacing: Direction?,
        quads: MutableList<BakedQuad>,
        machine: MetaMachine,
        modelState: ModelState,
        iMultiPart: IMultiPart?
    ) {
        var casing: BlockState? = null
        var sprite: ResourceLocation? = null
        if (side != null && modelFacing != null && machine is IGTPPRenderMachine) {
            if (iMultiPart != null) {
                sprite = machine.partLocationGetter(iMultiPart)
            }
            quads.add(
                FaceQuad.bakeFace(
                    modelFacing,
                    ModelFactory.getBlockSprite(sprite ?: machine.locationGetter()),
                    modelState
                )
            )
            casing = machine.getAppearance()
        }
        machine.self().definition.appearance = Supplier { casing }
    }

    @OnlyIn(Dist.CLIENT)
    override fun renderMachine(
        quads: MutableList<BakedQuad>,
        definition: MachineDefinition?,
        machine: MetaMachine?,
        frontFacing: Direction?,
        side: Direction?,
        rand: RandomSource?,
        modelFacing: Direction?,
        modelState: ModelState?
    ) {
        super.renderMachine(quads, definition, machine, frontFacing, side, rand, modelFacing, modelState)
        if (machine is IGTPPRenderMachine && machine is MultiblockControllerMachine) {
            if (machine.isFormed) {
                quads.clear()
                render(side, modelFacing, quads, machine, modelState!!, null)
            }
        }

        if (machine is IWorkable) {
            quads.addAll(
                overlayModel.bakeQuads(
                    side,
                    frontFacing!!,
                    Direction.NORTH,
                    machine.isActive,
                    machine.isWorkingEnabled
                )
            )
        } else {
            quads.addAll(overlayModel.bakeQuads(side, frontFacing!!, Direction.NORTH, false, false))
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun renderPartModel(
        quads: MutableList<BakedQuad>,
        machine: IMultiController?,
        part: IMultiPart?,
        frontFacing: Direction?,
        side: Direction?,
        rand: RandomSource?,
        modelFacing: Direction?,
        modelState: ModelState?
    ) {
        render(side, modelFacing, quads, machine!!.self(), modelState!!,  part)
    }

    @OnlyIn(Dist.CLIENT)
    override fun onPrepareTextureAtlas(atlasName: ResourceLocation, register: Consumer<ResourceLocation>) {
        super.onPrepareTextureAtlas(atlasName, register)
        if (atlasName == InventoryMenu.BLOCK_ATLAS) {
            this.overlayModel.registerTextureAtlas(register)
        }
    }
}
