package com.reed.artifacts.init;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.AncientArtifactsMaterial;
import com.reed.artifacts.util.AncientArtifactsTier;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.reed.artifacts.items.*;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ArtifactsMod.MOD_ID);
    public static final RegistryObject<Item> A = ITEMS.register("forgotten_helm",
            () -> new AItem(AncientArtifactsMaterial.ANCIENT_ARTIFACT, EquipmentSlot.HEAD, new Item.Properties().tab(ArtifactsTab.instance).setNoRepair()));
    public static final RegistryObject<Item> B = ITEMS.register("forgotten_chest",
            () -> new BItem(AncientArtifactsMaterial.ANCIENT_ARTIFACT, EquipmentSlot.CHEST, new Item.Properties().tab(ArtifactsTab.instance).setNoRepair()));
    public static final RegistryObject<Item> C = ITEMS.register("forgotten_legs",
            () ->  new CItem(AncientArtifactsMaterial.ANCIENT_ARTIFACT, EquipmentSlot.LEGS, new Item.Properties().tab(ArtifactsTab.instance).setNoRepair()));
    public static final RegistryObject<Item> D = ITEMS.register("forgotten_boots",
            () -> new DItem(AncientArtifactsMaterial.ANCIENT_ARTIFACT_BOOTS, EquipmentSlot.FEET, new Item.Properties().tab(ArtifactsTab.instance).setNoRepair()));
    public static final RegistryObject<Item> E = ITEMS.register("forgotten_sword",
            () -> new EItem(AncientArtifactsTier.ANCIENT_ARTIFACT, 3, -2.4F, new Item.Properties().tab(ArtifactsTab.instance).setNoRepair()));
    public static final RegistryObject<Item> F = ITEMS.register("forgotten_bow",
            () -> new FItem(new Item.Properties().tab(ArtifactsTab.instance).setNoRepair()));
    public static final RegistryObject<Item> G = ITEMS.register("forgotten_shield",
            () -> new GItem(new Item.Properties().tab(ArtifactsTab.instance).setNoRepair()));

    public static class ArtifactsTab extends CreativeModeTab {

        public static final ArtifactsTab instance = new ArtifactsTab(CreativeModeTab.TABS.length, ArtifactsMod.MOD_ID);
        private ArtifactsTab(int index, String label){
            super(index, label);
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(A.get());
        }
    }
}
