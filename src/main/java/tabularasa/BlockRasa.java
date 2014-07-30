package tabularasa;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockRasa extends Block {

	IIcon icons[] = new IIcon[16];
	public String[] textures = new String[16];
	public String[] names = new String[16];

	public float[] hardness = new float[16];
	public float[] resistance = new float[16];

	public BlockRasa(Material material) {

		super(material);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {

		for (int i = 0; i < 16; i++) {
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
	public int damageDropped(int metadata) {

		return metadata;
	}

	@Override
	public IIcon getIcon(int side, int metadata) {

		return icons[metadata];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {

		for (int i = 0; i < 16; i++) {
			icons[i] = ir.registerIcon(textures[i]);
		}
	}

}
