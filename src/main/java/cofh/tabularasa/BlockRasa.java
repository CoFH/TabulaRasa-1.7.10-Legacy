package cofh.tabularasa;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRasa extends Block {

	public int metadata;

	IIcon icons[];
	String[] textures;
	String[] names;

	float[] hardness;
	float[] resistance;
	int[] light;
	int[] redstone;
	int[] rarity;

	boolean[] canCreatureSpawn;
	boolean canProvidePower;
	boolean[] isBeaconBase;

	public BlockRasa(Material material, int metadata) {

		super(material);
		this.metadata = metadata;

		icons = new IIcon[metadata];
		textures = new String[metadata];
		names = new String[metadata];

		hardness = new float[metadata];
		resistance = new float[metadata];
		light = new int[metadata];
		redstone = new int[metadata];
		rarity = new int[metadata];

		canCreatureSpawn = new boolean[metadata];
		isBeaconBase = new boolean[metadata];

		setCreativeTab(TabulaRasa.tab);

		for (int i = 0; i < metadata; i++) {
			names[i] = "BlockRasa";
			textures[i] = "tabularasa:BlockRasa";
		}
		setHardness(3.0F);
		setResistance(3.0F);
	}

	@Override
	public BlockRasa setHardness(float hardness) {

		for (int i = 0; i < metadata; i++) {
			this.hardness[i] = hardness;
		}
		return this;
	}

	@Override
	public BlockRasa setResistance(float resistance) {

		for (int i = 0; i < metadata; i++) {
			this.resistance[i] = resistance;
		}
		return this;
	}

	public BlockRasa setNames(String name) {

		for (int i = 0; i < metadata; i++) {
			this.names[i] = name;
		}
		return this;
	}

	public BlockRasa setTextures(String texture) {

		for (int i = 0; i < metadata; i++) {
			this.textures[i] = texture;
		}
		return this;
	}

	public BlockRasa setLight(int light) {

		if (light < 0) {
			light = 0;
		} else if (light > 15) {
			light = 15;
		}
		for (int i = 0; i < metadata; i++) {
			this.light[i] = light;
		}
		return this;
	}

	public BlockRasa setRedstone(int redstone) {

		if (redstone < 0) {
			redstone = 0;
		} else if (redstone > 15) {
			redstone = 15;
		}
		for (int i = 0; i < metadata; i++) {
			this.redstone[i] = redstone;
		}
		return this;
	}

	public BlockRasa setRarity(int rarity) {

		if (rarity < 0) {
			rarity = 0;
		} else if (rarity > 3) {
			rarity = 3;
		}
		for (int i = 0; i < metadata; i++) {
			this.rarity[i] = rarity;
		}
		return this;
	}

	public BlockRasa setCreatureSpawn(boolean spawn) {

		for (int i = 0; i < metadata; i++) {
			this.canCreatureSpawn[i] = spawn;
		}
		return this;
	}

	public BlockRasa setBeaconBase(boolean beacon) {

		for (int i = 0; i < metadata; i++) {
			this.isBeaconBase[i] = beacon;
		}
		return this;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {

		for (int i = 0; i < metadata; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {

		return hardness[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {

		return resistance[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {

		return light[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {

		return redstone[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public int damageDropped(int metadata) {

		return metadata;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {

		return canCreatureSpawn[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public boolean canProvidePower() {

		return canProvidePower;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess world, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {

		return isBeaconBase[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public IIcon getIcon(int side, int metadata) {

		return icons[metadata];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {

		icons = new IIcon[metadata];
		for (int i = 0; i < metadata; i++) {
			icons[i] = ir.registerIcon(textures[i]);
		}
	}

}
