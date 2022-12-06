package com.reed.artifacts.init;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.util.AncientArtifactsMaterial;
import com.reed.artifacts.util.AncientArtifactsTier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS=
            DeferredRegister.create(ForgeRegistries.ITEMS, ArtifactsMod.MOD_ID);
    public static final RegistryObject<Item> A = ITEMS.register("a",
            () -> new ArmorItem(AncientArtifactsMaterial.ANCIENT_ARTIFACT, EquipmentSlot.HEAD, new Item.Properties().tab(ArtifactsTab.instance)));
    public static final RegistryObject<Item> B = ITEMS.register("b",
            () -> new ArmorItem(AncientArtifactsMaterial.ANCIENT_ARTIFACT, EquipmentSlot.CHEST, new Item.Properties().tab(ArtifactsTab.instance)));
    public static final RegistryObject<Item> C = ITEMS.register("c",
            () -> new ArmorItem(AncientArtifactsMaterial.ANCIENT_ARTIFACT, EquipmentSlot.LEGS, new Item.Properties().tab(ArtifactsTab.instance)));
    public static final RegistryObject<Item> D = ITEMS.register("d",
            () -> new ArmorItem(AncientArtifactsMaterial.ANCIENT_ARTIFACT, EquipmentSlot.FEET, new Item.Properties().tab(ArtifactsTab.instance)));
    public static final RegistryObject<Item> E = ITEMS.register("e",
            () -> new SwordItem(AncientArtifactsTier.ANCIENT_ARTIFACT, 3, -2.4F, new Item.Properties().tab(ArtifactsTab.instance)));
    public static final RegistryObject<Item> F = ITEMS.register("f",
            () -> new BowItem(new Item.Properties().tab(ArtifactsTab.instance)));
    public static final RegistryObject<Item> G = ITEMS.register("g",
            () -> new ShieldItem(new Item.Properties().tab(ArtifactsTab.instance)));

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
