package vazkii.quark.building.module;

import java.util.List;
import java.util.function.BooleanSupplier;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;
import vazkii.quark.base.block.QuarkBlock;
import vazkii.quark.base.block.QuarkPillarBlock;
import vazkii.quark.base.handler.FuelHandler;
import vazkii.quark.base.module.Config;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.building.block.BeaconBaseBlock;
import vazkii.quark.building.block.BurnForeverBlock;

@LoadModule(category = ModuleCategory.BUILDING)
public class CompressedBlocksModule extends Module {

	@Config(name = "Charcoal Block and Blaze Lantern Stay On Fire Forever")
	public static boolean burnsForever = true;

	@Config(name = "Charcoal Block Fuel Time")
	@Config.Min(0)
	public static int charcoalBlockFuelTime = 16000;

	@Config(name = "Blaze Lantern Fuel Time")
	@Config.Min(0)
	public static int blazeLanternFuelTime = 24000;

	@Config(name = "Stick Block Fuel Time")
	@Config.Min(0)
	public static int stickBlockFuelTime = 900;
	
	@Config(flag = "charcoal_block") public static boolean enableCharcoalBlock = true;
	@Config(flag = "sugar_cane_block") public static boolean enableSugarCaneBlock = true;
	@Config(flag = "bamboo_block") public static boolean enableBambooBlock = true;
	@Config(flag = "cactus_block") public static boolean enableCactusBlock = true;
	@Config(flag = "chorus_fruit_block") public static boolean enableChorusFruitBlock = true;
	@Config(flag = "stick_block") public static boolean enableStickBlock = true;

	@Config(flag = "apple_crate") public static boolean enableAppleCrate = true;
	@Config(flag = "golden_apple_crate") public static boolean enableGoldenAppleCrate = true;
	@Config(flag = "potato_crate") public static boolean enablePotatoCrate = true;
	@Config(flag = "carrot_crate") public static boolean enableCarrotCrate = true;
	@Config(flag = "beetroot_crate") public static boolean enableBeetrootCrate = true;

	@Config(flag = "cocoa_beans_sack") public static boolean enableCocoaBeanSack = true;
	@Config(flag = "nether_wart_sack") public static boolean enableNetherWartSack = true;
	@Config(flag = "gunpowder_sack") public static boolean enableGunpowderSack = true;
	@Config(flag = "berry_sack") public static boolean enableBerrySack = true;

	@Config(flag = "blaze_lantern") public static boolean enableBlazeLantern = true;

	public static Block charcoal_block, stick_block, blaze_lantern;

	private final List<Block> compostable = Lists.newArrayList();

	@Override
	public void construct() {
		charcoal_block = new BurnForeverBlock("charcoal_block", this, ItemGroup.BUILDING_BLOCKS,
				Block.Properties.create(Material.ROCK, MaterialColor.BLACK)
						.hardnessAndResistance(5F, 10F)
						.sound(SoundType.STONE))
				.setCondition(() -> enableCharcoalBlock);
		
		pillar("sugar_cane", MaterialColor.LIME, true, () -> enableSugarCaneBlock);
		pillar("bamboo", MaterialColor.YELLOW, true, () -> enableBambooBlock);
		pillar("cactus", MaterialColor.GREEN, true, () -> enableCactusBlock);
		pillar("chorus_fruit", MaterialColor.PURPLE, false, () -> enableChorusFruitBlock);
		stick_block = pillar("stick", MaterialColor.WOOD, false, () -> enableStickBlock);

		new BeaconBaseBlock("golden_apple_crate", this, ItemGroup.DECORATIONS,
				Block.Properties.create(Material.WOOD, MaterialColor.GOLD)
						.hardnessAndResistance(1.5F)
						.sound(SoundType.WOOD))
				.setCondition(() -> enableGoldenAppleCrate);

		crate("apple", MaterialColor.RED, true, () -> enableAppleCrate);
		crate("potato", MaterialColor.ADOBE, true, () -> enablePotatoCrate);
		crate("carrot", MaterialColor.ORANGE_TERRACOTTA, true, () -> enableCarrotCrate);
		crate("beetroot", MaterialColor.RED, true, () -> enableBeetrootCrate);

		sack("cocoa_beans", MaterialColor.BROWN, true, () -> enableCocoaBeanSack);
		sack("nether_wart", MaterialColor.RED, false, () -> enableNetherWartSack);
		sack("gunpowder", MaterialColor.GRAY, false, () -> enableGunpowderSack);
		sack("berry", MaterialColor.RED, true, () -> enableBerrySack);

		blaze_lantern = new BurnForeverBlock("blaze_lantern", this, ItemGroup.BUILDING_BLOCKS,
				Block.Properties.create(Material.GLASS, DyeColor.YELLOW)
				.hardnessAndResistance(0.3F)
				.sound(SoundType.GLASS)
				.lightValue(15))
		.setCondition(() -> enableBlazeLantern);
	}

	@Override
	public void setup() {
		for (Block block : compostable)
			ComposterBlock.CHANCES.put(block.asItem(), 1F);
		
		FuelHandler.addFuel(stick_block, stickBlockFuelTime);
		FuelHandler.addFuel(charcoal_block, charcoalBlockFuelTime);
		FuelHandler.addFuel(blaze_lantern, blazeLanternFuelTime);
	}

	private Block pillar(String name, MaterialColor color, boolean compost, BooleanSupplier cond) {
		Block block = new QuarkPillarBlock(name + "_block", this, ItemGroup.BUILDING_BLOCKS,
				Block.Properties.create(Material.WOOD, color)
				.hardnessAndResistance(0.5F)
				.sound(SoundType.WOOD))
		.setCondition(cond);

		if (compost)
			compostable.add(block);
		return block;
	}
	
	private Block crate(String name, MaterialColor color, boolean compost, BooleanSupplier cond) {
		Block block = new QuarkBlock(name + "_crate", this, ItemGroup.DECORATIONS,
				Block.Properties.create(Material.WOOD, color)
				.hardnessAndResistance(1.5F)
				.sound(SoundType.WOOD))
		.setCondition(cond);

		if (compost)
			compostable.add(block);
		return block;
	}

	private Block sack(String name, MaterialColor color, boolean compost, BooleanSupplier cond) {
		Block block = new QuarkBlock(name + "_sack", this, ItemGroup.DECORATIONS,
				Block.Properties.create(Material.WOOL, color)
				.hardnessAndResistance(0.5F)
				.sound(SoundType.CLOTH))
		.setCondition(cond);

		if (compost)
			compostable.add(block);
		return block;
	}

}
