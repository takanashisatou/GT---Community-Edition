package dev.arbor.gtnn.client

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer
import com.gregtechceu.gtceu.api.recipe.RecipeHelper
import com.lowdragmc.lowdraglib.Platform
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import com.mojang.serialization.JsonOps
import dev.arbor.gtnn.data.GTNNRecipeTypes
import kotlinx.coroutines.*
import net.minecraft.client.Minecraft
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.literal
import net.minecraft.nbt.*
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import java.io.DataInputStream
import java.io.File
import kotlin.io.path.exists
import kotlin.io.path.pathString


object GTNNCommands {
    @OptIn(DelicateCoroutinesApi::class)
    @JvmStatic
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(literal("gtnn")
            .requires { it.hasPermission(3) }
            .then(literal("getRecipes")
                .executes {
                    it.source.sendSystemMessage(Component.literal("Starting recipe processing..."))
                    GlobalScope.launch {
                        try {
                            processRecipes(it)
                        } catch (e: Exception) {
                            it.source.sendFailure(Component.literal("An error occurred: ${e.message}"))
                        }
                    }
                    return@executes 1
                }
            )
        )

        dispatcher.register(
            literal("gtnn")
            .requires { it.hasPermission(3) }
            .then(literal("test")
                .executes {
                    it.source.sendSystemMessage(Component.literal("Starting recipe processing..."))
                    GlobalScope.launch {
                        try {
                            testRecipes(it)
                        } catch (e: Exception) {
                            it.source.sendFailure(Component.literal("An error occurred: ${e.message}"))
                        }
                    }
                    return@executes 1
                }
            )
        )

        dispatcher.register(
            literal("gtnn")
            .requires { it.hasPermission(3) }
            .then(literal("doTempThing")
                .executes {
                    it.source.sendSystemMessage(Component.literal("Starting temp thing..."))
                    GlobalScope.launch {
                        try {
                            TempThing.init()
                        } catch (e: Exception) {
                            it.source.sendFailure(Component.literal("An error occurred: ${e.message}"))
                        }
                    }
                    return@executes 1
                }
            )
        )
    }

    private suspend fun testRecipes(context: CommandContext<CommandSourceStack>) {
        val source = context.source.level
        withContext(Dispatchers.IO) {
            source.recipeManager.recipes
                .filter { it.type == GTNNRecipeTypes.COMPONENT_ASSEMBLY_LINE_RECIPES }
                .forEach {
                    it as GTRecipe
                    context.source.sendSystemMessage(
                        Component.literal("Recipe: ${it.id}\nCondition: ${it.conditions}")
                    )
                }
        }
    }

    private suspend fun processRecipes(context: CommandContext<CommandSourceStack>) {
        val source = context.source.level
        withContext(Dispatchers.IO) {
            val tierToPath: Map<Int, String> = GTValues.ALL_TIERS.associateWith { tier -> getPath(GTValues.VN[tier]) }
            source.recipeManager.recipes.forEach { recipe ->
                if (recipe is GTRecipe) {
                    GTRecipeSerializer.CODEC.encodeStart(JsonOps.INSTANCE, recipe).resultOrPartial { s ->
                        throw RuntimeException(s)
                    }.ifPresent { json ->
                        val tier = RecipeHelper.getRecipeEUtTier(recipe)
                        val file = File(tierToPath[tier] + ".json")
                        val text = "^" + recipe.id.path.replace("/", "_") + "^n:" + json
                        if (!file.parentFile.exists()) file.parentFile.mkdirs()
                        if (file.exists()) {
                            file.appendText("\n$text")
                        } else {
                            file.writeText(text)
                        }
                    }
                    context.source.sendSystemMessage(Component.literal("Processed recipe: ${recipe.id}"))
                }
            }
        }
        context.source.sendSuccess({ Component.literal("Recipes processing completed.") }, false)
    }

    private fun getPath(v: String) : String {
        val lvDir = Platform.getGamePath().resolve("gtnn_recipes/$v")
        if (!lvDir.parent.exists()) lvDir.parent.toFile().mkdirs()
        return lvDir.pathString
    }

    private object TempThing {
        val fileList = arrayOf(
            "chemical_plant",
            "component_assembly_line",
            "large_naquadah_reactor",
            "neutron_activator",
            "precise_assembler",
            "precision_assembly"
        )

        fun init() {
            val resourceManager = Minecraft.getInstance().resourceManager
            val nbtList = mutableListOf<CompoundTag>()
            for (file in fileList) {
                try {
                    val resource = resourceManager.getResourceOrThrow(
                        ResourceLocation.fromNamespaceAndPath("gtceu", "ui/recipe_type/%s.rtui".format(file))
                    )

                    resource.open().use { inputStream ->
                        DataInputStream(inputStream).use { dataInputStream ->
                            nbtList += NbtIo.read(dataInputStream, NbtAccounter.UNLIMITED)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            renameItemSlot(nbtList)
            for (i in 0 until nbtList.size) {
                val nbt = nbtList[i]
                val mcDir = Platform.getGamePath().resolve("gtnn")
                val file = File(mcDir.pathString + "/" + fileList[i] + ".rtui")
                if (!file.parentFile.exists()) file.parentFile.mkdirs()
                NbtIo.write(nbt, file)
            }
        }

        private fun renameItemSlot(list: List<CompoundTag>) {
            for (nbt in list) {
                travel(nbt, "type:item_slot", "gtm_item_slot")
                travel(nbt, "type:fluid_slot", "gtm_fluid_slot")
            }
        }

        private fun travel(node: Tag, target: String, replacement: String) {
            val type = target.split(':')[0]
            val value = target.split(':')[1]

            when (node) {
                is CompoundTag -> {
                    if (node.contains(type) && node.getString(type) == value) {
                        node.putString(type, replacement)
                    }

                    node.allKeys.forEach { key ->
                        node.get(key)?.let { child ->
                            travel(child, target, replacement)
                        }
                    }
                }
                is ListTag -> {
                    for (i in 0 until node.size) {
                        travel(node[i], target, replacement)
                    }
                }
            }
        }
    }
}