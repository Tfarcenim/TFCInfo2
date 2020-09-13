package tfar.tfcinfo;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import tfar.tfcinfo.recipes.SpiritFluidStackIngredient;

@Config(modid = TerrafirmaCraftInfo.MODID)
@Mod.EventBusSubscriber
public class TFCInfoConfig {

	private static final String s = "requirement time in ticks";
	private static final double ticksToDays = 20 * 60 * 20;

	@Config.Name("Average Temp Knowledge "+s)
	public static double avg_temp_knowledge_requirement = 8 * ticksToDays;

	@Config.Name("Maximum Temp Knowledge "+s)
	public static double max_temp_knowledge_requirement = 48 * 20 * 60 * 20;

	@Config.Name("Minimum Temp Knowledge "+s)
	public static double min_temp_knowledge_requirement = 96 * 20 * 60 * 20;

	@Config.Name("Average Temp Memorization "+s)
	public static double avg_temp_memorization_requirement = 144 * 20 * 60 * 20;

	@Config.Name("Maximum Temp Memorization "+s)
	public static double max_temp_memorization_requirement = 192 * 20 * 60 * 20;

	@Config.Name("Minimum Temp Memorization "+s)
	public static double min_temp_memorization_requirement = 288 * 20 * 60 * 20;

	@Config.Name("Rainfall Knowledge "+s)
	public static double rainfall_knowledge_requirement =  8 * 20 * 60 * 20;

	@Config.Name("Rainfall Memorization "+s)
	public static double rainfall_memorization_requirement = 8 * 20 * 60 * 20;

	@Config.Name("Monster Ferocity Knowledge "+s)
	public static double monster_ferocity_knowledge_requirement =  3 * 20 * 60 * 20;

	@Config.Name("Monster Migration Knowledge "+s)
	public static double monster_migration_knowledge_requirement = 8 * 20 * 60 * 20;

	@Config.Name("Constellation Knowledge "+s)
	public static double constellation_knowledge_requirement = 8 * 20 * 60 * 20;

	@Config.Name("Average Temp Item Unlock")
	public static String avg_temp_item_unlock = TerrafirmaCraftInfo.MODID + ":spirit_thermometer";

	@Config.Name("Record Temp Item Unlock")
	public static String record_temp_item_unlock = TerrafirmaCraftInfo.MODID + ":recent_temperature_records";

	@Config.Name("Monster Ferocity Item Unlock")
	public static String monster_ferocity_item_unlock = TerrafirmaCraftInfo.MODID + ":monster_ferocity_sample";

	@Config.Name("Date Item Unlock")
	public static String date_item_unlock = TerrafirmaCraftInfo.MODID + ":calendar";

	@Config.Name("Time Item Unlock")
	public static String time_item_unlock = TerrafirmaCraftInfo.MODID + ":calendar";

	@Config.Name("Constellation Item Unlock")
	public static String constellation_item_unlock = TerrafirmaCraftInfo.MODID + ":calendar";

	@Config.Name("Spirits")
	@Config.Comment("Fluids considered valid for thermometer recipe")
	public static String[] spirits = new String[]{
					"rum",
					"whiskey",
					"rye_whiskey",
					"corn_whiskey",
					"sake",
					"vodka",
					"cider"};

	@Config.Ignore
	public static Item avg_temp_unlock = ModItems.SPIRIT_THERMOMETER;
	@Config.Ignore
	public static Item record_temp_unlock = ModItems.RECENT_TEMPERATURE_RECORDS;
	@Config.Ignore
	public static Item monster_ferocity_unlock = ModItems.MONSTER_FEROCITY_SAMPLE;
	@Config.Ignore
	public static Item date_unlock = ModItems.CALENDAR;
	@Config.Ignore
	public static Item time_unlock = ModItems.CALENDAR;
	@Config.Ignore
	public static Item constellation_unlock = ModItems.CALENDAR;

	@SubscribeEvent
	public static void reloadConfigs(ConfigChangedEvent.OnConfigChangedEvent e) {
		if (e.getModID().equals(TerrafirmaCraftInfo.MODID)) {
			avg_temp_unlock = ForgeRegistries.ITEMS.getValue(new ResourceLocation(avg_temp_item_unlock));
			record_temp_unlock = ForgeRegistries.ITEMS.getValue(new ResourceLocation(record_temp_item_unlock));
			monster_ferocity_unlock = ForgeRegistries.ITEMS.getValue(new ResourceLocation(monster_ferocity_item_unlock));
			date_unlock = ForgeRegistries.ITEMS.getValue(new ResourceLocation(date_item_unlock));
			time_unlock = ForgeRegistries.ITEMS.getValue(new ResourceLocation(time_item_unlock));
			constellation_unlock = ForgeRegistries.ITEMS.getValue(new ResourceLocation(constellation_item_unlock));
			SpiritFluidStackIngredient.INSTANCE.invalidateCache();
		}
	}
}
