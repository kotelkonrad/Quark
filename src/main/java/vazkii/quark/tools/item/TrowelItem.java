package vazkii.quark.tools.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import vazkii.arl.util.ItemNBTHelper;
import vazkii.quark.base.handler.MiscUtil;
import vazkii.quark.base.item.QuarkItem;
import vazkii.quark.base.module.Module;
import vazkii.quark.tools.module.TrowelModule;

public class TrowelItem extends QuarkItem {

	private static final String TAG_PLACING_SEED = "placing_seed";
	private static final String TAG_LAST_STACK = "last_stack";
	
	public TrowelItem(Module module) {
		super("trowel", module, new Item.Properties()
				.maxDamage(255)
				.group(ItemGroup.TOOLS));
	}
	
	@Nonnull
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		Hand hand = context.getHand();
		
		List<ItemStack> targets = new ArrayList<>();
		for(int i = 0; i < PlayerInventory.getHotbarSize(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if(!stack.isEmpty() && stack.getItem() instanceof BlockItem)
				targets.add(stack);
		}
		
		ItemStack ourStack = player.getHeldItem(hand);
		if(targets.isEmpty())
			return ActionResultType.PASS;

		long seed = ItemNBTHelper.getLong(ourStack, TAG_PLACING_SEED, 0);
		Random rand = new Random(seed);
		ItemNBTHelper.setLong(ourStack, TAG_PLACING_SEED, rand.nextLong());
		
		ItemStack target = targets.get(rand.nextInt(targets.size()));
		int count = target.getCount();
		ActionResultType result = placeBlock(target, context);
		if(player.isCreative())
			target.setCount(count);
		
		if(result == ActionResultType.SUCCESS) {
			CompoundNBT cmp = target.serializeNBT();
			ItemNBTHelper.setCompound(ourStack, TAG_LAST_STACK, cmp);
			
			MiscUtil.damageStack(player, hand, context.getItem(), 1);
		}
		
		return result;
	}
	
	private ActionResultType placeBlock(ItemStack itemstack, ItemUseContext context) {
		if(itemstack.getItem() instanceof BlockItem) {
			BlockItem item = (BlockItem) itemstack.getItem();
			BlockItemUseContext newContext = new TrowelBlockItemUseContext(context, itemstack);
			return item.tryPlace(newContext);
		}

		return ActionResultType.PASS;
	}

	public static ItemStack getLastStack(ItemStack stack) {
		CompoundNBT cmp = ItemNBTHelper.getCompound(stack, TAG_LAST_STACK, false);
		return ItemStack.read(cmp);
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
		return TrowelModule.maxDamage;
	}
	
	class TrowelBlockItemUseContext extends BlockItemUseContext {

		public TrowelBlockItemUseContext(ItemUseContext context, ItemStack stack) {
			super(context.getWorld(), context.getPlayer(), context.getHand(), stack, 
					new BlockRayTraceResult(context.getHitVec(), context.getFace(), context.getPos(), context.func_221533_k()));
		}
		
	}

}
