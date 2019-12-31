package vazkii.quark.client.module;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeIngameGui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;

@LoadModule(category = ModuleCategory.CLIENT, hasSubscriptions = true, subscribeOn = Dist.CLIENT)
public class ImprovedMountHudModule extends Module {

	@SubscribeEvent
	public void onRenderHUD(RenderGameOverlayEvent.Pre event) {
		if(event.getType() == ElementType.ALL) {
			Minecraft mc = Minecraft.getInstance();
			Entity riding = mc.player.getRidingEntity();
			
			if(riding != null) {
				ForgeIngameGui.renderFood = true;
				if(riding instanceof AbstractHorseEntity)
					ForgeIngameGui.renderJumpBar = mc.gameSettings.keyBindJump.isKeyDown() && mc.currentScreen == null;
			}
		}
	}
	
}
