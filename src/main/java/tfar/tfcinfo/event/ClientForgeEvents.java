package tfar.tfcinfo.event;

import net.darkhax.gamestages.GameStageHelper;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendarFormatted;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateHelper;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import tfar.tfcinfo.ModItems;
import tfar.tfcinfo.Stages;
import tfar.tfcinfo.TFCInfoConfig;
import tfar.tfcinfo.Utils;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static net.dries007.tfc.util.calendar.ICalendarFormatted.*;
import static net.minecraft.util.text.TextFormatting.GRAY;
import static net.minecraft.util.text.TextFormatting.WHITE;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientForgeEvents {

	public static long avg_temp_knowledge_start = -1;
	public static long max_temp_knowledge_start = -1;
	public static long rainfall_knowledge_start = -1;
	public static long monster_ferocity_knowledge_start = -1;
	public static long constellation_knowledge_start = -1;

	public static final Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void lessDebugInfo(RenderGameOverlayEvent.Text e) {

		EntityPlayer player = Minecraft.getMinecraft().player;
		List<String> left = e.getLeft();
		List<String> right = e.getRight();

		//remove first
		deleteLeftLines(left);

		//then add new lines
		addLeftLines(left,mc.player);

		//then modify existing
		for (int i = 0; i < left.size(); i++) {
			String s = left.get(i);
			if (s.contains("fps")) {
				left.set(i, s.split("T")[0]);
				break;
			}
		}

			for (int i = 0; i < left.size(); i++) {
			String s = left.get(i);
			if (s.contains("fps") && !s.contains("Minecraft")) continue;

			if (s.contains("Local Difficulty")) {
				String s1 = canDisplayMonsterFerocity(player) ? s.split("\\(")[0] : "";
				left.set(i, s1);
			}
		}

		right.removeIf(s -> {
			return s.startsWith("CPU") || s.startsWith("Display") || s.contains(GlStateManager.glGetString(7936)) ||
							s.contains(GlStateManager.glGetString(7937)) || s.contains(GlStateManager.glGetString(7938))

							;
		});


		for (int i = 0; i < right.size(); i++) {
			String s = right.get(i);
			if (s.contains("Region:")) {
				BlockPos blockpos = new BlockPos(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().getEntityBoundingBox().minY, mc.getRenderViewEntity().posZ);
				Chunk chunk = mc.world.getChunk(blockpos);
				if (mc.world.isBlockLoaded(blockpos) && !chunk.isEmpty()) {
					final int x = blockpos.getX() & 15, z = blockpos.getZ() & 15;
					ChunkDataTFC data = chunk.getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);

					s = "";
					if (canDisplayRegionalTemp(mc.player)) {
						s += String.format("%sRegion: %s%.1f\u00b0C%s", GRAY, WHITE, data.getRegionalTemp(), GRAY);
					}

					if (canDisplayAverageTemp(mc.player)) {
						s += String.format("Avg: %s%.1f\u00b0C%s", WHITE, data.getAverageTemp(), GRAY);
					}

					if (canDisplayMaxTemp(mc.player)) {
						s += String.format("C%s Max: %s%.1f\u00b0C%s", GRAY,
										WHITE, ClimateHelper.monthFactor(data.getRegionalTemp(), Month.JULY.getTemperatureModifier(), blockpos.getZ()), GRAY);
					}

					if (canDisplayMinTemp(mc.player)) {
						s += String.format("C%s Min: %s%.1f\u00b0C%s", GRAY,
										WHITE, ClimateHelper.monthFactor(data.getRegionalTemp(), Month.JANUARY.getTemperatureModifier(), blockpos.getZ()), GRAY);
					}
					right.set(i, s);
				}
			}

			if (s.contains("Rainfall")) {
				if (!canDisplayRainfall(player)) {
					right.set(i, "");
				}
			}

			if (s.contains("Date")) {
				String s1 = "";
				if (canDisplayDate(player)) {
					s1 = getDate();
				}
				//list.add(I18n.format("tfc.tooltip.debug_times", CalendarTFC.PLAYER_TIME.getTicks(), CalendarTFC.CALENDAR_TIME.getTicks()));
				right.set(i,s1);

				if (canDisplayTime(player)) {
					right.add(i,getTime());
				}
			}
		}
	}

	public static void deleteLeftLines(List<String> left) {
		left.removeIf(s -> {
			return !s.contains("fps") && !s.contains("Minecraft");
		});
	}

	public static void addLeftLines(List<String> left,EntityPlayer player) {
		if (canDisplayX(player)) {
			left.add("X: "+ player.posX);
		}

		if (canDisplayY(player)) {
			left.add("Y: "+ player.posY);
		}

		if (canDisplayZ(player)) {
			left.add("Z: "+ player.posZ);
		}
	}

	public static String getDate() {
		ICalendarFormatted iCalendarFormatted = CalendarTFC.CALENDAR_TIME;
		long time = iCalendarFormatted.getTicks();
		long daysInMonth = iCalendarFormatted.getDaysInMonth();
		return I18n.format("tfcinfo.tooltip.date",
				I18n.format("tfc.enum.month."+getMonthOfYear(time, daysInMonth).toString().toLowerCase(Locale.ROOT))
				, getDayOfMonth(time, daysInMonth) , getTotalYears(time, daysInMonth));
	}

	public static String getTime() {
		ICalendarFormatted iCalendarFormatted = CalendarTFC.CALENDAR_TIME;
		long time = iCalendarFormatted.getTicks();
		return "Time: " + getHourOfDay(time)+":"+getMinuteOfHour(time);
	}

	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent e) {
		EntityPlayer player = e.getEntityPlayer();
		if (player != null) {
			if (e.getItemStack().getItem() == TFCInfoConfig.avg_temp_unlock) {
				long current_time = player.world.getTotalWorldTime();
				if (!GameStageHelper.hasStage(player, Stages.averageTempKnowledge) && avg_temp_knowledge_start > -1) {
					long remaining_time = (long) (TFCInfoConfig.avg_temp_knowledge_requirement - current_time + avg_temp_knowledge_start);
					long days = remaining_time / 20 / 60 / 20;
					e.getToolTip().add("Remaining Time to unlock Average Temperature knowledge: " + days  + " days");
				} else if (!GameStageHelper.hasStage(player, Stages.memorizedAverageTemp) && avg_temp_knowledge_start > -1) {
					long remaining_time = (long) (TFCInfoConfig.avg_temp_memorization_requirement - current_time + avg_temp_knowledge_start);
					long days = remaining_time / 20 / 60 / 20;
					e.getToolTip().add("Remaining Time to unlock Average Temperature memorization: " + days  + " days");
				}
			} else if  (e.getItemStack().getItem() == TFCInfoConfig.record_temp_unlock) {
				long current_time = player.world.getTotalWorldTime();
				if (!GameStageHelper.hasStage(player, Stages.maxTempKnowledge) && max_temp_knowledge_start > -1) {
					long remaining_time = (long) (TFCInfoConfig.max_temp_knowledge_requirement - current_time + max_temp_knowledge_start);
					long days = remaining_time / 20 / 60 / 20;
					e.getToolTip().add("Remaining Time to unlock Maximum Temperature knowledge: " + days  + " days");
				}

				else if (!GameStageHelper.hasStage(player, Stages.memorizedMaxTemp) && max_temp_knowledge_start > -1) {
					long remaining_time = (long) (TFCInfoConfig.max_temp_memorization_requirement - current_time + max_temp_knowledge_start);
					long days = remaining_time / 20 / 60 / 20;
					e.getToolTip().add("Remaining Time to unlock Maximum Temperature memorization: " + days  + " days");
				}


				if (!GameStageHelper.hasStage(player, Stages.minTempKnowledge) && max_temp_knowledge_start > -1) {
					long remaining_time = (long) (TFCInfoConfig.max_temp_knowledge_requirement - current_time + max_temp_knowledge_start);
					long days = remaining_time / 20 / 60 / 20;
					e.getToolTip().add("Remaining Time to unlock Minimum Temperature knowledge: " + days  + " days");
				}

				else if (!GameStageHelper.hasStage(player, Stages.memorizedMinTemp) && max_temp_knowledge_start > -1) {
					long remaining_time = (long) (TFCInfoConfig.min_temp_memorization_requirement - current_time + max_temp_knowledge_start);
					long days = remaining_time / 20 / 60 / 20;
					e.getToolTip().add("Remaining Time to unlock Minimum Temperature memorization: " + days  + " days");
				}
			}
		}
	}

	public static boolean canDisplayRegionalTemp(EntityPlayer player) {
		return GameStageHelper.hasStage(player, Stages.memorizedRegionalTemp) ||
						(player.inventory.hasItemStack(new ItemStack(ModItems.SPIRIT_THERMOMETER)));
	}

	public static boolean canDisplayAverageTemp(EntityPlayer player) {
		return GameStageHelper.hasStage(player, Stages.memorizedAverageTemp) ||
						(GameStageHelper.hasStage(player, Stages.averageTempKnowledge) && player.inventory.hasItemStack(new ItemStack(TFCInfoConfig.avg_temp_unlock)));
	}

	public static boolean canDisplayMaxTemp(EntityPlayer player) {
		return GameStageHelper.hasStage(player, Stages.memorizedMaxTemp) ||
						(GameStageHelper.hasStage(player, Stages.maxTempKnowledge) && player.inventory.hasItemStack(new ItemStack(TFCInfoConfig.record_temp_unlock)));
	}

	public static boolean canDisplayMinTemp(EntityPlayer player) {
		return GameStageHelper.hasStage(player, Stages.memorizedMinTemp) ||
						(GameStageHelper.hasStage(player, Stages.minTempKnowledge) && player.inventory.hasItemStack(new ItemStack(TFCInfoConfig.record_temp_unlock)));
	}

	public static boolean canDisplayRainfall(EntityPlayer player) {
		return GameStageHelper.hasStage(player, Stages.rainfallMemorized) ||
						(GameStageHelper.hasStage(player, Stages.rainfallKnowledge) && Utils.hasEmptyBucket(player));
	}

	public static boolean canDisplayDate(EntityPlayer player) {
		return (GameStageHelper.hasStage(player, Stages.dateKnowledge) && player.inventory.hasItemStack(new ItemStack(TFCInfoConfig.date_unlock)));
	}

	public static boolean canDisplayTime(EntityPlayer player) {
		return (GameStageHelper.hasStage(player, Stages.timeKnowledge) && player.inventory.hasItemStack(new ItemStack(TFCInfoConfig.time_unlock)));
	}

	public static boolean canDisplayX(EntityPlayer player) {
		return (GameStageHelper.hasStage(player, Stages.timeKnowledge) && player.inventory.hasItemStack(new ItemStack(TFCInfoConfig.constellation_unlock)));
	}

	public static boolean canDisplayY(EntityPlayer player) {
		return (GameStageHelper.hasStage(player, Stages.timeKnowledge) && player.inventory.hasItemStack(new ItemStack(TFCInfoConfig.constellation_unlock)));
	}

	public static boolean canDisplayZ(EntityPlayer player) {
		return (GameStageHelper.hasStage(player, Stages.timeKnowledge) && player.inventory.hasItemStack(new ItemStack(TFCInfoConfig.constellation_unlock)));
	}


	public static boolean canDisplayMonsterFerocity(EntityPlayer player) {
		return (GameStageHelper.hasStage(player, Stages.monsterFerocityKnowledge) && Utils.hasEmptyBucket(player));
	}
}
