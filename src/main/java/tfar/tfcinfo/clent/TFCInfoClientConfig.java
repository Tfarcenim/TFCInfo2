package tfar.tfcinfo.clent;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.Mod;
import tfar.tfcinfo.TerrafirmaCraftInfo;

@Config(modid = TerrafirmaCraftInfo.MODID,name = "tfcinfo - client")
@Mod.EventBusSubscriber
public class TFCInfoClientConfig {

	private static final String s = "Show ";

	@Config.Name(s+"Current Temperature")
	public static boolean show_current_temp	= true;

	@Config.Name(s+"Average Temp")
	public static boolean show_avg_temp = true;

	@Config.Name(s+"Maximum Temp")
	public static boolean show_max_temp = true;

	@Config.Name(s+"Rainfall")
	public static boolean show_rainfall =  true;

	@Config.Name(s+"Spawn Protection Timer")
	public static boolean show_spawn_protection_timer = true;

	@Config.Name(s+"Date")
	public static boolean show_date = true;

	@Config.Name(s+"Time")
	public static boolean show_time = true;

	@Config.Name(s+"Slime Chunks")
	public static boolean show_slime_chunks = true;

	@Config.Name(s+"X Position")
	public static boolean show_longitudinal = true;

	@Config.Name(s+"Y Position")
	public static boolean show_depth = true;

	@Config.Name(s+"Z Position")
	public static boolean show_constellation = true;



	// pda/encyclopedia only

	@Config.Name(s+"Minimum Temp")
	public static boolean show_min_temp = true;

	@Config.Name(s+"Biome")
	public static boolean show_biome = true;

	@Config.Name(s+"Moon Phase")
	public static boolean show_moon_phase = true;


	@Config.Name(s+"Light Level")
	public static boolean show_light_level = true;

	@Config.Name(s+"Flora")
	public static boolean show_flora = true;

	@Config.Name(s+"Trees")
	public static boolean show_trees = true;

	@Config.Name(s+"Local Difficulty")
	public static boolean show_local_difficulty = true;
}
