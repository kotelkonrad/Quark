/**
 * This class was created by <WireSegal>. It's distributed as
 * part of the Quark Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Quark
 * <p>
 * Quark is Open Source and distributed under the
 * CC-BY-NC-SA 3.0 License: https://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB
 * <p>
 * File Created @ [Jul 13, 2019, 12:17 AM (EST)]
 */
package vazkii.quark.world.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import vazkii.quark.world.entity.FoxhoundEntity;

import java.util.EnumSet;

public class SleepGoal extends Goal {

	private final FoxhoundEntity foxhound;
	private boolean isSleeping;

	public SleepGoal(FoxhoundEntity foxhound) {
		this.foxhound = foxhound;
		this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK, Flag.TARGET));
	}

	@Override
	public boolean shouldExecute() {
		if (!this.foxhound.isTamed() || this.foxhound.isInWater() || !this.foxhound.onGround)
			return false;
		else {
			LivingEntity living = this.foxhound.getOwner();

			if (living == null) return true;
			else
				return (!(this.foxhound.getDistanceSq(living) < 144.0D) || living.getRevengeTarget() == null) && this.isSleeping;
		}
	}

	@Override
	public void startExecuting() {
		this.foxhound.getNavigator().clearPath();
		this.foxhound.getAISit().setSitting(true);
		this.foxhound.setSitting(true);
		this.foxhound.setSleeping(true);
	}

	@Override
	public void resetTask() {
		this.foxhound.getAISit().setSitting(false);
		this.foxhound.setSitting(false);
		this.foxhound.setSleeping(false);
	}

	public void setSleeping(boolean sitting) {
		this.isSleeping = sitting;
	}
}
