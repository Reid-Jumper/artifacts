package com.reed.artifacts.util;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.ArtifactsModSaveData;
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
    private IArtifactItem artifactItem;
    private ArtifactsModSaveData artifactsModSaveData;

    public ArtifactTrackerTicker(IArtifactItem item, ArtifactsModSaveData artifactsModSaveData) {
        this.ticks = 12096000;
        this.artifactItem = item;
        this.artifactsModSaveData = artifactsModSaveData;
    }

    public IArtifactItem getArtifactItem() {
        return this.artifactItem;
    }
    public String getLastEntityInPossession() {
        return this.artifactsModSaveData.getArtifactOwner(artifactItem.getArtifactType());
    }

    public boolean isPossessionRegistered() {
        return this.artifactsModSaveData.getArtifactOwner(artifactItem.getArtifactType()) != null;
    }
    public void resetTicks() {
        this.ticks = 12096000;
    }

    public <T> boolean changePossession(T entity) {
        System.out.println("Changing possession");
        if(entity instanceof LivingEntity) {
            resetTicks();
            artifactsModSaveData.setArtifactOwner(artifactItem.getArtifactType(), ((LivingEntity) entity).getScoreboardName());
            return true;
        }
        return false;
    }

    public void checkPlayerAndUpdatePossessionStatus(Player player) {
        if(isHoldingArtifacts(player)) {
            changePossession(player);
        }
    }

    public boolean isHoldingArtifacts(Player player) {
        return player.getInventory().contains(new ItemStack(artifactItem));
    }

    public void clearPossession() {
        artifactsModSaveData.setArtifactOwner(artifactItem.getArtifactType(), null);
        resetTicks();
    }
}
