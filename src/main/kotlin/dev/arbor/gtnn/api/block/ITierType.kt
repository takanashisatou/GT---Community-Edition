package dev.arbor.gtnn.api.block

import com.gregtechceu.gtceu.api.GTValues
import net.minecraft.util.StringRepresentable
import net.minecraft.world.level.block.Block
import java.util.*
import java.util.function.Supplier
import javax.annotation.Nonnull

interface ITierType: StringRepresentable {
    val typeName: String
    val tier: Int

    override fun getSerializedName(): String {
        return typeName
    }

    data class SimpleTierBlockType(override val typeName: String, override val tier: Int) : ITierType {
        @Nonnull
        override fun toString(): String {
            return typeName
        }
    }

    enum class TierBlockType(override val tier: Int) : ITierType {
        DUMMY(-1),
        ULV(GTValues.ULV),
        LV(GTValues.LV),
        MV(GTValues.MV),
        HV(GTValues.HV),
        EV(GTValues.EV),
        IV(GTValues.IV),
        LuV(GTValues.LuV),
        ZPM(GTValues.ZPM),
        UV(GTValues.UV),
        UHV(GTValues.UHV),
        UEV(GTValues.UEV),
        UIV(GTValues.UIV),
        UXV(GTValues.UXV),
        OpV(GTValues.OpV),
        MAX(GTValues.MAX);

        override val typeName: String = name.lowercase(Locale.getDefault())
    }

    class WrappedTierType<T : Block?>(private val supplier: Supplier<T>, override val tier: Int) : ITierType {
        override val typeName: String
            get() {
                return supplier.get()?.descriptionId ?: toString()
            }
    }
}

