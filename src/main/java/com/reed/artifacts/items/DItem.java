package com.reed.artifacts.items;

import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class DItem extends ArmorItem implements IArtifactItem {

    private ArtifactType artifactType;
    public DItem(ArmorMaterial material, EquipmentSlot slot, ArtifactType type, Item.Properties prop) {
        super(material, slot, prop);
        artifactType = type;
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }
}
