package cofh.tabularasa;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.common.registry.GameRegistry;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TRParser {

	private static File templateFolder;

	private static Logger log = LogManager.getLogger("TabulaRasa");

	private TRParser() {

	}

	public static void initialize() {

		templateFolder = new File(TabulaRasa.configDir, "/cofh/tabularasa/templates/");

		if (!templateFolder.exists()) {
			try {
				templateFolder.mkdir();
			} catch (Throwable t) {
				// pokemon!
			}
		}
	}

	private static void addFiles(ArrayList<File> list, File folder) {

		File[] fList = folder.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File file, String name) {

				if (name == null) {
					return false;
				}
				return name.toLowerCase().endsWith(".json") || new File(file, name).isDirectory();
			}
		});

		if (fList == null || fList.length <= 0) {
			log.error("There are no Tabula Rasa Template files present in " + folder + ".");
			return;
		}
		log.info("Tabula Rasa found " + fList.length + " Template files present in " + folder + "/.");
		list.addAll(Arrays.asList(fList));
	}

	public static void parseTemplateFiles() {

		JsonParser parser = new JsonParser();

		ArrayList<File> templateFileList = new ArrayList<File>();
		addFiles(templateFileList, templateFolder);

		for (int i = 0; i < templateFileList.size(); ++i) {
			File templateFile = templateFileList.get(i);
			if (templateFile.isDirectory()) {
				addFiles(templateFileList, templateFile);
				continue;
			}
			JsonObject templateList;
			try {
				templateList = (JsonObject) parser.parse(new FileReader(templateFile));
			} catch (Throwable t) {
				log.error("Critical error reading from a template file: " + templateFile + " > Please be sure the file is correct!", t);
				continue;
			}

			log.info("Reading template info from: " + templateFile + ":");
			for (Entry<String, JsonElement> templateEntry : templateList.entrySet()) {
				try {
					if (parseTemplate(templateEntry.getKey(), templateEntry.getValue())) {
						log.debug("Template entry successfully parsed: \"" + templateEntry.getKey() + "\"");
					} else {
						log.error("Error parsing template entry: \"" + templateEntry.getKey() + "\" > Please check the parameters. It *may* be a duplicate.");
					}
				} catch (Throwable t) {
					log.fatal("There was a severe error parsing '" + templateEntry.getKey() + "'!", t);
				}
			}
		}
	}

	private static boolean parseTemplate(String name, JsonElement templateObject) {

		JsonObject template = templateObject.getAsJsonObject();

		if (template.has("enabled")) {
			if (!template.get("enabled").getAsBoolean()) {
				log.info('"' + name + "\" is disabled.");
				return true;
			}
		}
		String type = template.get("template").getAsString();

		if (type.equals("block")) {
			return parseBlockTemplate(name, template, log);
		} else if (type.equals("item")) {
			return parseItemTemplate(name, template, log);
		} else if (type.equals("fluid")) {
			return parseFluidTemplate(name, template, log);
		}
		return false;
	}

	private static boolean parseBlockTemplate(String name, JsonObject templateObject, Logger log) {

		if (templateObject.has("data")) {
			return parseBlockEntryList(name, templateObject, log);
		}
		return parseBlockArrays(name, templateObject, log);
	}

	private static boolean parseBlockEntryList(String name, JsonObject templateObject, Logger log) {

		if (!templateObject.has("data")) {
			return false;
		}
		BlockRasa block;
		int metadata = 1;
		Material material = Material.rock;
		Block.SoundType stepSound = Block.soundTypeStone;

		/* MATERIAL */
		if (templateObject.has("material")) {
			String materialType = templateObject.get("material").getAsString();

			if (materialType.equalsIgnoreCase("air")) {
				material = Material.air;
			} else if (materialType.equalsIgnoreCase("grass")) {
				material = Material.grass;
			} else if (materialType.equalsIgnoreCase("ground")) {
				material = Material.ground;
			} else if (materialType.equalsIgnoreCase("wood")) {
				material = Material.wood;
			} else if (materialType.equalsIgnoreCase("rock")) {
				material = Material.rock;
			} else if (materialType.equalsIgnoreCase("iron")) {
				material = Material.iron;
			} else if (materialType.equalsIgnoreCase("anvil")) {
				material = Material.anvil;
			} else if (materialType.equalsIgnoreCase("water")) {
				material = Material.water;
			} else if (materialType.equalsIgnoreCase("lava")) {
				material = Material.lava;
			} else if (materialType.equalsIgnoreCase("leaves")) {
				material = Material.leaves;
			} else if (materialType.equalsIgnoreCase("plants")) {
				material = Material.plants;
			} else if (materialType.equalsIgnoreCase("vine")) {
				material = Material.vine;
			} else if (materialType.equalsIgnoreCase("sponge")) {
				material = Material.sponge;
			} else if (materialType.equalsIgnoreCase("cloth")) {
				material = Material.cloth;
			} else if (materialType.equalsIgnoreCase("fire")) {
				material = Material.fire;
			} else if (materialType.equalsIgnoreCase("sand")) {
				material = Material.sand;
			} else if (materialType.equalsIgnoreCase("circuits")) {
				material = Material.circuits;
			} else if (materialType.equalsIgnoreCase("carpet")) {
				material = Material.carpet;
			} else if (materialType.equalsIgnoreCase("glass")) {
				material = Material.glass;
			} else if (materialType.equalsIgnoreCase("redstoneLight")) {
				material = Material.redstoneLight;
			} else if (materialType.equalsIgnoreCase("tnt")) {
				material = Material.tnt;
			} else if (materialType.equalsIgnoreCase("coral")) {
				material = Material.coral;
			} else if (materialType.equalsIgnoreCase("ice")) {
				material = Material.ice;
			} else if (materialType.equalsIgnoreCase("packedIce")) {
				material = Material.packedIce;
			} else if (materialType.equalsIgnoreCase("snow")) {
				material = Material.snow;
			} else if (materialType.equalsIgnoreCase("craftedSnow")) {
				material = Material.craftedSnow;
			} else if (materialType.equalsIgnoreCase("cactus")) {
				material = Material.cactus;
			} else if (materialType.equalsIgnoreCase("clay")) {
				material = Material.clay;
			} else if (materialType.equalsIgnoreCase("gourd")) {
				material = Material.gourd;
			} else if (materialType.equalsIgnoreCase("dragonEgg")) {
				material = Material.dragonEgg;
			} else if (materialType.equalsIgnoreCase("portal")) {
				material = Material.portal;
			} else if (materialType.equalsIgnoreCase("cake")) {
				material = Material.cake;
			} else if (materialType.equalsIgnoreCase("web")) {
				material = Material.web;
			}
		}
		/* ORE NAMES */
		ArrayList<ArrayList<String>> oreNameList = new ArrayList<ArrayList<String>>();

		JsonElement data = templateObject.get("data");
		if (data.isJsonArray()) {
			JsonArray array = data.getAsJsonArray();
			metadata = Math.min(16, array.size());
			block = new BlockRasa(material, metadata);
			for (int i = 0; i < metadata; i++) {
				JsonObject element = array.get(i).getAsJsonObject();
				oreNameList.add(new ArrayList<String>());
				parseBlockEntry(block, i, oreNameList.get(i), element, log);
			}
		} else {
			block = new BlockRasa(material, metadata);
			JsonObject element = data.getAsJsonObject();
			oreNameList.add(new ArrayList<String>());
			parseBlockEntry(block, 0, oreNameList.get(0), element, log);
		}
		/* STEP SOUND */
		if (templateObject.has("stepSound")) {
			String soundType = templateObject.get("stepSound").getAsString();

			if (soundType.equalsIgnoreCase("stone")) {
				stepSound = Block.soundTypeStone;
			} else if (soundType.equalsIgnoreCase("wood")) {
				stepSound = Block.soundTypeWood;
			} else if (soundType.equalsIgnoreCase("gravel")) {
				stepSound = Block.soundTypeGravel;
			} else if (soundType.equalsIgnoreCase("grass")) {
				stepSound = Block.soundTypeGrass;
			} else if (soundType.equalsIgnoreCase("piston")) {
				stepSound = Block.soundTypePiston;
			} else if (soundType.equalsIgnoreCase("metal")) {
				stepSound = Block.soundTypeMetal;
			} else if (soundType.equalsIgnoreCase("glass")) {
				stepSound = Block.soundTypeGlass;
			} else if (soundType.equalsIgnoreCase("cloth")) {
				stepSound = Block.soundTypeCloth;
			} else if (soundType.equalsIgnoreCase("sand")) {
				stepSound = Block.soundTypeSand;
			} else if (soundType.equalsIgnoreCase("snow")) {
				stepSound = Block.soundTypeSnow;
			} else if (soundType.equalsIgnoreCase("ladder")) {
				stepSound = Block.soundTypeLadder;
			} else if (soundType.equalsIgnoreCase("anvil")) {
				stepSound = Block.soundTypeAnvil;
			}
			block.setStepSound(stepSound);
		}
		/* CREATIVE TAB */
		if (templateObject.has("creativeTab")) {
			String tabType = templateObject.get("creativeTab").getAsString();
			CreativeTabs creativeTab = TabulaRasa.tab;

			if (tabType.equals("buildingBlocks")) {
				creativeTab = CreativeTabs.tabBlock;
			} else if (tabType.equals("decorations")) {
				creativeTab = CreativeTabs.tabDecorations;
			} else if (tabType.equals("redstone")) {
				creativeTab = CreativeTabs.tabRedstone;
			} else if (tabType.equals("transportation")) {
				creativeTab = CreativeTabs.tabTransport;
			} else if (tabType.equals("misc")) {
				creativeTab = CreativeTabs.tabMisc;
			} else if (tabType.equals("food")) {
				creativeTab = CreativeTabs.tabFood;
			} else if (tabType.equals("tools")) {
				creativeTab = CreativeTabs.tabTools;
			} else if (tabType.equals("combat")) {
				creativeTab = CreativeTabs.tabCombat;
			} else if (tabType.equals("brewing")) {
				creativeTab = CreativeTabs.tabBrewing;
			} else if (tabType.equals("materials")) {
				creativeTab = CreativeTabs.tabMaterials;
			} else if (tabType.equals("inventory")) {
				creativeTab = CreativeTabs.tabInventory;
			}
			block.setCreativeTab(creativeTab);
		}
		TabulaRasa.blocks.add(block);
		GameRegistry.registerBlock(block, ItemBlockRasa.class, name);

		for (int i = 0; i < metadata; i++) {
			ArrayList<String> oreNameMeta = oreNameList.get(i);
			if (oreNameMeta == null) {
				continue;
			}
			for (int j = 0; j < oreNameMeta.size(); j++) {
				OreDictionary.registerOre(oreNameMeta.get(j), new ItemStack(block, 1, i));
			}
		}
		return true;
	}

	private static boolean parseBlockArrays(String name, JsonObject templateObject, Logger log) {

		BlockRasa block;
		int metadata = 1;
		Material material = Material.rock;
		Block.SoundType stepSound = Block.soundTypeStone;
		CreativeTabs creativeTab = TabulaRasa.tab;

		/* MATERIAL */
		if (templateObject.has("material")) {
			String materialType = templateObject.get("material").getAsString();

			if (materialType.equalsIgnoreCase("air")) {
				material = Material.air;
			} else if (materialType.equalsIgnoreCase("grass")) {
				material = Material.grass;
			} else if (materialType.equalsIgnoreCase("ground")) {
				material = Material.ground;
			} else if (materialType.equalsIgnoreCase("wood")) {
				material = Material.wood;
			} else if (materialType.equalsIgnoreCase("rock")) {
				material = Material.rock;
			} else if (materialType.equalsIgnoreCase("iron")) {
				material = Material.iron;
			} else if (materialType.equalsIgnoreCase("anvil")) {
				material = Material.anvil;
			} else if (materialType.equalsIgnoreCase("water")) {
				material = Material.water;
			} else if (materialType.equalsIgnoreCase("lava")) {
				material = Material.lava;
			} else if (materialType.equalsIgnoreCase("leaves")) {
				material = Material.leaves;
			} else if (materialType.equalsIgnoreCase("plants")) {
				material = Material.plants;
			} else if (materialType.equalsIgnoreCase("vine")) {
				material = Material.vine;
			} else if (materialType.equalsIgnoreCase("sponge")) {
				material = Material.sponge;
			} else if (materialType.equalsIgnoreCase("cloth")) {
				material = Material.cloth;
			} else if (materialType.equalsIgnoreCase("fire")) {
				material = Material.fire;
			} else if (materialType.equalsIgnoreCase("sand")) {
				material = Material.sand;
			} else if (materialType.equalsIgnoreCase("circuits")) {
				material = Material.circuits;
			} else if (materialType.equalsIgnoreCase("carpet")) {
				material = Material.carpet;
			} else if (materialType.equalsIgnoreCase("glass")) {
				material = Material.glass;
			} else if (materialType.equalsIgnoreCase("redstoneLight")) {
				material = Material.redstoneLight;
			} else if (materialType.equalsIgnoreCase("tnt")) {
				material = Material.tnt;
			} else if (materialType.equalsIgnoreCase("coral")) {
				material = Material.coral;
			} else if (materialType.equalsIgnoreCase("ice")) {
				material = Material.ice;
			} else if (materialType.equalsIgnoreCase("packedIce")) {
				material = Material.packedIce;
			} else if (materialType.equalsIgnoreCase("snow")) {
				material = Material.snow;
			} else if (materialType.equalsIgnoreCase("craftedSnow")) {
				material = Material.craftedSnow;
			} else if (materialType.equalsIgnoreCase("cactus")) {
				material = Material.cactus;
			} else if (materialType.equalsIgnoreCase("clay")) {
				material = Material.clay;
			} else if (materialType.equalsIgnoreCase("gourd")) {
				material = Material.gourd;
			} else if (materialType.equalsIgnoreCase("dragonEgg")) {
				material = Material.dragonEgg;
			} else if (materialType.equalsIgnoreCase("portal")) {
				material = Material.portal;
			} else if (materialType.equalsIgnoreCase("cake")) {
				material = Material.cake;
			} else if (materialType.equalsIgnoreCase("web")) {
				material = Material.web;
			}
		}
		/* STEP SOUND */
		if (templateObject.has("stepSound")) {
			String soundType = templateObject.get("stepSound").getAsString();

			if (soundType.equalsIgnoreCase("stone")) {
				stepSound = Block.soundTypeStone;
			} else if (soundType.equalsIgnoreCase("wood")) {
				stepSound = Block.soundTypeWood;
			} else if (soundType.equalsIgnoreCase("gravel")) {
				stepSound = Block.soundTypeGravel;
			} else if (soundType.equalsIgnoreCase("grass")) {
				stepSound = Block.soundTypeGrass;
			} else if (soundType.equalsIgnoreCase("piston")) {
				stepSound = Block.soundTypePiston;
			} else if (soundType.equalsIgnoreCase("metal")) {
				stepSound = Block.soundTypeMetal;
			} else if (soundType.equalsIgnoreCase("glass")) {
				stepSound = Block.soundTypeGlass;
			} else if (soundType.equalsIgnoreCase("cloth")) {
				stepSound = Block.soundTypeCloth;
			} else if (soundType.equalsIgnoreCase("sand")) {
				stepSound = Block.soundTypeSand;
			} else if (soundType.equalsIgnoreCase("snow")) {
				stepSound = Block.soundTypeSnow;
			} else if (soundType.equalsIgnoreCase("ladder")) {
				stepSound = Block.soundTypeLadder;
			} else if (soundType.equalsIgnoreCase("anvil")) {
				stepSound = Block.soundTypeAnvil;
			}
		}
		/* CREATIVE TAB */
		if (templateObject.has("creativeTab")) {
			String tabType = templateObject.get("creativeTab").getAsString();

			if (tabType.equals("buildingBlocks")) {
				creativeTab = CreativeTabs.tabBlock;
			} else if (tabType.equals("decorations")) {
				creativeTab = CreativeTabs.tabDecorations;
			} else if (tabType.equals("redstone")) {
				creativeTab = CreativeTabs.tabRedstone;
			} else if (tabType.equals("transportation")) {
				creativeTab = CreativeTabs.tabTransport;
			} else if (tabType.equals("misc")) {
				creativeTab = CreativeTabs.tabMisc;
			} else if (tabType.equals("food")) {
				creativeTab = CreativeTabs.tabFood;
			} else if (tabType.equals("tools")) {
				creativeTab = CreativeTabs.tabTools;
			} else if (tabType.equals("combat")) {
				creativeTab = CreativeTabs.tabCombat;
			} else if (tabType.equals("brewing")) {
				creativeTab = CreativeTabs.tabBrewing;
			} else if (tabType.equals("materials")) {
				creativeTab = CreativeTabs.tabMaterials;
			} else if (tabType.equals("inventory")) {
				creativeTab = CreativeTabs.tabInventory;
			}
		}
		JsonElement element;

		/* METADATA - NAME SIZE USED IF NOT PRESENT */
		if (templateObject.has("metadata")) {
			element = templateObject.get("metadata");
			metadata = Math.min(16, element.getAsInt());
			if (metadata <= 0) {
				metadata = 1;
			}
		} else if (templateObject.has("name")) {
			element = templateObject.get("name");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				metadata = Math.min(16, array.size());
			}
		}
		block = new BlockRasa(material, metadata);
		block.setStepSound(stepSound);
		block.setCreativeTab(creativeTab);

		/* NAME */
		if (templateObject.has("name")) {
			element = templateObject.get("name");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int i = 0; i < metadata; i++) {
					block.names[i] = array.get(i).getAsString();
				}
			} else {
				block.setNames(element.getAsString());
			}
		}
		/* TEXTURE */
		if (templateObject.has("texture")) {
			element = templateObject.get("texture");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int i = 0; i < metadata; i++) {
					block.textures[i] = array.get(i).getAsString();
				}
			} else {
				block.setTextures(element.getAsString());
			}
		}
		/* HARDNESS */
		if (templateObject.has("hardness")) {
			element = templateObject.get("hardness");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int i = 0; i < metadata; i++) {
					block.hardness[i] = array.get(i).getAsFloat();
					block.resistance[i] = (Math.max(block.resistance[0], block.hardness[0]));
				}
			} else {
				block.setHardness(element.getAsFloat());
				block.setResistance(Math.max(block.resistance[0], block.hardness[0]));
			}
		}
		/* RESISTANCE */
		if (templateObject.has("resistance")) {
			element = templateObject.get("resistance");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int i = 0; i < metadata; i++) {
					block.resistance[i] = array.get(i).getAsFloat() * 3.0F / 5.0F;
				}
			} else {
				block.setResistance(element.getAsFloat() * 3.0F / 5.0F);
			}
		}
		/* LIGHT */
		if (templateObject.has("light")) {
			element = templateObject.get("light");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int i = 0; i < metadata; i++) {
					block.light[i] = Math.min(array.get(i).getAsInt(), 15);
				}
			} else {
				block.setLight(element.getAsInt());
			}
		}
		/* REDSTONE */
		if (templateObject.has("redstone")) {
			element = templateObject.get("redstone");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int i = 0; i < metadata; i++) {
					block.redstone[i] = Math.min(array.get(i).getAsInt(), 15);

					if (block.redstone[i] > 0) {
						block.canProvidePower = true;
					}
				}
			} else {
				block.setRedstone(element.getAsInt());

				if (block.redstone[0] > 0) {
					block.canProvidePower = true;
				}
			}
		}
		/* RARITY */
		if (templateObject.has("rarity")) {
			element = templateObject.get("rarity");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int i = 0; i < metadata; i++) {
					block.rarity[i] = array.get(i).getAsInt();
				}
			} else {
				block.setRarity(element.getAsInt());
			}
		}
		/* HARVEST TOOL */
		if (templateObject.has("harvestTool")) {
			element = templateObject.get("harvestTool");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int i = 0; i < metadata; i++) {
					block.setHarvestLevel(array.get(i).getAsString(), 0, i);
				}
			} else {
				block.setHarvestLevel(element.getAsString(), 0);
			}
		}
		/* HARVEST LEVEL */
		if (templateObject.has("harvestLevel")) {
			element = templateObject.get("harvestLevel");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int i = 0; i < metadata; i++) {
					block.setHarvestLevel(block.getHarvestTool(i), array.get(i).getAsInt(), i);
				}
			} else {
				// This is necessary in case different tools are specified above, but only 1 common level.
				for (int i = 0; i < metadata; i++) {
					block.setHarvestLevel(block.getHarvestTool(i), element.getAsInt(), i);
				}
			}
		}
		/* ORE NAMES */
		ArrayList<ArrayList<String>> oreNameList = new ArrayList<ArrayList<String>>();

		if (templateObject.has("oreNames")) {
			element = templateObject.get("oreNames");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int i = 0; i < metadata; i++) {
					oreNameList.add(new ArrayList<String>());
					if (array.get(i).isJsonArray()) {
						JsonArray oreArray = array.get(i).getAsJsonArray();
						for (int j = 0; j < oreArray.size(); j++) {
							oreNameList.get(i).add(oreArray.get(j).getAsString());
						}
					} else {
						oreNameList.get(i).add(array.get(i).getAsString());
					}
				}
			} else {
				oreNameList.clear();
				for (int i = 0; i < metadata; i++) {
					oreNameList.add(new ArrayList<String>());
					oreNameList.get(i).add(element.getAsString());
				}
			}
		}
		TabulaRasa.blocks.add(block);
		GameRegistry.registerBlock(block, ItemBlockRasa.class, name);

		for (int i = 0; i < metadata; i++) {
			ArrayList<String> oreNameMeta = oreNameList.get(i);
			for (int j = 0; j < oreNameMeta.size(); j++) {
				OreDictionary.registerOre(oreNameMeta.get(j), new ItemStack(block, 1, i));
			}
		}
		return true;
	}

	private static boolean parseFluidBlockTemplate(String name, JsonObject templateObject, Logger log) {

		return true;
	}

	private static boolean parseFluidTemplate(String name, JsonObject templateObject, Logger log) {

		if (!templateObject.has("name")) {
			// Name absolutely required.
			return false;
		}
		FluidRasa fluid;
		JsonElement element = templateObject.get("name");

		fluid = new FluidRasa(element.getAsString());

		/* TEXTURES */
		if (templateObject.has("texture")) {
			element = templateObject.get("texture");
			if (element.isJsonArray()) {
				JsonArray icons = element.getAsJsonArray();
				fluid.stillTexture = icons.get(0).getAsString();
				fluid.flowingTexture = icons.get(1).getAsString();
			} else {
				fluid.setTexture(element.getAsString());
			}
		} else if (templateObject.has("textureStill")) {
			element = templateObject.get("textureStill");
			fluid.setTexture(element.getAsString());
			if (templateObject.has("textureFlowing")) {
				element = templateObject.get("textureFlowing");
				fluid.flowingTexture = element.getAsString();
			}
		}
		/* LUMINOSITY */
		if (templateObject.has("luminosity")) {
			element = templateObject.get("luminosity");
			fluid.setLuminosity(element.getAsInt());
		}
		/* DENSITY */
		if (templateObject.has("density")) {
			element = templateObject.get("density");
			fluid.setDensity(element.getAsInt());
		}
		/* TEMPERATURE */
		if (templateObject.has("temperature")) {
			element = templateObject.get("temperature");
			fluid.setTemperature(element.getAsInt());
		}
		/* VISCOSITY */
		if (templateObject.has("viscosity")) {
			element = templateObject.get("viscosity");
			fluid.setViscosity(element.getAsInt());
		}
		/* GASEOUS */
		if (templateObject.has("isGaseous")) {
			element = templateObject.get("isGaseous");
			fluid.setGaseous(element.getAsBoolean());
		} else if (templateObject.has("gaseous")) {
			element = templateObject.get("gaseous");
			fluid.setGaseous(element.getAsBoolean());
		}
		/* RARITY */
		if (templateObject.has("rarity")) {
			element = templateObject.get("rarity");
			int rarity = element.getAsInt();

			if (rarity < 0) {
				rarity = 0;
			} else if (rarity > 3) {
				rarity = 3;
			}
			fluid.setRarity(EnumRarity.values()[rarity]);
		}
		if (!FluidRegistry.registerFluid(fluid)) {
			log.error("Fluid " + name + "failed to register!");
			return false;
		}
		return true;
	}

	private static boolean parseItemTemplate(String name, JsonObject templateObject, Logger log) {

		if (templateObject.has("data")) {
			return parseItemEntryList(name, templateObject, log);
		}
		return parseItemArrays(name, templateObject, log);
	}

	private static boolean parseItemEntryList(String name, JsonObject templateObject, Logger log) {

		if (!templateObject.has("data")) {
			return false;
		}
		ItemRasa item;
		int metadata = 1;
		ArrayList<ArrayList<String>> oreNameList = new ArrayList<ArrayList<String>>();

		JsonElement data = templateObject.get("data");
		if (data.isJsonArray()) {
			JsonArray array = data.getAsJsonArray();
			metadata = Math.min(16, array.size());
			item = new ItemRasa(metadata);
			for (int i = 0; i < metadata; i++) {
				JsonObject element = array.get(i).getAsJsonObject();
				parseItemEntry(item, i, oreNameList.get(i), element, log);
			}
		} else {
			item = new ItemRasa(metadata);
			JsonObject element = data.getAsJsonObject();
			parseItemEntry(item, 0, oreNameList.get(0), element, log);
		}
		TabulaRasa.items.add(item);
		GameRegistry.registerItem(item, name);

		for (int i = 0; i < metadata; i++) {
			ArrayList<String> oreNameMeta = oreNameList.get(i);
			if (oreNameMeta == null) {
				continue;
			}
			for (int j = 0; j < oreNameMeta.size(); j++) {
				OreDictionary.registerOre(oreNameMeta.get(j), new ItemStack(item, 1, i));
			}
		}
		return true;
	}

	public static boolean parseItemArrays(String name, JsonObject templateObject, Logger log) {

		if (!templateObject.has("name")) {
			return false;
		}
		ItemRasa item;
		int metadata = 1;

		JsonElement element;

		/* METADATA - NAME SIZE USED IF NOT PRESENT */
		if (templateObject.has("metadata")) {
			element = templateObject.get("metadata");
			metadata = Math.min(16, element.getAsInt());
			if (metadata <= 0) {
				metadata = 1;
			}
		} else if (templateObject.has("name")) {
			element = templateObject.get("name");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				metadata = Math.min(16, array.size());
			}
		}
		item = new ItemRasa(metadata);

		/* NAME */
		if (templateObject.has("name")) {
			element = templateObject.get("name");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int i = 0; i < metadata; i++) {
					item.names[i] = array.get(i).getAsString();
				}
			} else {
				item.setNames(element.getAsString());
			}
		}
		/* TEXTURE */
		if (templateObject.has("texture")) {
			element = templateObject.get("texture");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int i = 0; i < metadata; i++) {
					item.textures[i] = array.get(i).getAsString();
				}
			} else {
				item.setTextures(element.getAsString());
			}
		}
		/* ORE NAMES */
		ArrayList<ArrayList<String>> oreNameList = new ArrayList<ArrayList<String>>();

		if (templateObject.has("oreNames")) {
			element = templateObject.get("oreNames");
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int i = 0; i < metadata; i++) {
					oreNameList.add(new ArrayList<String>());
					if (array.get(i).isJsonArray()) {
						JsonArray oreArray = array.get(i).getAsJsonArray();
						for (int j = 0; j < oreArray.size(); j++) {
							oreNameList.get(i).add(oreArray.get(j).getAsString());
						}
					} else {
						oreNameList.get(i).add(array.get(i).getAsString());
					}
				}
			} else {
				oreNameList.clear();
				for (int i = 0; i < metadata; i++) {
					oreNameList.add(new ArrayList<String>());
					oreNameList.get(i).add(element.getAsString());
				}
			}
		}
		TabulaRasa.items.add(item);
		GameRegistry.registerItem(item, name);

		for (int i = 0; i < metadata; i++) {
			ArrayList<String> oreNameMeta = oreNameList.get(i);
			for (int j = 0; j < oreNameMeta.size(); j++) {
				OreDictionary.registerOre(oreNameMeta.get(j), new ItemStack(item, 1, i));
			}
		}
		return true;
	}

	private static void parseBlockEntry(BlockRasa block, int metadata, ArrayList<String> oreNames, JsonObject templateObject, Logger log) {

		JsonElement element;

		/* NAME */
		if (templateObject.has("name")) {
			element = templateObject.get("name");
			block.names[metadata] = element.getAsString();
		}
		/* TEXTURE */
		if (templateObject.has("texture")) {
			element = templateObject.get("texture");
			block.textures[metadata] = element.getAsString();
		}
		/* HARDNESS */
		if (templateObject.has("hardness")) {
			element = templateObject.get("hardness");
			block.hardness[metadata] = element.getAsFloat();
			block.resistance[metadata] = (Math.max(block.resistance[0], block.hardness[0]));
		}
		/* RESISTANCE */
		if (templateObject.has("resistance")) {
			element = templateObject.get("resistance");
			block.resistance[metadata] = element.getAsFloat() * 3.0F / 5.0F;
		}
		/* LIGHT */
		if (templateObject.has("light")) {
			element = templateObject.get("light");
			block.light[metadata] = element.getAsInt();

			if (block.light[metadata] < 0) {
				block.light[metadata] = 0;
			} else if (block.light[metadata] > 15) {
				block.light[metadata] = 15;
			}
		}
		/* REDSTONE */
		if (templateObject.has("redstone")) {
			element = templateObject.get("redstone");
			block.redstone[metadata] = element.getAsInt();

			if (block.redstone[metadata] < 0) {
				block.redstone[metadata] = 0;
			} else {
				block.canProvidePower = true;
				if (block.redstone[metadata] > 15) {
					block.redstone[metadata] = 15;
				}
			}
		}
		/* RARITY */
		if (templateObject.has("rarity")) {
			element = templateObject.get("rarity");
			block.rarity[metadata] = element.getAsInt();

			if (block.rarity[metadata] < 0) {
				block.rarity[metadata] = 0;
			} else if (block.rarity[metadata] > 3) {
				block.rarity[metadata] = 3;
			}
		}
		/* HARVEST TOOL */
		if (templateObject.has("harvestTool")) {
			element = templateObject.get("harvestTool");
			block.setHarvestLevel(element.getAsString(), 0, metadata);
		}
		/* HARVEST LEVEL */
		if (templateObject.has("harvestLevel")) {
			element = templateObject.get("harvestLevel");
			block.setHarvestLevel(block.getHarvestTool(metadata), element.getAsInt(), metadata);
		}
		/* ORE NAMES */
		if (templateObject.has("oreNames")) {
			element = templateObject.get("oreNames");
			oreNames = new ArrayList<String>();
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int j = 0; j < array.size(); j++) {
					oreNames.add(array.get(j).getAsString());
				}
			} else {
				oreNames.add(element.getAsString());
			}
		}
	}

	private static void parseItemEntry(ItemRasa item, int metadata, ArrayList<String> oreNames, JsonObject templateObject, Logger log) {

		JsonElement element;

		/* NAME */
		if (templateObject.has("name")) {
			element = templateObject.get("name");
			item.names[metadata] = element.getAsString();
		}
		/* TEXTURE */
		if (templateObject.has("texture")) {
			element = templateObject.get("texture");
			item.textures[metadata] = element.getAsString();
		}
		/* ORE NAMES */
		if (templateObject.has("oreNames")) {
			element = templateObject.get("oreNames");
			oreNames = new ArrayList<String>();
			if (element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				for (int j = 0; j < array.size(); j++) {
					oreNames.add(array.get(j).getAsString());
				}
			} else {
				oreNames.add(element.getAsString());
			}
		}
	}

}
