package org.zzl.minegaming.Drill;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

import cpw.mods.fml.common.network.FMLNetworkHandler;

import net.minecraft.item.*;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.*;
import net.minecraft.block.*;

public class BlockDrill extends BlockContainer
{

	protected BlockDrill(int i, int j, int k, int top, int bottom)
	{
		super(i, Material.rock);
		inputoutput[i] = this;
		random = new Random();
		blockIndexInTexture = 0;
		//this.setTickRandomly(true);
		this.drillID = k;
		this.top = top;
		this.bottom = bottom;
	}

	@Override
	public TileEntity createNewTileEntity(World w)
	{
		return new TileEntityDrill();
	}

	public int getBlockTextureFromSide(int i)
	{
		if (i == 0)
			return bottom;
		if (i == 1)
			return top;
		else
			return blockIndexInTexture;
	}

	/*
	 * public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int
	 * k, int l) { if(i == 0) return bottom; if(i == 1) return top; else return
	 * blockIndexInTexture; }
	 */

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k,
			EntityPlayer player, int idk, float what, float these, float are)
	{
		onBlockClicked(world, i, j, k, player);
		return true;
	}

	public void onBlockAdded(World world, int i, int j, int k)
	{
		TileEntityDrill tile = new TileEntityDrill();
		tile.Direction = 5;
		world.setBlockTileEntity(i, j, k, tile);
	}

	public void onBlockClicked(World world, int i, int j, int k,
			EntityPlayer entityplayer)
	{
		TileEntityDrill tileentity = (TileEntityDrill) world
				.getBlockTileEntity(i, j, k);
		if (tileentity != null)
			FMLNetworkHandler.openGui(entityplayer, DrillMod.instance, 0, world, i, j, k);
	}

	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
	{
		removedBlock(world,i,j,k);
		super.onBlockDestroyedByPlayer(world, i, j, k, l);
	}
	
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k, int l)
	{
		removedBlock(world,i,j,k);
		super.onBlockDestroyedByPlayer(world, i, j, k, l);
	}
	
	public void removedBlock(World world, int i, int j, int k)
	{
		TileEntityDrill tileentitychest = (TileEntityDrill) world
				.getBlockTileEntity(i, j, k);
		if(tileentitychest == null)
			return;
		label0: for (int l = 0; l < tileentitychest.getSizeInventory(); l++)
		{
			ItemStack itemstack = tileentitychest.getStackInSlot(l);
			if (itemstack == null)
			{
				continue;
			}
			float f = random.nextFloat() * 0.8F + 0.1F;
			float f1 = random.nextFloat() * 0.8F + 0.1F;
			float f2 = random.nextFloat() * 0.8F + 0.1F;
			do
			{
				if (itemstack.stackSize <= 0)
				{
					continue label0;
				}
				int i1 = random.nextInt(21) + 10;
				if (i1 > itemstack.stackSize)
				{
					i1 = itemstack.stackSize;
				}
				itemstack.stackSize -= i1;
				EntityItem entityitem = new EntityItem(world, (float) i + f,
						(float) j + f1, (float) k + f2,
						new ItemStack(itemstack.itemID, i1, itemstack
								.getItemDamage()));
				float f3 = 0.05F;
				entityitem.motionX = (float) random.nextGaussian() * f3;
				entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float) random.nextGaussian() * f3;
				world.spawnEntityInWorld(entityitem);
			} while (true);
		}
	}

	public void randomDisplayTick(World world, int i, int j, int k,
			Random random)
	{
		TileEntityDrill tileentity = (TileEntityDrill) world
				.getBlockTileEntity(i, j, k);

		if (tileentity == null)
		{
			TileEntityDrill tile = new TileEntityDrill();
			tile.Direction = 5;
			world.setBlockTileEntity(i, j, k, tile);
		}
		if (tileentity.isEnabled == false)
		{
			if (tileentity.GetCoalAmount() > 0)
			{
				tileentity.TakeCoal();
				tileentity.isEnabled = true;
			}
		}

		boolean isAlive = tileentity.isEnabled;
		this.onNeighborBlockChange(world, i, j, k, 0);
		if (isAlive && tileentity.BlockHit == false)
		{
			try
			{
				int count = tileentity.BlockCount;
				int totalcount = tileentity.TotalBlockCount;

				if (count >= 15)
				{
					tileentity.isEnabled = false;
					tileentity.BlockCount = 0;
					if (tileentity.GetCoalAmount() == 0)
					{
						tileentity.isEnabled = false;
					}
					world.setBlockTileEntity(i, j, k, tileentity);
					return;
				}

				tileentity.BlockCount++;
				tileentity.TotalBlockCount++;
				count = tileentity.BlockCount;
				totalcount = tileentity.TotalBlockCount;

				int item = 1;
				if (tileentity.Direction == 0)
					item = world.getBlockId(i - totalcount, j, k);
				if (tileentity.Direction == 1)
					item = world.getBlockId(i + totalcount, j, k);
				if (tileentity.Direction == 2)
					item = world.getBlockId(i, j, k + totalcount);
				if (tileentity.Direction == 3)
					item = world.getBlockId(i, j, k - totalcount);
				if (tileentity.Direction == 4)
					item = world.getBlockId(i, j + totalcount, k);
				if (tileentity.Direction == 5)
					item = world.getBlockId(i, j - totalcount, k);

				float f = 0.7F;
				double d = (double) (world.rand.nextFloat() * f)
						+ (double) (1.0F - f) * 0.5D;
				double d1 = (double) (world.rand.nextFloat() * f)
						+ (double) (1.0F - f) * 0.20000000000000001D
						+ 0.59999999999999998D;
				double d2 = (double) (world.rand.nextFloat() * f)
						+ (double) (1.0F - f) * 0.5D;
				ItemStack itemstack = new ItemStack(Block.bedrock);
				boolean cannotmine = false;
				if (inputoutput[item] instanceof Block)
					itemstack = new ItemStack((Block) inputoutput[item], 1,
							world.getBlockMetadata(i, j, k));
				else if (inputoutput[item] instanceof Item)
				{
					int damage = 0;
					ItemStack tmpstack = new ItemStack((Item) inputoutput[item]);
					if (tmpstack.getItem().itemID == Item.dyePowder.itemID)
						damage = 4;
					itemstack = new ItemStack((Item) inputoutput[item],
							Block.blocksList[item].quantityDropped(random),
							damage);
				} else if (item == 0)
					cannotmine = false;
				else
					cannotmine = true;

				EntityItem entityitem;
				if (cannotmine == false)
				{
					if (item != 0)
					{
						if (tileentity.Direction == 0)
							world.setBlockWithNotify(i - totalcount, j, k, 0);
						if (tileentity.Direction == 1)
							world.setBlockWithNotify(i + totalcount, j, k, 0);
						if (tileentity.Direction == 2)
							world.setBlockWithNotify(i, j, k + totalcount, 0);
						if (tileentity.Direction == 3)
							world.setBlockWithNotify(i, j, k - totalcount, 0);
						if (tileentity.Direction == 4)
							world.setBlockWithNotify(i, j + totalcount, k,
									drillID);
						if (tileentity.Direction == 5)
							world.setBlockWithNotify(i, j - totalcount, k,
									drillID);

						if (!tileentity.InsertItemIntoHopper(itemstack))
						{
							entityitem = new EntityItem(world, (double) i + d,
									(double) j + d1, (double) k + d2, itemstack);
							entityitem.delayBeforeCanPickup = 10;
							world.spawnEntityInWorld(entityitem);
						}
						double d3 = 0.0625D;
						for (int l = 0; l < 6; l++)
						{
							double d4 = (float) i + random.nextFloat();
							double d5 = (float) j + random.nextFloat();
							double d6 = (float) k + random.nextFloat();
							if (l == 0 && !world.isBlockOpaqueCube(i, j + 1, k))
							{
								d5 = (double) (j + 1) + d;
							}
							if (l == 1 && !world.isBlockOpaqueCube(i, j - 1, k))
							{
								d5 = (double) (j + 0) - d;
							}
							if (l == 2 && !world.isBlockOpaqueCube(i, j, k + 1))
							{
								d6 = (double) (k + 1) + d;
							}
							if (l == 3 && !world.isBlockOpaqueCube(i, j, k - 1))
							{
								d6 = (double) (k + 0) - d;
							}
							if (l == 4 && !world.isBlockOpaqueCube(i + 1, j, k))
							{
								d4 = (double) (i + 1) + d;
							}
							if (l == 5 && !world.isBlockOpaqueCube(i - 1, j, k))
							{
								d4 = (double) (i + 0) - d;
							}
							if (d4 < (double) i || d4 > (double) (i + 1)
									|| d2 < 0.0D || d2 > (double) (j + 1)
									|| d3 < (double) k || d3 > (double) (k + 1))
							{
								int randy = random.nextInt(8);
								if (randy > 4)
									world.spawnParticle("smoke", d4, d5, d6,
											0.0D, 0.0D, 0.0D);
								if (randy <= 4)
									world.spawnParticle("fire", d4, d5, d6,
											0.0D, 0.0D, 0.0D);
							}
						}
					}
				} else
				{
					tileentity.BlockHit = true;
					if (tileentity.Direction == 5
							&& ((Number) inputoutput[world.getBlockId(i, j - 1,
									k)]).intValue() != 0)
						world.setBlock(i, j - 1, k, 1);
					entityitem = new EntityItem(world, (double) i + d,
							(double) j + d1, (double) k + d2, new ItemStack(
									Item.itemsList[263],
									tileentity.GetCoalAmount()));
					entityitem.delayBeforeCanPickup = 10;
					world.spawnEntityInWorld(entityitem);
					world.setBlockWithNotify(i, j - 1, k, 1);
					tileentity.TakeAllCoal();
				}
			} catch (Error e)
			{
			}
		} else

			world.setBlockTileEntity(i, j, k, tileentity);
	}
	
	public static void RegisterBlock(int blockID, Object output)
	{
		if(blockID < 256)
			inputoutput[blockID] = output;
	}

	private Random random;
	private int drillID;
	public int top;
	public int bottom;

	public static Object[] inputoutput = { -1, Block.cobblestone, Block.dirt,
			Block.dirt, Block.cobblestone, Block.planks, Block.sapling, 0, 0,
			0, 0, 0, Block.sand, Block.gravel, Block.oreGold, Block.oreIron,
			Item.coal, Block.wood, 0, Block.sponge, 0, Item.dyePowder,
			Block.blockLapis, Block.dispenser, Block.sandStone,
			Block.music, Item.bed, 0, 0, 0, 0, 0, 0, 0, 0, 0, Block.cloth,
			Block.plantRed, Block.plantYellow, Block.mushroomBrown,
			Block.mushroomRed, Block.blockGold, Block.blockSteel,
			Block.wood, Block.wood, Block.brick, Block.tnt, 0,
			Block.cobblestoneMossy, 0, Block.torchWood, 0, 0,
			Block.stairCompactPlanks, 0, Item.redstone, Item.diamond,
			Block.blockDiamond, Block.workbench, 0, 0, Block.stoneOvenIdle,
			Block.stoneOvenIdle, Item.sign, Item.doorWood, Block.ladder,
			Block.rail, Block.stairCompactCobblestone, Item.sign,
			Block.lever, Block.pressurePlateStone, Item.doorSteel,
			Block.pressurePlatePlanks, Item.redstone, Item.redstone,
			Block.torchRedstoneActive, Block.torchRedstoneActive, Block.stone,
			0, 0, Block.blockSnow, Block.cactus, Item.clay, Item.reed,
			Block.jukebox, Block.fence, Block.pumpkin, Block.netherrack,
			Block.slowSand, Item.lightStoneDust, 0, Block.pumpkinLantern, 0,
			Block.redstoneRepeaterIdle, Block.redstoneRepeaterIdle, 0, 0, 0, 0,
			Block.trapdoor, Block.cobblestone, Block.cobblestone, Block.stoneBrick, 0, 
			0, 0, Block.fenceIron, 0, Item.melon, 0, 0, 0, 0, Block.stairsBrick, 
			Block.stairsStoneBrickSmooth, 0, 0, Block.netherBrick, Block.netherFence, 
			Block.stairsNetherBrick, 0, 0, 0, 0, 0, 0, Block.whiteStone, Block.dragonEgg, 
			Block.redstoneLampIdle, Block.redstoneLampIdle, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	
	@Override
    public String getTextureFile () {
            return CommonProxy.BLOCK_PNG;
    }
}
