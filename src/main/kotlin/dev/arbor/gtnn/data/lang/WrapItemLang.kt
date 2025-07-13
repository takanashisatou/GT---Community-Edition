package dev.arbor.gtnn.data.lang

import com.gregtechceu.gtceu.api.GTValues
import dev.arbor.gtnn.api.registry.NNLangProvider
import dev.arbor.gtnn.data.item.EPMItems
import dev.arbor.gtnn.data.item.GTNNWrapItem
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

object WrapItemLang {
    fun init(provider: NNLangProvider) {
        EPMItemLang.init(provider)

        provider.add("gtnn.item.etched", "Etched %s", "蚀刻%s")
        provider.add("gtnn.item.etched_board", "Etched %s Board", "蚀刻%s基板")
        provider.add("gtnn.item.etched_base", "Etched %s Base", "蚀刻%s基底")

        provider.addItemName(GTNNWrapItem.WRAP_BOARD_COATED, "Wrap Resin Circuit Board", "封装覆膜电路基板")
        provider.addItemName(GTNNWrapItem.WRAP_BOARD_PHENOLIC, "Wrap Phenolic Circuit Board", "封装酚醛树脂电路基板")
        provider.addItemName(GTNNWrapItem.WRAP_BOARD_PLASTIC, "Wrap Plastic Circuit Board", "封装塑料电路基板")
        provider.addItemName(GTNNWrapItem.WRAP_BOARD_EPOXY, "Wrap Epoxy Circuit Board", "封装环氧树脂基板")
        provider.addItemName(GTNNWrapItem.WRAP_BOARD_FIBER, "Wrap Fiber-Reinforced Circuit Board", "封装纤维强化电路基板")
        provider.addItemName(
            GTNNWrapItem.WRAP_BOARD_MULTILAYER_FIBER,
            "Wrap Multi-layer Fiber-Reinforced Circuit Board",
            "封装多层纤维强化电路基板"
        )
        provider.addItemName(GTNNWrapItem.WRAP_BOARD_WETWARE, "Wrap Wetware Circuit Board", "封装湿件电路基板")

        provider.addItemName(
            GTNNWrapItem.WRAP_CIRCUIT_BOARD_BASIC, "Wrap Resin Printed Circuit Board", "封装覆膜印刷电路基板"
        )
        provider.addItemName(
            GTNNWrapItem.WRAP_CIRCUIT_BOARD_GOOD, "Wrap Phenolic Printed Circuit Board", "封装酚醛树脂印刷电路基板"
        )
        provider.addItemName(
            GTNNWrapItem.WRAP_CIRCUIT_BOARD_PLASTIC, "Wrap Plastic Printed Circuit Board", "封装塑料印刷电路基板"
        )
        provider.addItemName(
            GTNNWrapItem.WRAP_CIRCUIT_BOARD_ADVANCED, "Wrap Epoxy Printed Circuit Board", "封装环氧树脂印刷电路基板"
        )
        provider.addItemName(
            GTNNWrapItem.WRAP_CIRCUIT_BOARD_EXTREME, "Wrap Fiber-Reinforced Printed Circuit Board", "封装纤维强化印刷电路基板"
        )
        provider.addItemName(
            GTNNWrapItem.WRAP_CIRCUIT_BOARD_ELITE,
            "Wrap Multi-layer Fiber-Reinforced Printed Circuit Board",
            "封装多层纤维强化印刷电路基板"
        )
        provider.addItemName(
            GTNNWrapItem.WRAP_CIRCUIT_BOARD_WETWARE, "Wrap Wetware Printed Circuit Board", "封装湿件印刷电路基板"
        )

        provider.addItemName(GTNNWrapItem.WRAP_SMD_TRANSISTOR, "Wrap Transistor", "封装贴片晶体管")
        provider.addItemName(GTNNWrapItem.WRAP_SMD_RESISTOR, "Wrap Resistor", "封装贴片电阻")
        provider.addItemName(GTNNWrapItem.WRAP_SMD_CAPACITOR, "Wrap Capacitor", "封装贴片电容")
        provider.addItemName(GTNNWrapItem.WRAP_SMD_DIODE, "Wrap Diode", "封装贴片二极管")
        provider.addItemName(GTNNWrapItem.WRAP_SMD_INDUCTOR, "Wrap Inductor", "封装贴片电感")
        provider.addItemName(GTNNWrapItem.WRAP_SMD_TRANSISTOR_ADVANCED, "Wrap SMD Transistor", "封装高级贴片晶体管")
        provider.addItemName(GTNNWrapItem.WRAP_SMD_RESISTOR_ADVANCED, "Wrap SMD Resistor", "封装高级贴片电阻")
        provider.addItemName(GTNNWrapItem.WRAP_SMD_CAPACITOR_ADVANCED, "Wrap SMD Capacitor", "封装高级贴片电容")
        provider.addItemName(GTNNWrapItem.WRAP_SMD_DIODE_ADVANCED, "Wrap SMD Diode", "封装高级贴片二极管")
        provider.addItemName(GTNNWrapItem.WRAP_SMD_INDUCTOR_ADVANCED, "Wrap SMD Inductor", "封装高级贴片电感")

        provider.addItemName(GTNNWrapItem.WRAP_CPU_CHIP, "Warp CPU Chip", "封装CPU芯片")
        provider.addItemName(GTNNWrapItem.WRAP_RAM_CHIP, "Warp RAM Chip", "封装RAM芯片")
        provider.addItemName(GTNNWrapItem.WRAP_ILC_CHIP, "Warp IC Chip", "封装IC芯片")
        provider.addItemName(GTNNWrapItem.WARP_NANO_CPU_CHIP, "Warp Nano CPU Chip", "封装纳米CPU芯片")
        provider.addItemName(GTNNWrapItem.WARP_QBIT_CPU_CHIP, "Warp Qubit CPU Chip", "封装量子位CPU芯片")
        provider.addItemName(GTNNWrapItem.WARP_SIMPLE_SOC, "Warp Simple SoC", "封装简易SoC")
        provider.addItemName(GTNNWrapItem.WARP_SOC, "Warp SoC", "封装SoC")
        provider.addItemName(GTNNWrapItem.WARP_ADVANCED_SOC, "Warp ASoC", "封装ASoC")
        provider.addItemName(GTNNWrapItem.WARP_HIGHLY_ADVANCED_SOC, "Warp HASoC", "封装HASoC")
        provider.addItemName(GTNNWrapItem.WARP_NAND_MEMORY_CHIP, "Warp NAND Chip", "封装NAND存储器芯片")
        provider.addItemName(GTNNWrapItem.WARP_NOR_MEMORY_CHIP, "Warp NOR Memory Chip", "封装NOR存储器芯片")

        GTNNWrapItem.WRAP_ITEM_MAP
            .object2ObjectEntrySet()
            .fastForEach({ entry ->
                generateWrapItemLang(
                    provider, entry.value.asItem(), entry.key.asItem()
                )
            })
        GTNNWrapItem.WRAP_CIRCUIT_MAP
            .object2ObjectEntrySet()
            .fastForEach(
                { entry -> generateWrapCircuitLang(provider, entry.value.asItem(), entry.key) })
    }

    private fun generateWrapItemLang(provider: NNLangProvider, item: Item, wrappedItem: Item) {
        val id = wrappedItem.descriptionId
        val text = provider.data()[id]
        val textCN = provider.cnData()[id]
        if (text != null) {
            provider.addItem({ item }, "Wrap $text")
        }
        if (textCN != null) {
            provider.addItemCN({ item }, "封装$textCN")
        }
    }

    private fun generateWrapCircuitLang(
        provider: NNLangProvider, item: Item, wrappedKey: TagKey<Item>
    ) {
        val key: String? =
            wrappedKey.location().path.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
        for (i in GTValues.VN.indices) {
            val tier = GTValues.VN[i]
            if (tier.equals(key, ignoreCase = true)) {
                provider.addItemName(
                    { item },
                    "Wrap Circuit (${GTValues.VNF[i]}§r)",
                    "封装电路板（${GTValues.VNF[i]}§r）"
                )
            }
        }
    }

    object EPMItemLang {
        fun init(provider: NNLangProvider) {
            /**/////////////////////////////////// */
            // *******    Circuit Items   *******//
            /**/////////////////////////////////// */

            //  Boards

            provider.addItemWithTooltip(
                EPMItems.GOOWARE_BOARD,
                "Kapton™ Board",
                "Kapton™电路基板",
                "§7Dupont™ Pyralux® TK Laminate Film",
                "§7杜邦™ Pyralux® TK层压薄膜"
            )
            provider.addItemWithTooltip(
                EPMItems.OPTICAL_BOARD,
                "Gallium Nitride Semiconducting Board",
                "氮化镓半导体材料电路基板",
                "§7Absolute ideal materials for Optoelectronic Devices",
                "§7绝对理想的光电器件材料"
            )
            provider.addItemWithTooltip(
                EPMItems.SPINTRONIC_BOARD,
                "Carbon Nanotube Magnetic storage Board",
                "碳纳米管磁性存储电路基板",
                "§7Third generation Spintronic Technology",
                "§7第三代自旋电子技术"
            )

            provider.addItemWithTooltip(
                EPMItems.GOOWARE_PRINTED_CIRCUIT_BOARD,
                "Super Circuit Board",
                "终极印刷电路基板",
                "§7Revitalization on circuit boards",
                "§7电路板上焕发生机"
            )
            provider.addItemWithTooltip(
                EPMItems.OPTICAL_PRINTED_CIRCUIT_BOARD,
                "Ultimate Circuit Board",
                "究极印刷电路基板",
                "§7The Technology Star of the Semiconductor Industry",
                "§7半导体工业的技术之星"
            )
            provider.addItemWithTooltip(
                EPMItems.SPINTRONIC_PRINTED_CIRCUIT_BOARD,
                "Infinite Circuit Board",
                "无尽印刷电路基板",
                "§7The Best Implementation of Spin Transfer Torque",
                "§7自旋转移矩效应的最好实现"
            )

            //  Circuit Tier
            provider.addMultiLang(
                { tier ->
                    "item.gtnn.circuit.${GTValues.VN[tier].lowercase()}.desc"
                },
                { tier -> "${GTValues.VNF[tier]}-Tier Circuit" },
                { tier -> "${GTValues.VNF[tier]}级电路" },
                *GTValues.tiersBetween(GTValues.ZPM, GTValues.MAX)
            )

            //  Gooware Circuits
            provider.addItemWithTooltip(
                EPMItems.GOOWARE_PROCESSOR,
                "Gooware Processor",
                "生物活性处理器",
                "Viscous organic slurry adheres the board",
                "§7粘稠的有机浆液附着于表面"
            )
            provider.addItemWithTooltip(
                EPMItems.GOOWARE_ASSEMBLY,
                "Gooware Assembly",
                "生物活性处理器集群",
                "Seems to be able to hear whispers",
                "§7似乎能听到窃窃私语"
            )
            provider.addItemWithTooltip(
                EPMItems.GOOWARE_COMPUTER,
                "Gooware Supercomputer",
                "生物活性超级计算机",
                "Slime mold covered in metal",
                "§7金属之间布满了黏菌"
            )
            provider.addItemWithTooltip(
                EPMItems.GOOWARE_MAINFRAME,
                "Gooware Mainframe",
                "生物活性主机",
                "Microbial Awareness Network",
                "§7菌群意识网络"
            )

            //  Optical Circuits
            provider.addItemWithTooltip(
                EPMItems.OPTICAL_PROCESSOR,
                "Optical IMC Processor",
                "光学存算一体处理器",
                "§7Ultra efficient Photoelectron Transport",
                "§7超高效光电子载运"
            )
            provider.addItemWithTooltip(
                EPMItems.OPTICAL_ASSEMBLY, "Optical IMC Assembly", "光学存算一体处理器集群", "§7Photon Surge", "§7光子涌流"
            )
            provider.addItemWithTooltip(
                EPMItems.OPTICAL_COMPUTER,
                "Optical IMC Supercomputer",
                "光学存算一体超级计算机",
                "§7Ultra Large scale Computing data support",
                "§7超大规模计算数据支持"
            )
            provider.addItemWithTooltip(
                EPMItems.OPTICAL_MAINFRAME,
                "Optical IMC Mainframe",
                "光学存算一体主机",
                "§7Calculating speed infinitely close to Light speed",
                "§7计算速度无限逼近于光速"
            )

            //  Spintronic Circuits
            provider.addItemWithTooltip(
                EPMItems.SPINTRONIC_PROCESSOR,
                "Spintronic IMC Processor",
                "自旋电子存算一体处理器",
                "§7Super Magnetic Semiconductor Circuit",
                "§7超级磁性半导体电路"
            )
            provider.addItemWithTooltip(
                EPMItems.SPINTRONIC_ASSEMBLY,
                "Spintronic IMC Assembly",
                "自旋电子存算一体处理器集群",
                "§7Quantum Random Walk",
                "§7量子随机游走"
            )
            provider.addItemWithTooltip(
                EPMItems.SPINTRONIC_COMPUTER,
                "Spintronic IMC Supercomputer",
                "自旋电子存算一体超级计算机",
                "§7Control everything with Spin",
                "§7以自旋控制一切"
            )
            provider.addItemWithTooltip(
                EPMItems.SPINTRONIC_MAINFRAME,
                "Spintronic IMC Mainframe",
                "自旋电子存算一体主机",
                "§7Circuit from the Future",
                "§7来自未来的电路"
            )

            //  Cosmic Circuits
            provider.addItemWithTooltip(
                EPMItems.COSMIC_PROCESSOR,
                "Cosmic Planetary Processor",
                "寰宇行星级处理器",
                "§7Holding Star in Hand",
                "§7手握星辰"
            )
            provider.addItemWithTooltip(
                EPMItems.COSMIC_ASSEMBLY,
                "Cosmic Planetary Assembly",
                "寰宇行星级处理器集群",
                "§7Rotate slightly",
                "§7于握揽微微转动"
            )
            provider.addItemWithTooltip(
                EPMItems.COSMIC_COMPUTER,
                "Cosmic Planetary Supercomputer",
                "寰宇行星级超级计算机",
                "§7A small thing with a density approaching a Singularity",
                "§7密度趋近于奇点的小东西"
            )
            provider.addItemWithTooltip(
                EPMItems.COSMIC_MAINFRAME,
                "Cosmic Planetary Mainframe",
                "寰宇行星级主机",
                "§7Simulate everything, Analyze everything, Understand everything",
                "§7模拟一切，分析一切，理解一切"
            )

            //  Supracausal Circuits
            provider.addItemWithTooltip(
                EPMItems.SUPRACAUSAL_PROCESSOR,
                "Supracausal Galactic Processor",
                "超因果星系级处理器",
                "§7The laws of the Universe emerge here",
                "§7宇宙的法则涌现于此"
            )
            provider.addItemWithTooltip(
                EPMItems.SUPRACAUSAL_ASSEMBLY,
                "Supracausal Galactic Assembly",
                "超因果星系级处理器集群",
                "§7Crossing the Barrier in front of the Gate of Truth",
                "§7跨过真理之门前的宏伟障壁"
            )
            provider.addItemWithTooltip(
                EPMItems.SUPRACAUSAL_COMPUTER,
                "Supracausal Galactic Supercomputer",
                "超因果星系级超级计算机",
                "§7Beyond the Shadow of Time",
                "§7超越时间之影"
            )
            provider.addItemWithTooltip(
                EPMItems.SUPRACAUSAL_MAINFRAME,
                "Supracausal Galactic Mainframe",
                "超因果星系级主机",
                "§7One of All Things,and the Original Miracle",
                "§7万物归一者，原初的神迹"
            )

            //  Components
            provider.addItemWithTooltip(
                EPMItems.OPTICAL_TRANSISTOR, "Phototransistor", "光学晶体管", "§Optical Transistor", "§7光电子晶体管"
            )
            provider.addItemWithTooltip(
                EPMItems.OPTICAL_RESISTOR, "Photoresistor", "光敏电阻", "§7Optical Resistor", "§7光电子电阻"
            )
            provider.addItemWithTooltip(
                EPMItems.OPTICAL_CAPACITOR, "Optical Integrator", "光学整合器", "§7Optical Capacitor", "§7光电子电容"
            )
            provider.addItemWithTooltip(
                EPMItems.OPTICAL_DIODE, "Optical Isolator", "光频隔离器", "§7Optical Diode", "§7光电子二极管"
            )
            provider.addItemWithTooltip(
                EPMItems.OPTICAL_INDUCTOR, "Optical Polarizer", "光学偏振器", "§7Optical Inductor", "§7光电子电感"
            )

            provider.addItemWithTooltip(
                EPMItems.SPINTRONIC_TRANSISTOR, "MOSFET", "自旋金属-氧半场效晶体管", "§7Spintronic Transistor", "§7自旋电子晶体管"
            )
            provider.addItemWithTooltip(
                EPMItems.SPINTRONIC_RESISTOR, "Magnetoresistor", "磁阻器", "§7Spintronic Resistor", "§7自旋电子电阻"
            )
            provider.addItemWithTooltip(
                EPMItems.SPINTRONIC_CAPACITOR, "Ultracapacitor", "超级电容", "§7Spintronic Capacitor", "§7自旋电子电容"
            )
            provider.addItemWithTooltip(
                EPMItems.SPINTRONIC_DIODE, "Schottky Diode", "肖氏势垒二极管", "§7Spintronic Diode", "§7自旋电子二极管"
            )
            provider.addItemWithTooltip(
                EPMItems.SPINTRONIC_INDUCTOR, "Spin Polarizer", "自旋偏振器", "§7Spintronic Inductor", "§7自旋电子电感"
            )

            provider.addItemWithTooltip(
                EPMItems.COSMIC_TRANSISTOR,
                "Crystal Information Payload",
                "晶体信息载荷",
                "§7Cosmic Transistor",
                "§7寰宇晶体管"
            )
            provider.addItemWithTooltip(
                EPMItems.COSMIC_RESISTOR,
                "Micro Interstellar material Information Wall",
                "微型星际物质信息壁",
                "§7Cosmic Resistor",
                "§7寰宇电阻"
            )
            provider.addItemWithTooltip(
                EPMItems.COSMIC_CAPACITOR, "Holographic Energy Charge", "全息能量荷", "§7Cosmic Capacitor", "§7寰宇电容"
            )
            provider.addItemWithTooltip(
                EPMItems.COSMIC_DIODE, "Cosmic Ion Diode", "宇宙离子极管", "§7Cosmic Diode", "§7寰宇二极管"
            )
            provider.addItemWithTooltip(
                EPMItems.COSMIC_INDUCTOR, "Zenith Polarizer", "天顶星偏振器", "§7Cosmic Inductor", "§7寰宇电感"
            )

            provider.addItemWithTooltip(
                EPMItems.SUPRACAUSAL_TRANSISTOR,
                "Kaluza-Klein Extradimensional Dilator Field Effect Transistor",
                "卡鲁扎-克莱因额外维胀子场效应管",
                "§7Supracausal Transistor",
                "§7超因果晶体管"
            )
            provider.addItemWithTooltip(
                EPMItems.SUPRACAUSAL_RESISTOR,
                "Non anomalous Quantum Main Constraint Generator",
                "非反常量子主约束生成器",
                "§7Supracausal Resistor",
                "§7超因果电阻"
            )
            provider.addItemWithTooltip(
                EPMItems.SUPRACAUSAL_CAPACITOR,
                "Energy-Momentum-Stress Tensor Memory",
                "能量-动量-应力张量存储器",
                "§7Supracausal Capacitor",
                "§7超因果电容"
            )
            provider.addItemWithTooltip(
                EPMItems.SUPRACAUSAL_DIODE,
                "Spin network Carrier Diode",
                "自旋网络载波极管",
                "§7Supracausal Diode",
                "§7超因果二极管"
            )
            provider.addItemWithTooltip(
                EPMItems.SUPRACAUSAL_INDUCTOR,
                "Supersymmetric Conformal Polarizer",
                "超对称共形偏振器",
                "§7Supracausal Inductor",
                "§7超因果电感"
            )

            //  SoC
            provider.addItemWithTooltip(
                EPMItems.INTRAVITAL_SOC, "Intravital SoC", "活体SoC", "§7A wriggling Circuits", "§7蠕动着的电路"
            )
            provider.addItemWithTooltip(
                EPMItems.PHOTOELECTRON_SOC, "Photoelectric SoC", "光电子SoC", "§7Luminous Circuits", "§7荧光电路"
            )

            //  Gooware Components

            //  Optical Components
            provider.addItemWithTooltip(
                EPMItems.OPTICAL_IMC_BOARD,
                "Preassembled Photoelectric Circuit Board",
                "光学控制电路基板",
                "§7The Basis Point Of The Integration Of Storage And Computing",
                "§7存算一体的基点"
            )

            //  Spintronic Components

            //  Cosmic Components

            //  Supracausal Components
            provider.addItemName(
                EPMItems.MANIFOLD_OSCILLATORY_POWER_CELL, "Manifold Oscillatory Power Cell", "流形震荡能量单元"
            )

            //  Crystal Components
            provider.addItemWithTooltip(
                EPMItems.DIAMOND_CHIP,
                "Engraved Diamond Crystal Chip",
                "刻蚀钻石晶片",
                "§7Raw Crystal Logic Circuit",
                "§7晶体逻辑电路原料"
            )
            provider.addItemWithTooltip(
                EPMItems.RUBY_CHIP,
                "Engraved Ruby Crystal Chip",
                "刻蚀红宝石晶片",
                "§7Raw Crystal Control Circuit",
                "§7晶体控制电路原料"
            )
            provider.addItemWithTooltip(
                EPMItems.SAPPHIRE_CHIP,
                "Engraved Sapphire Crystal Chip",
                "刻蚀蓝宝石晶片",
                "§7Raw Crystal Conversion Circuit",
                "§7晶体转换电路原料"
            )
            provider.addItemWithTooltip(
                EPMItems.DIAMOND_MODULATOR,
                "Diamond Crystal Modulator",
                "钻石晶体调节器",
                "§7Logic Processing Circuit",
                "§7逻辑处理电路"
            )
            provider.addItemWithTooltip(
                EPMItems.RUBY_MODULATOR,
                "Ruby Crystal Modulator",
                "红宝石晶体调节器",
                "§7Control Processing Circuit",
                "§7控制处理电路"
            )
            provider.addItemWithTooltip(
                EPMItems.SAPPHIRE_MODULATOR,
                "Sapphire Crystal Modulator",
                "蓝宝石晶体调节器",
                "§7Conversion Processing Circuit",
                "§7转换处理电路"
            )

            /**/////////////////////////////////// */
            // *******     Wafer Items    *******//
            /**/////////////////////////////////// */

            //  Wafers
            provider.addItemWithTooltip(
                EPMItems.NANO_PIC_WAFER, "NPIC Wafer", "NPIC晶圆", "§7Raw Nano Power Circuit", "§7纳米功率集成电路原料"
            )
            provider.addItemWithTooltip(
                EPMItems.NANO_PIC_CHIP, "NPIC", "NPIC芯片", "§7Nano Power Integrated Circuit", "§7纳米功率集成电路"
            )
            provider.addItemWithTooltip(
                EPMItems.PICO_PIC_WAFER, "PPIC Wafer", "PPIC晶圆", "§7Raw Pico Power Circuit", "§7皮米功率集成电路原料"
            )
            provider.addItemWithTooltip(
                EPMItems.PICO_PIC_CHIP, "PPIC", "PPIC芯片", "§7Pico Power Integrated Circuit", "§7皮米功率集成电路"
            )
            provider.addItemWithTooltip(
                EPMItems.DUBNIUM_BOULE,
                "Dubnium-doped Monocrystalline Silicon Boule",
                "𬭊掺杂的单晶硅",
                "§7Raw Circuit",
                "§7电路原料"
            )
            provider.addItemWithTooltip(
                EPMItems.DUBNIUM_WAFER, "Dubnium-doped Wafer", "𬭊掺杂的晶圆", "§7Raw Circuit", "§7电路原料"
            )
            provider.addItemWithTooltip(
                EPMItems.EUROPIUM_CUBIC_ZIRCONIA_BOULE,
                "Europium-doped Monocrystalline Cubic Zirconia Boule",
                "铕掺杂的单晶立方氧化锆",
                "§7Raw Crystal",
                "§7晶体原料"
            )
            provider.addItemWithTooltip(
                EPMItems.EUROPIUM_CUBIC_ZIRCONIA_WAFER,
                "Europium-doped Cubic Zirconia Wafer",
                "铕掺杂的立方氧化锆晶圆",
                "§7Raw Crystal",
                "§7晶体原料"
            )
            provider.addItemWithTooltip(
                EPMItems.CRYSTAL_INTERFACE_WAFER, "Crystal Interface Wafer", "晶体接口晶圆", "§7Raw Crystal", "§7晶体原料"
            )
            provider.addItemWithTooltip(
                EPMItems.BOHRIUM_STRONTIUM_CARBONATE_BOULE,
                "Bohrium-doped Monocrystalline Strontium Carbonate Boule",
                "𬭛掺杂的单晶碳酸锶",
                "§7Raw Optical Crystal",
                "§7光学晶体原料"
            )
            provider.addItemWithTooltip(
                EPMItems.BOHRIUM_STRONTIUM_CARBONATE_WAFER,
                "Bohrium-doped Strontium Carbonate Wafer",
                "𬭛掺杂的碳酸锶晶圆",
                "§7Raw Optical Crystal",
                "§7光学晶体原料"
            )
            provider.addItemWithTooltip(
                EPMItems.STRONTIUM_CARBONATE_WAFER,
                "Coated Strontium Carbonate Wafer",
                "光聚合液涂覆的碳酸锶晶圆",
                "§7Pre-treatment Of Dielectric Reflective Wafer",
                "§7预处理电介质反射晶圆"
            )
        }
    }
}

