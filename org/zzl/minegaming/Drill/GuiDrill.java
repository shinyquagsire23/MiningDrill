package org.zzl.minegaming.Drill;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiDrill extends GuiContainer
{

	private boolean mouseWasDown;
    public GuiDrill(InventoryPlayer inventoryplayer, TileEntityDrill tileentitydrill)
    {
        super(new ContainerDrill(inventoryplayer, tileentitydrill));
        tile = tileentitydrill;
        direction = tile.Direction;
    }

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString("Hopper", 8, 6, 0x404040);
        fontRenderer.drawString("Fuel", 77, 20, 0x404040);
        fontRenderer.drawString("Direction", 115, 6, 0x404040);
        //fontRenderer.drawString("Pickaxe", 70, 40, 0x404040);
        fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }
    
    public void drawScreen(int mouseX, int mouseY, float speed)
    {
	    World world = tile.worldObj;
	    int x = tile.xCoord;
	    int y = tile.yCoord;
	    int z = tile.zCoord;
	      
	    tile = (TileEntityDrill)world.getBlockTileEntity(x, y, z);
	    
    	super.drawScreen(mouseX, mouseY, speed);
    	if(Mouse.isButtonDown(0) && (!this.mouseWasDown))
    	{
    	      int q = this.width - this.xSize >> 1;
    	      int s = this.height - this.ySize >> 1;
    	      mouseX -= q;
    	      mouseY -= s;
    	      
    	      direction = tile.Direction;
    	      byte olddirection = direction;
    	      if ((mouseX > 114) && (mouseY > 38) && (mouseX <= 124) && (mouseY <= 48))
    	    	  direction = 0; //Left
    	      if ((mouseX > 134) && (mouseY > 38) && (mouseX <= 144) && (mouseY <= 48))
    	    	  direction = 1; //Right
    	      if ((mouseX > 124) && (mouseY > 48) && (mouseX <= 134) && (mouseY <= 58))
    	    	  direction = 2; //Up
    	      if ((mouseX > 124) && (mouseY > 28) && (mouseX <= 134) && (mouseY <= 38))
    	    	  direction = 3; //Down
    	      if ((mouseX > 154) && (mouseY > 38) && (mouseX <= 164) && (mouseY <= 48))
    	    	  direction = 4; //Vertical Up
    	      if ((mouseX > 154) && (mouseY > 48) && (mouseX <= 164) && (mouseY <= 58))
    	    	  direction = 5; //Vertical Down
    	      if(olddirection != direction)
    	      {
    	    	  tile.BlockHit = false;
    	    	  tile.BlockCount = 0;
    	    	  tile.TotalBlockCount = 0;
    	      }
    	      TileEntityDrill newentity = (TileEntityDrill)world.getBlockTileEntity(x, y, z);
    	      newentity.Direction = direction;
    	      world.setBlockTileEntity(x, y, z, newentity);
    	      tile = newentity;
    	      this.mouseWasDown = true;
        } 
    	else if (!Mouse.isButtonDown(0)) 
    	{
          this.mouseWasDown = false;
        }

    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        int i1 = mc.renderEngine.getTexture("/org/zzl/minegaming/Drill/gui/drill.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i1);
	      int x = this.width - this.xSize >> 1;
	      int y = this.height - this.ySize >> 1;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        //drawTexturedModalRect(x + 100, y + 20, 186, 0, 10, 10); //Enable Toggle
        drawTexturedModalRect(x + 114, y + 38, direction != 0 ? 186 : 176, 0, 10, 10); //Left
        drawTexturedModalRect(x + 134, y + 38, direction != 1 ? 186 : 176, 0, 10, 10); //Right
        drawTexturedModalRect(x + 124, y + 48, direction != 2 ? 186 : 176, 0, 10, 10); //Up
        drawTexturedModalRect(x + 124, y + 28, direction != 3 ? 186 : 176, 0, 10, 10); //Down
        
        drawTexturedModalRect(x + 124, y + 38, 196, 0, 10, 10); //Block Image
        
        drawTexturedModalRect(x + 154, y + 38, direction != 4 ? 186 : 176, 0, 10, 10); //Vertical Up
        drawTexturedModalRect(x + 154, y + 48, direction != 5 ? 186 : 176, 0, 10, 10); //Vertical Down
    }
    
    public TileEntityDrill tile;
    public byte direction;
}
