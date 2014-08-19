package tabularasa;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import java.io.File;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = "TabulaRasa", name = "Tabula Rasa", version = "1.7.10R1.0.1")
public class TabulaRasa {

	public static final String modId = "TabulaRasa";
	public static final String modName = "Tabula Rasa";
	public static final String version = "1.7.10R1.0.1";
	public static final String dependencies = "required-after:FML@[7.10.0.1151);required-after:Forge@[10.13.0.1151)";

	@Instance("TabulaRasa")
	public static TabulaRasa instance;

	Configuration config;

	public static BlockRasa[] rasaBlocks;
	public static ItemRasa[] rasaItems;

	public static int numBlocks;
	public static int numItems;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		config = new Configuration(new File(event.getModConfigurationDirectory(), "/cofh/TabulaRasa.cfg"));

		numBlocks = config.getInt("BlockCount", "block.feature", 1, 0, 16, "The number of blocks that you would like to add.");
		numItems = config.getInt("ItemCount", "item.feature", 1, 0, 16, "The number of items that you would like to add.");

		rasaBlocks = new BlockRasa[numBlocks];
		rasaItems = new ItemRasa[numItems];

		for (int i = 0; i < numBlocks; i++) {
			rasaBlocks[i] = new BlockRasa(Material.rock);

			for (int j = 0; j < 16; j++) {
				rasaBlocks[i].names[j] = config.get("block.block" + i, "Name." + j, "BlockRasa").getString();
				rasaBlocks[i].textures[j] = config.get("block.block" + i, "Texture." + j, "tabularasa:RasaBlock").getString();
				rasaBlocks[i].hardness[j] = (float) config.get("block.block" + i, "Hardness." + j, 1.5D).getDouble(1.5D);
				rasaBlocks[i].resistance[j] = (float) config.get("block.block" + i, "Resistance." + j, 10.0D).getDouble(10.0D);

				if (rasaBlocks[i].hardness[j] < 0) {
					rasaBlocks[i].hardness[j] = -1.0F;
				}
				rasaBlocks[i].hardness[j] = MathHelper.clamp_float(rasaBlocks[i].hardness[j], -1.0F, 6000000.0F);
				rasaBlocks[i].resistance[j] = MathHelper.clamp_float(rasaBlocks[i].hardness[j], 0.0F, 6000000.0F);

				rasaBlocks[i].resistance[j] *= 3;
				rasaBlocks[i].resistance[j] /= 5;
			}
			rasaBlocks[i].setCreativeTab(CreativeTabs.tabBlock);
			GameRegistry.registerBlock(rasaBlocks[i], ItemBlockRasa.class, "RasaBlock" + i);
		}

		// TODO: config for registering custom itemstacks, for minetweaker and such?

		for (int i = 0; i < numItems; i++) {
			rasaItems[i] = new ItemRasa();
			rasaItems[i].numItems = config.getInt("Count", "item.item" + i, 16, 1, 32000, "Number of contiguous metadata entries for this item.");
			rasaItems[i].names = new String[rasaItems[i].numItems];
			rasaItems[i].textures = new String[rasaItems[i].numItems];

			for (int j = 0; j < rasaItems[i].numItems; j++) {
				rasaItems[i].names[j] = config.get("item.item" + i, "Name." + j, "ItemRasa").getString();
				rasaItems[i].textures[j] = config.get("item.item" + i, "Texture." + j, "tabularasa:RasaItem").getString();
			}
			rasaItems[i].setCreativeTab(CreativeTabs.tabMisc);
			GameRegistry.registerItem(rasaItems[i], "RasaItem" + i);
		}
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event) {

		config.save();
	}

}
