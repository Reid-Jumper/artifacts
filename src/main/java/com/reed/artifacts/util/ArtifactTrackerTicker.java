package com.reed.artifacts.util;

import com.reed.artifacts.items.IArtifactItem;
import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArtifactTrackerTicker {
    private int ticks;
    private boolean isTickable;
    private IArtifactItem artifactItem;
    private LivingEntity lastEntityInPossession;
    private Container lastBlockInPossession;

    public ArtifactTrackerTicker(IArtifactItem item) {
        this.ticks = 12096000;
        this.isTickable = false;
        this.artifactItem = item;
        this.lastEntityInPossession = null;
        this.lastBlockInPossession = null;
    }
    public ArtifactTrackerTicker(LivingEntity lastEntityInPossession, IArtifactItem item) {
        this.ticks = 12096000;
        this.isTickable = true;
        this.artifactItem = item;
        this.lastEntityInPossession = lastEntityInPossession;
        this.lastBlockInPossession = null;
    }
    public ArtifactTrackerTicker(Container lastBlockInPossession, IArtifactItem item) {
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
    public Container getLastBlockInPossession() {
        return this.lastBlockInPossession;
    }

    public boolean isPossessionRegistered() {
        return this.lastEntityInPossession != null || this.lastBlockInPossession != null;
    }
    public boolean possessedByEntity() {
        return this.lastEntityInPossession != null;
    }
    public void resetTicks() {
        this.ticks = 12096000;
    }

    public <T> boolean changePossession(T entity) {
        System.out.println("Changing possession");
        if(entity instanceof LivingEntity) {
            this.isTickable = true;
            resetTicks();
            this.lastEntityInPossession = (LivingEntity)entity;
            this.lastBlockInPossession = null;
            System.out.println(this.lastEntityInPossession);
            return true;
        }
        if(entity instanceof Container) {
            this.isTickable = false;
            resetTicks();
            this.lastEntityInPossession = null;
            this.lastBlockInPossession = (Container)entity;
            System.out.println(this.lastBlockInPossession);
            return true;
        }
        return false;
    }
    public void checkContainerAndUpdate(Container container) {
        if(checkContainer(container)) {
            changePossession(container);
        }
    }
    public void checkPlayerAndUpdate(Player player) {
        if(isHoldingArtifacts(player)) {
            changePossession(player);
        }
    }
    public boolean checkContainer(Container container) {
        Set<Item> set = new HashSet<>();
        set.add((Item)artifactItem);
        return container.hasAnyOf(Collections.unmodifiableSet(set));
    }
    public boolean isHoldingArtifacts(Player player) {
        return player.getInventory().contains(new ItemStack(artifactItem));
    }
    public void clearPossession() {
        this.lastEntityInPossession = null;
        this.lastBlockInPossession = null;
        this.isTickable = false;
        resetTicks();
    }
}
