package vazkii.quark.automation.module;

import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.quark.automation.ai.FormShulkerGoal;
import vazkii.quark.base.module.Config;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;

@LoadModule(category = ModuleCategory.AUTOMATION, hasSubscriptions = true)
public class EndermitesFormShulkersModule extends Module {

	@Config public static double chance = 0.005;
	
	@SubscribeEvent
	public void onEnterChunk(EnteringChunk event) {
		if(event.getEntity() instanceof EndermiteEntity) {
			EndermiteEntity endermite = (EndermiteEntity) event.getEntity();
            boolean alreadySetUp = endermite.goalSelector.goals.stream().anyMatch((goal) -> goal.getGoal() instanceof TemptGoal);

            if(!alreadySetUp) 
            	endermite.goalSelector.addGoal(2, new FormShulkerGoal(endermite));
		}
	}
	
}
