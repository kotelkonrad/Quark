package vazkii.quark.world.module.underground;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.BiomeDictionary;
import vazkii.quark.base.Quark;
import vazkii.quark.base.block.QuarkPaneBlock;
import vazkii.quark.base.module.Config;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.world.block.CaveCrystalBlock;
import vazkii.quark.world.config.UndergroundBiomeConfig;
import vazkii.quark.world.gen.underground.CaveCrystalUndergroundBiome;

import java.util.List;

@LoadModule(category = ModuleCategory.WORLD)
public class CaveCrystalUndergroundBiomeModule extends UndergroundBiomeModule {

	@Config
	@Config.Min(value = 0)
	@Config.Max(value = 1)
	public static double crystalSpawnChance = 0.025;

	@Config
	public static boolean crystalsGrowInLava = false;
	
	@Config(description = "The chance that a crystal can grow, this is on average 1 in X world ticks, set to a higher value to make them grow slower. Minimum is 1, for every tick. Set to 0 to disable growth.")
	public static int caveCrystalGrowthChance = 5;

	@Config(flag = "cave_crystal_runes")
	public static boolean crystalsCraftRunes = true;

	public static List<CaveCrystalBlock> crystals = Lists.newArrayList();
	public static Tag<Block> crystalTag;

	public static Block crystal(int floorIdx) {
		return crystals.get(MathHelper.clamp(floorIdx, 0, crystals.size() - 1));
	}

	@Override
	public void construct() {
		crystals.add(new CaveCrystalBlock("red_crystal", 0xff0000, this, MaterialColor.RED));
		crystals.add(new CaveCrystalBlock("orange_crystal", 0xff8000, this, MaterialColor.ADOBE));
		crystals.add(new CaveCrystalBlock("yellow_crystal", 0xffff00, this, MaterialColor.YELLOW));
		crystals.add(new CaveCrystalBlock("green_crystal", 0x00ff00, this, MaterialColor.GREEN));
		crystals.add(new CaveCrystalBlock("blue_crystal", 0x00ffff, this, MaterialColor.LIGHT_BLUE)); // *grumbling about the names of colors in the rainbow*
		crystals.add(new CaveCrystalBlock("indigo_crystal", 0x0000ff, this, MaterialColor.BLUE));
		crystals.add(new CaveCrystalBlock("violet_crystal", 0xff00ff, this, MaterialColor.MAGENTA));
		crystals.add(new CaveCrystalBlock("white_crystal", 0xffffff, this, MaterialColor.SNOW));
		crystals.add(new CaveCrystalBlock("black_crystal", 0x000000, this, MaterialColor.BLACK));

		for(CaveCrystalBlock block : crystals)
			new QuarkPaneBlock(block);

		super.construct();
	}

	@Override
	public void setup() {
		super.setup();
		crystalTag = new BlockTags.Wrapper(new ResourceLocation(Quark.MOD_ID, "crystal"));
	}
	
	@Override
	protected String getBiomeName() {
		return "crystal";
	}

	@Override
	protected UndergroundBiomeConfig getBiomeConfig() {
		return new UndergroundBiomeConfig(new CaveCrystalUndergroundBiome(), 400, true, BiomeDictionary.Type.OCEAN)
				.setDefaultSize(42, 18, 22, 8);
	}

}
