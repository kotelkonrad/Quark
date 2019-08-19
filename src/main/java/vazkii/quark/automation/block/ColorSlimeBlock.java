package vazkii.quark.automation.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlimeBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.arl.util.RegistryHelper;
import vazkii.quark.api.INonSticky;
import vazkii.quark.base.module.Module;

public class ColorSlimeBlock extends SlimeBlock implements INonSticky {

	private final SlimeColor color;
	private final Module module;
	
	public ColorSlimeBlock(SlimeColor color, Module module) {
		super(Block.Properties.create(Material.CLAY, DyeColor.GRAY)
				.hardnessAndResistance(5F, 10F)
				.sound(SoundType.SLIME));
		
		RegistryHelper.registerBlock(this, color.name().toLowerCase() + "_slime_block");
		RegistryHelper.setCreativeTab(this, ItemGroup.DECORATIONS);
		
		this.color = color;
		this.module = module;
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if(module.enabled || group == ItemGroup.SEARCH)
			super.fillItemGroup(group, items);
	}
	
	@Override
	public boolean canStickToBlock(World world, BlockPos pistonPos, BlockPos pos, BlockPos slimePos, BlockState state, BlockState slimeState, Direction direction) {
		SlimeColor ourColor = color;
		Block block = slimeState.getBlock();
		
		if(block instanceof ColorSlimeBlock) {
			SlimeColor otherColor = ((ColorSlimeBlock) block).color;
			if(!ourColor.sticksTo(otherColor) && ourColor != otherColor)
				return false;
		}
		
		if(block == Blocks.SLIME_BLOCK)
			return ourColor.sticksToGreen;

		return true;
	}
	
	@Override
	public boolean isStickyBlock(BlockState state) {
		return true;
	}

	public enum SlimeColor {

		RED(MaterialColor.RED, false, 3, 4), // 0
		BLUE(MaterialColor.BLUE, false, 2, 3), // 1
		CYAN(MaterialColor.CYAN, true, 1), // 2
		MAGENTA(MaterialColor.MAGENTA, false, 0, 1), // 3
		YELLOW(MaterialColor.YELLOW,true, 0); // 4

		SlimeColor(MaterialColor color, boolean sticksToGreen, int... sticksTo) {
			this.color = color;
			this.sticksToGreen = sticksToGreen;
			this.sticksTo = sticksTo;
		}

		public final MaterialColor color;
		public final boolean sticksToGreen;
		private final int[] sticksTo;

		boolean sticksTo(SlimeColor otherVariant) {
			int ord = otherVariant.ordinal();
			for(int i : sticksTo)
				if(i == ord)
					return true;

			return false;
		}

	}

}
