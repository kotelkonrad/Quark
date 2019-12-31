package vazkii.quark.building.module;

import net.minecraft.block.Block;import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameRules;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.quark.base.block.QuarkBlock;
import vazkii.quark.base.item.QuarkItem;
import vazkii.quark.base.module.Config;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.building.block.CandleBlock;

@LoadModule(category = ModuleCategory.BUILDING, hasSubscriptions = true)
public class TallowAndCandlesModule extends Module {

	@Config
	public static boolean candlesFall = true;

	@Config
	@Config.Min(0)
	public static int minDrop = 1;
	@Config
	@Config.Min(0)
	public static int maxDrop = 3;

	@Config
	@Config.Min(0)
	public static int tallowBurnTime = 200;

	@Config
	@Config.Min(0)
	@Config.Max(15)
	public static double enchantPower = 0.5;

	public static Item tallow;
	public static Block tallow_block;

	@Override
	public void construct() {
		tallow = new QuarkItem("tallow", this, new Item.Properties().group(ItemGroup.MATERIALS));

		for(DyeColor dye : DyeColor.values())
			new CandleBlock(dye.getName() + "_candle", this, dye);
		
		tallow_block = new QuarkBlock("tallow_block", this, ItemGroup.BUILDING_BLOCKS, Block.Properties.from(Blocks.YELLOW_TERRACOTTA).sound(SoundType.CLOTH));
	}

	@SubscribeEvent
	public void onDrops(LivingDropsEvent event) {
		LivingEntity e = event.getEntityLiving();
		if (e instanceof PigEntity && !((PigEntity) e).isChild() && maxDrop > 0 && e.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
			int drops = minDrop + e.world.rand.nextInt(maxDrop - minDrop + 1);
			if (drops > 0)
				event.getDrops().add(new ItemEntity(e.world, e.posX, e.posY, e.posZ, new ItemStack(tallow, drops)));
		}
	}

	@SubscribeEvent
	public void onFurnaceTimeCheck(FurnaceFuelBurnTimeEvent event) {
		if(tallowBurnTime <= 0)
			return;
		
		Item item = event.getItemStack().getItem();
		if(item == tallow)
			event.setBurnTime(tallowBurnTime);
		else if(item == tallow_block.asItem())
			event.setBurnTime(tallowBurnTime * 9);
	}

}
