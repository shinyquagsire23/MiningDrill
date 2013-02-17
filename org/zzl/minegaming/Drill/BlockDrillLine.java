package org.zzl.minegaming.Drill;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.block.*;

public class BlockDrillLine extends Block
{

    protected BlockDrillLine(int i, int j)
    {
        super(i, Material.plants);
        blockIndexInTexture = j;
        //setTickOnLoad(true);
        float f = 0.2F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3F, 0.5F + f);
    }
    
    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        return canThisPlantGrowOnThisBlockID(world.getBlockId(i, j + 1, k));
    }

    protected boolean canThisPlantGrowOnThisBlockID(int i)
    {
        return (i == this.blockID || i == DrillMod.GetIDs()[0]);
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        super.onNeighborBlockChange(world, i, j, k, l);
        func_268_h(world, i, j, k);
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        func_268_h(world, i, j, k);
    }

    protected final void func_268_h(World world, int i, int j, int k)
    {
        if(!canBlockStay(world, i, j, k))
        {
            //dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    public boolean canBlockStay(World world, int i, int j, int k)
    {
        return canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return 1;
    }
    
	@Override
    public String getTextureFile () {
            return CommonProxy.BLOCK_PNG;
    }
}
