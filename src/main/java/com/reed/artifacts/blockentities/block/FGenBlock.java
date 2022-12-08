package com.reed.artifacts.blockentities.block;

import com.reed.artifacts.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class FGenBlock extends Block implements EntityBlock {
    public FGenBlock(BlockBehaviour.Properties props) {
        super(props);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TileEntityInit.F_GEN_BLOCK.get().create(pos, state);
    }
}
