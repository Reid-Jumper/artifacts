package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class ForgottenBoots extends ArmorItem implements IArtifactItem {

    private ArtifactType artifactType;
    public ForgottenBoots(ArmorMaterial material, EquipmentSlot slot, Item.Properties prop) {
        super(material, slot, prop);
        artifactType = ArtifactType.FORGOTTEN_BOOTS;
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof ForgottenBoots) {
            ArtifactsMod.HANDLER.clearArtifact(ArtifactType.FORGOTTEN_BOOTS);
        }
    }
}
