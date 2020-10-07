package tfar.tfcinfo.event;

import net.darkhax.bookshelf.util.BlockUtils;
import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.gamestages.event.GameStageEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tfar.tfcinfo.*;
import tfar.tfcinfo.network.PacketHandler;
import tfar.tfcinfo.network.S2CSyncSlimeChunkPacket;
import tfar.tfcinfo.network.S2CSyncTimePacket;

import java.util.UUID;

@Mod.EventBusSubscriber
public class CommonForgeEvents {

    @SubscribeEvent
    public static void onStageGrant(GameStageEvent.Added e) {
        if (e.getStageName().contains(TerrafirmaCraftInfo.MODID)) {
            EntityPlayer player = e.getEntityPlayer();
            player.sendStatusMessage(new TextComponentString(String.format("You've learned %s! Press F3 to see what you've learned!", e.getStageName())), true);
        }
    }

    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent e) {
        if (e.phase == TickEvent.Phase.END && !e.player.world.isRemote) {
            WSD wsd = WSD.getDefaultInstance();
            EntityPlayer player = e.player;
            UUID uuid = player.getUniqueID();
            TimeData timeData = wsd.uuidTimeHashMap.computeIfAbsent(uuid, o -> new TimeData());

            if (checkTriggers(player,timeData)) {
                PacketHandler.INSTANCE.sendTo(new S2CSyncTimePacket(
                        timeData
                ), (EntityPlayerMP) player);
            }
            checkKnowledgeMemoryUnlocks(player,timeData);

            if (Helper.canSeeSlimeChunks(player)) {
                PacketHandler.INSTANCE.sendTo(new S2CSyncSlimeChunkPacket(BlockUtils.isSlimeChunk(player.world,player.getPosition())), (EntityPlayerMP) player);
            }
        }
    }

    public static void checkKnowledgeMemoryUnlocks(EntityPlayer player, TimeData timeData) {
        if (timeData.avg_temp_knowledge_start > -1) {
            unlockAverageTempKnowledge(player, timeData);
        }

        if (timeData.max_temp_knowledge_start > -1) {
            unlockMaxTempKnowledge(player, timeData);
        }

        if (timeData.min_temp_knowledge_start > -1) {
            unlockMinTempKnowledge(player, timeData);
        }

        if (timeData.rainfall_knowledge_start > -1) {
            unlockRainfallKnowledge(player, timeData);
        }

        if (timeData.longitudinal_knowledge_start > -1) {
            unlockLongitudinalKnowledge(player, timeData);
        }

        if (timeData.depth_knowledge_start > -1) {
            unlockDepthKnowledge(player, timeData);
        }

        if (timeData.constellation_knowledge_start > -1) {
            checkConstellationProgress(player, timeData);
        }

        if (timeData.monster_migration_knowledge_start > -1) {
            checkSpawnProtectionProgress(player, timeData);
        }

        if (timeData.date_memory_start > -1) {
            checkDateProgress(player, timeData);
        }

        if (timeData.time_knowledge_start > -1) {
            checkTimeProgress(player, timeData);
        }

        //these checks aren't tied to items
        checkMonsterFerocityProgress(player, timeData);
        checkNutritionProgress(player,timeData);
    }

    public static boolean checkTriggers(EntityPlayer player,TimeData timeData) {
        boolean dirty = false;
        if (Utils.hasOreDictItem(Stages.averageTemp.unlocksKnowledge(),player.inventory)) {
            if (timeData.avg_temp_knowledge_start == -1) {
                timeData.avg_temp_knowledge_start = player.world.getTotalWorldTime();
                dirty = true;
            }
        }

        if (Utils.hasOreDictItem(Stages.maxTemp.unlocksKnowledge(),player.inventory)) {
            if (timeData.max_temp_knowledge_start == -1) {
                timeData.max_temp_knowledge_start = player.world.getTotalWorldTime();
                dirty = true;
            }
        }

        if (Utils.hasOreDictItem(Stages.minTemp.unlocksKnowledge(),player.inventory)) {
            if (timeData.min_temp_knowledge_start == -1) {
                timeData.min_temp_knowledge_start = player.world.getTotalWorldTime();
                dirty = true;
            }
        }

        if (Utils.hasOreDictItem(Stages.rainfall.unlocksKnowledge(),player.inventory)) {
            if (timeData.rainfall_knowledge_start == -1) {
                timeData.rainfall_knowledge_start = player.world.getTotalWorldTime();
                dirty = true;
            }
        }

        if (Utils.hasOreDictItem(Stages.longitudinal.unlocksKnowledge(),player.inventory)) {
            if (timeData.longitudinal_knowledge_start == -1) {
                timeData.longitudinal_knowledge_start = player.world.getTotalWorldTime();
                dirty = true;
            }
        }

        if (Utils.hasOreDictItem(Stages.depth.unlocksKnowledge(),player.inventory)) {
            if (timeData.depth_knowledge_start == -1) {
                timeData.depth_knowledge_start = player.world.getTotalWorldTime();
                dirty = true;
            }
        }

        if (Utils.hasOreDictItem(Stages.constellation.unlocksKnowledge(),player.inventory)) {
            if (timeData.constellation_knowledge_start == -1) {
                timeData.constellation_knowledge_start = player.world.getTotalWorldTime();
                dirty = true;
            }
        }

        if (Utils.hasOreDictItem(Stages.monsterMigration.unlocksKnowledge(),player.inventory)) {
            if (timeData.monster_migration_knowledge_start == -1) {
                timeData.monster_migration_knowledge_start = player.world.getTotalWorldTime();
                dirty = true;
            }
        }

        if (Utils.hasOreDictItem(Stages.date.unlocksKnowledge(),player.inventory)) {
            if (timeData.date_memory_start == -1) {
                timeData.date_memory_start = player.world.getTotalWorldTime();
                dirty = true;
            }
        }

        if (Utils.hasOreDictItem(Stages.time.unlocksKnowledge(),player.inventory)) {
            if (timeData.time_knowledge_start == -1) {
                timeData.time_knowledge_start = player.world.getTotalWorldTime();
                dirty = true;
            }
        }

        //note: monster ferocity doesn't require an item to unlock
        if (timeData.monster_ferocity_knowledge_start == -1) {
            timeData.monster_ferocity_knowledge_start = player.world.getTotalWorldTime();
            dirty = true;
        }
        if (timeData.nutrition_knowledge_start == -1) {
            timeData.nutrition_knowledge_start = player.world.getTotalWorldTime();
            dirty = true;
        }
        return dirty;
    }

    @SubscribeEvent
    public static void login(PlayerEvent.PlayerLoggedInEvent e) {
        EntityPlayer player = e.player;
        WSD wsd = WSD.getDefaultInstance();
        UUID uuid = player.getUniqueID();
        TimeData timeData = wsd.uuidTimeHashMap.computeIfAbsent(uuid, o -> new TimeData());
        PacketHandler.INSTANCE.sendTo(new S2CSyncTimePacket(timeData), (EntityPlayerMP) player);
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
        long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.min_temp_knowledge_start;
        if (diff > TFCInfoConfig.min_temp_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.minTemp.knowledge())) {
            GameStageHelper.addStage(player, Stages.minTemp.knowledge());
        }

        if (diff > TFCInfoConfig.min_temp_memory_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.minTemp.memory())) {
            GameStageHelper.addStage(player, Stages.minTemp.memory());
        }
    }

    public static void unlockRainfallKnowledge(EntityPlayer player, TimeData timeData) {
        long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.rainfall_knowledge_start;
        if (diff > TFCInfoConfig.rainfall_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.rainfall.knowledge())) {
            GameStageHelper.addStage(player, Stages.rainfall.knowledge());
        }

        if (diff > TFCInfoConfig.rainfall_memory_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.rainfall.memory())) {
            GameStageHelper.addStage(player, Stages.rainfall.memory());
        }
    }


    //days minutes seconds ticks
    public static void unlockLongitudinalKnowledge(EntityPlayer player, TimeData timeData) {
        long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.longitudinal_knowledge_start;
        if (diff > TFCInfoConfig.longitudinal_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.longitudinal.knowledge())) {
            GameStageHelper.addStage(player, Stages.longitudinal.knowledge());
        }

        if (diff > TFCInfoConfig.longitudinal_memory_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.longitudinal.memory())) {
            GameStageHelper.addStage(player, Stages.longitudinal.memory());
        }
    }

    public static void unlockDepthKnowledge(EntityPlayer player, TimeData timeData) {
        long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.depth_knowledge_start;
        if (diff > TFCInfoConfig.depth_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.depth.knowledge())) {
            GameStageHelper.addStage(player, Stages.depth.knowledge());
        }

        if (diff > TFCInfoConfig.depth_memory_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.depth.memory())) {
            GameStageHelper.addStage(player, Stages.depth.memory());
        }
    }

    public static void checkConstellationProgress(EntityPlayer player, TimeData timeData) {
        long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.constellation_knowledge_start;
        if (diff > TFCInfoConfig.constellation_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.constellation.knowledge())) {
            GameStageHelper.addStage(player, Stages.constellation.knowledge());
        }

        if (diff > TFCInfoConfig.constellation_memory_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.constellation.memory())) {
            GameStageHelper.addStage(player, Stages.constellation.memory());
        }
    }

    public static void checkMonsterFerocityProgress(EntityPlayer player, TimeData timeData) {
        long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.monster_ferocity_knowledge_start;
        if (diff > TFCInfoConfig.monster_ferocity_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.monsterFerocity.knowledge())) {
            GameStageHelper.addStage(player, Stages.monsterFerocity.knowledge());
        }

        if (diff > TFCInfoConfig.monster_ferocity_memory_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.monsterFerocity.memory())) {
            GameStageHelper.addStage(player, Stages.monsterFerocity.memory());
        }
    }

    public static void checkSpawnProtectionProgress(EntityPlayer player, TimeData timeData) {
        long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.monster_migration_knowledge_start;
        if (diff > TFCInfoConfig.monster_migration_knowledge_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.monsterMigration.knowledge())) {
            GameStageHelper.addStage(player, Stages.monsterMigration.knowledge());
        }

        if (diff > TFCInfoConfig.monster_migration_memory_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.monsterMigration.memory())) {
            GameStageHelper.addStage(player, Stages.monsterMigration.memory());
        }
    }

    public static void checkNutritionProgress(EntityPlayer player, TimeData timeData) {
        long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.nutrition_knowledge_start;
        if (diff > TFCInfoConfig.nutrition_memory_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.nutrition.knowledge())) {
            GameStageHelper.addStage(player, Stages.nutrition.knowledge());
        }
    }

    public static void checkDateProgress(EntityPlayer player, TimeData timeData) {
        long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.date_memory_start;
        if (diff > TFCInfoConfig.date_memory_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.date.memory())) {
            GameStageHelper.addStage(player, Stages.date.memory());
        }
    }

    public static void checkTimeProgress(EntityPlayer player, TimeData timeData) {
        long diff = player.world.getWorldInfo().getWorldTotalTime() - timeData.time_knowledge_start;
        if (diff > TFCInfoConfig.time_memory_requirement && !GameStageHelper.hasStage(player, GameStageHelper.getPlayerData(player), Stages.time.knowledge())) {
            GameStageHelper.addStage(player, Stages.time.memory());
        }
    }
}
