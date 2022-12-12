package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

public class GItem extends BowItem implements IArtifactItem {
    private ArtifactType artifactType;
    public GItem(Item.Properties prop) {
        super(prop);
        artifactType = ArtifactType.G;
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof GItem) {
            ArtifactsMod.HANDLER.clearArtifact(ArtifactType.G);
        }
    }
}
