package dev.xkmc.l2menustacker.screen.base;

import dev.xkmc.l2menustacker.init.L2MenuStacker;
import dev.xkmc.l2menustacker.screen.packets.CacheMouseToClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;

import javax.annotation.Nullable;

public enum MenuTriggerType {
	OPEN_MENU, DISABLED;

	public boolean restore(ServerPlayer player, MenuProvider pvd, @Nullable FriendlyByteBuf buf) {
		if (this == MenuTriggerType.OPEN_MENU) {
			if (buf == null) player.openMenu(pvd);
			else player.openMenu(pvd, e -> e.writeBytes(buf));
			return true;
		}
		return false;
	}
}
