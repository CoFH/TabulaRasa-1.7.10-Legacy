package cofh.tabularasa;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TRCreativeTab extends CreativeTabs {

	private final String label;

	ItemStack display = new ItemStack(Blocks.stone);

	public TRCreativeTab() {

		this("");
	}

	public TRCreativeTab(String label) {

		super("TabulaRasa" + label);
		this.label = label;
	}

	protected ItemStack getStack() {

		return display;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack() {

		return getStack();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {

		return getIconItemStack().getItem();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTabLabel() {

		return "tabularasa.creativeTab" + label;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {

		return "Tabula Rasa";
	}

}
