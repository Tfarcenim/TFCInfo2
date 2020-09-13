package tfar.tfcinfo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

@GameRegistry.ObjectHolder("tfc")
public class Utils {

	public static List<ItemStack> empty_buckets;

	public static boolean hasEmptyBucket(EntityPlayer player) {

		if (empty_buckets == null) {
			empty_buckets = new ArrayList<>();
			for (Item item : Item.REGISTRY) {
				if (item.getRegistryName().getPath().contains("bucket") && item != Items.LAVA_BUCKET && item != Items.WATER_BUCKET) {
					empty_buckets.add(new ItemStack(item));
				}
			}
		}
		return empty_buckets.stream().anyMatch(stack -> player.inventory.hasItemStack(stack));
	}
}
