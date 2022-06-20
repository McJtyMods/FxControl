package mcjty.fxcontrol.commands;

public class CmdDumpBlockNBT {} /* @todo 1.15 extends CommandBase {
    @Override
    public String getName() {
        return "fctrldumpblock";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "fctrldumpblock";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) sender;
            RayTraceResult result = LookAtTools.getMovingObjectPositionFromPlayer(player.getEntityWorld(), player, false);
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockState state = player.getEntityWorld().getBlockState(result.getBlockPos());
                sender.sendSystemMessage(new StringTextComponent(TextFormatting.GOLD + state.getBlock().getRegistryName().toString()));
                for (IProperty<?> key : state.getPropertyKeys()) {
                    String value = state.getValue(key).toString();
                    sender.sendSystemMessage(new StringTextComponent("    " + key.getName() + " = " + value));
                }
            }
        } else {
            sender.sendSystemMessage(new StringTextComponent(TextFormatting.RED + "This can only be done for a player!"));
        }
    }


    private static void dumpNBT(ICommandSender sender, int indent, CompoundNBT nbt) {
        for (String key : nbt.getKeySet()) {
            NBTBase base = nbt.getTag(key);
            byte id = base.getId();
            switch (id) {
                case Constants.NBT.TAG_INT:
                    sender.sendSystemMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(Int) " + key + " = " + nbt.getInteger(key)));
                    break;
                case Constants.NBT.TAG_LONG:
                    sender.sendSystemMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(Long) " + key + " = " + nbt.getLong(key)));
                    break;
                case Constants.NBT.TAG_DOUBLE:
                    sender.sendSystemMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(Double) " + key + " = " + nbt.getDouble(key)));
                    break;
                case Constants.NBT.TAG_FLOAT:
                    sender.sendSystemMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(Float) " + key + " = " + nbt.getFloat(key)));
                    break;
                case Constants.NBT.TAG_STRING:
                    sender.sendSystemMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(String) " + key + " = " + nbt.getString(key)));
                    break;
                case Constants.NBT.TAG_BYTE:
                    sender.sendSystemMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(Byte) " + key + " = " + nbt.getByte(key)));
                    break;
                case Constants.NBT.TAG_SHORT:
                    sender.sendSystemMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(Short) " + key + " = " + nbt.getShort(key)));
                    break;
                case Constants.NBT.TAG_LIST:
                    sender.sendSystemMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(List) " + key));
                    NBTBase b = nbt.getTag(key);
                    if (((NBTTagList)b).getTagType() == Constants.NBT.TAG_COMPOUND) {
                        NBTTagList list = nbt.getTagList(key, Constants.NBT.TAG_COMPOUND);
                        int idx = 0;
                        for (NBTBase bs : list) {
                            sender.sendSystemMessage(new StringTextComponent(TextFormatting.YELLOW + StringUtils.repeat(' ', indent+2) + "Index " + idx)); idx++;
                            dumpNBT(sender, indent + 4, (CompoundNBT) bs);
                        }
                    }
                    break;
                case Constants.NBT.TAG_COMPOUND:
                    sender.sendSystemMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(NBT) " + key));
                    dumpNBT(sender, indent + 2, nbt.getCompoundTag(key));
                    break;
                default:
                    sender.sendSystemMessage(new StringTextComponent(StringUtils.repeat(' ', indent) + "(?) " + key));
                    break;
            }
        }
    }
}
*/