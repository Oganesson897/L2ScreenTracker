package dev.xkmc.l2screentracker.click;

import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2screentracker.init.L2STLangData;
import dev.xkmc.l2screentracker.init.L2STTagGen;
import dev.xkmc.l2screentracker.init.L2ScreenTracker;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = L2ScreenTracker.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SlotClickEventHandler {

	@SubscribeEvent
	public static void onScreenRightClick(ScreenEvent.MouseButtonPressed.Pre event) {
		Screen screen = event.getScreen();
		if (screen instanceof AbstractContainerScreen cont) {
			Slot slot = cont.getSlotUnderMouse();
			if (slot == null) return;
			if (slot.getItem().isStackable()) {
				ItemStack stack = slot.getItem();
				if (stack.getCount() > 1) return;
				if (!stack.is(L2STTagGen.QUICK_ACCESS)) return;
			}
			if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
				boolean b1 = slot.container == Proxy.getClientPlayer().getInventory();
				boolean b2 = cont.getMenu().containerId > 0;
				if (b1 || b2) {
					int inv = b1 ? slot.getSlotIndex() : -1;
					int ind = inv == -1 ? slot.index : -1;
					int wid = cont.getMenu().containerId;
					if ((inv >= 0 || ind >= 0)) {
						for (var handler : SlotClickHandler.MAP.values()) {
							if (handler.isAllowed(slot.getItem())) {
								handler.slotClickToServer(ind, inv, wid);
								event.setCanceled(true);
								return;
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void tooltipEvent(ItemTooltipEvent event) {
		if (event.getItemStack().is(L2STTagGen.QUICK_ACCESS_VANILLA))
			event.getToolTip().add(L2STLangData.QUICK_ACCESS.get().withStyle(ChatFormatting.GRAY));
	}


}
