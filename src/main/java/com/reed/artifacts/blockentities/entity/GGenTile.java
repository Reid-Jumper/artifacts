package com.reed.artifacts.blockentities.entity;

import com.reed.artifacts.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GGenTile extends BlockEntity {
    public GGenTile(BlockPos pos, BlockState state) {
        super(TileEntityInit.G_GEN_BLOCK.get(), pos, state);
    }
}
