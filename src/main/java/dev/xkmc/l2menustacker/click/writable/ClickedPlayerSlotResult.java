package dev.xkmc.l2menustacker.click.writable;

import dev.xkmc.l2menustacker.screen.source.PlayerSlot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public record ClickedPlayerSlotResult(ItemStack stack, @Nonnull PlayerSlot<?> slot, ContainerCallback container) {

}
