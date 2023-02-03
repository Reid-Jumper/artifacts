package com.reed.artifacts.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.reed.artifacts.ArtifactsModSaveData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.world.phys.Vec3;

public class MakeEndCommand {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher, ArtifactsModSaveData artifactsModSaveData) {
        commandDispatcher
                .register(Commands.literal("make_end")
                .requires((cmdDisp) -> {return cmdDisp.hasPermission(4);})
                .then(Commands.argument("location", Vec3Argument.vec3())
                .then(Commands.argument("size", DoubleArgumentType.doubleArg(0))
                .executes((cmd) -> {
                    Vec3 pos = Vec3Argument.getVec3(cmd, "location");
                    double size =  DoubleArgumentType.getDouble(cmd, "size");
                    //Component component1 = new TranslatableComponent("chat.type.announcement", cmd.getSource().getDisplayName(), new TextComponent(pos.x + " " + pos.y + " " + pos.z + " -> " + size));
                    //cmd.getSource().getServer().getPlayerList().broadcastMessage(component1, ChatType.SYSTEM, Util.NIL_UUID);
                    artifactsModSaveData.setEndPosition(pos);
                    artifactsModSaveData.setEndSize(size);
                    return 1;
                }))));
    }
}
