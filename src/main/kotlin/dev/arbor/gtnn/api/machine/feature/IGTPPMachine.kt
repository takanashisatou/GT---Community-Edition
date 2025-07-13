package dev.arbor.gtnn.api.machine.feature

interface IGTPPMachine {
    val maxParallel: Int get() = 1
    val speedMultiplier: Int get() = 100
    val energyConsumeMultiplier: Int get() = 100
    val pollution get() = 0
}