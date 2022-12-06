package com.reed.artifacts.init;

import com.reed.artifacts.ArtifactsMod;
import net.minecraft.world.level.block.Block;
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
    public static final RegistryObject<Block> A_GEN_BLOCK = BLOCKS.register("a_gen_block",
            () -> new Block(Block.Properties.of(Material.STONE).strength(4f, 1200f).lightLevel((state) -> 15)));
    public static final RegistryObject<Block> B_GEN_BLOCK = BLOCKS.register("b_gen_block",
            () -> new Block(Block.Properties.of(Material.STONE).strength(4f, 1200f).lightLevel((state) -> 15)));
    public static final RegistryObject<Block> C_GEN_BLOCK = BLOCKS.register("c_gen_block",
            () -> new Block(Block.Properties.of(Material.STONE).strength(4f, 1200f).lightLevel((state) -> 15)));
    public static final RegistryObject<Block> D_GEN_BLOCK = BLOCKS.register("d_gen_block",
            () -> new Block(Block.Properties.of(Material.STONE).strength(4f, 1200f).lightLevel((state) -> 15)));
    public static final RegistryObject<Block> E_GEN_BLOCK = BLOCKS.register("e_gen_block",
            () -> new Block(Block.Properties.of(Material.STONE).strength(4f, 1200f).lightLevel((state) -> 15)));
    public static final RegistryObject<Block> F_GEN_BLOCK = BLOCKS.register("f_gen_block",
            () -> new Block(Block.Properties.of(Material.STONE).strength(4f, 1200f).lightLevel((state) -> 15)));
    public static final RegistryObject<Block> G_GEN_BLOCK = BLOCKS.register("g_gen_block",
            () -> new Block(Block.Properties.of(Material.STONE).strength(4f, 1200f).lightLevel((state) -> 15)));

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        BLOCKS.getEntries().stream().map(RegistryObject::get).forEach( (block) -> {
            final Item.Properties properties = new Item.Properties().tab(ItemInit.ModCreativeTab.instance);
            final BlockItem blockItem = new BlockItem(block, properties);
            blockItem.setRegistryName(block.getRegistryName());
            registry.register(blockItem);
        });
    }
}
