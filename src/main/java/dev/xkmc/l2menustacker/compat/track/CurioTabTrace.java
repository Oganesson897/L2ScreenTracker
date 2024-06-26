package dev.xkmc.l2menustacker.compat.track;

import dev.xkmc.l2menustacker.screen.base.LayerPopType;
import dev.xkmc.l2menustacker.screen.track.NoData;
import dev.xkmc.l2menustacker.screen.track.TrackedEntryType;
import dev.xkmc.l2tabs.compat.TabCuriosCompat;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public class CurioTabTrace extends TrackedEntryType<NoData> {

	@Override
	public LayerPopType restoreMenuNotifyClient(ServerPlayer player, NoData data, @Nullable Component comp) {
		TabCuriosCompat.openCuriosTab(player);
		return LayerPopType.CLEAR;
	}

}
