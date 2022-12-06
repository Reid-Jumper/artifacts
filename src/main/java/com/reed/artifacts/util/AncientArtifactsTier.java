package com.reed.artifacts.util;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum AncientArtifactsTier implements Tier {
    ANCIENT_ARTIFACT(4, 3000, 10.0F, 5.0F, 5, () -> {
        return null;
    });

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    AncientArtifactsTier(int level, int durability, float miningSpeed, float damage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.uses = durability;
        this.speed = miningSpeed;
        this.damage = damage;
        this.enchantmentValue = enchantability;
        this.repairIngredient = new LazyLoadedValue<Ingredient>(repairIngredient);
    }

    public int getUses() {
        return this.uses;
    }
    public float getSpeed() {
        return this.speed;
    }
    public float getAttackDamageBonus() {
        return this.damage;
    }
    public int getLevel() {
        return this.level;
    }
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
