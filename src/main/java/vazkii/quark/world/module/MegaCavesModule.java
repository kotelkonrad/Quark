package vazkii.quark.world.module;

import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BiomeDictionary.Type;
import vazkii.quark.base.module.Config;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.base.world.WorldGenHandler;
import vazkii.quark.base.world.WorldGenWeights;
import vazkii.quark.base.world.config.ClusterSizeConfig;
import vazkii.quark.base.world.config.DimensionConfig;
import vazkii.quark.world.gen.MegaCaveGenerator;

@LoadModule(category = ModuleCategory.WORLD)
public class MegaCavesModule extends Module {

	@Config public DimensionConfig dimensions = DimensionConfig.overworld(false);
	@Config public ClusterSizeConfig spawnSettings = new ClusterSizeConfig(800, 80, 25, 30, 10, true, Type.OCEAN, Type.BEACH)
			.setYLevels(10, 20);
	
	@Override
	public void setup() {
		WorldGenHandler.addGenerator(this, new MegaCaveGenerator(dimensions, spawnSettings), GenerationStage.Decoration.UNDERGROUND_DECORATION, WorldGenWeights.UNDERGROUND_OPEN_ROOMS);
	}
	
}
