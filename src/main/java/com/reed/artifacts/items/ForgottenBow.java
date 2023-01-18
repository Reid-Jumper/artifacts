package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

public class ForgottenBow extends BowItem implements IArtifactItem {
    private final ArtifactType artifactType = ArtifactType.FORGOTTEN_BOW;
    public ForgottenBow(Item.Properties prop) {
        super(prop);
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof ForgottenBow) {
            ArtifactsMod.ARTIFACT_HANDLER.clearArtifact(artifactType);
        }
    }
}
