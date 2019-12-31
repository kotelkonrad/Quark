package vazkii.quark.world.gen;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import vazkii.quark.base.handler.MiscUtil;
import vazkii.quark.base.world.config.DimensionConfig;
import vazkii.quark.base.world.generator.Generator;
import vazkii.quark.world.block.RootBlock;
import vazkii.quark.world.module.CaveRootsModule;

public class CaveRootGenerator extends Generator {

	public CaveRootGenerator(DimensionConfig dimConfig) {
		super(dimConfig);
	}

	@Override
	public void generateChunk(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos corner) {
		for(int i = 0; i < CaveRootsModule.chunkAttempts; i++) {
			int x = rand.nextInt(16);
			int z = rand.nextInt(16);
			int y = rand.nextInt(CaveRootsModule.maxY - CaveRootsModule.minY) + CaveRootsModule.minY;
			
			BlockPos pos = corner.add(x, y, z);
			if(worldIn.isAirBlock(pos)) {
				for(Direction facing : MiscUtil.HORIZONTALS) {
					BlockPos target = pos.offset(facing);
					if(RootBlock.isAcceptableNeighbor(worldIn, target, facing.getOpposite()) && worldIn.isBlockLoaded(pos)) {
						BlockState state = CaveRootsModule.root.getDefaultState().with(RootBlock.getPropertyFor(facing), true);
						worldIn.setBlockState(pos, state, 2);
						RootBlock.growMany(worldIn, rand, pos, state, 0.4F);
					}
				}
			}
		}
	}

}
