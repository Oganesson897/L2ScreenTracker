package dev.xkmc.l2menustacker.compat;

import dev.xkmc.l2tabs.compat.TabCuriosCompat;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

public class L2CuriosCompat {

	public static void onStartup() {
		if (ModList.get().isLoaded("curios")) {
			CuriosTrackCompatImpl.get().onStartUp();
		}
	}

	public static void openCuriosInv(ServerPlayer player) {
		if (ModList.get().isLoaded("curios")) {
			CuriosTrackCompatImpl.get().openCurioImpl(player);
		}
	}

	public static ItemStack getItemFromSlot(Player player, int slot) {
		if (ModList.get().isLoaded("curios")) {
			return CuriosTrackCompatImpl.get().getItemFromSlotImpl(player, slot);
		}
		return ItemStack.EMPTY;
	}

	public static void commonSetup() {
		if (ModList.get().isLoaded("curios")) {
			CuriosTrackCompatImpl.get().onCommonSetup();
			if (ModList.get().isLoaded("l2tabs")) {
				CuriosTrackCompatImpl.get().onCompatSetup(TabCuriosCompat.getMenuType());
			}
		}
	}
}
