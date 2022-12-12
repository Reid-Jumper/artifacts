package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class AItem extends ArmorItem implements IArtifactItem {

    private ArtifactType artifactType;
    public AItem(ArmorMaterial material, EquipmentSlot slot, Item.Properties prop) {
        super(material, slot, prop);
        artifactType = ArtifactType.A;
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof AItem) {
            ArtifactsMod.HANDLER.clearArtifact(ArtifactType.A);
        }
    }
}
