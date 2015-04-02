package cofh.tabularasa;

import cofh.tabularasa.TRParser.TemplateTypes;
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
import net.minecraftforge.fluids.BlockFluidBase;
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

	public static ArrayList<Item> items = new ArrayList<Item>();
	public static ArrayList<Fluid> fluids = new ArrayList<Fluid>();
	public static ArrayList<Block> blocks = new ArrayList<Block>();
	public static ArrayList<BlockFluidBase> fluidBlocks = new ArrayList<BlockFluidBase>();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		configDir = event.getModConfigurationDirectory();

		TRParser.initialize();

		try {
			TRParser.parseTemplateFiles();
			TRParser.parseTemplates(TemplateTypes.ITEM);
			TRParser.parseTemplates(TemplateTypes.FLUID);
			TRParser.parseTemplates(TemplateTypes.BLOCK);
			TRParser.parseTemplates(TemplateTypes.FLUIDBLOCK);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event) {

	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {

		try {
			TRParser.parseTemplates(TemplateTypes.RECIPE);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
