package tabularasa;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemRasa extends Item {

	IIcon icons[];
	public int numItems;
	public String[] names;
	public String[] textures;

	public ItemRasa() {

		super();
		setHasSubtypes(true);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {

		for (int i = 0; i < numItems; i++) {
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

		icons = new IIcon[numItems];
		for (int i = 0; i < numItems; i++) {
			icons[i] = ir.registerIcon(textures[i]);
		}
	}

}
