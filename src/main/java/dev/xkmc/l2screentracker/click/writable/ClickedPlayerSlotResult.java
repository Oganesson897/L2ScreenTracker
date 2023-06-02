package dev.xkmc.l2screentracker.click.writable;

import dev.xkmc.l2screentracker.screen.source.PlayerSlot;
import net.minecraft.world.item.ItemStack;

public record ClickedPlayerSlotResult(ItemStack stack, PlayerSlot<?> slot, ContainerCallback container) {

}
