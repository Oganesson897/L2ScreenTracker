package dev.xkmc.l2screentracker.click.writable;

import dev.xkmc.l2screentracker.screen.source.PlayerSlot;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public record ClickedPlayerSlotResult(ItemStack stack, @Nonnull PlayerSlot<?> slot, ContainerCallback container) {

}
