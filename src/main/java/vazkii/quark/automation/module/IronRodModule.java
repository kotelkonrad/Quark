package vazkii.quark.automation.module;

import vazkii.quark.automation.block.IronRodBlock;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;

@LoadModule(category = ModuleCategory.AUTOMATION)
public class IronRodModule extends Module {
	
	@Override
	public void construct() {
		new IronRodBlock(this);
	}
	
}
