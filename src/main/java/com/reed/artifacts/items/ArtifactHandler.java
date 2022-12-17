package com.reed.artifacts.items;

import com.reed.artifacts.util.ArtifactTrackerTicker;
import com.reed.artifacts.util.ArtifactType;
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

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
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
    /*

    @SubscribeEvent
    public void onPlayerContainerEvent(PlayerContainerEvent.Close event) {
        System.out.println("Container Closed");
        Set<Item> set = new HashSet<Item>();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < artifactItems.size(); i++) {
            if (artifactItems.get(i) != null) {
                set.add((Item)artifactItems.get(i).getItem());
                indices.add(i);
            }
        }
        Container container = event.getContainer().getSlot(0).container;
        Player player = event.getPlayer();
        if(container.hasAnyOf(Collections.unmodifiableSet(set))) {
            for(int i = 0; i < indices.size(); i++) {
                artifactTrackers.get(indices.get(i)).checkContainer(container);
            }
        }
        for(int i = 0; i < indices.size(); i++) {
            artifactTrackers.get(indices.get(i)).checkPlayer(player);
        }
    }
    */
    /*
    public ArtifactTrackerTicker updateItemStack(ItemStack stack, ArtifactType type) {
        switch (type) {
            case A:
                artifactItems.set(0, stack);
                return artifactTrackers.get(0);
            case B:
                artifactItems.set(1, stack);
                return artifactTrackers.get(1);
            case C:
                artifactItems.set(2, stack);
                return artifactTrackers.get(2);
            case D:
                artifactItems.set(3, stack);
                return artifactTrackers.get(3);
            case E:
                artifactItems.set(4, stack);
                return artifactTrackers.get(4);
            case F:
                artifactItems.set(5, stack);
                return artifactTrackers.get(5);
            case G:
                artifactItems.set(6, stack);
                return artifactTrackers.get(6);
            default:
                return null;
        }
    }

     */
}
