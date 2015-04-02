package cofh.tabularasa;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockFluidRasa extends BlockFluidClassic {

	String fluidName;
	String[] textures;
	IIcon icons[];

	boolean canCreatureSpawn = false;
	boolean shouldDisplaceFluids = false;
	boolean flammable = false;

	int fireSpreadSpeed = 300;
	int flammability = 25;

	public BlockFluidRasa(Fluid fluid, Material material) {

		super(fluid, material);

		this.fluidName = fluid.getName();

		icons = new IIcon[2];
		textures = new String[2];
	}

	public BlockFluidRasa setDisplaceFluids(boolean a) {

		this.shouldDisplaceFluids = a;
		return this;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {

		return canCreatureSpawn;
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {

		if (!shouldDisplaceFluids && world.getBlock(x, y, z).getMaterial().isLiquid()) {
			return false;
		}
		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {

		if (!shouldDisplaceFluids && world.getBlock(x, y, z).getMaterial().isLiquid()) {
			return false;
		}
		return super.displaceIfPossible(world, x, y, z);
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {

		return flammable ? fireSpreadSpeed : 0;
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {

		return flammability;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {

		return flammable;
	}

	@Override
	public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {

		return flammable;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {

		return side <= 1 ? icons[0] : icons[1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {

		icons = new IIcon[2];

		if (textures[0] != null && textures[1] != null) {
			for (int i = 0; i < 2; i++) {
				icons[i] = ir.registerIcon(textures[i]);
			}
		}
		if (icons[0] == null) {
			icons[0] = FluidRegistry.getFluid(fluidName).getStillIcon();
		}
		if (icons[1] == null) {
			icons[1] = FluidRegistry.getFluid(fluidName).getFlowingIcon();
		}
	}

}
