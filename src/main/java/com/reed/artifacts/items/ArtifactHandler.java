package com.reed.artifacts.items;

import com.reed.artifacts.init.ItemInit;
import com.reed.artifacts.util.ArtifactTrackerTicker;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtifactHandler {
    private final Map<ArtifactType, IArtifactItem> artifactItems = new HashMap<>();
    private final Map<ArtifactType, ArtifactTrackerTicker> artifactTrackers = new HashMap<>();

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

    public ArtifactHandler() {
        for(ArtifactType artifactType : ArtifactType.values()) {
            artifactItems.put(artifactType, null);
            artifactTrackers.put(artifactType, new ArtifactTrackerTicker());
        }
    }

    public boolean artifactSlotOpen(ArtifactType type) {
        return artifactItems.get(type) == null;
    }

    public void putArtifact(IArtifactItem item, ArtifactType type) {
        artifactItems.put(type, item);
        artifactTrackers.put(type, new ArtifactTrackerTicker((IArtifactItem) item.asItem()));
    }

    public void clearArtifact(ArtifactType type) {
        artifactItems.put(type, null);
        artifactTrackers.put(type, new ArtifactTrackerTicker());
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
                    if (tracker.possessedByEntity() && tracker.getLastEntityInPossession() instanceof Player) {
                        if (!tracker.isHoldingArtifacts((Player)tracker.getLastEntityInPossession())) {
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
}
