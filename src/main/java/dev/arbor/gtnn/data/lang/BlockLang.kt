package dev.arbor.gtnn.data.lang

import com.gregtechceu.gtceu.api.GTValues
import dev.arbor.gtnn.api.registry.NNLangProvider
import dev.arbor.gtnn.data.GTNNBlocks.OSMIUM_BOROSILICATE_GLASS
import dev.arbor.gtnn.data.GTNNCasingBlocks.ADVANCED_FILTER_CASING
import dev.arbor.gtnn.data.GTNNCasingBlocks.CASING_POLYBENZIMIDAZOLE_PIPE
import dev.arbor.gtnn.data.GTNNCasingBlocks.IRIDIUM_CASING
import dev.arbor.gtnn.data.lang.NNLangHandler.tsl

object BlockLang {
    fun init(provider: NNLangProvider) {
        provider.addBlockWithTooltip(
            OSMIUM_BOROSILICATE_GLASS,
            "Osmium-reinforced Borosilicate Glass",
            "锇强化硼硅玻璃",
            "§7Star of Industry",
            "§7工业之星"
        )


        //  Component Assembly Line Casings
        provider.addTieredBlockWithTooltip(
            { "component_assline_casing_${GTValues.VN[it].lowercase()}" },
            { "Component Assembly Line Casing (${GTValues.VNF[it]}§r)" },
            { "部件装配线外壳（${GTValues.VNF[it]}§r）" },
            { "§7${getCATier(it, true)} Assembly Unit" },
            { "§7${getCATier(it, false)}装配单元" },
            *GTValues.tiersBetween(GTValues.LV, GTValues.MAX)
        )

        provider.addBlockName(ADVANCED_FILTER_CASING, "Advanced Filter Casing", "高级过滤器机械方块")
        provider.addBlockName(IRIDIUM_CASING, "Iridium Casing", "铱机械方块")
        provider.addBlockName(
            CASING_POLYBENZIMIDAZOLE_PIPE, "Casing Polybenzimidazole Pipe", "聚苯并咪唑管道方块"
        )

        tsl("block.gtnn.clean_machine_casing", "洁净机械方块")
        tsl("block.gtnn.field_restriction_casing", "立场约束机械方块")
        tsl("block.gtnn.radiation_proof_machine_casing", "防辐射机械方块")
        tsl("block.gtnn.high_speed_pipe_block", "高速管道方块")
    }

    private fun getCATier(tier: Int, isEN: Boolean): String {
        return when (tier) {
            GTValues.LV -> if (isEN) "Simple" else "简易"
            GTValues.MV -> if (isEN) "Crude" else "粗制"
            GTValues.HV -> if (isEN) "Premium" else "优质"
            GTValues.EV -> if (isEN) "Advanced" else "进阶"
            GTValues.IV -> if (isEN) "High-quality" else "高级"
            GTValues.LuV -> if (isEN) "High-precision" else "高精度"
            GTValues.ZPM -> if (isEN) "Ultra-high Precision" else "超高精度"
            GTValues.UV -> if (isEN) "Extreme Precision" else "极限精度"
            GTValues.UHV -> if (isEN) "Extremely High-precision" else "极高精度"
            GTValues.UEV -> if (isEN) "Structural" else "结构"
            GTValues.UIV -> if (isEN) "Large-scale Structural" else "大尺度结构"
            GTValues.UXV -> if (isEN) "Superstructural" else "超结构"
            GTValues.OpV -> if (isEN) "Cosmic" else "宇宙"
            else -> if (isEN) "Space-time" else "时空"
        }
    }
}