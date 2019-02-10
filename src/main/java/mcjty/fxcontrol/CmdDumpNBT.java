package mcjty.fxcontrol;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

public class CmdDumpNBT extends CommandBase {
    @Override
    public String getName() {
        return "fctrldump";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "fctrldump";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
            Item item = heldItem.getItem();
            sender.sendMessage(new TextComponentString(TextFormatting.GOLD + item.getRegistryName().toString() + " Damage " + heldItem.getItemDamage()));
            NBTTagCompound nbt = heldItem.getTagCompound();
            if (nbt != null) {
                dumpNBT(sender, 2, nbt);
            }
        } else {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "This can only be done for a player!"));
        }
    }

    private static void dumpNBT(ICommandSender sender, int indent, NBTTagCompound nbt) {
        for (String key : nbt.getKeySet()) {
            NBTBase base = nbt.getTag(key);
            byte id = base.getId();
            switch (id) {
                case Constants.NBT.TAG_INT:
                    sender.sendMessage(new TextComponentString(StringUtils.repeat(' ', indent) + "(Int) " + key + " = " + nbt.getInteger(key)));
                    break;
                case Constants.NBT.TAG_LONG:
                    sender.sendMessage(new TextComponentString(StringUtils.repeat(' ', indent) + "(Long) " + key + " = " + nbt.getLong(key)));
                    break;
                case Constants.NBT.TAG_DOUBLE:
                    sender.sendMessage(new TextComponentString(StringUtils.repeat(' ', indent) + "(Double) " + key + " = " + nbt.getDouble(key)));
                    break;
                case Constants.NBT.TAG_FLOAT:
                    sender.sendMessage(new TextComponentString(StringUtils.repeat(' ', indent) + "(Float) " + key + " = " + nbt.getFloat(key)));
                    break;
                case Constants.NBT.TAG_STRING:
                    sender.sendMessage(new TextComponentString(StringUtils.repeat(' ', indent) + "(String) " + key + " = " + nbt.getString(key)));
                    break;
                case Constants.NBT.TAG_BYTE:
                    sender.sendMessage(new TextComponentString(StringUtils.repeat(' ', indent) + "(Byte) " + key + " = " + nbt.getByte(key)));
                    break;
                case Constants.NBT.TAG_SHORT:
                    sender.sendMessage(new TextComponentString(StringUtils.repeat(' ', indent) + "(Short) " + key + " = " + nbt.getShort(key)));
                    break;
                case Constants.NBT.TAG_LIST:
                    sender.sendMessage(new TextComponentString(StringUtils.repeat(' ', indent) + "(List) " + key));
                    NBTBase b = nbt.getTag(key);
                    if (((NBTTagList)b).getTagType() == Constants.NBT.TAG_COMPOUND) {
                        NBTTagList list = nbt.getTagList(key, Constants.NBT.TAG_COMPOUND);
                        int idx = 0;
                        for (NBTBase bs : list) {
                            sender.sendMessage(new TextComponentString(TextFormatting.YELLOW + StringUtils.repeat(' ', indent+2) + "Index " + idx)); idx++;
                            dumpNBT(sender, indent + 4, (NBTTagCompound) bs);
                        }
                    }
                    break;
                case Constants.NBT.TAG_COMPOUND:
                    sender.sendMessage(new TextComponentString(StringUtils.repeat(' ', indent) + "(NBT) " + key));
                    dumpNBT(sender, indent + 2, nbt.getCompoundTag(key));
                    break;
                default:
                    sender.sendMessage(new TextComponentString(StringUtils.repeat(' ', indent) + "(?) " + key));
                    break;
            }
        }
    }
}
