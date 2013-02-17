package org.zzl.minegaming.Drill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ContainerDrill extends Container
{

    public ContainerDrill(IInventory iinventory, TileEntityDrill tileentitydispenser)
    {
        field_21149_a = tileentitydispenser;
        for(int i = 0; i < 3; i++)
        {
            for(int l = 0; l < 3; l++)
            {
                addSlotToContainer(new Slot(tileentitydispenser, l + i * 3, 7 + l * 18, 17 + i * 18));
                /*
                 * addSlot(new Slot(
                 * tileentitydispenser, - The Tile Entity, passed through GUI class
                 *  l + i * 3, - The number of the slot
                 *   61 + l * 18, - The X distance from the left
                 *    17 + i * 18)); - The Y distance from the top
                 *    
                 *    The X and Y distance INCLUDE the outside of the "Box"
                		*/
            }

        }
        
//addSlot(new Slot(tileentitydispenser, 10, 79, 35));
        addSlotToContainer(new SlotLimited(tileentitydispenser, 10, 79, 35, Item.coal.itemID));
        //addSlot(new SlotLimited(tileentitydispenser, 11, 79, 53, pickaxes));

        for(int j = 0; j < 3; j++)
        {
            for(int i1 = 0; i1 < 9; i1++)
            {
                addSlotToContainer(new Slot(iinventory, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18));
            }

        }

        for(int k = 0; k < 9; k++)
        {
            addSlotToContainer(new Slot(iinventory, k, 8 + k * 18, 142));
        }

    }
    
    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int par1)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(par1);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par1 < 9)
            {
                if (!mergeItemStack(itemstack1, 9, inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(itemstack1, 0, 9, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    public boolean isUsableByPlayer(EntityPlayer entityplayer)
    {
        return field_21149_a.isUseableByPlayer(entityplayer);
    }

    private TileEntityDrill field_21149_a;
    public int[] pickaxes = new int[] { Item.pickaxeWood.itemID,Item.pickaxeSteel.itemID,Item.pickaxeStone.itemID,Item.pickaxeGold.itemID,Item.pickaxeDiamond.itemID};
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		// TODO Auto-generated method stub
		return false;
	}
}
