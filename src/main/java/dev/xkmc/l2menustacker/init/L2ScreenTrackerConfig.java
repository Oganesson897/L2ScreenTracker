package dev.xkmc.l2menustacker.init;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class L2ScreenTrackerConfig {

	public static class Client {

		public final ModConfigSpec.BooleanValue showTabs;

		Client(ModConfigSpec.Builder builder) {
			showTabs = builder.comment("Show inventory tabs")
					.define("showTabs", true);
		}

	}

	public static class Server {

		public final ModConfigSpec.BooleanValue tabSafeMode;

		Server(ModConfigSpec.Builder builder) {
			tabSafeMode = builder.comment("Safe Mode for menu stacking")
					.define("tabSafeMode", false);
		}

	}

	public static final ModConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	public static final ModConfigSpec SERVER_SPEC;
	public static final Server SERVER;

	static {
		final Pair<Client, ModConfigSpec> client = new ModConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();

		final Pair<Server, ModConfigSpec> common = new ModConfigSpec.Builder().configure(Server::new);
		SERVER_SPEC = common.getRight();
		SERVER = common.getLeft();
	}

	/**
	 * Registers any relevant listeners for config
	 */
	public static void init() {
		register(ModConfig.Type.CLIENT, CLIENT_SPEC);
		register(ModConfig.Type.SERVER, SERVER_SPEC);
	}

	private static void register(ModConfig.Type type, IConfigSpec<?> spec) {
		var mod = ModLoadingContext.get().getActiveContainer();
		String path = "l2_configs/" + mod.getModId() + "-" + type.extension() + ".toml";
		mod.registerConfig(type, spec, path);
	}


}
