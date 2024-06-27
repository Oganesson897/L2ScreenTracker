package dev.xkmc.l2menustacker.screen.base;

import dev.xkmc.l2core.util.Proxy;
import dev.xkmc.l2menustacker.init.L2MenuStacker;
import dev.xkmc.l2menustacker.screen.packets.RestoreMenuToServer;
import dev.xkmc.l2menustacker.screen.packets.ScreenType;
import dev.xkmc.l2menustacker.screen.track.TrackedEntry;
import dev.xkmc.l2tabs.tabs.core.TabBase;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;

public class ScreenTrackerClient {

	public static void cacheMouse() {
		TabBase.cacheMousePos();
	}

	private static ScreenTracker getClient() {
		LocalPlayer player = Proxy.getClientPlayer();
		assert player != null;
		return ScreenTracker.get(player);
	}

	/**
	 * called when screen is about to close. Return true to prevent screen closure.
	 */
	public static boolean onClientClose(int wid) {
		ScreenTracker tracker = getClient();
		var result = onClientCloseImpl(tracker, wid);
		if (result == ClientCloseResult.REMAIN) {
			tracker.isWaiting = true;
			L2MenuStacker.PACKET_HANDLER.toServer(new RestoreMenuToServer(wid));
			return true;
		}
		tracker.stack.clear();
		L2MenuStacker.PACKET_HANDLER.toServer(new RestoreMenuToServer(result.id));
		return false;
	}

	public static void clientAddLayer(TrackedEntry<?> entry, int toRemove, int wid) {
		ScreenTracker tracker = getClient();
		for (int i = 0; i < toRemove; i++) {
			if (!tracker.stack.isEmpty()) {
				tracker.stack.pop();
			} else break;
		}
		tracker.wid = wid;
		tracker.stack.add(entry);
	}

	public static void clientClear(ScreenType type) {
		ScreenTracker tracker = getClient();
		tracker.isWaiting = false;
		tracker.stack.clear();
		type.perform();
	}

	private static ClientCloseResult onClientCloseImpl(ScreenTracker tracker, int wid) {
		if (Screen.hasShiftDown() || tracker.isWaiting) {
			// second exit: close screen
			tracker.isWaiting = false;
			return ClientCloseResult.POP_ALL;
		}
		if (tracker.stack.isEmpty()) return ClientCloseResult.FAIL;
		return tracker.wid == wid ? ClientCloseResult.REMAIN : ClientCloseResult.FAIL;
	}

	public static void clientPop(LayerPopType type, int wid) {
		ScreenTracker tracker = getClient();
		tracker.isWaiting = false;
		if (type == LayerPopType.REMAIN) {
			tracker.stack.pop();
		} else {
			tracker.stack.clear();
		}
		tracker.wid = wid;
	}

}