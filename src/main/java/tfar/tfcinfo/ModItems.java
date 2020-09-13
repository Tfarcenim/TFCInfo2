package tfar.tfcinfo;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;

public class ModItems {

	static final CreativeTabs tab = new CreativeTabs(TerrafirmaCraftInfo.MODID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Blocks.COAL_BLOCK);
		}
	};
	public static Item SPIRIT_THERMOMETER = new Item().setCreativeTab(tab);
	public static Item RECENT_TEMPERATURE_RECORDS = new Item().setCreativeTab(tab);
	public static Item MONSTER_FEROCITY_SAMPLE = new Item().setCreativeTab(tab);
	public static Item CALENDAR = new Item().setCreativeTab(tab);

	public static void registerItems(RegistryEvent.Register<Item> e) {
		e.getRegistry().registerAll(
						SPIRIT_THERMOMETER.setRegistryName("spirit_thermometer").setTranslationKey(TerrafirmaCraftInfo.MODID+".spirit_thermometer"),
						RECENT_TEMPERATURE_RECORDS.setRegistryName("recent_temperature_records").setTranslationKey(TerrafirmaCraftInfo.MODID+".recent_temperature_records"),
						MONSTER_FEROCITY_SAMPLE.setRegistryName("monster_ferocity_sample").setTranslationKey(TerrafirmaCraftInfo.MODID+".monster_ferocity_sample"),
						CALENDAR.setRegistryName("calendar").setTranslationKey(TerrafirmaCraftInfo.MODID+".calendar")
		);
	}
}
