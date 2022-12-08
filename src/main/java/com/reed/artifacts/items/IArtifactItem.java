package com.reed.artifacts.items;

import net.minecraft.world.level.ItemLike;

public interface IArtifactItem extends ItemLike {
    int existCount = 0;
    int getExistCount();
    void spawned();
}
