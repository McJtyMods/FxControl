package mcjty.fxcontrol.commands;

public class CmdDumpItemNBT {} /* @todo 1.15 extends CommandBase {
    @Override
    public String getName() {
        return "fctrldumpitem";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "fctrldumpitem";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) sender;
            ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
            Item item = heldItem.getItem();
            sender.sendMessage(new StringTextComponent(TextFormatting.GOLD + item.getRegistryName().toString() + " Damage " + heldItem.getItemDamage()));
            CompoundNBT nbt = heldItem.getTagCompound();
            if (nbt != null) {
                dumpNBT(sender, 2, nbt);
            }
        } else {
            sender.sendMessage(new StringTextComponent(TextFormatting.RED + "This can only be done for a player!"));
        }
    }

    private static void dumpNBT(ICommandSender sender, int indent, CompoundNBT nbt) {
        for (String key : nbt.getKeySet()) {
            NBTBase base = nbt.getTag(key);
            byte id = base.getId();
            switch (id) {
                case Constants.NBT.TAG_INT:
                    sender.sendMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(Int) " + key + " = " + nbt.getInteger(key)));
                    break;
                case Constants.NBT.TAG_LONG:
                    sender.sendMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(Long) " + key + " = " + nbt.getLong(key)));
                    break;
                case Constants.NBT.TAG_DOUBLE:
                    sender.sendMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(Double) " + key + " = " + nbt.getDouble(key)));
                    break;
                case Constants.NBT.TAG_FLOAT:
                    sender.sendMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(Float) " + key + " = " + nbt.getFloat(key)));
                    break;
                case Constants.NBT.TAG_STRING:
                    sender.sendMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(String) " + key + " = " + nbt.getString(key)));
                    break;
                case Constants.NBT.TAG_BYTE:
                    sender.sendMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(Byte) " + key + " = " + nbt.getByte(key)));
                    break;
                case Constants.NBT.TAG_SHORT:
                    sender.sendMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(Short) " + key + " = " + nbt.getShort(key)));
                    break;
                case Constants.NBT.TAG_LIST:
                    sender.sendMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(List) " + key));
                    NBTBase b = nbt.getTag(key);
                    if (((NBTTagList)b).getTagType() == Constants.NBT.TAG_COMPOUND) {
                        NBTTagList list = nbt.getTagList(key, Constants.NBT.TAG_COMPOUND);
                        int idx = 0;
                        for (NBTBase bs : list) {
                            sender.sendMessage(new StringTextComponent(TextFormatting.YELLOW + StringUtils.repeat(' ', indent+2) + "Index " + idx)); idx++;
                            dumpNBT(sender, indent + 4, (CompoundNBT) bs);
                        }
                    }
                    break;
                case Constants.NBT.TAG_COMPOUND:
                    sender.sendMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(NBT) " + key));
                    dumpNBT(sender, indent + 2, nbt.getCompoundTag(key));
                    break;
                default:
                    sender.sendMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(?) " + key));
                    break;
            }
        }
    }
}
*/