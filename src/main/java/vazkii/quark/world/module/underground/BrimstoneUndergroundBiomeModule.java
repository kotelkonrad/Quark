package vazkii.quark.world.module.underground;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.BiomeDictionary.Type;
import vazkii.quark.base.block.QuarkBlock;
import vazkii.quark.base.handler.VariantHandler;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.world.config.UndergroundBiomeConfig;
import vazkii.quark.world.gen.underground.BrimstoneUndergroundBiome;

@LoadModule(category = ModuleCategory.WORLD)
public class BrimstoneUndergroundBiomeModule extends UndergroundBiomeModule {

	public static QuarkBlock brimstone;
	
	@Override
	public void construct() {
		brimstone = new QuarkBlock("brimstone", this, ItemGroup.BUILDING_BLOCKS, 
				Block.Properties.create(Material.ROCK, MaterialColor.RED)
				.hardnessAndResistance(1.5F, 10F)
				.sound(SoundType.STONE));
		
		VariantHandler.addSlabStairsWall(brimstone);
		VariantHandler.addSlabStairsWall(new QuarkBlock("brimstone_bricks", this, ItemGroup.BUILDING_BLOCKS, Block.Properties.from(brimstone)));
		
		super.construct();
	}
	
	@Override
	protected String getBiomeName() {
		return "brimstone";
	}
	
	@Override
	protected UndergroundBiomeConfig getBiomeConfig() {
		return new UndergroundBiomeConfig(new BrimstoneUndergroundBiome(), 80, Type.MESA);
	}

}
