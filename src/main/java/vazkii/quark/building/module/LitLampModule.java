package vazkii.quark.building.module;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import vazkii.quark.base.block.QuarkBlock;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;

/**
 * @author WireSegal
 * Created at 11:04 AM on 10/4/19.
 */
@LoadModule(category = ModuleCategory.BUILDING)
public class LitLampModule extends Module {
    @Override
    public void construct() {
        new QuarkBlock("lit_lamp", this, ItemGroup.DECORATIONS,
                Block.Properties.from(Blocks.REDSTONE_LAMP));
    }
}
