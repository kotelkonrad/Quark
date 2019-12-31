package vazkii.quark.management.module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import vazkii.arl.util.RegistryHelper;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.base.network.QuarkNetwork;
import vazkii.quark.base.network.message.OpenBoatChestMessage;
import vazkii.quark.management.client.render.ChestPassengerRenderer;
import vazkii.quark.management.entity.ChestPassengerEntity;

import java.util.List;

@LoadModule(category = ModuleCategory.MANAGEMENT, hasSubscriptions = true)
public class ChestsInBoatsModule extends Module {
	public static EntityType<ChestPassengerEntity> chestPassengerEntityType;

	@Override
	public void construct() {
		chestPassengerEntityType = EntityType.Builder.<ChestPassengerEntity>create(ChestPassengerEntity::new, EntityClassification.MISC)
				.size(0.8F, 0.8F)
				.setTrackingRange(64)
				.setUpdateInterval(128)
				.setCustomClientFactory((spawnEntity, world) -> new ChestPassengerEntity(chestPassengerEntityType, world))
				.build("chest_passenger");
		RegistryHelper.register(chestPassengerEntityType, "chest_passenger");
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void clientSetup() {
		RenderingRegistry.registerEntityRenderingHandler(ChestPassengerEntity.class, ChestPassengerRenderer::new);
	}

	@SubscribeEvent
	public void onEntityInteract(PlayerInteractEvent.EntityInteractSpecific event) {
		Entity target = event.getTarget();
		PlayerEntity player = event.getPlayer();

		if(target instanceof BoatEntity && target.getPassengers().isEmpty()) {
			Hand hand = Hand.MAIN_HAND;
			ItemStack stack = player.getHeldItemMainhand();
			if(!isChest(stack)) {
				stack = player.getHeldItemOffhand();
				hand = Hand.OFF_HAND;
			}

			if(isChest(stack)) {
				World world = event.getWorld();
				
				if(!event.getWorld().isRemote) {
					ItemStack chestStack = stack.copy();
					chestStack.setCount(1);
					if (!player.isCreative())
						stack.shrink(1);

					ChestPassengerEntity passenger = new ChestPassengerEntity(world, chestStack);
					passenger.setPosition(target.posX, target.posY, target.posZ);
					passenger.rotationYaw = target.rotationYaw;
					passenger.startRiding(target, true);
					world.addEntity(passenger);
				}
				
				player.swingArm(hand);
				event.setCancellationResult(ActionResultType.SUCCESS);
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	@OnlyIn(Dist.CLIENT)
	public void onOpenGUI(GuiOpenEvent event) {
		PlayerEntity player = Minecraft.getInstance().player;
		if(player != null && event.getGui() instanceof InventoryScreen && player.isPassenger()) {
			Entity riding = player.getRidingEntity();
			if(riding instanceof BoatEntity) {
				List<Entity> passengers = riding.getPassengers();
				for(Entity passenger : passengers)
					if(passenger instanceof ChestPassengerEntity) {
						QuarkNetwork.sendToServer(new OpenBoatChestMessage());
						event.setCanceled(true);
						return;
					}
			}
		}
	}
	
	private boolean isChest(ItemStack stack) {
		if (stack.isEmpty())
			return false;

		return stack.getItem().isIn(Tags.Items.CHESTS) && stack.getItem() != Items.ENDER_CHEST;
	}
}
