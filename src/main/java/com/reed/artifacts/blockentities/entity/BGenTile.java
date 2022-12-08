package com.reed.artifacts.blockentities.entity;

import com.reed.artifacts.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BGenTile extends BlockEntity {
    public BGenTile(BlockPos pos, BlockState state) {
        super(TileEntityInit.B_GEN_BLOCK.get(), pos, state);
    }
}
