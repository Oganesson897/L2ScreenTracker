package dev.xkmc.l2menustacker.screen.triggers;

import dev.xkmc.l2library.serial.advancements.BaseCriterion;
import dev.xkmc.l2library.serial.advancements.BaseCriterionInstance;
import dev.xkmc.l2menustacker.init.L2ScreenTracker;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ExitMenuTrigger extends BaseCriterion<ExitMenuTrigger.Ins, ExitMenuTrigger> {

	public static final ExitMenuTrigger EXIT_MENU = new ExitMenuTrigger(L2ScreenTracker.loc("exit_menu"));

	public static void register() {

	}

	public static Ins exitOne() {
		return new Ins(EXIT_MENU.getId(), ContextAwarePredicate.ANY);
	}

	public static Ins exitAll() {
		Ins ans = exitOne();
		ans.all = true;
		return ans;
	}

	public ExitMenuTrigger(ResourceLocation id) {
		super(id, Ins::new, Ins.class);
	}

	public void trigger(ServerPlayer player, boolean all) {
		this.trigger(player, e -> e.all == all);
	}

	@SerialClass
	public static class Ins extends BaseCriterionInstance<Ins, ExitMenuTrigger> {

		@SerialClass.SerialField
		private boolean all = false;

		public Ins(ResourceLocation id, ContextAwarePredicate player) {
			super(id, player);
		}

	}

}
