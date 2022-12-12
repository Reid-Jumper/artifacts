package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BItem extends ArmorItem implements IArtifactItem {

    private ArtifactType artifactType;
    private boolean breathCharge;
    private boolean fireResCharge;

    public BItem(ArmorMaterial material, EquipmentSlot slot, Item.Properties prop) {
        super(material, slot, prop);
        artifactType = ArtifactType.B;
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
        if(item instanceof BItem) {
            ArtifactsMod.HANDLER.clearArtifact(ArtifactType.B);
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
