package com.reed.artifacts.items;

import com.reed.artifacts.util.ArtifactTrackerTicker;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ArtifactHandler {
    private ArrayList<ItemStack> artifactItems;
    private ArrayList<ArtifactTrackerTicker> artifactTrackers;

    public ArtifactHandler() {
        artifactItems = new ArrayList<ItemStack>();
        artifactTrackers = new ArrayList<ArtifactTrackerTicker>();
        for(int i = 0; i < 7; i++) {
            artifactItems.add(null);
            artifactTrackers.add(new ArtifactTrackerTicker());
        }
    }
    public ArrayList<ItemStack> getAllArtifactItems() {
        return artifactItems;
    }
    public ArrayList<ArtifactTrackerTicker> getAllArtifactTrackers() {
        return artifactTrackers;
    }
    public boolean checkOpenArtifact(IArtifactItem item) {
        return checkOpenArtifact(item.getArtifactType());
    }
    public boolean checkOpenArtifact(ArtifactType type) {
        switch (type) {
            case FORGOTTEN_HELM:
                return artifactItems.get(0) == null;
            case FORGOTTEN_CHEST:
                return artifactItems.get(1) == null;
            case FORGOTTEN_LEGS:
                return artifactItems.get(2) == null;
            case FORGOTTEN_BOOTS:
                return artifactItems.get(3) == null;
            case FORGOTTEN_SWORD:
                return artifactItems.get(4) == null;
            case FORGOTTEN_BOW:
                return artifactItems.get(5) == null;
            case FORGOTTEN_SHIELD:
                return artifactItems.get(6) == null;
            default:
                return false;
        }
    }
    public void putArtifact(ItemStack item, ArtifactType type) {
        switch (type) {
            case FORGOTTEN_HELM:
                artifactItems.set(0, item);
                artifactTrackers.set(0, new ArtifactTrackerTicker((IArtifactItem)item.getItem()));
                break;
            case FORGOTTEN_CHEST:
                artifactItems.set(1, item);
                artifactTrackers.set(1, new ArtifactTrackerTicker((IArtifactItem)item.getItem()));
                break;
            case FORGOTTEN_LEGS:
                artifactItems.set(2, item);
                artifactTrackers.set(2, new ArtifactTrackerTicker((IArtifactItem)item.getItem()));
                break;
            case FORGOTTEN_BOOTS:
                artifactItems.set(3, item);
                artifactTrackers.set(3, new ArtifactTrackerTicker((IArtifactItem)item.getItem()));
                break;
            case FORGOTTEN_SWORD:
                artifactItems.set(4, item);
                artifactTrackers.set(4, new ArtifactTrackerTicker((IArtifactItem)item.getItem()));
                break;
            case FORGOTTEN_BOW:
                artifactItems.set(5, item);
                artifactTrackers.set(5, new ArtifactTrackerTicker((IArtifactItem)item.getItem()));
                break;
            case FORGOTTEN_SHIELD:
                artifactItems.set(6, item);
                artifactTrackers.set(6, new ArtifactTrackerTicker((IArtifactItem)item.getItem()));
                break;
        }
    }
    public void clearArtifact(ArtifactType type) {
        switch (type) {
            case FORGOTTEN_HELM:
                artifactItems.set(0, null);
                artifactTrackers.set(0, new ArtifactTrackerTicker());
                break;
            case FORGOTTEN_CHEST:
                artifactItems.set(1, null);
                artifactTrackers.set(1, new ArtifactTrackerTicker());
                break;
            case FORGOTTEN_LEGS:
                artifactItems.set(2, null);
                artifactTrackers.set(2, new ArtifactTrackerTicker());
                break;
            case FORGOTTEN_BOOTS:
                artifactItems.set(3, null);
                artifactTrackers.set(3, new ArtifactTrackerTicker());
                break;
            case FORGOTTEN_SWORD:
                artifactItems.set(4, null);
                artifactTrackers.set(4, new ArtifactTrackerTicker());
                break;
            case FORGOTTEN_BOW:
                artifactItems.set(5, null);
                artifactTrackers.set(5, new ArtifactTrackerTicker());
                break;
            case FORGOTTEN_SHIELD:
                artifactItems.set(6, null);
                artifactTrackers.set(6, new ArtifactTrackerTicker());
                break;
        }
    }
    public ItemStack getArtifact(ArtifactType type) {
        switch (type) {
            case FORGOTTEN_HELM:
                return artifactItems.get(0);
            case FORGOTTEN_CHEST:
                return artifactItems.get(1);
            case FORGOTTEN_LEGS:
                return artifactItems.get(2);
            case FORGOTTEN_BOOTS:
                return artifactItems.get(3);
            case FORGOTTEN_SWORD:
                return artifactItems.get(4);
            case FORGOTTEN_BOW:
                return artifactItems.get(5);
            default:
                return artifactItems.get(6);
        }
    }
    /*
    public void tick(ArtifactType type) {
        switch (type) {
            case FORGOTTEN_HELM:
                artifactTrackers.get(0).tickDown();
                break;
            case FORGOTTEN_CHEST:
                artifactTrackers.get(1).tickDown();
                break;
            case FORGOTTEN_LEGS:
                artifactTrackers.get(2).tickDown();
                break;
            case FORGOTTEN_BOOTS:
                artifactTrackers.get(3).tickDown();
                break;
            case FORGOTTEN_SWORD:
                artifactTrackers.get(4).tickDown();
                break;
            case FORGOTTEN_BOW:
                artifactTrackers.get(5).tickDown();
                break;
            case FORGOTTEN_SHIELD:
                artifactTrackers.get(6).tickDown();
                break;
        }
    }
    public void tickAll() {
        for(int i = 0; i < artifactTrackers.size(); i++) {
            artifactTrackers.get(i).tickDown();
        }
    }

     */

    public ArtifactTrackerTicker updateItemStack(ItemStack stack, ArtifactType type) {
        switch (type) {
            case FORGOTTEN_HELM:
                artifactItems.set(0, stack);
                System.out.println(artifactTrackers.get(0) + ": A");
                return artifactTrackers.get(0);
            case FORGOTTEN_CHEST:
                artifactItems.set(1, stack);
                return artifactTrackers.get(1);
            case FORGOTTEN_LEGS:
                artifactItems.set(2, stack);
                return artifactTrackers.get(2);
            case FORGOTTEN_BOOTS:
                artifactItems.set(3, stack);
                return artifactTrackers.get(3);
            case FORGOTTEN_SWORD:
                artifactItems.set(4, stack);
                return artifactTrackers.get(4);
            case FORGOTTEN_BOW:
                artifactItems.set(5, stack);
                return artifactTrackers.get(5);
            case FORGOTTEN_SHIELD:
                artifactItems.set(6, stack);
                return artifactTrackers.get(6);
            default:
                return null;
        }
    }
    public void checkAllPossession(MinecraftServer server) {
        for (int i = 0; i < artifactTrackers.size(); i++) {
            ArtifactTrackerTicker tracker = artifactTrackers.get(i);
            if (tracker != null) {
                if (tracker.possessionRegistered()) {
                    if (tracker.possessedByEntity() && tracker.getLastEntityInPossession() instanceof Player) {
                        if (!tracker.checkPlayer((Player)tracker.getLastEntityInPossession())) {
                            clearArtifact(tracker.getArtifactItem().getArtifactType());
                            server.getPlayerList().broadcastMessage(new TextComponent("An artifact has been lost to the world..."), ChatType.SYSTEM, Util.NIL_UUID);
                        }
                    }
                }
            }
        }
    }
    public void clearPossession(ArtifactType type) {
        switch(type) {
            case FORGOTTEN_HELM:
                artifactTrackers.get(0).clearPossession();
                break;
            case FORGOTTEN_CHEST:
                artifactTrackers.get(1).clearPossession();
                break;
            case FORGOTTEN_LEGS:
                artifactTrackers.get(2).clearPossession();
                break;
            case FORGOTTEN_BOOTS:
                artifactTrackers.get(3).clearPossession();
                break;
            case FORGOTTEN_SWORD:
                artifactTrackers.get(4).clearPossession();
                break;
            case FORGOTTEN_BOW:
                artifactTrackers.get(5).clearPossession();
                break;
            case FORGOTTEN_SHIELD:
                artifactTrackers.get(6).clearPossession();
                break;
        }
    }
}
