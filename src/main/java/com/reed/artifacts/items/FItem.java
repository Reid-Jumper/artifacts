package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

public class FItem extends BowItem implements IArtifactItem {
    private ArtifactType artifactType;
    public FItem(Item.Properties prop) {
        super(prop);
        artifactType = ArtifactType.F;
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof FItem) {
            ArtifactsMod.handler.clearArtifact(ArtifactType.F);
        }
    }
}
