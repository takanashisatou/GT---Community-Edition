package dev.arbor.gtnn.data.lang

import dev.arbor.gtnn.api.registry.NNLangProvider
import dev.arbor.gtnn.data.GTNNItems
import dev.arbor.gtnn.data.lang.NNLangHandler.tsl

object MiscLang {
    fun init(provider: NNLangProvider) {
        config()
        debug(provider)
        tsl("gtnn.shift_info", "§7按住§6SHIFT§7以显示更多信息", "§7Hold down §6SHIFT§7 to show more information")
        tsl("gtnn.universal.tier", "§7等级：%s", "§7Tier: %s")
        tsl("itemGroup.gtnn.gtnn_circuit_reform", "GT-- | 电路板改革")
        tsl("gtnn.jei.bedrock_ores.dimension", "维度: %s", "Dimension: %s")
        tsl("gtnn.jei.bedrock_ores", "基岩矿石", "Bedrock Ores")
        tsl("cover.ender_item_link.title", "末影物品连接", "Ender Item Link")
        tsl("cover.ender_fluid_link.tooltip.channel_description", "设置频道描述", "Set channel description with input text")
        tsl("cover.ender_fluid_link.tooltip.channel_name", "设置频道名称", "Set channel name with input text")
        tsl("cover.ender_fluid_link.tooltip.list_button", "显示频道列表", "Show channel list")
        tsl("cover.ender_fluid_link.tooltip.clear_button", "清除频道描述", "Clear channel description")
    }

    private fun config() {
        tsl("config.gtnn.option.Client", "客户端", "Client")
        tsl("config.gtnn.option.Server", "服务器", "Server")
        tsl(
            "config.gtnn.option.enableHarderNaquadahLine", "开启更难的硅岩处理", "Enable Harder Naquadah Process Line"
        )
        tsl(
            "config.gtnn.option.enableHarderPlatinumLine", "开启更难的铂处理", "Enable Harder Platinum Process Line"
        )
        tsl("config.gtnn.option.extraHeartRenderer", "启用血条渲染", "Enable extra Heart renderer")
        tsl(
            "config.gtnn.option.banCreateFanBlasting", "禁用机械动力风扇熔炼", "Disable Create Fan Blasting"
        )
        tsl("config.gtnn.option.makesEMIBetter", "使EMI更好用", "Makes EMI Better")
        tsl("config.gtnn.option.killToast", "禁用弹窗", "Disable toast")
        tsl("config.gtnn.option.addChatAnimation", "启用聊天动画", "Enable Chat Animation")
        tsl("config.gtnn.option.enableRemakeGTMEMI", "启用重制GTM-EMI支持", "Enable Remake GTM-EMI support")
        tsl("config.gtnn.option.skyblock", "开启空岛模式", "Enable SkyBlock mode")
        tsl("config.gtnn.option.gtOresMultiplyNum", "GT矿脉大小倍数", "GT OreVeins Size Multiplier")
        tsl("config.gtnn.option.timesOreVeins", "单区块GT矿脉生成数量", "GT OreVeins Num Per Chunk")
        tsl("config.gtnn.option.isTurnOnEnderFluidCover", "启用末影流体覆盖板", "Enable Ender Fluid Link Cover")
        tsl("config.gtnn.option.isTurnOnEnderItemCover", "启用末影物品覆盖板", "Enable Ender Item Link Cover")
    }

    private fun debug(provider: NNLangProvider) {
        provider.addItemWithTooltip(
            GTNNItems.DEBUG_STRUCTURE_WRITER,
            "多方块导出工具",
            "§7Because I need one...",
            "§7因为我需要一个..."
        )
        tsl("item.gtnn.debug.structure_writer.structural_scale",
            "结构规模： X:%s  Y:%s  Z:%s",
            "Structure size: X:%s Y:%s Z:%s")
        tsl("item.gtnn.debug.structure_writer.export_order",
            "导出顺序： C:%s  S:%s  A:%s",
            "Export order: C:%s S:%s A:%s")
        tsl("item.gtnn.debug.structure_writer.export_to_log", "导出为日志", "Export as a log")
        tsl("item.gtnn.debug.structure_writer.export_to_msg", "导出为消息", "Export to Message")
        tsl("item.gtnn.debug.structure_writer.rotate_along_x_axis",
            "沿X轴旋转",
            "Rotate along the X axis")
        tsl("item.gtnn.debug.structure_writer.rotate_along_y_axis",
            "沿Y轴旋转",
            "Rotate along the Y axis")
    }
}
