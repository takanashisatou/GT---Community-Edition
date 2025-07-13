package dev.arbor.gtnn.client.helper

import dev.arbor.gtnn.GTNN
import net.minecraft.client.model.geom.builders.UVPair
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.block.model.BlockElementFace
import net.minecraft.client.renderer.block.model.BlockFaceUV
import net.minecraft.client.renderer.block.model.FaceBakery
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.BlockModelRotation
import net.minecraft.client.resources.model.ModelState
import net.minecraft.core.Direction
import net.minecraft.world.phys.AABB
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.model.QuadTransformers
import org.joml.Vector3f

@Suppress("unused")
@OnlyIn(Dist.CLIENT)
object FaceQuadBakery {
    val BLOCK = AABB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)
    val SLIGHTLY_OVER_BLOCK = AABB(-0.001, -0.001, -0.001, 1.001, 1.001, 1.001)

    private val MODEL = GTNN.id("face_quad")
    private val FACE_BAKERY = FaceBakery()

    fun bakeFace(
        posFrom: Vector3f,
        posTo: Vector3f,
        face: BlockElementFace,
        sprite: TextureAtlasSprite,
        facing: Direction,
        rotation: ModelState,
        emissivity: Int,
        shade: Boolean
    ): BakedQuad {
        val quad = FACE_BAKERY.bakeQuad(posFrom, posTo, face, sprite, facing, rotation, null, shade, MODEL)
        QuadTransformers.settingEmissivity(emissivity).processInPlace(quad)
        return quad
    }

    @JvmOverloads
    fun bakeFace(
        cube: AABB,
        facing: Direction,
        sprite: TextureAtlasSprite,
        rotation: ModelState = BlockModelRotation.X0_Y0,
        tintIndex: Int = -1,
        emissivity: Int = 0,
        cull: Boolean = true,
        shade: Boolean = true
    ): BakedQuad {
        return bakeFace(
            Vector3f(cube.minX.toFloat() * 16f, cube.minY.toFloat() * 16f, cube.minZ.toFloat() * 16f),
            Vector3f(cube.maxX.toFloat() * 16f, cube.maxY.toFloat() * 16f, cube.maxZ.toFloat() * 16f),
            BlockElementFace(if (cull) facing else null, tintIndex, "", BlockFaceUV(floatArrayOf(0f, 0f, 16f, 16f), 0)),
            sprite,
            facing,
            rotation,
            emissivity,
            shade
        )
    }

    @JvmOverloads
    fun bakeFace(
        face: Direction,
        sprite: TextureAtlasSprite,
        rotation: ModelState = BlockModelRotation.X0_Y0,
        tintIndex: Int = -1,
        emissivity: Int = 0,
        cull: Boolean = true,
        shade: Boolean = true
    ): BakedQuad {
        return bakeFace(BLOCK, face, sprite, rotation, tintIndex, emissivity, cull, shade)
    }

    fun builder(face: Direction, sprite: TextureAtlasSprite): Builder {
        return Builder(face, sprite)
    }

    class Builder(private var facing: Direction, private var sprite: TextureAtlasSprite) {
        var from: Vector3f = Vector3f(0f, 0f, 0f)
        var to: Vector3f = Vector3f(16f, 16f, 16f)
        var rotation: ModelState = BlockModelRotation.X0_Y0
        var tintIndex: Int = -1
        var emissivity: Int = 0
        var cull: Boolean = true
        var shade: Boolean = true
        var uv0: UVPair = UVPair(0f, 0f)
        var uv1: UVPair = UVPair(16f, 16f)

        fun from(from: Vector3f): Builder = apply { this.from = from }
        fun to(to: Vector3f): Builder = apply { this.to = to }
        fun facing(facing: Direction): Builder = apply { this.facing = facing }
        fun sprite(sprite: TextureAtlasSprite): Builder = apply { this.sprite = sprite }
        fun rotation(rotation: ModelState): Builder = apply { this.rotation = rotation }
        fun tintIndex(tintIndex: Int): Builder = apply { this.tintIndex = tintIndex }
        fun emissivity(emissivity: Int): Builder = apply { this.emissivity = emissivity }
        fun cull(cull: Boolean): Builder = apply { this.cull = cull }
        fun shade(shade: Boolean): Builder = apply { this.shade = shade }
        fun uv0(uv0: UVPair): Builder = apply { this.uv0 = uv0 }
        fun uv1(uv1: UVPair): Builder = apply { this.uv1 = uv1 }

        fun uv0(u: Float, v: Float): Builder = apply { uv0 = UVPair(u, v) }
        fun uv1(u: Float, v: Float): Builder = apply { uv1 = UVPair(u, v) }

        fun cube(cube: AABB): Builder = apply {
            from = Vector3f(cube.minX.toFloat() * 16f, cube.minY.toFloat() * 16f, cube.minZ.toFloat() * 16f)
            to = Vector3f(cube.maxX.toFloat() * 16f, cube.maxY.toFloat() * 16f, cube.maxZ.toFloat() * 16f)
        }

        fun cubeUV(): Builder = apply {
            when (facing) {
                Direction.UP -> uv0(from.x(), from.z()).uv1(to.x(), to.z())
                Direction.DOWN -> uv0(from.x(), to.z()).uv1(to.x(), from.z())
                Direction.NORTH -> uv0(to.x(), to.y()).uv1(from.x(), from.y())
                Direction.SOUTH -> uv0(from.x(), to.y()).uv1(to.x(), from.y())
                Direction.WEST -> uv0(from.z(), to.y()).uv1(to.z(), from.y())
                Direction.EAST -> uv0(to.z(), to.y()).uv1(from.z(), from.y())
            }
        }

        fun bake(): BakedQuad {
            return bakeFace(
                from,
                to,
                BlockElementFace(
                    if (cull) facing else null,
                    tintIndex,
                    "",
                    BlockFaceUV(floatArrayOf(uv0.u(), uv0.v(), uv1.u(), uv1.v()), 0)
                ),
                sprite,
                facing,
                rotation,
                emissivity,
                shade
            )
        }
    }
}