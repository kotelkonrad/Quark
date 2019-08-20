package vazkii.quark.building.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.ItemGroup;
import vazkii.quark.base.block.QuarkBlock;
import vazkii.quark.base.module.Module;

public class SturdyStoneBlock extends QuarkBlock {

	public SturdyStoneBlock(Module module) {
		super("sturdy_stone", module, ItemGroup.BUILDING_BLOCKS,
				Block.Properties.create(Material.ROCK)
				.hardnessAndResistance(4F, 10F)
				.sound(SoundType.STONE));
	}
	
	@Override
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

}
