package vazkii.quark.automation.module;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import vazkii.arl.util.RegistryHelper;
import vazkii.quark.automation.block.WeatherSensorBlock;
import vazkii.quark.automation.tile.WeatherSensorTileEntity;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;

/**
 * @author WireSegal
 * Created at 9:11 AM on 8/26/19.
 */
@LoadModule(category = ModuleCategory.AUTOMATION)
public class WeatherSensorModule extends Module {
    public static TileEntityType<WeatherSensorTileEntity> weatherSensorTEType;

    @Override
    public void construct() {
        Block weatherSensor = new WeatherSensorBlock("weather_sensor", this, ItemGroup.REDSTONE,
                Block.Properties.create(Material.ROCK, MaterialColor.MAGENTA).hardnessAndResistance(0.2F));
        weatherSensorTEType = TileEntityType.Builder.create(WeatherSensorTileEntity::new, weatherSensor).build(null);
        RegistryHelper.register(weatherSensorTEType, "weather_sensor");
    }

}
