package tabularasa;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockRasa extends ItemBlock {

	BlockRasa myBlock;

	public ItemBlockRasa(Block block) {

		super(block);
		myBlock = (BlockRasa) block;
		setHasSubtypes(true);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {

		return myBlock.names[stack.getItemDamage()];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		return myBlock.names[stack.getItemDamage()] + ".name";
	}

	@Override
	public int getMetadata(int i) {

		return i;
	}

}
