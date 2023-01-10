package com.reed.artifacts.items;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Arrow;

public class ForgottenBow extends BowItem implements IArtifactItem {
    private ArtifactType artifactType;
    public ForgottenBow(Item.Properties prop) {
        super(prop);
        artifactType = ArtifactType.FORGOTTEN_BOW;
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof ForgottenBow) {
            ArtifactsMod.HANDLER.clearArtifact(ArtifactType.FORGOTTEN_BOW);
        }
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow) {
        if (arrow instanceof Arrow) {
            ((Arrow) arrow).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1, false, true, true));
            arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
        }
        return arrow;
    }
}
