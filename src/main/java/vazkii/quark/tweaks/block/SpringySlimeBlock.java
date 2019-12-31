/**
 * This class was created by <WireSegal>. It's distributed as
 * part of the Quark Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Quark
 * <p>
 * Quark is Open Source and distributed under the
 * CC-BY-NC-SA 3.0 License: https://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB
 * <p>
 * File Created @ [May 20, 2019, 10:26 AM (EST)]
 */
package vazkii.quark.tweaks.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SlimeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import vazkii.quark.tweaks.module.SpringySlimeModule;

import javax.annotation.Nonnull;

public class SpringySlimeBlock extends SlimeBlock {
    public SpringySlimeBlock(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        SpringySlimeModule.collideWithSlimeBlock(pos, entity);
    }

    @Override
    public void onLanded(@Nonnull IBlockReader world, Entity entity) {
        // Override slime block behavior, as it's handled in SpringySlime
        entity.setMotion(entity.getMotion().mul(1, 0, 1));
    }
}
