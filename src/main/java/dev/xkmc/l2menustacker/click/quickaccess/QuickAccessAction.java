package dev.xkmc.l2menustacker.click.quickaccess;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public interface QuickAccessAction {

	void perform(ServerPlayer player, ItemStack stack);

}
