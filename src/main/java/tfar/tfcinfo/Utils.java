package tfar.tfcinfo;

import net.darkhax.bookshelf.util.OreDictUtils;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Utils {

	public static boolean hasOreDictItem(String ore, InventoryPlayer inv) {
		return Stream.of(inv.mainInventory, inv.armorInventory, inv.offHandInventory)
				.flatMap(List::stream).anyMatch(stack -> OreDictionaryHelper.doesStackMatchOre(stack,ore));
	}

	public static void copyOresFromItems(Item original,Item... toCopy) {
		Set<String> toadd = new HashSet<>();
		for (Item item : toCopy) {
			ItemStack stack = new ItemStack(item);
			Set<String> oreNames = OreDictUtils.getOreNames(stack);
			toadd.addAll(oreNames);
		}
		toadd.forEach(s -> OreDictionary.registerOre(s,original));
	}
}
