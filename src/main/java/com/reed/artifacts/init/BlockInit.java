package com.reed.artifacts.init;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.blockentities.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.event.RegistryEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ArtifactsMod.MOD_ID);
    public static final RegistryObject<Block> HELM_GEN_BLOCK = BLOCKS.register("helm_gen_block",
            () -> new HelmGenBlock(BlockBehaviour.Properties.of(Material.STONE).strength(100f, 1200f)));
    public static final RegistryObject<Block> CHEST_GEN_BLOCK = BLOCKS.register("chest_gen_block",
            () -> new ChestGenBlock(Block.Properties.of(Material.STONE).strength(100f, 1200f)));
    public static final RegistryObject<Block> LEGS_GEN_BLOCK = BLOCKS.register("legs_gen_block",
            () -> new LegsGenBlock(Block.Properties.of(Material.STONE).strength(100f, 1200f)));
    public static final RegistryObject<Block> BOOTS_GEN_BLOCK = BLOCKS.register("boots_gen_block",
            () -> new BootsGenBlock(Block.Properties.of(Material.STONE).strength(100f, 1200f)));
    public static final RegistryObject<Block> SWORD_GEN_BLOCK = BLOCKS.register("sword_gen_block",
            () -> new SwordGenBlock(Block.Properties.of(Material.STONE).strength(100f, 1200f)));
    public static final RegistryObject<Block> BOW_GEN_BLOCK = BLOCKS.register("bow_gen_block",
            () -> new BowGenBlock(Block.Properties.of(Material.STONE).strength(100f, 1200f)));
    public static final RegistryObject<Block> SHIELD_GEN_BLOCK = BLOCKS.register("shield_gen_block",
            () -> new ShieldGenBlock(Block.Properties.of(Material.STONE).strength(100f, 1200f)));

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        BLOCKS.getEntries().stream().map(RegistryObject::get).forEach( (block) -> {
            final Item.Properties properties = new Item.Properties().tab(ItemInit.ArtifactsTab.instance);
            final BlockItem blockItem = new BlockItem(block, properties);
            blockItem.setRegistryName(block.getRegistryName());
            registry.register(blockItem);
        });
    }
}
