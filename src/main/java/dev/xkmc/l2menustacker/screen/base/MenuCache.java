package dev.xkmc.l2menustacker.screen.base;

import dev.xkmc.l2menustacker.init.L2ScreenTrackerConfig;
import dev.xkmc.l2menustacker.screen.track.MenuProviderTraceData;
import dev.xkmc.l2menustacker.screen.track.TrackedEntry;
import io.netty.buffer.Unpooled;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.connection.ConnectionType;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public record MenuCache(AbstractContainerMenu menu, MenuProvider pvd, Component title,
						MenuTriggerType type, @Nullable RegistryFriendlyByteBuf buf) {

	public static MenuCache of(RegistryAccess access, AbstractContainerMenu menu, MenuProvider next, MenuTriggerType type, @Nullable Consumer<RegistryFriendlyByteBuf> writer) {
		RegistryFriendlyByteBuf buf = null;
		if (writer != null) {
			buf = new RegistryFriendlyByteBuf(Unpooled.buffer(), access, ConnectionType.NEOFORGE);
			writer.accept(buf);
		}
		if (L2ScreenTrackerConfig.SERVER.tabSafeMode.get()) {
			if (buf != null && buf.writerIndex() > 0) {
				type = MenuTriggerType.DISABLED;
			} else {
				type = MenuTriggerType.OPEN_MENU;
			}
			buf = null;
		}
		return new MenuCache(menu, next, next.getDisplayName(), type, buf);
	}

	public TrackedEntry<?> constructEntry() {
		return TrackedEntry.of(ScreenTrackerRegistry.TE_MENU_PROVIDER.get(), new MenuProviderTraceData(this));
	}

	public boolean restore(ServerPlayer player) {
		return type.restore(player, pvd, buf);
	}

}
