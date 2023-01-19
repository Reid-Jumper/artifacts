package com.reed.artifacts;

import com.mojang.logging.LogUtils;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import org.slf4j.Logger;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ArtifactsModSaveData extends SavedData {

    public static final String ARTIFACTS_MOD_DATA = "artifacts_mod_data";
    public static final String KEY_CHESTPLATE_FIRE_RES_CHARGE = "chestplate_fire_res_charge";
    public static final String KEY_CHESTPLATE_BREATH_CHARGE = "chestplate_breath_charge";

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String LAST_SEEN_PREFIX = "LAST_SEEN_";

    private final Map<ArtifactType, String> artifactOwnersMap = new HashMap<>();
    private final Map<ArtifactType, Boolean> artifactDestructionMap = new HashMap<>();
    private final Map<String, Instant> playersLastSeenMap = new HashMap<>();

    private boolean chestplateFireResistanceCharged = true;
    private boolean chestplateBreathCharged = true;

    public static ArtifactsModSaveData create() {
        return new ArtifactsModSaveData();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        LOGGER.info("Hello from save");
        for (Map.Entry<ArtifactType, String> entry : artifactOwnersMap.entrySet()) {
            if (entry.getValue() == null) {
                tag.remove(getArtifactOwnerKey(entry.getKey()));
                LOGGER.info("Hello from save: Remove owner of: " + entry.getKey());

            } else {
                tag.putString(getArtifactOwnerKey(entry.getKey()), entry.getValue());
                LOGGER.info("Add owner of: " + entry.getKey() + " -> " + entry.getValue());
            }
        }

        for (Map.Entry<ArtifactType, Boolean> entry : artifactDestructionMap.entrySet()) {
            tag.putBoolean(getArtifactDestroyedKey(entry.getKey()), entry.getValue());
        }

        for (Map.Entry<String, Instant> entry : playersLastSeenMap.entrySet()) {
            tag.putString(getPlayerLastSeenKey(entry.getKey()), DateTimeFormatter.ISO_INSTANT.format(entry.getValue()));
        }
        tag.putBoolean(KEY_CHESTPLATE_BREATH_CHARGE, chestplateBreathCharged);
        tag.putBoolean(KEY_CHESTPLATE_FIRE_RES_CHARGE, chestplateFireResistanceCharged);
        return tag;
    }

    public static ArtifactsModSaveData load(CompoundTag tag) {
        ArtifactsModSaveData artifactsModSaveData = create();
        for (ArtifactType artifactType : ArtifactType.values()) {
            String artifactOwnerKey = getArtifactOwnerKey(artifactType);
            if (tag.contains(artifactOwnerKey)) {
                artifactsModSaveData.artifactOwnersMap.put(artifactType, tag.getString(artifactOwnerKey));
                LOGGER.info("Hello from load: detected " + tag.getString(artifactOwnerKey) + " as owner of " + artifactType);
            }
            String artifactDestroyedKey = getArtifactDestroyedKey(artifactType);
            if (tag.contains(artifactDestroyedKey)) {
                artifactsModSaveData.artifactDestructionMap.put(artifactType, tag.getBoolean(artifactDestroyedKey));
            }
        }

        for (String key : tag.getAllKeys()) {
            if (key.startsWith(LAST_SEEN_PREFIX)) {
                artifactsModSaveData.playersLastSeenMap.put(
                    key.replace(LAST_SEEN_PREFIX, ""),
                    Instant.from(DateTimeFormatter.ISO_INSTANT.parse(tag.getString(key)))
                );
            }
        }

        if (tag.contains(KEY_CHESTPLATE_BREATH_CHARGE)) {
            artifactsModSaveData.chestplateBreathCharged = tag.getBoolean(KEY_CHESTPLATE_BREATH_CHARGE);
        }
        if (tag.contains(KEY_CHESTPLATE_FIRE_RES_CHARGE)) {
            artifactsModSaveData.chestplateFireResistanceCharged = tag.getBoolean(KEY_CHESTPLATE_FIRE_RES_CHARGE);
        }
        return artifactsModSaveData;
    }

    public String getArtifactOwner(ArtifactType artifactType) {
        return artifactOwnersMap.getOrDefault(artifactType, null);
    }

    public void setArtifactOwner(ArtifactType artifactType, String ownerName) {
        artifactOwnersMap.put(artifactType, ownerName);
        setDirty();
    }

    public boolean getArtifactDestroyed(ArtifactType artifactType) {
        return artifactDestructionMap.getOrDefault(artifactType, false);
    }

    public void setArtifactDestroyed(ArtifactType artifactType, boolean destroyed) {
        artifactDestructionMap.put(artifactType, destroyed);
        setDirty();
    }

    public boolean isChestplateFireResistanceCharged() {
        return chestplateFireResistanceCharged;
    }

    public void setChestplateFireResistanceCharged(boolean chestplateFireResistanceCharged) {
        this.chestplateFireResistanceCharged = chestplateFireResistanceCharged;
        setDirty();
    }

    public boolean isChestplateBreathCharged() {
        return chestplateBreathCharged;
    }

    public void setChestplateBreathCharged(boolean chestplateBreathCharged) {
        this.chestplateBreathCharged = chestplateBreathCharged;
        setDirty();
    }

    public Map<String, Instant> getLastSeenTimes() {
        return playersLastSeenMap;
    }

    public void setLastSeenTimeFor(Player player, Instant instant) {
        playersLastSeenMap.put(player.getScoreboardName(), instant);
        setDirty();
    }

    public void clearLastSeenTimeFor(Player player) {
        playersLastSeenMap.remove(player.getScoreboardName());
        setDirty();
    }

    private static String getArtifactOwnerKey(ArtifactType artifactType) {
        return artifactType.name() + "_OWNER";
    }

    private static String getArtifactDestroyedKey(ArtifactType artifactType) {
        return artifactType.name() + "_DESTROYED";
    }

    private static String getPlayerLastSeenKey(String playerName) {
        return LAST_SEEN_PREFIX + playerName;
    }
}
