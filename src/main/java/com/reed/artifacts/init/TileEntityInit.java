package com.reed.artifacts.init;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.blockentities.entity.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class TileEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ArtifactsMod.MOD_ID);
    public static final RegistryObject<BlockEntityType<AGenTile>> A_GEN_BLOCK = TILE_ENTITY_TYPES.register("a_gen_block",
            () -> BlockEntityType.Builder.of(AGenTile::new, BlockInit.A_GEN_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BGenTile>> B_GEN_BLOCK = TILE_ENTITY_TYPES.register("b_gen_block",
            () -> BlockEntityType.Builder.of(BGenTile::new, BlockInit.B_GEN_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<CGenTile>> C_GEN_BLOCK = TILE_ENTITY_TYPES.register("c_gen_block",
            () -> BlockEntityType.Builder.of(CGenTile::new, BlockInit.C_GEN_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<DGenTile>> D_GEN_BLOCK = TILE_ENTITY_TYPES.register("d_gen_block",
            () -> BlockEntityType.Builder.of(DGenTile::new, BlockInit.D_GEN_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<EGenTile>> E_GEN_BLOCK = TILE_ENTITY_TYPES.register("e_gen_block",
            () -> BlockEntityType.Builder.of(EGenTile::new, BlockInit.E_GEN_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<FGenTile>> F_GEN_BLOCK = TILE_ENTITY_TYPES.register("f_gen_block",
            () -> BlockEntityType.Builder.of(FGenTile::new, BlockInit.F_GEN_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<GGenTile>> G_GEN_BLOCK = TILE_ENTITY_TYPES.register("g_gen_block",
            () -> BlockEntityType.Builder.of(GGenTile::new, BlockInit.G_GEN_BLOCK.get()).build(null));
}
