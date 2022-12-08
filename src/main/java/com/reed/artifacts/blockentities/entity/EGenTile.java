package com.reed.artifacts.blockentities.entity;

import com.reed.artifacts.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EGenTile extends BlockEntity {
    public EGenTile(BlockPos pos, BlockState state) {
        super(TileEntityInit.E_GEN_BLOCK.get(), pos, state);
    }
}
