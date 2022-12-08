package com.reed.artifacts.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class CItem extends ArmorItem implements IArtifactItem {
    private int existCount;
    public CItem(ArmorMaterial material, EquipmentSlot slot, Item.Properties prop) {
        super(material, slot, prop);
        existCount = 0;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        if(existCount > 0)
            existCount--;
    }
    public int getExistCount() {
        return existCount;
    }
    public void spawned() {
        existCount++;
    }
}
