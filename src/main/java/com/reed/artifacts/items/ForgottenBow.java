package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

public class ForgottenBow extends BowItem implements IArtifactItem {
    private ArtifactType artifactType;
    public ForgottenBow(Item.Properties prop) {
        super(prop);
        artifactType = ArtifactType.FORGOTTEN_BOW;
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof ForgottenBow) {
            ArtifactsMod.HANDLER.clearArtifact(ArtifactType.FORGOTTEN_BOW);
        }
    }
}
