package cofh.tabularasa;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;

@Mod(modid = TabulaRasa.modId, name = TabulaRasa.modName, version = TabulaRasa.version, dependencies = TabulaRasa.dependencies)
public class TabulaRasa {

	public static final String modId = "TabulaRasa";
	public static final String modName = "Tabula Rasa";
	public static final String version = "1.7.10R1.1.0";
	public static final String dependencies = "required-after:FML@[7.10.0.1151, 7.11);required-after:Forge@[10.13.0.1151, 10.14)";

	@Instance("TabulaRasa")
	public static TabulaRasa instance;

	static File configDir;
	Configuration config;

	public static final CreativeTabs tab = new TRCreativeTab();

	public static ArrayList<Block> blocks = new ArrayList<Block>();
	public static ArrayList<Item> items = new ArrayList<Item>();
	public static ArrayList<Fluid> fluids = new ArrayList<Fluid>();

	public static ArrayList<String> stillIcons;
	public static ArrayList<String> flowingIcons;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		configDir = event.getModConfigurationDirectory();

		// config = new Configuration(configDir, "/cofh/tabularasa/common.cfg");
		//
		// numBlocks = config.getInt("BlockCount", "block.feature", 1, 0, 16, "The number of blocks that you would like to add.");
		// numItems = config.getInt("ItemCount", "item.feature", 1, 0, 16, "The number of items that you would like to add.");
		//
		// rasaBlocks = new BlockRasa[numBlocks];
		// rasaItems = new ItemRasa[numItems];
		//
		// for (int i = 0; i < numBlocks; i++) {
		// rasaBlocks[i] = new BlockRasa(Material.rock);
		//
		// for (int j = 0; j < 16; j++) {
		// rasaBlocks[i].names[j] = config.get("block.block" + i, "Name." + j, "BlockRasa").getString();
		// rasaBlocks[i].textures[j] = config.get("block.block" + i, "Texture." + j, "tabularasa:RasaBlock").getString();
		// rasaBlocks[i].hardness[j] = (float) config.get("block.block" + i, "Hardness." + j, 1.5D).getDouble(1.5D);
		// rasaBlocks[i].resistance[j] = (float) config.get("block.block" + i, "Resistance." + j, 10.0D).getDouble(10.0D);
		//
		// if (rasaBlocks[i].hardness[j] < 0) {
		// rasaBlocks[i].hardness[j] = -1.0F;
		// }
		// rasaBlocks[i].hardness[j] = MathHelper.clamp_float(rasaBlocks[i].hardness[j], -1.0F, 6000000.0F);
		// rasaBlocks[i].resistance[j] = MathHelper.clamp_float(rasaBlocks[i].hardness[j], 0.0F, 6000000.0F);
		//
		// rasaBlocks[i].resistance[j] *= 3;
		// rasaBlocks[i].resistance[j] /= 5;
		// }
		// rasaBlocks[i].setCreativeTab(CreativeTabs.tabBlock);
		// GameRegistry.registerBlock(rasaBlocks[i], ItemBlockRasa.class, "RasaBlock" + i);
		// }
		//
		// // TODO: config for registering custom itemstacks, for minetweaker and such?
		//
		// for (int i = 0; i < numItems; i++) {
		// rasaItems[i] = new ItemRasa();
		// rasaItems[i].numItems = config.getInt("Count", "item.item" + i, 16, 1, 32000, "Number of contiguous metadata entries for this item.");
		// rasaItems[i].names = new String[rasaItems[i].numItems];
		// rasaItems[i].textures = new String[rasaItems[i].numItems];
		//
		// for (int j = 0; j < rasaItems[i].numItems; j++) {
		// rasaItems[i].names[j] = config.get("item.item" + i, "Name." + j, "ItemRasa").getString();
		// rasaItems[i].textures[j] = config.get("item.item" + i, "Texture." + j, "tabularasa:RasaItem").getString();
		// }
		// rasaItems[i].setCreativeTab(CreativeTabs.tabMisc);
		// GameRegistry.registerItem(rasaItems[i], "RasaItem" + i);
		// }

		TRParser.initialize();

		try {
			TRParser.parseTemplateFiles();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event) {

		// config.save();
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {

	}

}
