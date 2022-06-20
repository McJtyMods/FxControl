package mcjty.fxcontrol.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mcjty.fxcontrol.ErrorHandler;
import mcjty.fxcontrol.RulesManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.Util;

public class CmdReload implements Command<CommandSourceStack> {

    private static final CmdReload CMD = new CmdReload();

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("reload")
                .requires(cs -> cs.hasPermission(1))
                .executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        ErrorHandler.clearErrors();
        if (player != null) {
            player.sendSystemMessage(Component.literal("Reloaded FxControl rules"));
            RulesManager.reloadRules();
        }
        return 0;
    }
}
