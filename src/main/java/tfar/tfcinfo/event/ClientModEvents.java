package tfar.tfcinfo.event;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import tfar.tfcinfo.ModItems;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientModEvents {

	@SubscribeEvent
	public static void models(ModelRegistryEvent e) {
		registerItemModels(ModItems.items);
		registerItemModel(ModItems.SLIME_COMPASS);
		registerItemModel(ModItems.SLIME_SAMPLE);
	}


	public static void registerItemModels(List<Item> items) {
		items.forEach(ClientModEvents::registerItemModel);
	}

	public static void registerItemModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	public static void registerItemModel(Block block) {
		registerItemModel(Item.getItemFromBlock(block));
	}
}
