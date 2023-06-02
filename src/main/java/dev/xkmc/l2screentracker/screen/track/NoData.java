package dev.xkmc.l2screentracker.screen.track;

public record NoData() implements TrackedEntryData<NoData> {

	public static final NoData DATA = new NoData();

}
