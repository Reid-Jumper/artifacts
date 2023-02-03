package com.reed.artifacts.items;

import com.mojang.logging.LogUtils;
import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;

public class ForgottenBoots extends ArmorItem implements IArtifactItem {
    private static final Logger LOGGER = LogUtils.getLogger();


    private final ArtifactType artifactType = ArtifactType.FORGOTTEN_BOOTS;
    public ForgottenBoots(ArmorMaterial material, EquipmentSlot slot, Item.Properties prop) {
        super(material, slot, prop);
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    /*
    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof ForgottenBoots) {
            ArtifactsMod.ARTIFACT_HANDLER.clearArtifact(artifactType, false);
            LOGGER.info("Artifact " + artifactType + " entity destroyed.");
        }
    }
    */
}
