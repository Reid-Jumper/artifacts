package com.reed.artifacts.items;

import com.mojang.logging.LogUtils;
import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.slf4j.Logger;

public class ForgottenShield extends ShieldItem implements IArtifactItem {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final int EFFECTIVE_BLOCK_DELAY = 2;
    private final ArtifactType artifactType = ArtifactType.FORGOTTEN_SHIELD;
    public ForgottenShield(Item.Properties prop) {
        super(prop);
    }

    @Override
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    /*
    @Override
    public void onDestroyed(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if(item instanceof ForgottenShield) {
            ArtifactsMod.ARTIFACT_HANDLER.clearArtifact(artifactType, false);
            LOGGER.info("Artifact " + artifactType + " entity destroyed.");
        }
    }

     */

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack stack2) {
        return false;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction) || super.canPerformAction(stack, toolAction);
    }
}
