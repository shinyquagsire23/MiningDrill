package org.zzl.minegaming.Drill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class TileEntityDrill extends TileEntity implements IInventory
{

    public TileEntityDrill()
    {
    	contents = new ItemStack[12];
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Enabled", isEnabled);
        nbttagcompound.setInteger("TotalBlockCount", TotalBlockCount);
        nbttagcompound.setInteger("BlockCount", BlockCount);
        nbttagcompound.setBoolean("BlockHit", BlockHit);
        nbttagcompound.setByte("Direction", Direction);
        
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < contents.length; i++)
        {
            if(contents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                contents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        isEnabled = nbttagcompound.getBoolean("Enabled");
        BlockCount = nbttagcompound.getInteger("BlockCount");
        TotalBlockCount = nbttagcompound.getInteger("TotalBlockCount");
        BlockHit = nbttagcompound.getBoolean("BlockHit");
        Direction = nbttagcompound.getByte("Direction");
        
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        contents = new ItemStack[getSizeInventory()];
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;
            if(j >= 0 && j < contents.length)
            {
                contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }
    
    public int GetCoalAmount()
    {
    	if(contents[10] != null)
    	{
    	int i = contents[10].stackSize;
    	return contents[10].stackSize;
    	}
    	else
    		 return 0;
    }
    
    public void TakeCoal()
    {
    	if(contents[10] != null)
    	{
    		if(contents[10].stackSize != 1)
    			contents[10].stackSize -= 1;
    		else
    			contents[10] = null;
    	}
    }
    
    public void TakeCoal(int amount)
    {
    	if(contents[10] != null)
    	{
    		if(contents[10].stackSize != amount)
    			contents[10].stackSize -= amount;
    		else
    			contents[10] = null;
    	}
    }
    
    public void TakeAllCoal()
    {
    	if(contents[10] != null)
    	{
    			contents[10] = null;
    	}
    }
    
	@Override
	public int getSizeInventory() {
		return 11;
	}

	@Override
    public ItemStack getStackInSlot(int par1)
    {
        return this.contents[par1];
    }

	@Override
	public ItemStack decrStackSize(int i, int j)
    {
        if(contents[i] != null)
        {
            if(contents[i].stackSize <= j)
            {
                ItemStack itemstack = contents[i];
                contents[i] = null;
                onInventoryChanged();
                return itemstack;
            }
            ItemStack itemstack1 = contents[i].splitStack(j);
            if(contents[i].stackSize == 0)
            {
                contents[i] = null;
            }
            onInventoryChanged();
            return itemstack1;
        } else
        {
            return null;
        }
    }

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		contents[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
        onInventoryChanged();
		
	}

	@Override
	public String getInvName() 
	{
		return "Drill";
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) 
	{
		if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
        {
            return false;
        }
        return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
	}
	
	public boolean InsertItemIntoHopper(ItemStack item)
	{
		boolean isAdded = false;
		for(int i = 0; i < 10; i++)
		{
			if(getStackInSlot(i) != null && getStackInSlot(i).stackSize != item.getMaxStackSize())
			{
			if(getStackInSlot(i).itemID == item.itemID && item.stackSize + getStackInSlot(i).stackSize <= item.getMaxStackSize() && isAdded == false)
			{
				ItemStack newstack = getStackInSlot(i);
				newstack.stackSize += item.stackSize;
				setInventorySlotContents(i, newstack);
				isAdded = true;
			}
			if(getStackInSlot(i).itemID == item.itemID && item.stackSize + getStackInSlot(i).stackSize > item.getMaxStackSize() && isAdded == false)
			{
				ItemStack newstack = getStackInSlot(i);
				ItemStack splitstack = newstack;
				newstack.stackSize = item.getMaxStackSize();
				splitstack.stackSize = (item.stackSize + getStackInSlot(i).stackSize) - item.getMaxStackSize();
				int newslot = FindEmptySlot();
				if(newslot != -1 && newslot != i)
					setInventorySlotContents(newslot, splitstack);
				isAdded = true;
			}
			}
		}
		if(isAdded == false)
		{
				int newslot = FindEmptySlot();
				if(newslot != -1)
				{
					setInventorySlotContents(newslot, item);
					isAdded = true;
				}
				
		}
		return isAdded;
	}
	
	public int FindEmptySlot()
	{
		int result = -1;
		for(int i = 10; i >= 0; i--)
		{
			if(getStackInSlot(i) == null)
				result = i;
		}
		return result;
	}
	
	private ItemStack[] contents;
    public int TotalBlockCount;
    public int BlockCount;
    public boolean BlockHit = false;
    public boolean isEnabled;
    public byte Direction;
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}
}
