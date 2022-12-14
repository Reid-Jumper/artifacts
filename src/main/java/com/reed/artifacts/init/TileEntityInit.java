package com.reed.artifacts.init;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.blockentities.entity.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class TileEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ArtifactsMod.MOD_ID);
    public static final RegistryObject<BlockEntityType<HelmGenTile>> HELM_GEN_BLOCK = TILE_ENTITY_TYPES.register("helm_gen_block",
            () -> BlockEntityType.Builder.of(HelmGenTile::new, BlockInit.HELM_GEN_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<ChestGenTile>> CHEST_GEN_BLOCK = TILE_ENTITY_TYPES.register("chest_gen_block",
            () -> BlockEntityType.Builder.of(ChestGenTile::new, BlockInit.CHEST_GEN_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<LegsGenTile>> LEGS_GEN_BLOCK = TILE_ENTITY_TYPES.register("legs_gen_block",
            () -> BlockEntityType.Builder.of(LegsGenTile::new, BlockInit.LEGS_GEN_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BootsGenTile>> BOOTS_GEN_BLOCK = TILE_ENTITY_TYPES.register("boots_gen_block",
            () -> BlockEntityType.Builder.of(BootsGenTile::new, BlockInit.BOOTS_GEN_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<SwordGenTile>> SWORD_GEN_BLOCK = TILE_ENTITY_TYPES.register("sword_gen_block",
            () -> BlockEntityType.Builder.of(SwordGenTile::new, BlockInit.SWORD_GEN_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BowGenTile>> BOW_GEN_BLOCK = TILE_ENTITY_TYPES.register("bow_gen_block",
            () -> BlockEntityType.Builder.of(BowGenTile::new, BlockInit.BOW_GEN_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShieldGenTile>> SHIELD_GEN_BLOCK = TILE_ENTITY_TYPES.register("shield_gen_block",
            () -> BlockEntityType.Builder.of(ShieldGenTile::new, BlockInit.SHIELD_GEN_BLOCK.get()).build(null));
}
