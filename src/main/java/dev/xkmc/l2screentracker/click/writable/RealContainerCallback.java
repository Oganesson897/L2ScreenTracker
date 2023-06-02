package dev.xkmc.l2screentracker.click.writable;

import net.minecraft.world.Container;

public record RealContainerCallback(Container container) implements ContainerCallback {

	@Override
	public void update() {
		container.setChanged();
	}

}
