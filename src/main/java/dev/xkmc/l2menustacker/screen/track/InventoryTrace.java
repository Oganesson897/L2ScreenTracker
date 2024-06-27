package dev.xkmc.l2menustacker.screen.track;

import dev.xkmc.l2menustacker.screen.base.LayerPopType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;

public class InventoryTrace extends TrackedEntryType<NoData> {

	@Override
	public LayerPopType restoreMenuNotifyClient(ServerPlayer player, NoData data, @Nullable Component comp) {
		player.doCloseContainer();
		return LayerPopType.CLEAR;
	}

}
