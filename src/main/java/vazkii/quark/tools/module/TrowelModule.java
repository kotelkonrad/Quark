package vazkii.quark.tools.module;

import vazkii.quark.base.module.Config;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.tools.item.TrowelItem;

@LoadModule(category = ModuleCategory.TOOLS)
public class TrowelModule extends Module {

	@Config(name = "Trowel Max Durability",
			description = "Amount of blocks placed is this value + 1. Default is 255 (4 stacks).\nSet to 0 to make the Trowel unbreakable")
	@Config.Min(0)
	public static int maxDamage = 255;
	
	@Override
	public void construct() {
		new TrowelItem(this);
	}
	
	
}
