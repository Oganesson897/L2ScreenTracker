package dev.xkmc.l2menustacker.init;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public enum L2STLangData {
	QUICK_ACCESS("l2screentracker.tooltip.quick_access", "Right click in GUI to open", 0);

	private final String key, def;
	private final int arg;


	L2STLangData(String key, String def, int arg) {
		this.key = key;
		this.def = def;
		this.arg = arg;
	}

	public MutableComponent get(Object... args) {
		if (args.length != arg)
			throw new IllegalArgumentException("for " + name() + ": expect " + arg + " parameters, got " + args.length);
		return Component.translatable(key, args);
	}

	public static void genLang(RegistrateLangProvider pvd) {
		for (L2STLangData lang : L2STLangData.values()) {
			pvd.add(lang.key, lang.def);
		}
	}

}
