package org.zzl.minegaming.Drill;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {
    //public static String ITEMS_PNG = "/org/zzl/minegaming/Drill/items.png";
    public static String BLOCK_PNG = "/org/zzl/minegaming/Drill/blocks.png";
    
    // Client stuff
    public void registerRenderers() {
            // Nothing here as the server doesn't render graphics!
    }
    
  //returns an instance of the Container you made earlier
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world,
                    int x, int y, int z) {
    	TileEntity te = world.getBlockTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityDrill)
        {
            TileEntityDrill icte = (TileEntityDrill) te;
            return new ContainerDrill(player.inventory, (TileEntityDrill) icte);
        }
        else
        {
            return null;
        }
    }

    //returns an instance of the Gui you made earlier
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world,
                    int x, int y, int z) {
            return null;
    }
}
