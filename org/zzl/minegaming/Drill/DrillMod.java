package org.zzl.minegaming.Drill;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.*;

@Mod(modid="MineDrill", name="Mining Drill", version="0.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
//@NetworkMod(channels = { "DrillMod" }, versionBounds = "0.0.0,)", clientSideRequired = true, serverSideRequired = false, packetHandler =ServerPacketHandler.class)
public class DrillMod {

		static Block drill;
		static Block drilldown;
		public static StepSound soundStoneFootstep = new StepSound("stone", 1.0F, 1.0F);
		
        // The instance of your mod that Forge uses.
        @Instance("MineDrill")
        public static DrillMod instance;
        
        // Says where the client and server 'proxy' code is loaded.
        @SidedProxy(clientSide="org.zzl.minegaming.Drill.ClientProxy", serverSide="org.zzl.minegaming.Drill.CommonProxy")
        public static CommonProxy proxy;
        
        @PreInit
        public void preInit(FMLPreInitializationEvent event) {
                // Stub Method
        }
        
        @Init
        public void load(FMLInitializationEvent event) {
                proxy.registerRenderers();
                int[] ids = GetIDs();
            	drill = (new BlockDrill(ids[0], 2, ids[1], 1, 2)).setHardness(3.5F).setStepSound(soundStoneFootstep).setBlockName("drill").setCreativeTab(CreativeTabs.tabRedstone);
            	drilldown = (new BlockDrillLine(ids[1], 4).setResistance(-1.0F).setStepSound(soundStoneFootstep).setBlockName("drilldown").setCreativeTab(CreativeTabs.tabRedstone));
            	GameRegistry.registerBlock(drill);
            	GameRegistry.registerBlock(drilldown);
            	GameRegistry.registerTileEntity(TileEntityDrill.class, "tileEntityDrill");
            	LanguageRegistry.addName(drill, "Mining Drill");
            	LanguageRegistry.addName(drilldown, "You probably shouldn't have this...");
            	NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
        }
        
        @PostInit
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        }
        
        public static int[] GetIDs()
        {
        	int[] ids = new int[2];
        	try
            {
            ArrayList arraylist = loadConfigFile();
            for(int i = 0; i < arraylist.size(); i++)
            {
          	  String s = arraylist.get(i).toString();
          	  String[] parts = s.split(" ");
          	  int id = Integer.parseInt(parts[1]);
          	  if(i == 0)
          	  {
          		  ids[0] = id;
          		  System.out.println("Drill Block Loaded with ID " + id);
          	  }
          	  if(i == 1)
          	  {
          		  ids[1] = id;
          		System.out.println("Drill Bar Loaded with ID " + id);
          	  }
            }
            
            }
            catch(Error e)
            {
            	System.out.println("Error Reading File! Reverting to Default...");
            	ids[0] = 123;
            	ids[1] = 124;
            }
            return ids;
        }
        
        public static ArrayList loadConfigFile()
        {
            ArrayList arraylist = new ArrayList();
            arraylist.add("DrillBlock 174");
            arraylist.add("DrillBar 173");
            String s = getConfigPath();
            if(s == null || s == "")
            {
                return arraylist;
            }
            ArrayList arraylist1 = new ArrayList();
            try
            {
                BufferedReader bufferedreader = new BufferedReader(new FileReader(s));
                if(!bufferedreader.ready())
                {
                    return arraylist;
                }
                String s1;
                while((s1 = bufferedreader.readLine()) != null) 
                {
                    arraylist1.add(s1);
                }
                bufferedreader.close();
            }
            catch(IOException ioexception)
            {
                System.out.println(ioexception);
                return arraylist;
            }
            return arraylist1;
        }

        private static String getConfigPath()
        {
            try
            {
                return (new StringBuilder()).append(Minecraft.getMinecraftDir().getCanonicalPath()).append("/MineDrill.properties").toString();
            }
            catch(IOException ioexception)
            {
                throw new RuntimeException("could not generate config path");
            }
        }
}
