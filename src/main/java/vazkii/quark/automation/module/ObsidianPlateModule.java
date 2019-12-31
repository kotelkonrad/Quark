package vazkii.quark.automation.module;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import vazkii.quark.automation.block.ObsidianPressurePlateBlock;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;

/**
 * @author WireSegal
 * Created at 9:51 PM on 10/8/19.
 */
@LoadModule(category = ModuleCategory.AUTOMATION)
public class ObsidianPlateModule extends Module {
    @Override
    public void construct() {
        new ObsidianPressurePlateBlock("obsidian_pressure_plate", this, ItemGroup.REDSTONE,
                Block.Properties.create(Material.ROCK, MaterialColor.BLACK)
                        .doesNotBlockMovement()
                        .hardnessAndResistance(2F, 1200.0F));
    }
}
