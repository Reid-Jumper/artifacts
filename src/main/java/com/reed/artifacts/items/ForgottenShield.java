package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

public class ForgottenShield extends ShieldItem implements IArtifactItem {
    public static final int EFFECTIVE_BLOCK_DELAY = 2;
    private final ArtifactType artifactType = ArtifactType.FORGOTTEN_SHIELD;
    public ForgottenShield(Item.Properties prop) {
        super(prop);
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof ForgottenShield) {
            ArtifactsMod.ARTIFACT_HANDLER.clearArtifact(artifactType);
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack stack2) {
        return false;
    }
}
