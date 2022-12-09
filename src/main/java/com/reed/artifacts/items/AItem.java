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
    public AItem(ArmorMaterial material, EquipmentSlot slot, ArtifactType type, Item.Properties prop) {
        super(material, slot, prop);
        artifactType = type;
    }
    /*
    public AItem(AItem item) {
        super(item.getMaterial(), item.getSlot(), );
        artifactType = item.getArtifactType();
    }
    */

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    public IArtifactItem getAsArtifactItem() {
        return this;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof AItem) {
            ArtifactsMod.handler.clearArtifact(ArtifactType.A);
        }
    }
}
