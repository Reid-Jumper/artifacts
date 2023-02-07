package com.reed.artifacts.util;

import com.reed.artifacts.ArtifactsMod;
import com.reed.artifacts.init.ItemInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum AncientArtifactsMaterial implements ArmorMaterial {
    ANCIENT_ARTIFACT("ancient_artifacts", 200, new int[]{7, 15, 20, 8}, 50, SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0F, 0.1F, () -> {
        return Ingredient.of(ItemInit.FORGOTTEN_HELM.get());
    }),
    ANCIENT_ARTIFACT_BOOTS("ancient_artifacts_boots", 200, new int[]{7, 15, 20, 8}, 50, SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0F, 0.5F, () -> {
        return Ingredient.of(ItemInit.FORGOTTEN_BOOTS.get());
    });

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    AncientArtifactsMaterial(String name, int durability, int[] protection, int enchantability, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durability;
        this.slotProtections = protection;
        this.enchantmentValue = enchantability;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyLoadedValue<Ingredient>(repairIngredient);
    }

    public int getDurabilityForSlot(EquipmentSlot slot) {
        return HEALTH_PER_SLOT[slot.getIndex()] * this.durabilityMultiplier;
    }
    public int getDefenseForSlot(EquipmentSlot slot) {
        return this.slotProtections[slot.getIndex()];
    }
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }
    public SoundEvent getEquipSound() {
        return this.sound;
    }
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
    public String getName() {
        return ArtifactsMod.MOD_ID + ":" + this.name;
    }
    public float getToughness() {
        return this.toughness;
    }
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
