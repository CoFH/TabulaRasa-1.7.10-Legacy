package cofh.tabularasa;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemRasa extends Item {

	public int metadata;

	IIcon icons[];
	public String[] names;
	public String[] textures;

	public ItemRasa(int metadata) {

		super();
		this.metadata = metadata;

		icons = new IIcon[metadata];
		textures = new String[metadata];
		names = new String[metadata];

		if (metadata > 0) {
			setHasSubtypes(true);
		}
		setCreativeTab(TabulaRasa.tab);

		for (int i = 0; i < metadata; i++) {
			names[i] = "ItemRasa";
			textures[i] = "tabularasa:ItemRasa";
		}
	}

	public ItemRasa setNames(String name) {

		for (int i = 0; i < metadata; i++) {
			this.names[i] = name;
		}
		return this;
	}

	public ItemRasa setTextures(String texture) {

		for (int i = 0; i < metadata; i++) {
			this.textures[i] = texture;
		}
		return this;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {

		for (int i = 0; i < metadata; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public IIcon getIconFromDamage(int i) {

		return icons[i];
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {

		return names[stack.getItemDamage()];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		return names[stack.getItemDamage()] + ".name";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {

		icons = new IIcon[metadata];
		for (int i = 0; i < metadata; i++) {
			icons[i] = ir.registerIcon(textures[i]);
		}
	}

}
