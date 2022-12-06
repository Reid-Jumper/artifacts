package com.reed.artifacts.init;

import com.reed.artifacts.ArtifactsMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS=
            DeferredRegister.create(ForgeRegistries.ITEMS, ArtifactsMod.MOD_ID);
    public static final RegistryObject<Item> A = ITEMS.register("a",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.instance)));
    public static final RegistryObject<Item> B = ITEMS.register("b",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.instance)));
    public static final RegistryObject<Item> C = ITEMS.register("c",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.instance)));
    public static final RegistryObject<Item> D = ITEMS.register("d",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.instance)));
    public static final RegistryObject<Item> E = ITEMS.register("e",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.instance)));
    public static final RegistryObject<Item> F = ITEMS.register("f",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.instance)));
    public static final RegistryObject<Item> G = ITEMS.register("g",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.instance)));

    public static class ModCreativeTab extends CreativeModeTab {

        public static final ModCreativeTab instance = new ModCreativeTab(CreativeModeTab.TABS.length, ArtifactsMod.MOD_ID);
        private ModCreativeTab(int index, String label){
            super(index, label);
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(A.get());
        }
    }
}
