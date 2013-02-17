package org.zzl.minegaming.Drill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
		if(id==0)
		{
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity instanceof TileEntityDrill){
				return new ContainerDrill(player.inventory, (TileEntityDrill) tileEntity);
			}

		}



		return null;
	}
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z)
	{
		if(id==0)
		{
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity instanceof TileEntityDrill){
				return new GuiDrill(player.inventory, (TileEntityDrill) tileEntity);
			}

		}

		return null;
	}


}
