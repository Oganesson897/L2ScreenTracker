package dev.xkmc.l2menustacker.init;

import dev.xkmc.l2core.util.ConfigInit;
import net.neoforged.neoforge.common.ModConfigSpec;

public class L2MSConfig {

	public static class Server extends ConfigInit {

		public final ModConfigSpec.BooleanValue tabSafeMode;

		Server(Builder builder) {
			markL2();
			tabSafeMode = builder.text("Safe Mode for menu stacking")
					.comment("When enabled, some modded container will be skipped when exiting")
					.define("tabSafeMode", false);
		}

	}

	public static final Server SERVER = L2MenuStacker.REGISTRATE.registerSynced(Server::new);

	public static void init() {
	}

}
