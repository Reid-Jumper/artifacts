package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsModSaveData;
import com.reed.artifacts.init.ItemInit;
import com.reed.artifacts.util.ArtifactTrackerTicker;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtifactHandler {
    private final Map<ArtifactType, IArtifactItem> artifactItems = new HashMap<>();
    private final Map<ArtifactType, ArtifactTrackerTicker> artifactTrackers = new HashMap<>();
    private final ArtifactsModSaveData artifactsModSaveData;

    public static List<ArtifactType> getPlayerArtifacts(Player player) {
        ArrayList<ArtifactType> results = new ArrayList<>();
        for (ArtifactType artifactType : ArtifactType.values()) {
            boolean found = false;
            switch (artifactType) {
                case FORGOTTEN_HELM -> {
                    found = player.getInventory().contains(new ItemStack(ItemInit.FORGOTTEN_HELM.get()));
                }
                case FORGOTTEN_CHEST -> {
                    found = player.getInventory().contains(new ItemStack(ItemInit.FORGOTTEN_CHEST.get()));
                }
                case FORGOTTEN_LEGS -> {
                    found = player.getInventory().contains(new ItemStack(ItemInit.FORGOTTEN_LEGS.get()));
                }
                case FORGOTTEN_BOOTS -> {
                    found = player.getInventory().contains(new ItemStack(ItemInit.FORGOTTEN_BOOTS.get()));
                }
                case FORGOTTEN_SWORD -> {
                    found = player.getInventory().contains(new ItemStack(ItemInit.FORGOTTEN_SWORD.get()));
                }
                case FORGOTTEN_BOW -> {
                    found = player.getInventory().contains(new ItemStack(ItemInit.FORGOTTEN_BOW.get()));
                }
                case FORGOTTEN_SHIELD -> {
                    found = player.getInventory().contains(new ItemStack(ItemInit.FORGOTTEN_SHIELD.get()));
                }
            }
            if (found) {
               results.add(artifactType);
            }
        }
        return results;
    }

    public ArtifactHandler(ArtifactsModSaveData artifactsModSaveData) {
        this.artifactsModSaveData = artifactsModSaveData;
    }

    public boolean artifactSlotOpen(ArtifactType type) {
        return artifactItems.get(type) == null && artifactsModSaveData.getArtifactOwner(type) == null;
    }

    public void putArtifact(IArtifactItem item, ArtifactType type) {
        artifactItems.put(type, item);
        artifactTrackers.put(type, new ArtifactTrackerTicker((IArtifactItem) item.asItem(), artifactsModSaveData));
    }

    public void clearArtifact(ArtifactType type) {
        artifactItems.put(type, null);
        artifactTrackers.remove(type);
    }
    public IArtifactItem getArtifact(ArtifactType type) {
        return artifactItems.get(type);
    }

    public ArtifactTrackerTicker getArtifactTracker(ArtifactType artifactType) {
        return artifactTrackers.get(artifactType);
    }

    public ArtifactTrackerTicker updateArtifact(IArtifactItem item, ArtifactType type) {
        artifactItems.put(type, item);
        return artifactTrackers.get(type);
    }
    public void checkAllPossession(MinecraftServer server) {
        for (ArtifactTrackerTicker tracker : artifactTrackers.values()) {
            if (tracker != null) {
                if (tracker.isPossessionRegistered()) {
                    if (tracker.isPossessionRegistered() && tracker.getLastEntityInPossession() != null) {
                        ServerPlayer player = server.getPlayerList().getPlayerByName(tracker.getLastEntityInPossession());
                        if (player != null && !tracker.isHoldingArtifacts(player)) {
                            clearArtifact(tracker.getArtifactItem().getArtifactType());
                            server.getPlayerList().broadcastMessage(new TextComponent("An artifact has been lost to the world..."), ChatType.SYSTEM, Util.NIL_UUID);
                        }
                    }
                }
            }
        }
    }

    public void clearPossession(ArtifactType type) {
        artifactTrackers.get(type).clearPossession();
    }

    public boolean hasPossession(Player player, ArtifactType type) {
        String artifactOwner = artifactsModSaveData.getArtifactOwner(type);
        return (artifactOwner != null && artifactOwner.equals(player.getScoreboardName()));
    }
}
