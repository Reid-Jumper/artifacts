package com.reed.artifacts.blockentities.entity;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.items.IArtifactItem;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractGenTile extends BlockEntity {
    public AbstractGenTile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    public static <T extends BlockEntity> void spawnItem(Level level, T be, String resourceLocation, ArtifactType artifactType) {
        CompoundTag compoundtag = new CompoundTag();
        CompoundTag item = new CompoundTag();
        item.putString("id", resourceLocation);
        item.putInt("Count", 1);
        compoundtag.put("Item", item);
        compoundtag.putString("id", "minecraft:item");
        ItemEntity entity = (ItemEntity) EntityType.loadEntityRecursive(compoundtag, level, (p_138828_) -> {
            p_138828_.moveTo(be.getBlockPos().getX(), be.getBlockPos().getY() + 1, be.getBlockPos().getZ(), p_138828_.getYRot(), p_138828_.getXRot());
            return p_138828_;
        });
        ServerLevel serverLevel = (ServerLevel)level;
        if(serverLevel.tryAddFreshEntityWithPassengers(entity)) {
            ArtifactsMod.ARTIFACT_HANDLER.putArtifact((IArtifactItem) entity.getItem().getItem(), artifactType);
        }
    }


}
