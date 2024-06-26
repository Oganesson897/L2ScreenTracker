package dev.xkmc.l2menustacker.screen.track;

import dev.xkmc.l2menustacker.init.L2ScreenTracker;
import dev.xkmc.l2menustacker.screen.base.LayerPopType;
import dev.xkmc.l2menustacker.screen.packets.ScreenType;
import dev.xkmc.l2menustacker.screen.packets.SetScreenToClient;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;

public class InventoryTrace extends TrackedEntryType<NoData> {

	@Override
	public LayerPopType restoreMenuNotifyClient(ServerPlayer player, NoData data, @Nullable Component comp) {
		player.doCloseContainer();
		L2ScreenTracker.PACKET_HANDLER.toClientPlayer(new SetScreenToClient(ScreenType.PLAYER), player);
		return LayerPopType.CLEAR;
	}

}
