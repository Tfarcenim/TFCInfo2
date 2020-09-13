package tfar.tfcinfo.event;

import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.gamestages.event.GameStageEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tfar.tfcinfo.Stages;
import tfar.tfcinfo.TFCInfoConfig;
import tfar.tfcinfo.TerrafirmaCraftInfo;
import tfar.tfcinfo.WSD;
import tfar.tfcinfo.TimeData;
import tfar.tfcinfo.network.PacketHandler;
import tfar.tfcinfo.network.S2CSyncTimePacket;

import java.util.UUID;

@Mod.EventBusSubscriber
public class ForgeEvents {

	@SubscribeEvent
	public static void onStageGrant(GameStageEvent.Added e) {
		if (e.getStageName().contains(TerrafirmaCraftInfo.MODID)) {
			EntityPlayer player = e.getEntityPlayer();
			player.sendStatusMessage(new TextComponentString(String.format("You've learned %s! Press F3 to see what you've learned!", e.getStageName())), true);
		}
	}

	@SubscribeEvent
	public static void onCraft(PlayerEvent.ItemCraftedEvent e) {
		EntityPlayer player = e.player;
		if (!player.world.isRemote) {
			WSD wsd = WSD.getDefaultInstance();
			UUID uuid = player.getUniqueID();
			//todo config
			TimeData timeData = wsd.uuidTimeHashMap.computeIfAbsent(uuid, o -> new TimeData());
			if (e.crafting.getItem() == TFCInfoConfig.avg_temp_unlock) {
				if (timeData.avg_temp_knowledge_start == -1) {
					timeData.avg_temp_knowledge_start = player.world.getTotalWorldTime();
					wsd.markDirty();
				}
				//todo config
			} else if (e.crafting.getItem() == TFCInfoConfig.record_temp_unlock) {
						if (timeData.max_temp_knowledge_start == -1) {
							timeData.max_temp_knowledge_start = player.world.getTotalWorldTime();
							wsd.markDirty();
						}
					}
			PacketHandler.INSTANCE.sendTo(new S2CSyncTimePacket(timeData.avg_temp_knowledge_start,timeData.max_temp_knowledge_start), (EntityPlayerMP) player);
				}
			}

	@SubscribeEvent
	public static void tick(TickEvent.PlayerTickEvent e) {
		if (e.phase == TickEvent.Phase.END && !e.player.world.isRemote) {
			WSD wsd = WSD.getDefaultInstance();
			EntityPlayer player = e.player;
			UUID uuid = player.getUniqueID();
			TimeData timeData = wsd.uuidTimeHashMap.computeIfAbsent(uuid, o -> new TimeData());

			if (timeData.avg_temp_knowledge_start > -1) {
				unlockAverageTempKnowledge(player, timeData);
			}

			if (timeData.max_temp_knowledge_start > -1) {
				unlockMaxTempKnowledge(player, timeData);
			}

			if (timeData.max_temp_knowledge_start > -1) {
				unlockMinTempKnowledge(player, timeData);
			}
			if (timeData.rainfall_knowledge_start > -1) {
				unlockRainfallKnowledge(player,timeData);
			}
		}
	}

	@SubscribeEvent
	public static void login(PlayerEvent.PlayerLoggedInEvent e) {
		EntityPlayer player = e.player;
		WSD wsd = WSD.getDefaultInstance();
		UUID uuid = player.getUniqueID();
		TimeData timeData = wsd.uuidTimeHashMap.computeIfAbsent(uuid, o -> new TimeData());
		PacketHandler.INSTANCE.sendTo(new S2CSyncTimePacket(timeData.avg_temp_knowledge_start,timeData.max_temp_knowledge_start), (EntityPlayerMP) player);
	}


	//days minutes seconds ticks
	public static void unlockAverageTempKnowledge(EntityPlayer player, TimeData timeData) {
		long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.avg_temp_knowledge_start;
		if (diff > TFCInfoConfig.avg_temp_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.averageTempKnowledge)) {
			GameStageHelper.addStage(player, Stages.averageTempKnowledge);
		}

		if (diff > TFCInfoConfig.avg_temp_memorization_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.memorizedAverageTemp)) {
			GameStageHelper.addStage(player, Stages.memorizedAverageTemp);
		}
	}

	public static void unlockMaxTempKnowledge(EntityPlayer player, TimeData timeData) {
		long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.max_temp_knowledge_start;
		if (diff > TFCInfoConfig.max_temp_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.maxTempKnowledge)) {
			GameStageHelper.addStage(player, Stages.maxTempKnowledge);
		}

		if (diff > TFCInfoConfig.max_temp_memorization_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.memorizedMaxTemp)) {
			GameStageHelper.addStage(player, Stages.memorizedMaxTemp);
		}
	}

	public static void unlockMinTempKnowledge(EntityPlayer player, TimeData timeData) {
		long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.max_temp_knowledge_start;
		if (diff > TFCInfoConfig.min_temp_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.minTempKnowledge)) {
			GameStageHelper.addStage(player, Stages.minTempKnowledge);
		}

		if (diff > TFCInfoConfig.min_temp_memorization_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.memorizedMinTemp)) {
			GameStageHelper.addStage(player, Stages.memorizedMinTemp);
		}
	}

	public static void unlockRainfallKnowledge(EntityPlayer player, TimeData timeData) {
		long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.rainfall_knowledge_start;
		if (diff > TFCInfoConfig.rainfall_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.minTempKnowledge)) {
			GameStageHelper.addStage(player, Stages.minTempKnowledge);
		}

		if (diff > TFCInfoConfig.min_temp_memorization_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.memorizedMinTemp)) {
			GameStageHelper.addStage(player, Stages.memorizedMinTemp);
		}
	}
}
