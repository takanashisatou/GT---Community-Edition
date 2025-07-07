package dev.arbor.gtnn.api.registry

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.api.recipe.GTRecipeType
import com.gregtechceu.gtceu.utils.FormattingUtil
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.RegistrateLangProvider
import com.tterrag.registrate.util.nullness.NonNullSupplier
import com.tterrag.registrate.util.nullness.NonnullType
import dev.arbor.gtnn.data.GTNNDataGen
import net.minecraft.core.Registry
import net.minecraft.data.CachedOutput
import net.minecraft.data.PackOutput
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.contents.TranslatableContents
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.data.LanguageProvider
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.function.Function
import java.util.function.Supplier

@Suppress("unused")
class NNLangProvider(private val owner: AbstractRegistrate<*>, packOutput: PackOutput) : RegistrateLangProvider(owner, packOutput) {
    private class CNLanguageProvider(packOutput: PackOutput, modid: String) : LanguageProvider(packOutput, modid, "zh_cn") {
        fun addBlockWithTooltip(block: NonNullSupplier<out Block>, name: String, tooltip: String) {
            addBlock(block, name)
            addTooltip(block, tooltip)
        }

        fun addBlockWithTooltip(block: NonNullSupplier<out Block>, name: String, tooltip: List<@NonnullType String>) {
            addBlock(block, name)
            addTooltip(block, tooltip)
        }

        fun addItemWithTooltip(item: NonNullSupplier<out Item>, name: String, tooltip: List<@NonnullType String>) {
            addItem(item, name)
            addTooltip(item, tooltip)
        }

        fun addItemWithTooltip(item: NonNullSupplier<out Item>, name: String, tooltip: String) {
            addItem(item, name)
            addTooltip(item, tooltip)
        }

        fun addTooltip(item: NonNullSupplier<out ItemLike>, tooltip: String) {
            add(item.get().asItem().descriptionId + ".desc", tooltip)
        }

        fun addTooltip(item: NonNullSupplier<out ItemLike>, tooltip: List<@NonnullType String>) {
            for (i in tooltip.indices) {
                add(item.get().asItem().descriptionId + ".desc.$i", tooltip[i])
            }
        }

        fun add(tab: CreativeModeTab, name: String) {
            val contents = tab.displayName.contents
            if (contents is TranslatableContents) {
                add(contents.key, name)
            } else {
                throw IllegalArgumentException("Creative tab does not have a translatable name: ${tab.displayName}")
            }
        }

        override fun addTranslations() {}
    }

    private val modId: String = owner.modid
    private val simplifiedChinese: CNLanguageProvider = CNLanguageProvider(packOutput, owner.modid)

    override fun getName() = "Lang (en_us/en_ud/zh_cn)"

    override fun addTranslations() {
        owner.genData(GTNNDataGen.NN_LANG, this)
    }

    override fun run(cache: CachedOutput): CompletableFuture<*> {
        return CompletableFuture.allOf(super.run(cache), simplifiedChinese.run(cache))
    }

    /* -------------------------------------------------- Utilities -------------------------------------------------- */
    fun add(key: String, enText: String, cnText: String) {
        simplifiedChinese.add(key, cnText)
        add(key, enText)
    }

    fun addItemWithTooltip(item: NonNullSupplier<out Item>, name: String, tooltip: String) {
        addItem(item, name)
        addTooltip(item, tooltip)
    }

    fun addBlockWithTooltip(block: NonNullSupplier<out Block>, name: String, tooltip: List<@NonnullType String>) {
        addBlock(block, name)
        addTooltip(block, tooltip)
    }

    fun addMultiLang(
        keyGetter: Function<Int, String>,
        enTextGetter: Function<Int, String>,
        cnTextGetter: Function<Int, String>,
        vararg tiers: Int
    ) {
        for (tier in tiers) {
            val name = keyGetter.apply(tier)
            simplifiedChinese.add(name, cnTextGetter.apply(tier))
            add(name, enTextGetter.apply(tier))
        }
    }

    fun addMultiLang(key: String, enText: List<String>, cnText: List<String>) {
        for (i in cnText.indices) {
            simplifiedChinese.add(getSubKey(key, i), cnText[i])
        }

        for (i in enText.indices) {
            add(getSubKey(key, i), enText[i])
        }
    }

    fun addMultilineLang(key: String, enText: String, cnText: String) {
        multilineLang(simplifiedChinese, key, cnText)
        multilineLang(this, key, enText)
    }

    private fun multiLang(provider: LanguageProvider, key: String, vararg values: String) {
        for (i in values.indices) {
            provider.add(getSubKey(key, i), values[i])
        }
    }

    private fun multilineLang(provider: LanguageProvider, key: String, multiline: String) {
        val lines = multiline.split("\n".toRegex()).toTypedArray()
        multiLang(provider, key, *lines)
    }

    fun addCN(key: String, cnText: String) {
        simplifiedChinese.add(key, cnText)
    }

    override fun add(key: String, value: String) {
        if (data()[key] != null){
            data()[key] = value
        } else {
            super.add(key, value)
        }
    }

    override fun addBlock(key: Supplier<out Block>, name: String) = add(key.get().descriptionId, name)

    override fun addItem(key: Supplier<out Item>, name: String) = add(key.get().descriptionId, name)

    fun addItemName(item: NonNullSupplier<out Item>, enName: String, cnName: String) {
        simplifiedChinese.addItem(item, cnName)
        addItem(item, enName)
    }

    fun addItemName(item: NonNullSupplier<out Item>, cnName: String) {
        simplifiedChinese.addItem(item, cnName)
    }

    fun addItemWithTooltip(
        item: NonNullSupplier<out Item>,
        enName: String,
        cnName: String,
        enTooltip: String,
        cnTooltip: String
    ) {
        simplifiedChinese.addItemWithTooltip(item, cnName, cnTooltip)
        addItemWithTooltip(item, enName, enTooltip)
    }

    fun addItemWithTooltip(
        item: NonNullSupplier<out Item>,
        cnName: String,
        enTooltip: String,
        cnTooltip: String
    ) {
        simplifiedChinese.addItemWithTooltip(item, cnName, cnTooltip)
        addTooltip(item, enTooltip)
    }

    fun addItemWithTooltip(
        item: NonNullSupplier<out Item>,
        enName: String,
        cnName: String,
        enTooltip: List<String>,
        cnTooltip: List<String>
    ) {
        simplifiedChinese.addItemWithTooltip(item, cnName, cnTooltip)
        addItemWithTooltip(item, enName, enTooltip)
    }

    fun addItemWithTooltip(
        item: NonNullSupplier<out Item>,
        cnName: String,
        enTooltip: List<String>,
        cnTooltip: List<String>
    ) {
        simplifiedChinese.addItemWithTooltip(item, cnName, cnTooltip)
        addTooltip(item, enTooltip)
    }

    fun addBlockName(block: NonNullSupplier<out Block>, enName: String, cnName: String) {
        simplifiedChinese.addBlock(block, cnName)
        addBlock(block, enName)
    }

    fun addBlockName(block: NonNullSupplier<out Block>, cnName: String) {
        simplifiedChinese.addBlock(block, cnName)
    }

    fun addBlockWithTooltip(
        block: NonNullSupplier<out Block>,
        enName: String,
        cnName: String,
        enTooltip: String,
        cnTooltip: String
    ) {
        simplifiedChinese.addBlockWithTooltip(block, cnName, cnTooltip)
        addBlockWithTooltip(block, enName, enTooltip)
    }

    fun addBlockWithTooltip(
        block: NonNullSupplier<out Block>,
        cnName: String,
        enTooltip: String,
        cnTooltip: String
    ) {
        simplifiedChinese.addBlockWithTooltip(block, cnName, cnTooltip)
        addTooltip(block, enTooltip)
    }

    fun addBlockWithTooltip(
        block: NonNullSupplier<out Block>,
        enName: String,
        cnName: String,
        enTooltip: List<String>,
        cnTooltip: List<String>
    ) {
        simplifiedChinese.addBlockWithTooltip(block, cnName, cnTooltip)
        addBlockWithTooltip(block, enName, enTooltip)
    }

    fun addBlockWithTooltip(
        block: NonNullSupplier<out Block>,
        cnName: String,
        enTooltip: List<String>,
        cnTooltip: List<String>
    ) {
        simplifiedChinese.addBlockWithTooltip(block, cnName, cnTooltip)
        addTooltip(block, enTooltip)
    }

    fun addBlockWithTooltip(blockName: String, enTooltip: String, cnTooltip: String) {
        val key = getBlockKey(modId, blockName) + ".desc"
        simplifiedChinese.add(key, cnTooltip)
        add(key, enTooltip)
    }

    fun addBlockWithTooltip(blockName: String, enTooltip: List<String>, cnTooltip: List<String>) {
        for (i in cnTooltip.indices) {
            val key = getBlockKey(modId, blockName) + ".desc.$i"
            simplifiedChinese.add(key, cnTooltip[i])
            add(key, enTooltip[i])
        }
    }

    fun addTieredBlockName(
        keyGetter: Function<Int, String>,
        enNameGetter: Function<Int, String>,
        cnNameGetter: Function<Int, String>,
        vararg tiers: Int
    ) {
        for (tier in tiers) {
            val name = getBlockKey(modId, keyGetter.apply(tier))
            simplifiedChinese.add(name, cnNameGetter.apply(tier))
            add(name, enNameGetter.apply(tier))
        }
    }

    fun addTieredBlockWithTooltip(
        keyGetter: Function<Int, String>,
        enNameGetter: Function<Int, String>,
        cnNameGetter: Function<Int, String>,
        enTooltipGetter: Function<Int, String>,
        cnTooltipGetter: Function<Int, String>,
        vararg tiers: Int
    ) {
        for (tier in tiers) {
            val name = getBlockKey(modId, keyGetter.apply(tier))
            simplifiedChinese.add(name, cnNameGetter.apply(tier))
            simplifiedChinese.add("$name.desc", cnTooltipGetter.apply(tier))
            add(name, enNameGetter.apply(tier))
            add("$name.desc", enTooltipGetter.apply(tier))
        }
    }

    fun addTieredMachineName(key: String, cnNameGetter: Function<Int, String>, vararg tiers: Int) {
        addTieredMachineName(
            { tier -> "${GTValues.VN[tier].lowercase(Locale.ROOT)}_$key" },
            cnNameGetter,
            *tiers
        )
    }

    fun addTieredMachineName(
        keyGetter: Function<Int, String>,
        cnNameGetter: Function<Int, String>,
        vararg tiers: Int
    ) {
        for (tier in tiers) {
            simplifiedChinese.add(getBlockKey(modId, keyGetter.apply(tier)), cnNameGetter.apply(tier))
        }
    }

    fun addTieredMachineName(key: String, cnName: String, vararg tiers: Int) {
        for (tier in tiers) {
            simplifiedChinese.add(
                getBlockKey(modId, "${GTValues.VN[tier].lowercase(Locale.ROOT)}_$key"),
                "${GTValues.VNF[tier]}$cnName"
            )
        }
    }

    fun addTooltip(item: NonNullSupplier<out ItemLike>, enTooltip: List<String>, cnTooltip: List<String>) {
        simplifiedChinese.addTooltip(item, cnTooltip)
        addTooltip(item, enTooltip)
    }

    fun addShiftTooltip(item: NonNullSupplier<out ItemLike>, enTooltip: List<String>, cnTooltip: List<String>) {
        for (i in cnTooltip.indices) {
            simplifiedChinese.add("${item.get().asItem().descriptionId}.shift_desc.$i", cnTooltip[i])
        }

        for (i in enTooltip.indices) {
            add("${item.get().asItem().descriptionId}.shift_desc.$i", enTooltip[i])
        }
    }

    fun addCtrlTooltip(item: NonNullSupplier<out ItemLike>, enTooltip: List<String>, cnTooltip: List<String>) {
        for (i in cnTooltip.indices) {
            simplifiedChinese.add("${item.get().asItem().descriptionId}.ctrl_desc.$i", cnTooltip[i])
        }

        for (i in enTooltip.indices) {
            add("${item.get().asItem().descriptionId}.ctrl_desc.$i", enTooltip[i])
        }
    }

    fun addAltTooltip(item: NonNullSupplier<out ItemLike>, enTooltip: List<String>, cnTooltip: List<String>) {
        for (i in cnTooltip.indices) {
            simplifiedChinese.add("${item.get().asItem().descriptionId}.alt_desc.$i", cnTooltip[i])
        }

        for (i in enTooltip.indices) {
            add("${item.get().asItem().descriptionId}.alt_desc.$i", enTooltip[i])
        }
    }

    fun addRecipeType(recipeType: GTRecipeType, enName: String, cnName: String) {
        val key = recipeType.registryName.toLanguageKey()
        simplifiedChinese.add(key, cnName)
        add(key, enName)
    }

    fun addOreVein(key: String, cn: String) {
        add(key, FormattingUtil.toEnglishName(key), cn)
    }

    fun addMaterial(material: Material, enName: String, cnName: String) {
        simplifiedChinese.add(material.unlocalizedName, cnName)
        add(material.unlocalizedName, enName)
    }

    fun addTagPrefix(tagPrefix: TagPrefix, enName: String, cnName: String) {
        simplifiedChinese.add(tagPrefix.unlocalizedName, cnName)
        add(tagPrefix.unlocalizedName, enName)
    }

    fun getBlockKey(modId: String, key: String): String {
        return "block.$modId.$key"
    }

    fun getKey(modId: String, key: String, registry: ResourceKey<Registry<*>>): String {
        return "${registry.location().path}.$modId.$key"
    }

    fun getSubKeys(key: String, tooltips: Int): MutableList<MutableComponent?> {
        val list: MutableList<MutableComponent?> = ArrayList<MutableComponent?>()
        if (tooltips <= 1) {
            list.add(Component.translatable(key))
            return list
        } else {
            for (i in 0..<tooltips) {
                list.add(Component.translatable(getSubKey(key, i)))
            }
        }
        return list
    }

    fun getSubKey(key: String?, index: Int): String {
        return "$key.$index"
    }

    fun addItemCN(key: Supplier<out Item>, name: String) {
        simplifiedChinese.add(key.get(), name)
    }

    fun data() = getData(this)
    fun cnData() = getData(simplifiedChinese)

    companion object {
        fun getData(provider: LanguageProvider): MutableMap<String, String> {
            try {
                val field = LanguageProvider::class.java.getDeclaredField("data")
                field.setAccessible(true)
                @Suppress("UNCHECKED_CAST")
                return field.get(provider) as MutableMap<String, String>
            } catch (e: NoSuchFieldException) {
                throw RuntimeException("Error generating entry in data-gen.", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Error generating entry in data-gen.", e)
            }
        }
    }
}
