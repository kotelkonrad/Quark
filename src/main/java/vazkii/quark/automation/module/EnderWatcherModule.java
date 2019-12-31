package vazkii.quark.automation.module;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import vazkii.arl.util.RegistryHelper;
import vazkii.quark.automation.block.EnderWatcherBlock;
import vazkii.quark.automation.tile.EnderWatcherTileEntity;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;

@LoadModule(category = ModuleCategory.AUTOMATION)
public class EnderWatcherModule extends Module {

	public static TileEntityType<EnderWatcherTileEntity> enderWatcherTEType;

	@Override
	public void construct() {
		Block ender_watcher = new EnderWatcherBlock(this);
		enderWatcherTEType = TileEntityType.Builder.create(EnderWatcherTileEntity::new, ender_watcher).build(null);
		RegistryHelper.register(enderWatcherTEType, "ender_watcher");
	}
	
}
