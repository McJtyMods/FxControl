package mcjty.fxcontrol.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mcjty.fxcontrol.ErrorHandler;
import mcjty.fxcontrol.RulesManager;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;

public class CmdReload implements Command<CommandSource> {

    private static final CmdReload CMD = new CmdReload();

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("reload")
                .requires(cs -> cs.hasPermission(1))
                .executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ErrorHandler.clearErrors();
        if (player != null) {
            player.sendMessage(new StringTextComponent("Reloaded FxControl rules"), Util.NIL_UUID);
            RulesManager.reloadRules();
        }
        return 0;
    }
}
