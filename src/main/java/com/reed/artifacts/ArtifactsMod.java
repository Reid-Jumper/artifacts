package com.reed.artifacts;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.logging.LogUtils;
import com.reed.artifacts.init.BlockInit;
import com.reed.artifacts.init.ItemInit;
import com.reed.artifacts.init.TileEntityInit;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.commands.arguments.EntitySummonArgument;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArtifactsMod.MOD_ID)
public class ArtifactsMod
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "artifacts";

    public ArtifactsMod()
    {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        modEventBus.addListener(this::setup);
        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        TileEntityInit.TILE_ENTITY_TYPES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");

    }

    @SubscribeEvent
    public void onPlayerLoad(final PlayerEvent.PlayerLoggedInEvent event) throws CommandSyntaxException{
        Vec3 spawnpos = new Vec3(0.0, 70.0, 0.0);
        CompoundTag tag;
        tag = new CompoundTag();
        CompoundTag Item = new CompoundTag();
        CompoundTag outer = new CompoundTag();
        ResourceLocation loc = new ResourceLocation("minecraft:item");
        Registry.ENTITY_TYPE.getOptional(loc).filter(EntityType::canSummon).orElseThrow(() -> {
            return EntitySummonArgument.ERROR_UNKNOWN_ENTITY.create(loc);
        });
        CompoundTag compoundtag = new CompoundTag();
        CompoundTag item = new CompoundTag();
        item.putString("id", "minecraft:iron_ingot");
        item.putInt("Count", 7);
        compoundtag.put("Item", item);
        compoundtag.putString("id", "minecraft:item");
        ServerLevel serverLevel = (ServerLevel)event.getPlayer().getLevel();
        Entity entity = EntityType.loadEntityRecursive(compoundtag, serverLevel, (p_138828_) -> {
            p_138828_.moveTo(0, 85, 0, p_138828_.getYRot(), p_138828_.getXRot());
            return p_138828_;
        });
        serverLevel.tryAddFreshEntityWithPassengers(entity);
        //((Mob)entity).finalizeSpawn(level, level.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.COMMAND, (SpawnGroupData)null, (CompoundTag)null);


    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }

}
