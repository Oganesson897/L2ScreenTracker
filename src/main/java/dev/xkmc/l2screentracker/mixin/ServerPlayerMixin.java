package dev.xkmc.l2screentracker.mixin;

import dev.xkmc.l2screentracker.screen.base.MenuTriggerType;
import dev.xkmc.l2screentracker.screen.base.ScreenTracker;
import dev.xkmc.l2serial.util.Wrappers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

	@Inject(at = @At("RETURN"), method = "openMenu")
	public void l2library_openMenu_recordTitle(MenuProvider menu, CallbackInfoReturnable<OptionalInt> cir) {
		if (menu != null) {
			ScreenTracker.onServerOpenMenu(Wrappers.cast(this), menu, MenuTriggerType.OPEN_MENU, null);
		}
	}

}
