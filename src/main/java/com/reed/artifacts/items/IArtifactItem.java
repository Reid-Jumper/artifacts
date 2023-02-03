package com.reed.artifacts.items;

import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.level.ItemLike;

public interface IArtifactItem extends ItemLike {
    ArtifactType getArtifactType();
}
