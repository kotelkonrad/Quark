package vazkii.quark.world.gen;

import java.util.Random;
import java.util.function.BooleanSupplier;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import vazkii.quark.base.world.generator.multichunk.ClusterBasedGenerator;
import vazkii.quark.world.config.BigStoneClusterConfig;
import vazkii.quark.world.module.BigStoneClustersModule;

public class BigStoneClusterGenerator extends ClusterBasedGenerator {

	private final BigStoneClusterConfig config;
	private final BlockState placeState;
	
	public BigStoneClusterGenerator(BigStoneClusterConfig config, BlockState placeState, BooleanSupplier condition) {
		super(config.dimensions, () -> config.enabled && condition.getAsBoolean(), config, (long) placeState.getBlock().getRegistryName().toString().hashCode());
		this.config = config;
		this.placeState = placeState;
	}

	@Override
	public boolean isSourceValid(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, BlockPos pos) {
		return config.biomes.canSpawn(getBiome(generator, pos));
	}

	@Override
	public BlockPos[] getSourcesInChunk(Random random, ChunkGenerator<? extends GenerationSettings> generator, BlockPos chunkLeft) {
		int chance = config.rarity;

		BlockPos[] sources;
		if(chance > 0 && random.nextInt(chance) == 0) {
			sources = new BlockPos[1];
			int lower = Math.abs(config.minYLevel);
			int range = Math.abs(config.maxYLevel - config.minYLevel);

			BlockPos pos = chunkLeft.add(random.nextInt(16), random.nextInt(range) + lower, random.nextInt(16));
			sources[0] = pos;
		} else sources = new BlockPos[0];

		return sources;
	}
	
	@Override
	public String toString() {
		return "BigStoneClusterGenerator[" + placeState + "]";
	}

	@Override
	public IGenerationContext createContext(BlockPos src, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos chunkCorner, IWorld world) {
		return pos -> {
			if(canPlaceBlock(world, pos))
				world.setBlockState(pos, placeState, 0);
		};
	}
	
	private boolean canPlaceBlock(IWorld world, BlockPos pos) {
		return BigStoneClustersModule.blockReplacePredicate.test(world.getBlockState(pos).getBlock());
	}
	
}
