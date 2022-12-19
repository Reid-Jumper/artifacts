package com.reed.artifacts.util;

import com.reed.artifacts.init.ItemInit;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
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

    public static Item getItemForArtifactType(ArtifactType artifactType) {
        switch (artifactType) {
            case FORGOTTEN_HELM -> {
                return ItemInit.FORGOTTEN_HELM.get();
            }
            case FORGOTTEN_CHEST -> {
                return ItemInit.FORGOTTEN_CHEST.get();

            }
            case FORGOTTEN_LEGS -> {
                return ItemInit.FORGOTTEN_LEGS.get();

            }
            case FORGOTTEN_BOOTS -> {
                return ItemInit.FORGOTTEN_BOOTS.get();

            }
            case FORGOTTEN_SWORD -> {
                return ItemInit.FORGOTTEN_SWORD.get();

            }
            case FORGOTTEN_BOW -> {
                return ItemInit.FORGOTTEN_BOW.get();
            }
            case FORGOTTEN_SHIELD -> {
                return ItemInit.FORGOTTEN_SHIELD.get();
            }
        }
        return null;
    }
}
