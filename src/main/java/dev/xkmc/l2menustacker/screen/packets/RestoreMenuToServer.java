package dev.xkmc.l2menustacker.screen.packets;

import dev.xkmc.l2menustacker.init.L2ScreenTracker;
import dev.xkmc.l2menustacker.screen.base.ClientCloseResult;
import dev.xkmc.l2menustacker.screen.base.ScreenTracker;
import dev.xkmc.l2menustacker.screen.triggers.ExitMenuTrigger;
import dev.xkmc.l2serial.network.SerialPacketBase;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public record RestoreMenuToServer(int wid)
		implements SerialPacketBase<RestoreMenuToServer> {

	@Override
	public void handle(Player player) {
		if (!(player instanceof ServerPlayer sp)) return;
		if (wid < 0) {
			if (wid == ClientCloseResult.POP_ALL.id && ScreenTracker.size(sp) >= 2) {
				ExitMenuTrigger.EXIT_MENU.trigger(sp, true);
			}
			ScreenTracker.removeAll(sp);
			return;
		}
		AbstractContainerMenu menu = player.containerMenu;
		if (menu.containerId != wid || !ScreenTracker.get(player).serverRestore(sp, wid)) {
			L2ScreenTracker.PACKET_HANDLER.toClientPlayer(new SetScreenToClient(ScreenType.NONE), sp);
			ExitMenuTrigger.EXIT_MENU.trigger(sp, false);
		}
	}

}
