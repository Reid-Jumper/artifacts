package com.reed.artifacts.items;

import com.mojang.logging.LogUtils;
import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.ArtifactsModSaveData;
import com.reed.artifacts.init.ItemInit;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ArtifactHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

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
        return artifactsModSaveData.getArtifactDestroyed(type);
    }

    public void putArtifact(ArtifactType type) {
        artifactsModSaveData.setArtifactDestroyed(type, false);
    }

    public void clearArtifact(ArtifactType type, boolean quietly) {
        if (!quietly) {
            ArtifactsMod.SERVER.getPlayerList().broadcastMessage(new TextComponent("An artifact has been lost to the world..."), ChatType.SYSTEM, Util.NIL_UUID);
        }
        artifactsModSaveData.setArtifactDestroyed(type, true);
    }

    public void checkAllPossession(MinecraftServer server) {
        for (ArtifactType artifactType : ArtifactType.values()) {
            if (getLastEntityInPossession(artifactType) != null) {
                ServerPlayer player = server.getPlayerList().getPlayerByName(getLastEntityInPossession(artifactType));
                if (player != null && !ArtifactHandler.getPlayerArtifacts(player).contains(artifactType)) {
                    clearArtifact(artifactType, false);
                    clearPossession(artifactType);
                    LOGGER.warn("Player " + player.getScoreboardName() + " is supposed to possess artifact " + artifactType + " but does not. Cleared possession...");
                }
            }
        }
    }

    public boolean hasPossession(Player player, ArtifactType type) {
        String artifactOwner = artifactsModSaveData.getArtifactOwner(type);
        return (artifactOwner != null && artifactOwner.equals(player.getScoreboardName()));
    }

    public void changePossession(Player entity, ArtifactType artifactType) {
        System.out.println("Changing possession of " + artifactType + " to " + entity.getScoreboardName());
        artifactsModSaveData.setArtifactOwner(artifactType, ((LivingEntity) entity).getScoreboardName());
    }

    public void checkPlayerAndUpdatePossessionStatus(ArtifactType artifactType, Player player) {
        if(ArtifactHandler.getPlayerArtifacts(player).contains(artifactType)) {
            changePossession(player, artifactType);
        }
    }

    public void clearPossession(ArtifactType artifactType) {
        System.out.println("Clearing possession of " + artifactType);
        artifactsModSaveData.setArtifactOwner(artifactType, null);
    }

    public String getLastEntityInPossession(ArtifactType artifactType) {
        return this.artifactsModSaveData.getArtifactOwner(artifactType);
    }
}
