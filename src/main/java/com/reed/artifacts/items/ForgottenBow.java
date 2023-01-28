package com.reed.artifacts.items;

import com.mojang.logging.LogUtils;
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
import org.slf4j.Logger;

public class ForgottenBow extends BowItem implements IArtifactItem {
    private static final Logger LOGGER = LogUtils.getLogger();

    private final ArtifactType artifactType = ArtifactType.FORGOTTEN_BOW;
    public ForgottenBow(Item.Properties prop) {
        super(prop);
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof ForgottenBow) {
            ArtifactsMod.ARTIFACT_HANDLER.clearArtifact(artifactType, false);
            LOGGER.info("Artifact " + artifactType + " entity destroyed.");
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
