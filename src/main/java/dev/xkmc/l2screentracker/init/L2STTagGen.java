package dev.xkmc.l2screentracker.init;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class L2STTagGen {

	public static final TagKey<Item> QUICK_ACCESS = ItemTags.create(new ResourceLocation(L2ScreenTracker.MODID, "quick_access"));

	public static final TagKey<Item> QUICK_ACCESS_VANILLA = ItemTags.create(new ResourceLocation(L2ScreenTracker.MODID, "quick_access_vanilla"));

	public static void genItemTags(RegistrateItemTagsProvider pvd) {
		pvd.addTag(QUICK_ACCESS_VANILLA).add(Items.CRAFTING_TABLE, Items.SMITHING_TABLE, Items.STONECUTTER,
				Items.GRINDSTONE, Items.CARTOGRAPHY_TABLE, Items.LOOM);
		pvd.addTag(QUICK_ACCESS).addTag(QUICK_ACCESS_VANILLA);
	}

}
