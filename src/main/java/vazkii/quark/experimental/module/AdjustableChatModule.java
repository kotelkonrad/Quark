package vazkii.quark.experimental.module;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.quark.base.module.Config;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;

@LoadModule(category = ModuleCategory.EXPERIMENTAL, enabledByDefault = false, hasSubscriptions = true, subscribeOn = Dist.CLIENT)
public class AdjustableChatModule extends Module {

	@Config public static int horizontalShift = 0;
	@Config public static int verticalShift = 0;
	
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void pre(RenderGameOverlayEvent.Pre event) {
		if(event.getType() == ElementType.CHAT)
			GlStateManager.translated(horizontalShift, verticalShift, 0);
	}
	
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void post(RenderGameOverlayEvent.Post event) {
		if(event.getType() == ElementType.CHAT)
			GlStateManager.translated(-horizontalShift, -verticalShift, 0);
	}
	
}
