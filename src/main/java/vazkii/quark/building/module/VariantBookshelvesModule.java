package vazkii.quark.building.module;

import net.minecraft.block.Blocks;
import vazkii.quark.base.handler.ItemOverrideHandler;
import vazkii.quark.base.handler.MiscUtil;
import vazkii.quark.base.module.Config;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.building.block.VariantBookshelfBlock;

@LoadModule(category = ModuleCategory.BUILDING)
public class VariantBookshelvesModule extends Module {

	@Config public static boolean changeNames = true;

	@Override
	public void construct() {
		for(String type : MiscUtil.VARIANT_WOOD_TYPES)
			new VariantBookshelfBlock(type, this);
	}

	@Override
	public void configChanged() {
		ItemOverrideHandler.changeBlockLocalizationKey(Blocks.BOOKSHELF, "block.quark.oak_bookshelf", changeNames && enabled);
	}
}
