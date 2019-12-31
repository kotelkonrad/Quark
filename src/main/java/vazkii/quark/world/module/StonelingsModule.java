package vazkii.quark.world.module;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import vazkii.arl.util.RegistryHelper;
import vazkii.quark.base.item.QuarkSpawnEggItem;
import vazkii.quark.base.module.Config;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.base.world.EntitySpawnHandler;
import vazkii.quark.base.world.config.BiomeTypeConfig;
import vazkii.quark.base.world.config.DimensionConfig;
import vazkii.quark.base.world.config.EntitySpawnConfig;
import vazkii.quark.world.client.render.StonelingRenderer;
import vazkii.quark.world.entity.StonelingEntity;
import vazkii.quark.world.item.DiamondHeartItem;

@LoadModule(category = ModuleCategory.WORLD, hasSubscriptions = true)
public class StonelingsModule extends Module {
	public static EntityType<StonelingEntity> stonelingType;

	@Config
	public static int maxYLevel = 24;
	@Config
	public static DimensionConfig dimensions = DimensionConfig.overworld(false);
	@Config 
	public static EntitySpawnConfig spawnConfig = new EntitySpawnConfig(80, 1, 1, new BiomeTypeConfig(true, BiomeDictionary.Type.VOID));
	@Config(flag = "stoneling_drop_diamond_heart")
	public static boolean enableDiamondHeart = true;
	@Config
	public static boolean cautiousStonelings = true;
	@Config
	public static boolean tamableStonelings = true;

	public static Item diamondHeart;

	@Override
	public void construct() {
		diamondHeart = new DiamondHeartItem("diamond_heart", this, new Item.Properties().group(ItemGroup.MISC));

		stonelingType = EntityType.Builder.create(StonelingEntity::new, EntityClassification.CREATURE)
				.size(0.5F, 0.9F)
				.setTrackingRange(80)
				.setUpdateInterval(3)
				.setShouldReceiveVelocityUpdates(true)
				.setCustomClientFactory((spawnEntity, world) -> new StonelingEntity(stonelingType, world))
				.build("stoneling");
		RegistryHelper.register(stonelingType, "stoneling");

		EntitySpawnHandler.registerSpawn(this, stonelingType, EntityClassification.MONSTER, PlacementType.ON_GROUND, Type.MOTION_BLOCKING_NO_LEAVES, StonelingEntity::spawnPredicate, spawnConfig);
		EntitySpawnHandler.addEgg(stonelingType, 0xA1A1A1, 0x505050, spawnConfig);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void clientSetup() {
		RenderingRegistry.registerEntityRenderingHandler(StonelingEntity.class, StonelingRenderer::new);
	}

}
