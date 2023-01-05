package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class ForgottenSword extends SwordItem implements IArtifactItem {
    private final ArtifactType artifactType = ArtifactType.FORGOTTEN_SWORD;
    public ForgottenSword(Tier tier, int strength, float speed, Item.Properties prop) {
        super(tier, strength, speed, prop);
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
            if(item instanceof ForgottenSword) {
            ArtifactsMod.HANDLER.clearArtifact(artifactType);
        }
    }
}
