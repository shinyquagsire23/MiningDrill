package org.zzl.minegaming.Drill;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLimited extends Slot 
{
	public SlotLimited(IInventory iinventory, int i, int j, int k, int ItemIDRestricted)
	{
		super(iinventory,i,j,k);
		restricted = ItemIDRestricted;
	}
	
	public SlotLimited(IInventory iinventory, int i, int j, int k, int[] ItemIDRestricted)
	{
		super(iinventory,i,j,k);
		restrictedarray = ItemIDRestricted;
		multiple = true;
	}
	
	public boolean isItemValid(ItemStack itemstack)
    {
		if(!multiple)
		{
		if(itemstack.getItem().itemID == restricted)
			return true;
		else
			return false;
		}
		else
		{
			boolean result = false;
			for(int i = 0; i < restrictedarray.length; i++)
			{
				if(itemstack.getItem().itemID == restrictedarray[i])
					result = true;
			}
			return result;
		}
    }
	
	private int restricted;
	private int[] restrictedarray;
	private boolean multiple = false;
}
