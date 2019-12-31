package vazkii.quark.world.gen;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import vazkii.quark.base.world.generator.Generator;
import vazkii.quark.world.block.BlossomSaplingBlock.BlossomTree;
import vazkii.quark.world.config.BlossomTreeConfig;

public class BlossomTreeGenerator extends Generator {

	BlossomTreeConfig config;
	BlossomTree tree;
	
	public BlossomTreeGenerator(BlossomTreeConfig config, BlossomTree tree) {
		super(config.dimensions);
		this.config = config;
		this.tree = tree;
	}

	@Override
	public void generateChunk(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos) {
		BlockPos placePos = pos.add(rand.nextInt(16), 0, rand.nextInt(16));
		if(config.biomeTypes.canSpawn(getBiome(generator, placePos)) && rand.nextInt(config.rarity) == 0) {
			placePos = worldIn.getHeight(Type.MOTION_BLOCKING, placePos).down();
			
			BlockState state = worldIn.getBlockState(placePos);
			if(state.getBlock().canSustainPlant(state, worldIn, pos, Direction.UP, (SaplingBlock) Blocks.OAK_SAPLING))
				tree.feature.place(worldIn, generator, rand, placePos.up(), NoFeatureConfig.NO_FEATURE_CONFIG);
		}
	}

}
