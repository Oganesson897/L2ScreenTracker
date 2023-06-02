package dev.xkmc.l2screentracker.compat.track;

import dev.xkmc.l2screentracker.compat.L2CuriosCompat;
import dev.xkmc.l2screentracker.screen.base.LayerPopType;
import dev.xkmc.l2screentracker.screen.track.NoData;
import dev.xkmc.l2screentracker.screen.track.TrackedEntryType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public class CurioInvTrace extends TrackedEntryType<NoData> {

	@Override
	public LayerPopType restoreMenuNotifyClient(ServerPlayer player, NoData data, @Nullable Component comp) {
		L2CuriosCompat.openCuriosInv(player);
		return LayerPopType.CLEAR;
	}

}
