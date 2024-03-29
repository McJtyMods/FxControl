package baubles.api.inv;

import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class BaublesInventoryWrapper implements IInventory {
	
	final IBaublesItemHandler handler;	

	public BaublesInventoryWrapper(IBaublesItemHandler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public String getName() {
		return "BaublesInventory";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(this.getName());
	}

	@Override
	public int getSizeInventory() {
		return handler.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return handler.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return handler.extractItem(index, count, false);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack out = this.getStackInSlot(index);
		handler.setStackInSlot(index, null);
		return out;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		handler.setStackInSlot(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return true;
	}

	@Override
	public void openInventory(PlayerEntity player) {	}

	@Override
	public void closeInventory(PlayerEntity player) {	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return handler.isItemValidForSlot(index, stack, null);
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {	
		for (int i = 0; i < this.getSizeInventory(); ++i)
        {
			this.setInventorySlotContents(i, null);
        }
	}
}
