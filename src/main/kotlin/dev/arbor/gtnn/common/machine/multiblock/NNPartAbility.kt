package dev.arbor.gtnn.common.machine.multiblock

import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility

class NNPartAbility(name: String) : PartAbility(name) {
    companion object{
        val NEUTRON_ACCELERATOR = PartAbility("neutron_accelerator")
        val CATALYST: PartAbility = PartAbility("catalyst")
        val NEUTRON_SENSOR = PartAbility("neutron_sensor")
    }
}
