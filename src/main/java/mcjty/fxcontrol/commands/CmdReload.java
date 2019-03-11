package mcjty.fxcontrol.commands;

import mcjty.fxcontrol.RulesManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CmdReload extends CommandBase {
    @Override
    public String getName() {
        return "fctrlreload";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "fctrlreload";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        sender.sendMessage(new TextComponentString("Reloaded FxControl rules"));
        RulesManager.reloadRules();
    }
}
