package vazkii.quark.building.module;

import java.util.function.BooleanSupplier;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import vazkii.quark.base.block.QuarkBlock;
import vazkii.quark.base.block.QuarkPillarBlock;
import vazkii.quark.base.handler.VariantHandler;
import vazkii.quark.base.module.Config;
import vazkii.quark.base.module.ConfigFlagManager;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.base.module.ModuleLoader;
import vazkii.quark.world.module.NewStoneTypesModule;

@LoadModule(category = ModuleCategory.BUILDING)
public class MoreStoneVariantsModule extends Module {

	@Config(flag = "stone_bricks") public boolean enableBricks = true;
	@Config(flag = "stone_chiseled") public boolean enableChiseledBricks = true;
	@Config(flag = "stone_pavement") public boolean enablePavement = true;
	@Config(flag = "stone_pillar") public boolean enablePillar = true;
	
	@Override
	public void construct() {
		BooleanSupplier _true = () -> true;
		add("granite", MaterialColor.DIRT, _true);
		add("diorite", MaterialColor.QUARTZ, _true);
		add("andesite", MaterialColor.STONE, _true);
		
		add("marble", MaterialColor.QUARTZ, () -> ModuleLoader.INSTANCE.isModuleEnabled(NewStoneTypesModule.class) && NewStoneTypesModule.enableMarble);
		add("limestone", MaterialColor.STONE, () -> ModuleLoader.INSTANCE.isModuleEnabled(NewStoneTypesModule.class) && NewStoneTypesModule.enableLimestone);
		add("jasper", MaterialColor.RED_TERRACOTTA, () -> ModuleLoader.INSTANCE.isModuleEnabled(NewStoneTypesModule.class) && NewStoneTypesModule.enableJasper);
		add("slate", MaterialColor.ICE, () -> ModuleLoader.INSTANCE.isModuleEnabled(NewStoneTypesModule.class) && NewStoneTypesModule.enableSlate);
		add("basalt", MaterialColor.BLACK, () -> ModuleLoader.INSTANCE.isModuleEnabled(NewStoneTypesModule.class) && NewStoneTypesModule.enableBasalt);
	}
	
	@Override
	public void pushFlags(ConfigFlagManager manager) {
		manager.putFlag(this, "granite", true);
		manager.putFlag(this, "diorite", true);
		manager.putFlag(this, "andesite", true);
	}
	
	private void add(String name, MaterialColor color, BooleanSupplier cond) {
		Block.Properties props = Block.Properties.create(Material.ROCK, color).hardnessAndResistance(1.5F, 6.0F);
		
		QuarkBlock bricks = new QuarkBlock(name + "_bricks", this, ItemGroup.BUILDING_BLOCKS, props).setCondition(() -> cond.getAsBoolean() && enableBricks);
		VariantHandler.addSlabStairsWall(bricks);
		
		new QuarkBlock("chiseled_" + name + "_bricks", this, ItemGroup.BUILDING_BLOCKS, props).setCondition(() -> cond.getAsBoolean() && enableBricks && enableChiseledBricks);
		new QuarkBlock(name + "_pavement", this, ItemGroup.BUILDING_BLOCKS, props).setCondition(() -> cond.getAsBoolean() && enablePavement);
		new QuarkPillarBlock(name + "_pillar", this, ItemGroup.BUILDING_BLOCKS, props).setCondition(() -> cond.getAsBoolean() && enablePillar);
	}
	
}
