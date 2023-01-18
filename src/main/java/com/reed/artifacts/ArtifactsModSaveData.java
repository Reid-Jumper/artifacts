package com.reed.artifacts;

import com.mojang.logging.LogUtils;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ArtifactsModSaveData extends SavedData {

    public static final String ARTIFACTS_MOD_DATA = "artifacts_mod_data";
    private static final Logger LOGGER = LogUtils.getLogger();

    private Map<ArtifactType, String> artifactOwnersMap = new HashMap<>();


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
                LOGGER.info("Add owner of: "+entry.getKey()+ " -> " + entry.getValue() );
            }
        }
        return tag;
    }

    public static ArtifactsModSaveData load(CompoundTag tag) {
        ArtifactsModSaveData artifactsModSaveData = create();
        for(ArtifactType artifactType : ArtifactType.values()) {
            String artifactOwnerKey = getArtifactOwnerKey(artifactType);
            if (tag.contains(artifactOwnerKey)) {
                artifactsModSaveData.artifactOwnersMap.put(artifactType, tag.getString(artifactOwnerKey));
                LOGGER.info("Hello from load: detected " + tag.getString(artifactOwnerKey) + " as owner of " + artifactType);
            }
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

    private static String getArtifactOwnerKey(ArtifactType artifactType) {
        return artifactType.name() + "_OWNER";
    }
}
