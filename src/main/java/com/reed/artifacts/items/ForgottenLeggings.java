package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class ForgottenLeggings extends ArmorItem implements IArtifactItem {

    private final ArtifactType artifactType = ArtifactType.FORGOTTEN_LEGS;
    public ForgottenLeggings(ArmorMaterial material, EquipmentSlot slot, Item.Properties prop) {
        super(material, slot, prop);
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof ForgottenLeggings) {
            ArtifactsMod.HANDLER.clearArtifact(artifactType);
        }
    }
}
