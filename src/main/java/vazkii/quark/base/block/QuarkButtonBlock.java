package vazkii.quark.base.block;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.IWorldReader;
import vazkii.arl.util.RegistryHelper;
import vazkii.quark.base.module.Module;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BooleanSupplier;

/**
 * @author WireSegal
 * Created at 9:14 PM on 10/8/19.
 */
public abstract class QuarkButtonBlock extends AbstractButtonBlock implements IQuarkBlock {

    private final Module module;
    private BooleanSupplier enabledSupplier = () -> true;

    public QuarkButtonBlock(String regname, Module module, ItemGroup creativeTab, Properties properties) {
        super(false, properties);
        this.module = module;

        RegistryHelper.registerBlock(this, regname);
        if(creativeTab != null)
            RegistryHelper.setCreativeTab(this, creativeTab);
    }


    @Nonnull
    @Override
    protected abstract SoundEvent getSoundEvent(boolean powered);

    @Override
    public abstract int tickRate(IWorldReader worldIn);

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if(isEnabled() || group == ItemGroup.SEARCH)
            super.fillItemGroup(group, items);
    }

    @Override
    public QuarkButtonBlock setCondition(BooleanSupplier enabledSupplier) {
        this.enabledSupplier = enabledSupplier;
        return this;
    }

    @Override
    public boolean doesConditionApply() {
        return enabledSupplier.getAsBoolean();
    }

    @Nullable
    @Override
    public Module getModule() {
        return module;
    }

}
