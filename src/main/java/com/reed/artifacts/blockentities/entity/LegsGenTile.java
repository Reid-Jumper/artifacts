package com.reed.artifacts.blockentities.entity;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.init.TileEntityInit;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LegsGenTile extends AbstractGenTile {
    public LegsGenTile(BlockPos pos, BlockState state) {
        super(TileEntityInit.LEGS_GEN_BLOCK.get(), pos, state);
    }

    public static ArtifactType artifactType = ArtifactType.FORGOTTEN_LEGS;

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
        if (!level.isClientSide()) {
            if (ArtifactsMod.ARTIFACT_HANDLER.artifactSlotOpen(artifactType) && !level.isClientSide) {
                spawnItem(level, be, artifactType.resourceLocation, artifactType);
            }
        }
    }
}
