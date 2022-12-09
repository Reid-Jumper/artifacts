package com.reed.artifacts.items;

import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

public class GItem extends BowItem implements IArtifactItem {
    private ArtifactType artifactType;
    public GItem(ArtifactType type, Item.Properties prop) {
        super(prop);
        artifactType = type;
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }
}
