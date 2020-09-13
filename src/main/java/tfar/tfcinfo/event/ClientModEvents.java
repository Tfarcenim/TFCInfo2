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

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientModEvents {

	@SubscribeEvent
	public static void models(ModelRegistryEvent e) {
		registerItemModels(
						ModItems.RECENT_TEMPERATURE_RECORDS,
						ModItems.SPIRIT_THERMOMETER
		);
	}


	public static void registerItemModels(Item... items) {
		Arrays.stream(items).forEach(ClientModEvents::registerItemModel);
	}

	public static void registerItemModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	public static void registerItemModel(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),0,new ModelResourceLocation(Item.getItemFromBlock(block).getRegistryName(), "inventory"));
	}
}
