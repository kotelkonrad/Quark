package vazkii.quark.building.module;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import vazkii.quark.base.block.IQuarkBlock;
import vazkii.quark.base.block.QuarkPaneBlock;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.building.block.FramedGlassBlock;

@LoadModule(category = ModuleCategory.BUILDING)
public class FramedGlassModule extends Module {

	@Override
	public void construct() {
		IQuarkBlock framedGlass = new FramedGlassBlock("framed_glass", this, ItemGroup.BUILDING_BLOCKS,
				Block.Properties.create(Material.GLASS)
						.hardnessAndResistance(3F, 10F)
						.sound(SoundType.GLASS)
						.harvestLevel(1)
						.harvestTool(ToolType.PICKAXE));
		new QuarkPaneBlock(framedGlass);
	}

}
