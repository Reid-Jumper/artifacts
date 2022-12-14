package com.reed.artifacts.init;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.AncientArtifactsMaterial;
import com.reed.artifacts.util.AncientArtifactsTier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.reed.artifacts.items.*;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ArtifactsMod.MOD_ID);
    public static final RegistryObject<Item> FORGOTTEN_HELM = ITEMS.register("forgotten_helm",
            () -> new ForgottenHelm(AncientArtifactsMaterial.ANCIENT_ARTIFACT, EquipmentSlot.HEAD, new Item.Properties().tab(ArtifactsTab.instance).setNoRepair()));
    public static final RegistryObject<Item> FORGOTTEN_CHEST = ITEMS.register("forgotten_chest",
            () -> new ForgottenChestplate(AncientArtifactsMaterial.ANCIENT_ARTIFACT, EquipmentSlot.CHEST, new Item.Properties().tab(ArtifactsTab.instance).setNoRepair()));
    public static final RegistryObject<Item> FORGOTTEN_LEGS = ITEMS.register("forgotten_legs",
            () ->  new ForgottenLeggings(AncientArtifactsMaterial.ANCIENT_ARTIFACT, EquipmentSlot.LEGS, new Item.Properties().tab(ArtifactsTab.instance).setNoRepair()));
    public static final RegistryObject<Item> FORGOTTEN_BOOTS = ITEMS.register("forgotten_boots",
            () -> new ForgottenBoots(AncientArtifactsMaterial.ANCIENT_ARTIFACT_BOOTS, EquipmentSlot.FEET, new Item.Properties().tab(ArtifactsTab.instance).setNoRepair()));
    public static final RegistryObject<Item> FORGOTTEN_SWORD = ITEMS.register("forgotten_sword",
            () -> new ForgottenSword(AncientArtifactsTier.ANCIENT_ARTIFACT, 3, -2.4F, new Item.Properties().tab(ArtifactsTab.instance).setNoRepair()));
    public static final RegistryObject<Item> FORGOTTEN_BOW = ITEMS.register("forgotten_bow", () -> new ForgottenBow(new Item.Properties().tab(ArtifactsTab.instance).setNoRepair()));
    public static final RegistryObject<Item> FORGOTTEN_SHIELD = ITEMS.register("forgotten_shield",
            () -> new ForgottenShield(new Item.Properties().tab(ArtifactsTab.instance).setNoRepair()));

    public static class ArtifactsTab extends CreativeModeTab {

        public static final ArtifactsTab instance = new ArtifactsTab(CreativeModeTab.TABS.length, ArtifactsMod.MOD_ID);
        private ArtifactsTab(int index, String label){
            super(index, label);
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(FORGOTTEN_HELM.get());
        }
    }
}
