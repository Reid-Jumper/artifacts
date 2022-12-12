package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class EItem extends SwordItem implements IArtifactItem {
    private ArtifactType artifactType;
    public EItem(Tier tier, int strength, float speed, Item.Properties prop) {
        super(tier, strength, speed, prop);
        artifactType = ArtifactType.E;
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof EItem) {
            ArtifactsMod.HANDLER.clearArtifact(ArtifactType.E);
        }
    }
}
