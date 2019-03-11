package mcjty.fxcontrol.commands;

import mcjty.fxcontrol.ForgeEventHandlers;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CmdDebug extends CommandBase {
    @Override
    public String getName() {
        return "fctrldebug";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "fctrldebug";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        ForgeEventHandlers.debug = !ForgeEventHandlers.debug;
        if (ForgeEventHandlers.debug) {
            sender.sendMessage(new TextComponentString("Enabled FxControl debug mode"));
        } else {
            sender.sendMessage(new TextComponentString("Disabled FxControl debug mode"));
        }
    }
}
