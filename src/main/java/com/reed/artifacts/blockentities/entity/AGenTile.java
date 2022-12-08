package com.reed.artifacts.blockentities.entity;

import com.reed.artifacts.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AGenTile extends BlockEntity {
    public AGenTile(BlockPos pos, BlockState state) {
        super(TileEntityInit.A_GEN_BLOCK.get(), pos, state);
    }
}
