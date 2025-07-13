package dev.arbor.gtnn.client.renderer.machine

import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine
import com.gregtechceu.gtceu.client.model.machine.IControllerModelRenderer
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRender
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderType
import com.gregtechceu.gtceu.client.util.ModelUtils
import com.gregtechceu.gtceu.common.data.GTBlocks
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.tterrag.registrate.util.entry.BlockEntry
import dev.arbor.gtnn.api.machine.feature.IGTPPRenderMachine
import dev.arbor.gtnn.data.block.NNBlockMaps
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.block.BlockModelShaper
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.model.data.ModelData


@OnlyIn(Dist.CLIENT)
class GTPPMachineRender(
    private val baseCasing: BlockState,
    val type: ModelTypes
) : DynamicRender<IGTPPRenderMachine, GTPPMachineRender>(), IControllerModelRenderer {
    private val backedModelsMap: MutableMap<BlockState, BakedModel?> = mutableMapOf()

    init {
        ModelUtils.registerBakeEventListener(false) { event ->
            backedModelsMap[baseCasing] = event.models[BlockModelShaper.stateToModelLocation(baseCasing)]
            type.list.invoke().forEach {
                backedModelsMap[it] = event.models[BlockModelShaper.stateToModelLocation(it)]
            }
        }
    }

    constructor(baseCasingBlock: BlockEntry<Block>, type: ModelTypes):
            this(baseCasingBlock.get().defaultBlockState(),  type)

    override fun getType(): DynamicRenderType<IGTPPRenderMachine, GTPPMachineRender> {
        return TYPE
    }

    override fun getRenderQuads(
        machine: IGTPPRenderMachine?,
        level: BlockAndTintGetter?,
        pos: BlockPos?,
        blockState: BlockState?,
        side: Direction?,
        rand: RandomSource,
        modelData: ModelData,
        renderType: RenderType?
    ): List<BakedQuad?> {
        val quads: MutableList<BakedQuad?> = mutableListOf()
        if (machine is IGTPPRenderMachine && machine is MultiblockControllerMachine) {
            val casing: BlockState = machine.getAppearance()
            val model = backedModelsMap[casing] ?: return super.getRenderQuads(machine, level, pos, blockState, side, rand, modelData, renderType)
            emitQuads(quads, model, machine.level!!, machine.pos, casing, side, rand, modelData, renderType)
            return quads
        }
        return super.getRenderQuads(machine, level, pos, blockState, side, rand, modelData, renderType)
    }

    override fun render(
        machine: IGTPPRenderMachine,
        partialTick: Float,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
    }

    override fun shouldRender(machine: IGTPPRenderMachine, cameraPos: Vec3): Boolean {
        return machine is MultiblockControllerMachine && machine.isFormed
    }

    override fun renderPartModel(
        quads: MutableList<BakedQuad?>,
        controller: IMultiController,
        part: IMultiPart,
        frontFacing: Direction,
        side: Direction?,
        rand: RandomSource,
        modelData: ModelData,
        renderType: RenderType?
    ) {
        val machine = controller.self()
        val partPos = part.self().pos
        val casing: BlockState = (machine as? IGTPPRenderMachine)?.getPartAppearance(part) ?: baseCasing

        val model = backedModelsMap[casing] ?: return
        emitQuads(quads, model, machine.level!!, partPos, casing, side, rand, modelData, renderType)
    }

    private fun emitQuads(
        quads: MutableList<BakedQuad?>,
        model: BakedModel?,
        level: BlockAndTintGetter,
        pos: BlockPos,
        state: BlockState,
        side: Direction?,
        rand: RandomSource,
        modelData: ModelData,
        renderType: RenderType?
    ) {
        if (model == null) return
        val modelData = model.getModelData(level, pos, state, modelData)
        quads.addAll(model.getQuads(state, side, rand, modelData, renderType))
    }

    companion object {
        val CODEC: Codec<GTPPMachineRender> = RecordCodecBuilder.create { instance ->
            instance
                .group(BlockState.CODEC.fieldOf("base_casing").forGetter { it.baseCasing },
                    Codec.STRING.fieldOf("model_type").forGetter { it.type.name })
                .apply(instance) { baseCasing, name ->
                    GTPPMachineRender(baseCasing, ModelTypes.valueOf(name))
                }
        }
        val TYPE = DynamicRenderType<IGTPPRenderMachine, GTPPMachineRender>(CODEC)
    }

    enum class ModelTypes(val list: () -> List<BlockState>) {
        ChemicalPlantMachine({
            NNBlockMaps.ALL_CP_CASINGS.values.map { it.get().defaultBlockState() }.toList()
        }),
        CircuitAssemblyLineMachine({
            listOf(
                GTBlocks.CASING_STEEL_SOLID,
                GTBlocks.CASING_GRATE
            ).map { casing -> casing.get().defaultBlockState() }
        })
    }
}
