package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class ForgottenChestplate extends ArmorItem implements IArtifactItem {

    private final ArtifactType artifactType = ArtifactType.FORGOTTEN_CHEST;
    private boolean breathCharge;
    private boolean fireResCharge;

    public ForgottenChestplate(ArmorMaterial material, EquipmentSlot slot, Item.Properties prop) {
        super(material, slot, prop);
        breathCharge = true;
        fireResCharge = true;
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof ForgottenChestplate) {
            ArtifactsMod.HANDLER.clearArtifact(artifactType);
        }
    }
    public boolean getFireResCharge() {
        return fireResCharge;
    }
    public void setFireResCharge(boolean charge) {
        fireResCharge = charge;
    }
    public boolean getBreathCharge() {
        return breathCharge;
    }
    public void setBreathCharge(boolean charge) {
        breathCharge = charge;
    }

}
