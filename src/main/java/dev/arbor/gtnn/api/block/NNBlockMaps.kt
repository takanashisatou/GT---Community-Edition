package dev.arbor.gtnn.api.block

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.GTCEuAPI
import com.gregtechceu.gtceu.api.block.ICoilType
import com.gregtechceu.gtceu.common.block.CoilBlock
import com.gregtechceu.gtceu.common.data.GTBlocks.*
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import java.util.*
import java.util.function.Supplier

object NNBlockMaps {
    // Glasses
    val ALL_GLASSES: Object2ObjectOpenHashMap<ITierType, Supplier<Block>> = Object2ObjectOpenHashMap()
    @JvmField
    val ALL_COIL_BLOCKS: Object2ObjectOpenHashMap<ITierType, Supplier<Block>> = Object2ObjectOpenHashMap()
    @JvmField
    val ALL_CP_CASINGS: Object2ObjectOpenHashMap<ITierType, Supplier<Block>> = Object2ObjectOpenHashMap()
    @JvmField
    val ALL_CP_TUBES: Object2ObjectOpenHashMap<ITierType, Supplier<Block>> = Object2ObjectOpenHashMap()
    @JvmField
    val ALL_MACHINE_CASINGS: Object2ObjectOpenHashMap<ITierType, Supplier<Block>> = Object2ObjectOpenHashMap()

    // Component Assembly Line
    val ALL_CA_TIRED_CASINGS: Object2ObjectOpenHashMap<ITierType, Supplier<Block>> =
        Object2ObjectOpenHashMap<ITierType, Supplier<Block>>()

    fun initBlocks() {
        GTCEuAPI.HEATING_COILS.forEach { (tier: ICoilType, block: Supplier<CoilBlock>) ->
            ALL_COIL_BLOCKS.put(object : ITierType {
                override val typeName: String = tier.name.lowercase(Locale.getDefault())
                override val tier: Int = tier.tier
            }, block::get)
        }

        //  ALL_CP_TUBES Init
        simpleTierTypeAdd(ALL_CP_TUBES, CASING_BRONZE_PIPE, 1)
        simpleTierTypeAdd(ALL_CP_TUBES, CASING_STEEL_PIPE, 2)
        simpleTierTypeAdd(ALL_CP_TUBES, CASING_TITANIUM_PIPE, 3)
        simpleTierTypeAdd(ALL_CP_TUBES, CASING_TUNGSTENSTEEL_PIPE, 4)


        //  ALL_CP_CASINGS Init
        cpTierTypeAdd(
            CASING_BRONZE_BRICKS,
            1,
            GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks")
        )
        cpTierTypeAdd(
            CASING_STEEL_SOLID, 2, GTCEu.id("block/casings/solid/machine_casing_solid_steel")
        )
        cpTierTypeAdd(
            CASING_ALUMINIUM_FROSTPROOF, 3, GTCEu.id("block/casings/solid/machine_casing_frost_proof")
        )
        cpTierTypeAdd(
            CASING_STAINLESS_CLEAN,
            4,
            GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel")
        )
        cpTierTypeAdd(
            CASING_TITANIUM_STABLE, 5, GTCEu.id("block/casings/solid/machine_casing_stable_titanium")
        )
        cpTierTypeAdd(
            CASING_TUNGSTENSTEEL_ROBUST,
            6,
            GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel")
        )
    }

    @Suppress("SameParameterValue")
    private fun simpleTierTypeAdd(
        map: MutableMap<ITierType, Supplier<Block>>, blockSupplier: Supplier<Block>, tier: Int
    ) {
        map[ITierType.WrappedTierType(blockSupplier, tier)] = blockSupplier
    }

    private fun cpTierTypeAdd(
        blockSupplier: Supplier<Block>, tier: Int, location: ResourceLocation
    ) {
        ALL_CP_CASINGS[IChemicalPlantCasing.CPCasingType(
            blockSupplier.get().descriptionId, tier, location
        )] = blockSupplier
    }
}