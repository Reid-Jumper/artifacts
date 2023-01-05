package com.reed.artifacts;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import com.reed.artifacts.init.BlockInit;
import com.reed.artifacts.init.ItemInit;
import com.reed.artifacts.init.TileEntityInit;
import com.reed.artifacts.items.*;
import com.reed.artifacts.util.ArtifactType;
import net.minecraft.commands.arguments.item.ItemPredicateArgument;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Container;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
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

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArtifactsMod.MOD_ID)
public class ArtifactsMod
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "artifacts";
    public static final ArtifactHandler HANDLER = new ArtifactHandler();

    public MinecraftServer server;

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
        server = event.getServer();

    }

    /*
    @SubscribeEvent
    public void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) throws CommandSyntaxException{

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
    }
    */

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (server.overworld().isNight() && server.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            server.getPlayerList().broadcastMessage(new TextComponent("Night has fallen. The world grows more dangerous..."), ChatType.SYSTEM, Util.NIL_UUID);
            server.getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).set(false, server);
        } else if (!server.overworld().isNight() && !server.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            server.getPlayerList().broadcastMessage(new TextComponent("Day has arrived. A weight has lifted..."), ChatType.SYSTEM, Util.NIL_UUID);
            server.getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).set(true, server);
            if(!HANDLER.artifactSlotOpen(ArtifactType.FORGOTTEN_CHEST)) {
                ((ForgottenChestplate)HANDLER.getArtifact(ArtifactType.FORGOTTEN_CHEST)).setFireResCharge(true);
                ((ForgottenChestplate)HANDLER.getArtifact(ArtifactType.FORGOTTEN_CHEST)).setBreathCharge(true);
            }
        }
        server.getPlayerList().getPlayers().forEach((player) -> {
            if(player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ForgottenHelm) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 399, 0, true, true));
            }
            if(player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ForgottenLeggings) {
                if(server.overworld().isNight()) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 399, 0, true, true));
                } else {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 399, 0, true, true));
                    player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 399, 0, true, true));
                }
            }
        });
    }
    @SubscribeEvent
    public void onEntityDamaged(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if(entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ForgottenChestplate && !entity.getLevel().isClientSide()) {
            if(event.getSource() instanceof EntityDamageSource) {
                event.getSource().getEntity().setSecondsOnFire(20);
            }else if(event.getSource().isFire() && ((ForgottenChestplate)entity.getItemBySlot(EquipmentSlot.CHEST).getItem()).getFireResCharge() == true) {
                event.setCanceled(true);
                event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0 , false, true));
                ((ForgottenChestplate)entity.getItemBySlot(EquipmentSlot.CHEST).getItem()).setFireResCharge(false);
            }else if(event.getSource().equals(DamageSource.DROWN) && ((ForgottenChestplate)entity.getItemBySlot(EquipmentSlot.CHEST).getItem()).getBreathCharge() == true) {
                event.setCanceled(true);
                event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 6000, 0, false, true));
                ((ForgottenChestplate)entity.getItemBySlot(EquipmentSlot.CHEST).getItem()).setBreathCharge(false);
            }
        }
        if(entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ForgottenBoots && !entity.getLevel().isClientSide()) {
            if(event.getSource().isFall()) {
                event.setCanceled(true);
            }
        }
        LOGGER.info("onEntityDamaged");
        HANDLER.checkAllPossession(server);
    }
    @SubscribeEvent
    public void onPlayerDestroyItem(PlayerDestroyItemEvent event) {
        LOGGER.info("onPlayerDestroyItem");
        HANDLER.checkAllPossession(server);
    }

    @SubscribeEvent
    public void onPlayerDisconnect(final PlayerEvent.PlayerLoggedOutEvent event) {

    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        LOGGER.info("Hello from player respawn");

        Player player = ((Player)event.getEntityLiving());
        List<ArtifactType> artifactTypes = ArtifactHandler.getPlayerArtifacts(player);
        if (!artifactTypes.isEmpty()) {
            for(ArtifactType artifactType : artifactTypes) {
                try {
                    player.getInventory().clearOrCountMatchingItems(ItemPredicateArgument.itemPredicate().parse(new StringReader(artifactType.resourceLocation)).create(null), -1, player.inventoryMenu.getCraftSlots());
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }
                player.containerMenu.broadcastChanges();
                player.inventoryMenu.slotsChanged(player.getInventory());
            }
        }
    }


    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof Player && server.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            Player player = ((Player)event.getEntityLiving());
            List<ArtifactType> artifactTypes = ArtifactHandler.getPlayerArtifacts(player);
            if (!artifactTypes.isEmpty()) {
                for(ArtifactType artifactType : artifactTypes) {
                    ItemStack artifactItem = new ItemStack(ArtifactType.getItemForArtifactType(artifactType));
                    player.drop(artifactItem, true, true);
                    HANDLER.clearPossession(artifactType);
                }
            }
        }
    }

    @SubscribeEvent
    public void onItemPickup(PlayerEvent.ItemPickupEvent event) {
        System.out.println("Event fired");
        System.out.println(HANDLER.getArtifact(ArtifactType.FORGOTTEN_HELM));
        if(event.getStack().getItem() instanceof IArtifactItem artifactItem) {
            HANDLER.updateArtifact(artifactItem, (artifactItem).getArtifactType()).changePossession(event.getPlayer());
        }
        System.out.println(HANDLER.getArtifact(ArtifactType.FORGOTTEN_HELM));
    }
    @SubscribeEvent
    public void onPlayerContainerEvent(PlayerContainerEvent.Close event) {
        Set<Item> set = new HashSet<Item>();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (ArtifactType artifactType : ArtifactType.values()) {
            IArtifactItem artifact = HANDLER.getArtifact(artifactType);
            if (artifact != null) {
                set.add(artifact.asItem());
            }
        }
        Container container = event.getContainer().getSlot(0).container;
        Player player = event.getPlayer();
        if(container.hasAnyOf(Collections.unmodifiableSet(set))) {
            for(Item item : set) {
                HANDLER.getArtifactTracker(((IArtifactItem)item).getArtifactType()).checkContainerAndUpdate(container);
            }
        }
        for(Item item : set) {
            HANDLER.getArtifactTracker(((IArtifactItem)item).getArtifactType()).checkPlayerAndUpdate(player);
        }
    }
    @SubscribeEvent
    public void onPlayerDropItem(ItemTossEvent event) {
        if (event.getEntityItem().getItem().getItem() instanceof IArtifactItem artifactItem) {
            HANDLER.clearPossession((artifactItem).getArtifactType());
        }
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
