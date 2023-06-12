package dev.xkmc.l2screentracker.click.quickaccess;

import dev.xkmc.l2screentracker.screen.base.ScreenTrackerRegistry;
import dev.xkmc.l2screentracker.screen.track.MenuTraceRegistry;
import dev.xkmc.l2screentracker.screen.track.QuickAccessTraceData;
import dev.xkmc.l2screentracker.screen.track.TrackedEntry;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Optional;

public class DefaultQuickAccessActions {

	public static void register() {
		quickAccess(MenuType.CRAFTING, Items.CRAFTING_TABLE, CraftingMenu::new, "container.crafting");
		quickAccess(MenuType.STONECUTTER, Items.STONECUTTER, StonecutterMenu::new, "container.stonecutter");
		quickAccess(MenuType.GRINDSTONE, Items.GRINDSTONE, GrindstoneMenu::new, "container.grindstone_title");
		quickAccess(MenuType.CARTOGRAPHY_TABLE, Items.CARTOGRAPHY_TABLE, CartographyTableMenu::new, "container.cartography_table");
		quickAccess(MenuType.LOOM, Items.LOOM, LoomMenu::new, "container.loom");
		quickAccess(MenuType.SMITHING, Items.SMITHING_TABLE, SmithingMenu::new, "container.upgrade");
	}

	public static <T extends AbstractContainerMenu> void quickAccess(MenuType<T> type, Item item, SimpleMenuAction.MenuFactory factory, String id) {
		QuickAccessClickHandler.register(item, new SimpleMenuAction(factory, id));
		quickAccess(type, item);
	}

	public static <T extends AbstractContainerMenu> void quickAccess(MenuType<T> type, Item item) {
		MenuTraceRegistry.register(type, menu ->
				Optional.of(TrackedEntry.of(ScreenTrackerRegistry.TE_QUICK_ACCESS.get(),
						new QuickAccessTraceData(item.getDefaultInstance()))));
	}

}
