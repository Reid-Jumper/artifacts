package com.reed.artifacts;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

public class TestCommand {
    static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("testsummon")
                .requires(cs->cs.hasPermission(4))
                .executes((ctx) -> execute(ctx.getSource().getLevel()));
    }

    private static int execute(ServerLevel dim) throws CommandSyntaxException {
        Vec3 spawnpos = new Vec3(0.0, 70.0, 0.0);
        CompoundTag tag;
            tag = new CompoundTag();
            tag.putString("nbt", "{Item:{id:\"minecraft:iron_ingot\",Count:1}}");
            tag.putString("id", "minecraft:item");



        Entity entity = EntityType.loadEntityRecursive(tag, dim, (p_138828_) -> {
            p_138828_.moveTo(spawnpos.x, spawnpos.y, spawnpos.z, p_138828_.getYRot(), p_138828_.getXRot());
            return p_138828_;
        });

        return 0;
    }
}
