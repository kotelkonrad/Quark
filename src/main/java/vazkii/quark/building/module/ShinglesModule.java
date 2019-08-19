package vazkii.quark.building.module;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;
import vazkii.quark.base.block.QuarkBlock;
import vazkii.quark.base.handler.VariantHandler;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;

@LoadModule(category = ModuleCategory.BUILDING)
public class ShinglesModule extends Module {

	@Override
	public void start() {
		for(DyeColor dye : DyeColor.values())
			VariantHandler.addSlabAndStairs(new QuarkBlock(dye.getName() + "_shingles", this, ItemGroup.BUILDING_BLOCKS, Block.Properties.from(Blocks.TERRACOTTA)));
	}

}
