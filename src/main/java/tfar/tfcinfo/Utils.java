package tfar.tfcinfo;

import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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

	public static boolean hasOreDictItem(String ore, InventoryPlayer inv) {
		return Stream.of(inv.mainInventory, inv.armorInventory, inv.offHandInventory)
				.flatMap(List::stream).anyMatch(stack -> OreDictionaryHelper.doesStackMatchOre(stack,ore));
	}
}
