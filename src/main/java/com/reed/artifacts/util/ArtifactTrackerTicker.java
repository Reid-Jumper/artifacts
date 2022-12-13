package com.reed.artifacts.util;

import com.reed.artifacts.items.IArtifactItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ArtifactTrackerTicker {
    private int ticks;
    private boolean isTickable;
    private IArtifactItem artifactItem;
    private LivingEntity lastEntityInPossession;
    private BlockEntity lastBlockInPossession;

    public ArtifactTrackerTicker(LivingEntity lastEntityInPossession, IArtifactItem item) {
        this.ticks = 12096000;
        this.isTickable = true;
        this.artifactItem = item;
        this.lastEntityInPossession = lastEntityInPossession;
        this.lastBlockInPossession = null;
    }
    public ArtifactTrackerTicker(BlockEntity lastBlockInPossession, IArtifactItem item) {
        this.ticks = 12096000;
        this.isTickable = false;
        this.artifactItem = item;
        this.lastEntityInPossession = null;
        this.lastBlockInPossession = lastBlockInPossession;
    }
    public ArtifactTrackerTicker() {
        this.ticks = 12096000;
        this.isTickable = false;
        this.artifactItem = null;
        this.lastEntityInPossession = null;
        this.lastBlockInPossession = null;
    }
    public IArtifactItem getArtifactItem() {
        return this.artifactItem;
    }
    public LivingEntity getLastEntityInPossession() {
        return this.lastEntityInPossession;
    }
    public BlockEntity getLastBlockInPossession() {
        return this.lastBlockInPossession;
    }
    public void tickDown() {
        if(this.isTickable) {
            this.ticks--;
        }
    }
    public void resetTicks() {
        this.ticks = 12096000;
    }
    public boolean isTickable() {
        return this.isTickable;
    }
    public <T> boolean changePossession(T entity) {
        if(entity instanceof LivingEntity) {
            this.isTickable = true;
            resetTicks();
            this.lastEntityInPossession = (LivingEntity)entity;
            this.lastBlockInPossession = null;
            return true;
        }
        if(entity instanceof BlockEntity) {
            this.isTickable = false;
            resetTicks();
            this.lastEntityInPossession = null;
            this.lastBlockInPossession = (BlockEntity)entity;
            return true;
        }
        return false;
    }
}
