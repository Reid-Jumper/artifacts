package com.reed.artifacts.items;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

public class GItem extends BowItem implements IArtifactItem {
    private int existCount;
    public GItem(Item.Properties prop) {
        super(prop);
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
