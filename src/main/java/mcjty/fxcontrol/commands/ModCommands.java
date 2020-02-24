package mcjty.fxcontrol.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import mcjty.fxcontrol.FxControl;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> commands = dispatcher.register(
                Commands.literal(FxControl.MODID)
                        .then(CmdDebug.register(dispatcher))
                        .then(CmdReload.register(dispatcher))
        );

        dispatcher.register(Commands.literal("fctrl").redirect(commands));
    }

}
