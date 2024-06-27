package dev.xkmc.l2menustacker.click.quickaccess;

import dev.xkmc.l2menustacker.screen.base.ScreenTracker;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

public record SimpleMenuAction(MenuFactory factory, String id) implements QuickAccessAction {

	@Override
	public void perform(ServerPlayer player, ItemStack stack) {
		ScreenTracker.onServerOpen(player);
		player.openMenu(new SimpleMenuProvider((wid, inv, pl) ->
				factory.create(wid, inv, new DummyHandler(pl)),
				Component.translatable(id)));
	}

	public interface MenuFactory {

		AbstractContainerMenu create(int wid, Inventory inv, ContainerLevelAccess access);

	}

}
