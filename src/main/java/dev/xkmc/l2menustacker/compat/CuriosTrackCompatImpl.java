package dev.xkmc.l2menustacker.compat;

import dev.xkmc.l2menustacker.compat.track.CurioInvTrace;
import dev.xkmc.l2menustacker.compat.track.CurioSource;
import dev.xkmc.l2menustacker.compat.track.CurioTabTrace;
import dev.xkmc.l2menustacker.init.L2ScreenTracker;
import dev.xkmc.l2menustacker.screen.base.ScreenTrackerRegistry;
import dev.xkmc.l2menustacker.screen.source.MenuSourceRegistry;
import dev.xkmc.l2menustacker.screen.source.PlayerSlot;
import dev.xkmc.l2menustacker.screen.source.SimpleSlotData;
import dev.xkmc.l2menustacker.screen.track.MenuTraceRegistry;
import dev.xkmc.l2menustacker.screen.track.NoData;
import dev.xkmc.l2menustacker.screen.track.TrackedEntry;
import dev.xkmc.l2tabs.compat.CuriosListMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;
import net.neoforged.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.common.CuriosRegistry;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.common.inventory.container.CuriosContainer;
import top.theillusivec4.curios.common.inventory.container.CuriosContainerProvider;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class CuriosTrackCompatImpl {

	private static CuriosTrackCompatImpl INSTANCE;

	public static CuriosTrackCompatImpl get() {
		if (INSTANCE == null) {
			INSTANCE = new CuriosTrackCompatImpl();
		}
		return INSTANCE;
	}

	ItemStack getItemFromSlotImpl(Player player, int slot) {
		var curios = CuriosApi.getCuriosInventory(player);
		if (curios.isPresent()) {
			var e = curios.get();
			return e.getEquippedCurios().getStackInSlot(slot);
		} else {
			return ItemStack.EMPTY;
		}
	}

	public Supplier<CurioSource> IS_CURIOS;
	public Supplier<CurioInvTrace> TE_CURIO_INV;
	public Supplier<CurioTabTrace> TE_CURIO_TAB;

	void onStartUp() {
		IS_CURIOS = L2ScreenTracker.REGISTRATE.simple("curios", ScreenTrackerRegistry.ITEM_SOURCE.key(), CurioSource::new);
		TE_CURIO_INV = L2ScreenTracker.REGISTRATE.simple("curios_inv", ScreenTrackerRegistry.TRACKED_ENTRY_TYPE.key(), CurioInvTrace::new);
		TE_CURIO_TAB = L2ScreenTracker.REGISTRATE.simple("curios_tab", ScreenTrackerRegistry.TRACKED_ENTRY_TYPE.key(), CurioTabTrace::new);
	}

	void onCommonSetup() {
		MenuSourceRegistry.register(CuriosRegistry.CURIO_MENU.get(), (menu, slot, index, wid) ->
				getPlayerSlotImpl(slot, index, wid, menu));

		MenuTraceRegistry.register(CuriosRegistry.CURIO_MENU.get(), menu ->
				Optional.of(TrackedEntry.of(TE_CURIO_INV.get(), NoData.DATA)));

	}

	void onCompatSetup(MenuType<?> type) {
		MenuSourceRegistry.register(type, (menu, slot, index, wid) -> getPlayerSlotImpl(slot, index, wid, menu));
		MenuTraceRegistry.register(type, menu -> Optional.of(TrackedEntry.of(TE_CURIO_TAB.get(), NoData.DATA)));

	}

	private Optional<Player> getPlayerFromCurioCont(AbstractContainerMenu menu) {
		if (menu instanceof CuriosContainer cont) {
			return Optional.of(cont.player);
		}
		if (ModList.get().isLoaded("l2tabs")) {
			if (menu instanceof CuriosListMenu cont) {
				return Optional.of(cont.inventory.player);
			}
		}
		return Optional.empty();
	}

	private Optional<PlayerSlot<?>> getPlayerSlotImpl(int slot, int index, int wid, AbstractContainerMenu menu) {
		Slot s;
		CurioSlot curioSlot;
		int slotIndex;
		int val;
		if (menu.containerId != wid) return Optional.empty();
		var pl = getPlayerFromCurioCont(menu);

		if (pl.isPresent()) {
			s = menu.getSlot(index);
			if (s instanceof CurioSlot) {
				curioSlot = (CurioSlot) s;
				slotIndex = getSlotIndexInContainer(pl.get(), curioSlot.getIdentifier());
				if (slotIndex < 0) {
					return Optional.empty();
				}

				val = slotIndex + curioSlot.getSlotIndex();
				return Optional.of(new PlayerSlot<>(IS_CURIOS.get(), new SimpleSlotData(val)));
			}
		}
		return Optional.empty();
	}

	private static int getSlotIndexInContainer(Player player, String id) {
		var curiosLazy = CuriosApi.getCuriosInventory(player);
		if (curiosLazy.isEmpty()) {
			return -1;
		}
		Map<String, ICurioStacksHandler> curios = curiosLazy.get().getCurios();
		int index = 0;

		for (ICurioStacksHandler stacksHandler : curios.values()) {
			if (stacksHandler.getIdentifier().equals(id)) {
				return index;
			}
			index += stacksHandler.getSlots();
		}
		return -1;
	}

	void openCurioImpl(ServerPlayer player) {
		NetworkHooks.openScreen(player, new CuriosContainerProvider());
	}

}
