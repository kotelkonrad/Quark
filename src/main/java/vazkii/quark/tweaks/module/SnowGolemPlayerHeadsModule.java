package vazkii.quark.tweaks.module;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.arl.util.ItemNBTHelper;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;

@LoadModule(category = ModuleCategory.TWEAKS, hasSubscriptions = true)
public class SnowGolemPlayerHeadsModule extends Module {

	@SubscribeEvent
	public void onDrops(LivingDropsEvent event) {
		Entity e = event.getEntity();

		if(e.hasCustomName() && e instanceof SnowGolemEntity && event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof WitchEntity) {
			SnowGolemEntity snowman = (SnowGolemEntity) e;
			if(snowman.isPumpkinEquipped()) { 
				ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
				ItemNBTHelper.setString(stack, "SkullOwner", e.getCustomName().getUnformattedComponentText());
				event.getDrops().add(new ItemEntity(e.getEntityWorld(), e.posX, e.posY, e.posZ, stack));
			}
		}
	}
	
}
