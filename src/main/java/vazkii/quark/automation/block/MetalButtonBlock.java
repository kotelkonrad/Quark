package vazkii.quark.automation.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.IWorldReader;
import vazkii.quark.base.block.QuarkButtonBlock;
import vazkii.quark.base.module.Module;

import javax.annotation.Nonnull;

/**
 * @author WireSegal
 * Created at 9:14 PM on 10/8/19.
 */
public class MetalButtonBlock extends QuarkButtonBlock {

    private final int speed;

    public MetalButtonBlock(String regname, Module module, int speed) {
        super(regname, module, ItemGroup.REDSTONE,
                Block.Properties.create(Material.MISCELLANEOUS)
                        .doesNotBlockMovement()
                        .hardnessAndResistance(0.5F)
                        .sound(SoundType.METAL));
        this.speed = speed;
    }

    @Override
    public int tickRate(IWorldReader worldIn) {
        return speed;
    }

    @Nonnull
    @Override
    protected SoundEvent getSoundEvent(boolean powered) {
        return powered ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
    }
}
