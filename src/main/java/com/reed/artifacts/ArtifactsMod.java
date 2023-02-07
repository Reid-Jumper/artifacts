package com.reed.artifacts;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import com.reed.artifacts.commands.MakeEndCommand;
import com.reed.artifacts.init.BlockInit;
import com.reed.artifacts.init.ItemInit;
import com.reed.artifacts.init.TileEntityInit;
import com.reed.artifacts.items.*;
import com.reed.artifacts.util.ArtifactType;
import com.reed.artifacts.util.CustomItemProperties;
import net.minecraft.commands.arguments.item.ItemPredicateArgument;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArtifactsMod.MOD_ID)
public class ArtifactsMod
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "artifacts";
    public static ArtifactHandler ARTIFACT_HANDLER;
    public ArtifactsModSaveData artifactsModSaveData;

    public static MinecraftServer SERVER;

    public static final Duration ARTIFACT_EXPIRY = Duration.ofDays(14);

    public ArtifactsMod() {

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);

        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        TileEntityInit.TILE_ENTITY_TYPES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        CustomItemProperties.addCustomItemProperties();
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
        SERVER = event.getServer();
        artifactsModSaveData = SERVER.overworld().getDataStorage().computeIfAbsent(ArtifactsModSaveData::load, ArtifactsModSaveData::create, ArtifactsModSaveData.ARTIFACTS_MOD_DATA);
        ARTIFACT_HANDLER = new ArtifactHandler(artifactsModSaveData);
        MakeEndCommand.register(SERVER.getCommands().getDispatcher(), artifactsModSaveData);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        // If a player with an artifact is gone for too long, clear possession and consider the artifact lost
        for(Map.Entry<String, Instant> entry : artifactsModSaveData.getLastSeenTimes().entrySet()) {
            if (Instant.now().minus(ARTIFACT_EXPIRY).isAfter(entry.getValue())) {
                for (ArtifactType artifactType : ArtifactType.values()) {
                    String lastEntityInPossession = ARTIFACT_HANDLER.getLastEntityInPossession(artifactType);
                    if (lastEntityInPossession != null && lastEntityInPossession.equals(entry.getKey())) {
                        ARTIFACT_HANDLER.clearPossession(artifactType);
                        ARTIFACT_HANDLER.clearArtifact(artifactType, true);
                        LOGGER.warn("Player " + lastEntityInPossession + " held artifact " + artifactType + " off server too long. Cleared possession...");
                    }
                }
            }
        }

        // Change Keep Inventory rule
        if (SERVER.overworld().isNight() && SERVER.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            SERVER.getPlayerList().broadcastMessage(new TextComponent("Night has fallen. The world grows more dangerous..."), ChatType.SYSTEM, Util.NIL_UUID);
            SERVER.getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).set(false, SERVER);
        } else if (!SERVER.overworld().isNight() && !SERVER.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            SERVER.getPlayerList().broadcastMessage(new TextComponent("Day has arrived. A weight has lifted..."), ChatType.SYSTEM, Util.NIL_UUID);
            SERVER.getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).set(true, SERVER);
            // Reset chestplate charges when it becomes day
            artifactsModSaveData.setChestplateFireResistanceCharged(true);
            artifactsModSaveData.setChestplateBreathCharged(true);
        }

        // Handle Helm and Leggings abilities
        SERVER.getPlayerList().getPlayers().forEach((player) -> {
            if(player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ForgottenHelm) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 399, 0, true, true));
            }
            if(player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ForgottenLeggings) {
                if(SERVER.overworld().isNight()) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 399, 0, true, true));
                } else {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 399, 0, true, true));
                    player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 399, 0, true, true));
                }
            }
        });

        // Check win condition
        if (!artifactsModSaveData.isGameWon()) {
            SERVER.getPlayerList().getPlayers().forEach((serverPlayer -> {
                if (ArtifactHandler.getPlayerArtifacts(serverPlayer).size() == 7) {

                    if (artifactsModSaveData.getEndPosition() != null && serverPlayer.level == SERVER.overworld()) {
                        double distance = (artifactsModSaveData.getEndPosition().subtract(serverPlayer.position())).horizontalDistance();
                        if (distance < (artifactsModSaveData).getEndSize()) {
                            artifactsModSaveData.setGameWon(true);
                            PlayerTeam playersTeam = SERVER.getScoreboard().getPlayersTeam(serverPlayer.getScoreboardName());
                            SERVER.getPlayerList().broadcastMessage(new TextComponent("The game is over. " + playersTeam.getName() + " has won!"), ChatType.SYSTEM, Util.NIL_UUID);
                        }
                    }
                }
            }));
        }
    }

    @SubscribeEvent
    public void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
        LOGGER.info("Hello from player login");

        Player player = ((Player)event.getEntityLiving());
        artifactsModSaveData.clearLastSeenTimeFor(player);
        List<ArtifactType> artifactTypes = ArtifactHandler.getPlayerArtifacts(player);
        if (!artifactTypes.isEmpty()) {
            for(ArtifactType artifactType : artifactTypes) {
                if (!ARTIFACT_HANDLER.hasPossession(player, artifactType)) {
                    try {
                        player.getInventory().clearOrCountMatchingItems(ItemPredicateArgument.itemPredicate().parse(new StringReader(artifactType.resourceLocation)).create(null), -1, player.inventoryMenu.getCraftSlots());
                        LOGGER.warn("Removed illegally possessed item " + artifactType.name() + " from player " + player.getScoreboardName());
                    } catch (CommandSyntaxException e) {
                        e.printStackTrace();
                    }
                    player.containerMenu.broadcastChanges();
                    player.inventoryMenu.slotsChanged(player.getInventory());
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityDamaged(LivingDamageEvent event) {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> handleChestplateAbilityOnEntityDamaged(event));
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> handleBootsAbilityOnEntityDamaged(event));
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> ARTIFACT_HANDLER.checkAllPossession(SERVER));
    }

    @SubscribeEvent
    public void onPlayerDestroyItem(PlayerDestroyItemEvent event) {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> ARTIFACT_HANDLER.checkAllPossession(SERVER));
    }

    @SubscribeEvent
    public void onPlayerDisconnect(final PlayerEvent.PlayerLoggedOutEvent event) {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> recordPlayerLastSeenInfoOnLogout(event));
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> removeIllegalArtifactsOnPlayerSpawn(event));
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> dropArtifactsAndUpdatePossessionOnPlayerDeath(event));
    }

    @SubscribeEvent
    public void onItemPickup(PlayerEvent.ItemPickupEvent event) {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> setPlayerArtifactPossessionOnItemPickup(event));
    }
    @SubscribeEvent
    public void onPlayerContainerEvent(PlayerContainerEvent.Close event) {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> setPlayerArtifactPossessionOnContainerEvent(event));
    }
    @SubscribeEvent
    public void onPlayerDropItem(ItemTossEvent event) {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> clearPlayerArtifactPossessionOnDrop(event));
    }

    @SubscribeEvent
    public void onEntityLeaveWorldEvent(EntityLeaveWorldEvent event) {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> checkEntityLeaveWorldForArtifacts(event));
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
    private void handleBootsAbilityOnEntityDamaged(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if(entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ForgottenBoots && !entity.getLevel().isClientSide()) {
            if(event.getSource().isFall()) {
                event.setCanceled(true);
            }
        }
    }

    private void handleChestplateAbilityOnEntityDamaged(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if(entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ForgottenChestplate && !entity.getLevel().isClientSide()) {
            if(event.getSource() instanceof EntityDamageSource) {
                event.getSource().getEntity().setSecondsOnFire(20);
            }else if(event.getSource().isFire() && artifactsModSaveData.isChestplateFireResistanceCharged()) {
                event.setCanceled(true);
                event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0, false, true));
                artifactsModSaveData.setChestplateFireResistanceCharged(false);
            }else if(event.getSource().equals(DamageSource.DROWN) && artifactsModSaveData.isChestplateBreathCharged()) {
                event.setCanceled(true);
                event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 6000, 0, false, true));
                artifactsModSaveData.setChestplateBreathCharged(false);
            }
        }
    }

    private void recordPlayerLastSeenInfoOnLogout(final PlayerEvent.PlayerLoggedOutEvent event) {
        artifactsModSaveData.setLastSeenTimeFor(event.getPlayer(), Instant.now());
    }

    private void removeIllegalArtifactsOnPlayerSpawn(PlayerEvent.PlayerRespawnEvent event) {
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

    private void dropArtifactsAndUpdatePossessionOnPlayerDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof Player player && SERVER.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            List<ArtifactType> artifactTypes = ArtifactHandler.getPlayerArtifacts(player);
            if (!artifactTypes.isEmpty()) {
                for(ArtifactType artifactType : artifactTypes) {
                    ItemStack artifactItem = new ItemStack(ArtifactType.getItemForArtifactType(artifactType));
                    player.drop(artifactItem, true, true);
                    ARTIFACT_HANDLER.clearPossession(artifactType);
                }
            }
        }
    }

    private void setPlayerArtifactPossessionOnItemPickup(PlayerEvent.ItemPickupEvent event) {
        if(event.getStack().getItem() instanceof IArtifactItem artifactItem) {
            ARTIFACT_HANDLER.changePossession(event.getPlayer(), artifactItem.getArtifactType());
            LOGGER.info(event.getPlayer().getScoreboardName() + " has picked up " + artifactItem.getArtifactType());
        }
    }

    private void setPlayerArtifactPossessionOnContainerEvent(PlayerContainerEvent.Close event) {
        Player player = event.getPlayer();
        for (ArtifactType artifactType : ArtifactType.values()) {
            ARTIFACT_HANDLER.checkPlayerAndUpdatePossessionStatus(artifactType, player);
        }
    }

    private void clearPlayerArtifactPossessionOnDrop(ItemTossEvent event) {
        if (event.getEntityItem().getItem().getItem() instanceof IArtifactItem artifactItem) {
            ARTIFACT_HANDLER.clearPossession((artifactItem).getArtifactType());
        }
    }

    private void checkEntityLeaveWorldForArtifacts(EntityLeaveWorldEvent event) {
        LOGGER.info("checking entity that left world: " + event.getEntity().toString());
        if (event.getEntity() instanceof ItemEntity) {
            LOGGER.info("--Entity is an item");
            LOGGER.info(String.valueOf(event.getEntity().getRemovalReason()));
            if (((ItemEntity)event.getEntity()).getItem().getItem() instanceof IArtifactItem artifactItem && (event.getEntity().getRemovalReason() == Entity.RemovalReason.DISCARDED || event.getEntity().getRemovalReason() == Entity.RemovalReason.KILLED)) {
                LOGGER.info("--Clearing artifact of type " + artifactItem.getArtifactType());
                ARTIFACT_HANDLER.clearArtifact((artifactItem).getArtifactType(), false);
                LOGGER.info("--Cleared");
            }
        }
    }

}
