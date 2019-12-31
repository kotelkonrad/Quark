package vazkii.quark.building.module;

import com.google.common.base.Functions;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import vazkii.quark.base.handler.VariantHandler;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;

@LoadModule(category = ModuleCategory.BUILDING)
public class MorePottedPlantsModule extends Module {

	@Override
	public void construct() {
		add(Blocks.BEETROOTS, "beetroot");
		add(Blocks.SWEET_BERRY_BUSH, "berries");
		add(Blocks.CARROTS, "carrot");
		add(Blocks.CHORUS_FLOWER, "chorus");
		add(Blocks.COCOA, "cocoa_bean");
		add(Blocks.GRASS, "grass");
		add(Blocks.MELON_STEM, "melon");
		add(Blocks.POTATOES, "potato");
		add(Blocks.PUMPKIN_STEM, "pumpkin");
		VariantHandler.addFlowerPot(Blocks.SEA_PICKLE, "sea_pickle", p -> p.lightValue(3));
		add(Blocks.SUGAR_CANE, "sugar_cane");
		add(Blocks.SUNFLOWER, "sunflower");
		add(Blocks.WHEAT, "wheat");
	}
	
	private void add(Block block, String name) {
		VariantHandler.addFlowerPot(block, name, Functions.identity());
	}
	
}
