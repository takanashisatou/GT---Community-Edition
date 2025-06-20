package dev.arbor.gtnn.api.machine.multiblock

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern
import com.gregtechceu.gtceu.api.pattern.Predicates.*
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection
import com.gregtechceu.gtceu.common.data.GTBlocks
import com.gregtechceu.gtceu.utils.GTUtil
import dev.arbor.gtnn.api.machine.feature.IGTPPMachine

class FactoryMacerationMachine(holder: IMachineBlockEntity) : WorkableElectricMultiblockMachine(holder), IGTPPMachine {
    override val maxParallel: Int get() = 2 * GTUtil.getTierByVoltage(maxVoltage)
    override val speedMultiplier: Int get() = 60

    companion object {
        val Pattern
            get() = { definition: MultiblockMachineDefinition ->
                FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.BACK, RelativeDirection.UP)
                    .aisle("YSY", "YYY", "YYY")
                    .aisle("XXX", "X#X", "XXX").setRepeatable(4)
                    .aisle("XXX", "XXX", "XXX")
                    .where('S', controller(blocks(definition.block)))
                    .where(
                        'Y', blocks(GTBlocks.CASING_TITANIUM_STABLE.get())
                            .or(abilities(PartAbility.INPUT_ENERGY).setMinGlobalLimited(1))
                            .or(abilities(PartAbility.IMPORT_ITEMS))
                            .or(abilities(PartAbility.IMPORT_FLUIDS))
                            .or(autoAbilities(true, false, false).setMaxGlobalLimited(1))
                    )
                    .where(
                        'X', blocks(GTBlocks.CASING_TITANIUM_STABLE.get())
                            .or(abilities(PartAbility.EXPORT_ITEMS).setMaxLayerLimited(1))
                            .or(abilities(PartAbility.MUFFLER).setExactLimit(1))
                    )
                    .where('#', air())
                    .build()
            }
    }
}