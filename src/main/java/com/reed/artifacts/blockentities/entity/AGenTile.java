package com.reed.artifacts.blockentities.entity;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.init.TileEntityInit;
import com.reed.artifacts.items.IArtifactItem;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AGenTile extends BlockEntity {
    public AGenTile(BlockPos pos, BlockState state) {
        super(TileEntityInit.A_GEN_BLOCK.get(), pos, state);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
        AGenTile tile = (AGenTile) be;
        if(ArtifactsMod.handler.checkOpenArtifact(ArtifactType.A) && !level.isClientSide) {
            CompoundTag tag;
            tag = new CompoundTag();
            CompoundTag Item = new CompoundTag();
            CompoundTag outer = new CompoundTag();
            ResourceLocation loc = new ResourceLocation("minecraft:item");
            CompoundTag compoundtag = new CompoundTag();
            CompoundTag item = new CompoundTag();
            item.putString("id", "artifacts:a");
            item.putInt("Count", 1);
            compoundtag.put("Item", item);
            compoundtag.putString("id", "minecraft:item");
            ItemEntity entity = (ItemEntity)EntityType.loadEntityRecursive(compoundtag, level, (p_138828_) -> {
                p_138828_.moveTo(be.getBlockPos().getX(), be.getBlockPos().getY() + 1, be.getBlockPos().getZ(), p_138828_.getYRot(), p_138828_.getXRot());
                return p_138828_;
            });
            ServerLevel serverLevel = (ServerLevel)level;
            if(serverLevel.tryAddFreshEntityWithPassengers(entity)) {
                ArtifactsMod.handler.putArtifact((IArtifactItem)entity.getItem().getItem(), ArtifactType.A);
            }
        }
    }
}
