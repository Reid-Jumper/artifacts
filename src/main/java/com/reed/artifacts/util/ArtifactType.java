package com.reed.artifacts.util;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public enum ArtifactType implements StringRepresentable {
    FORGOTTEN_HELM("artifacts:forgotten_helm"),
    FORGOTTEN_CHEST("artifacts:forgotten_chest"),
    FORGOTTEN_LEGS("artifacts:forgotten_legs"),
    FORGOTTEN_BOOTS("artifacts:forgotten_boots"),
    FORGOTTEN_SWORD("artifacts:forgotten_sword"),
    FORGOTTEN_BOW("artifacts:forgotten_bow"),
    FORGOTTEN_SHIELD("artifacts:forgotten_shield");

    public final String resourceLocation;

    ArtifactType(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name();
    }

    public static ArtifactType fromString(String value) {
         return Arrays.stream(ArtifactType.values()).filter(artifactType -> artifactType.name().equals(value)).findFirst().get();
    }
}
