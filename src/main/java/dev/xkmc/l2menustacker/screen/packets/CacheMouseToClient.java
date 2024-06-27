package dev.xkmc.l2menustacker.screen.packets;

import dev.xkmc.l2menustacker.screen.base.ScreenTrackerClient;
import dev.xkmc.l2serial.network.SerialPacketBase;
import net.minecraft.world.entity.player.Player;

public record CacheMouseToClient() implements SerialPacketBase<CacheMouseToClient> {

	@Override
	public void handle(Player player) {
		ScreenTrackerClient.cacheMouse();
	}

}
