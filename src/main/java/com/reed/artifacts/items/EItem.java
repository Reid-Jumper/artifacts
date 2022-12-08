package com.reed.artifacts.items;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class EItem extends SwordItem implements IArtifactItem {
    private int existCount;
    public EItem(Tier tier, int strength, float speed, Item.Properties prop) {
        super(tier, strength, speed, prop);
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
