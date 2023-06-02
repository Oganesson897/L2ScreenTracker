package dev.xkmc.l2screentracker.screen.track;

import dev.xkmc.l2screentracker.screen.base.MenuCache;

public record MenuProviderTraceData(MenuCache cache)
		implements TrackedEntryData<MenuProviderTraceData> {
}
