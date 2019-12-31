package vazkii.quark.automation.module;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import vazkii.arl.util.RegistryHelper;
import vazkii.quark.automation.block.ChuteBlock;
import vazkii.quark.automation.tile.ChuteTileEntity;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;

/**
 * @author WireSegal
 * Created at 10:25 AM on 9/29/19.
 */
@LoadModule(category = ModuleCategory.AUTOMATION)
public class ChuteModule extends Module {

    public static TileEntityType<ChuteTileEntity> tileEntityType;

    @Override
    public void construct() {
        Block chute = new ChuteBlock("chute", this, ItemGroup.REDSTONE,
                Block.Properties.create(Material.WOOD)
                        .hardnessAndResistance(2.5F)
                        .sound(SoundType.WOOD));

        tileEntityType = TileEntityType.Builder.create(ChuteTileEntity::new, chute).build(null);
        RegistryHelper.register(tileEntityType, "chute");
    }
}
