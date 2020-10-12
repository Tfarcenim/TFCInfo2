package tfar.tfcinfo;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tfar.tfcinfo.recipes.SpiritFluidStackIngredient;

@Config(modid = TerrafirmaCraftInfo.MODID)
@Mod.EventBusSubscriber
public class TFCInfoConfig {

	@Config.Ignore
	private static final String s = "requirement time in ticks";
	@Config.Ignore
	public static final double ticksToDays = 20 * 60 * 20;

	//knowledge
	
	@Config.Name("Average Temp Knowledge "+s)
	public static double avg_temp_knowledge_requirement = 8 * ticksToDays;

	@Config.Name("Maximum Temp Knowledge "+s)
	public static double max_temp_knowledge_requirement = 48 * ticksToDays;

	@Config.Name("Minimum Temp Knowledge "+s)
	public static double min_temp_knowledge_requirement = 96 * ticksToDays;

	@Config.Name("Rainfall Knowledge "+s)
	public static double rainfall_knowledge_requirement =  8 * ticksToDays;

	@Config.Name("Monster Ferocity Knowledge "+s)
	public static double monster_ferocity_knowledge_requirement =  3 * ticksToDays;

	@Config.Name("Monster Migration Knowledge "+s)
	public static double monster_migration_knowledge_requirement = 8 * ticksToDays;

	@Config.Name("Longitudinal Knowledge "+s)
	@Config.Comment("Required to display X coordinate")
	public static double longitudinal_knowledge_requirement = 24 * ticksToDays;

	@Config.Name("Depth Knowledge "+s)
	@Config.Comment("Required to display Y coordinate")
	public static double depth_knowledge_requirement = 24 * ticksToDays;

	@Config.Name("Constellation Knowledge "+s)
	@Config.Comment("Required to display Z coordinate")
	public static double constellation_knowledge_requirement = 24 * ticksToDays;


	//memorization

	@Config.Name("Average Temp Memorization "+s)
	public static double avg_temp_memory_requirement = 144 * ticksToDays;

	@Config.Name("Maximum Temp Memorization "+s)
	public static double max_temp_memorization_requirement = 192 * ticksToDays;

	@Config.Name("Minimum Temp Memorization "+s)
	public static double min_temp_memory_requirement = 288 * ticksToDays;

	@Config.Name("Arboreal Memorization "+s)
	public static double arboreal_memory_requirement = 32 * ticksToDays;

	@Config.Name("Biome Memorization "+s)
	public static double biome_memory_requirement = 96 * ticksToDays;

	@Config.Name("Rainfall Memorization "+s)
	public static double rainfall_memory_requirement = 96 * ticksToDays;

	@Config.Name("Date Memorization "+s)
	public static double date_memory_requirement = 96 * ticksToDays;

	@Config.Name("Flora Memorization "+s)
	public static double flora_memory_requirement = 8 * ticksToDays;


	@Config.Name("Longitudinal memorization "+s)
	@Config.Comment("Required to display X coordinate")
	public static double longitudinal_memory_requirement = 400 * ticksToDays;

	@Config.Name("Depth Memorization "+s)
	@Config.Comment("Required to display Y coordinate")
	public static double depth_memory_requirement = 96 * ticksToDays;

	@Config.Name("Constellation Memorization "+s)
	@Config.Comment("Required to display Z coordinate")
	public static double constellation_memory_requirement = 288 * ticksToDays;


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

	@Config.Name("Local Difficulty Memorization "+s)
	@Config.Comment("Required to display Local Difficulty")
	public static double monster_ferocity_memory_requirement = 64 * ticksToDays;

	@Config.Name("Spawn Timer Memorization "+s)
	@Config.Comment("Required to display Spawn Timer")
	public static double monster_migration_memory_requirement = 48 * ticksToDays;

	@Config.Name("Nutrition Memorization "+s)
	@Config.Comment("Required to display Nutrition tab")
	public static double nutrition_memory_requirement = 24 * ticksToDays;

	@Config.Name("Time Memorization "+s)
	@Config.Comment("Required to display Time")
	public static double time_memory_requirement = 192 * ticksToDays;

	@Config.Name("Hwyla Memorization "+s)
	@Config.Comment("Required to display Hwyla info")
	public static double hwyla_memory_requirement = 96 * ticksToDays;

	@Config.Name("Skill Memorization "+s)
	@Config.Comment("Required to display Skill tab")
	public static double skill_memory_requirement = 96 * ticksToDays;

	//fields


	@SubscribeEvent
	public static void reloadConfigs(ConfigChangedEvent.OnConfigChangedEvent e) {
		if (e.getModID().equals(TerrafirmaCraftInfo.MODID)) {
			SpiritFluidStackIngredient.INSTANCE.invalidateCache();
		}
	}
}
