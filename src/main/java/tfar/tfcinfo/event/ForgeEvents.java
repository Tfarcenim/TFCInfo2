package tfar.tfcinfo.event;

import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.gamestages.event.GameStageEvent;
import net.dries007.tfc.util.OreDictionaryHelper;
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
            TimeData timeData = wsd.uuidTimeHashMap.computeIfAbsent(uuid, o -> new TimeData());
            if (OreDictionaryHelper.doesStackMatchOre(e.crafting, Stages.averageTemp.knowledge())) {
                if (timeData.avg_temp_knowledge_start == -1) {
                    timeData.avg_temp_knowledge_start = player.world.getTotalWorldTime();
                    wsd.markDirty();
                }
            } else if (OreDictionaryHelper.doesStackMatchOre(e.crafting, Stages.maxTemp.knowledge())) {
                if (timeData.max_temp_knowledge_start == -1) {
                    timeData.max_temp_knowledge_start = player.world.getTotalWorldTime();
                    wsd.markDirty();
                }
            }
            PacketHandler.INSTANCE.sendTo(new S2CSyncTimePacket(timeData.avg_temp_knowledge_start, timeData.max_temp_knowledge_start), (EntityPlayerMP) player);
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
                unlockRainfallKnowledge(player, timeData);
            }
        }
    }

    @SubscribeEvent
    public static void login(PlayerEvent.PlayerLoggedInEvent e) {
        EntityPlayer player = e.player;
        WSD wsd = WSD.getDefaultInstance();
        UUID uuid = player.getUniqueID();
        TimeData timeData = wsd.uuidTimeHashMap.computeIfAbsent(uuid, o -> new TimeData());
        PacketHandler.INSTANCE.sendTo(new S2CSyncTimePacket(timeData.avg_temp_knowledge_start, timeData.max_temp_knowledge_start), (EntityPlayerMP) player);
    }


    //days minutes seconds ticks
    public static void unlockAverageTempKnowledge(EntityPlayer player, TimeData timeData) {
        long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.avg_temp_knowledge_start;
        if (diff > TFCInfoConfig.avg_temp_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.averageTemp.knowledge())) {
            GameStageHelper.addStage(player, Stages.averageTemp.knowledge());
        }

        if (diff > TFCInfoConfig.avg_temp_memory_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.averageTemp.memory())) {
            GameStageHelper.addStage(player, Stages.averageTemp.memory());
        }
    }

    public static void unlockMaxTempKnowledge(EntityPlayer player, TimeData timeData) {
        long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.max_temp_knowledge_start;
        if (diff > TFCInfoConfig.max_temp_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.maxTemp.knowledge())) {
            GameStageHelper.addStage(player, Stages.maxTemp.knowledge());
        }

        if (diff > TFCInfoConfig.max_temp_memorization_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.maxTemp.memory())) {
            GameStageHelper.addStage(player, Stages.maxTemp.memory());
        }
    }

    public static void unlockMinTempKnowledge(EntityPlayer player, TimeData timeData) {
        long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.max_temp_knowledge_start;
        if (diff > TFCInfoConfig.min_temp_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.minTemp.knowledge())) {
            GameStageHelper.addStage(player, Stages.minTemp.knowledge());
        }

        if (diff > TFCInfoConfig.min_temp_memory_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.minTemp.memory())) {
            GameStageHelper.addStage(player, Stages.minTemp.memory());
        }
    }

    public static void unlockRainfallKnowledge(EntityPlayer player, TimeData timeData) {
        long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.rainfall_knowledge_start;
        if (diff > TFCInfoConfig.rainfall_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.minTemp.knowledge())) {
            GameStageHelper.addStage(player, Stages.minTemp.knowledge());
        }

        if (diff > TFCInfoConfig.min_temp_memory_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.minTemp.memory())) {
            GameStageHelper.addStage(player, Stages.minTemp.memory());
        }
    }
}
