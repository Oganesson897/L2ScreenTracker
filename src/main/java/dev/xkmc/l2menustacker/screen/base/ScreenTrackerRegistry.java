package dev.xkmc.l2menustacker.screen.base;

import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2menustacker.init.L2MenuStacker;
import dev.xkmc.l2menustacker.screen.source.*;
import dev.xkmc.l2menustacker.screen.track.*;
import dev.xkmc.l2menustacker.screen.triggers.ExitMenuTrigger;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.PlayerEnderChestContainer;

import java.util.Optional;
import java.util.function.Supplier;

public class ScreenTrackerRegistry {

	public static final L2Registrate.RegistryInstance<ItemSource<?>> ITEM_SOURCE = L2MenuStacker.REGISTRATE.newRegistry("item_source", ItemSource.class);
	public static final L2Registrate.RegistryInstance<TrackedEntryType<?>> TRACKED_ENTRY_TYPE = L2MenuStacker.REGISTRATE.newRegistry("tracked_entry_type", TrackedEntryType.class);

	public static final Supplier<InventorySource> IS_INVENTORY = L2MenuStacker.REGISTRATE.simple("inventory", ITEM_SOURCE.key(), InventorySource::new);
	public static final Supplier<EnderSource> IS_ENDER = L2MenuStacker.REGISTRATE.simple("ender", ITEM_SOURCE.key(), EnderSource::new);

	public static final Supplier<InventoryTrace> TE_INVENTORY = L2MenuStacker.REGISTRATE.simple("inventory", TRACKED_ENTRY_TYPE.key(), InventoryTrace::new);
	public static final Supplier<EnderTrace> TE_ENDER = L2MenuStacker.REGISTRATE.simple("ender", TRACKED_ENTRY_TYPE.key(), EnderTrace::new);
	public static final Supplier<QuickAccessTrace> TE_QUICK_ACCESS = L2MenuStacker.REGISTRATE.simple("quick_access", TRACKED_ENTRY_TYPE.key(), QuickAccessTrace::new);
	public static final Supplier<MenuProviderTrace> TE_MENU_PROVIDER = L2MenuStacker.REGISTRATE.simple("menu_provider", TRACKED_ENTRY_TYPE.key(), MenuProviderTrace::new);

	public static void register() {
		ExitMenuTrigger.register();
	}

	public static void commonSetup() {

		MenuSourceRegistry.register(MenuType.GENERIC_9x3, (menu, slot, index, wid) ->
				menu.getContainer() instanceof PlayerEnderChestContainer ?
						Optional.of(new PlayerSlot<>(IS_ENDER.get(), new SimpleSlotData(index))) :
						Optional.empty());

		MenuTraceRegistry.register(MenuType.GENERIC_9x3, menu ->
				menu.getContainer() instanceof PlayerEnderChestContainer ?
						Optional.of(TrackedEntry.of(TE_ENDER.get(), NoData.DATA)) :
						Optional.empty());

	}

}
