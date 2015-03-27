package cofh.tabularasa;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockLiquid;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;

public class FluidRasa extends Fluid {

	String stillTexture;
	String flowingTexture;

	public FluidRasa(String fluidName) {

		super(fluidName);

		MinecraftForge.EVENT_BUS.register(this);
	}

	public FluidRasa setTexture(String texture) {

		stillTexture = texture;
		flowingTexture = texture;
		return this;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerIcons(TextureStitchEvent.Pre event) {

		if (stillTexture == null) {
			return;
		}
		stillIcon = event.map.registerIcon(stillTexture);
		flowingIcon = event.map.registerIcon(flowingTexture);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void initializeIcons(TextureStitchEvent.Post event) {

		if (stillIcon == null) {
			setIcons(BlockLiquid.getLiquidIcon("water_still"), BlockLiquid.getLiquidIcon("water_flow"));
		}
	}
}
