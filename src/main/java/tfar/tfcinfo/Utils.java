package tfar.tfcinfo;

import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.entity.player.InventoryPlayer;

import java.util.List;
import java.util.stream.Stream;

public class Utils {

	public static boolean hasOreDictItem(String ore, InventoryPlayer inv) {
		return Stream.of(inv.mainInventory, inv.armorInventory, inv.offHandInventory)
				.flatMap(List::stream).anyMatch(stack -> OreDictionaryHelper.doesStackMatchOre(stack,ore));
	}
}
