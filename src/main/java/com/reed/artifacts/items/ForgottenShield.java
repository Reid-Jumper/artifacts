package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;

public class ForgottenShield extends ShieldItem implements IArtifactItem {
    private ArtifactType artifactType;
    public ForgottenShield(Item.Properties prop) {
        super(prop);
        artifactType = ArtifactType.FORGOTTEN_SHIELD;
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof ForgottenShield) {
            ArtifactsMod.HANDLER.clearArtifact(ArtifactType.FORGOTTEN_SHIELD);
        }
    }
}