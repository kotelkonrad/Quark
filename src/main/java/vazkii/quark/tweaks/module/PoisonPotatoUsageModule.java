package vazkii.quark.tweaks.module;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.quark.base.module.Config;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;

@LoadModule(category = ModuleCategory.TWEAKS, hasSubscriptions = true)
public class PoisonPotatoUsageModule extends Module {

	private static final String TAG_POISONED = "quark:poison_potato_applied";
	
	@Config public static double chance = 0.1;
	@Config public static boolean poisonEffect = true;
	
	@SubscribeEvent
	public void onInteract(EntityInteract event) {
		if(event.getTarget() instanceof AgeableEntity && event.getItemStack().getItem() == Items.POISONOUS_POTATO) {
			AgeableEntity ageable = (AgeableEntity) event.getTarget();
			if(ageable.isChild() && !isEntityPoisoned(ageable)) {
				if(!event.getWorld().isRemote) {
					if(ageable.world.rand.nextDouble() < chance) {
						ageable.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.5f, 0.25f);
						ageable.world.addParticle(ParticleTypes.ENTITY_EFFECT, ageable.posX, ageable.posY, ageable.posZ, 0.2, 0.8, 0);
						poisonEntity(ageable);
						if (poisonEffect)
							ageable.addPotionEffect(new EffectInstance(Effects.POISON, 200));
					} else {
						ageable.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.5f, 0.5f + ageable.world.rand.nextFloat() / 2);
						ageable.world.addParticle(ParticleTypes.SMOKE, ageable.posX, ageable.posY, ageable.posZ, 0, 0.1, 0);
					}

					if (!event.getPlayer().isCreative())
						event.getItemStack().shrink(1);

				} else event.getPlayer().swingArm(event.getHand());

			}
		}
	}
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {
		if(event.getEntity() instanceof AnimalEntity) {
			AnimalEntity animal = (AnimalEntity) event.getEntity();
			if(animal.isChild() && isEntityPoisoned(animal))
				animal.setGrowingAge(-24000);
		}
	}
	
	private boolean isEntityPoisoned(Entity e) {
		return e.getPersistentData().getBoolean(TAG_POISONED);
	}
	
	private void poisonEntity(Entity e) {
		e.getPersistentData().putBoolean(TAG_POISONED, true);
	}
	
}
