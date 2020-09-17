package tfar.tfcinfo.event;

import net.minecraft.entity.player.EntityPlayer;
import tfar.tfcinfo.Stages;
import tfar.tfcinfo.Utils;

public class DisplayHelper {
    public static boolean canDisplayRegionalTemp(EntityPlayer player) {
        return ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.regionalTemp);
    }

    public static boolean canDisplayAverageTemp(EntityPlayer player) {
        return ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.averageTemp);
    }

    public static boolean canDisplayMaxTemp(EntityPlayer player) {
        return ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.maxTemp);
    }

    public static boolean canDisplayMinTemp(EntityPlayer player) {
        return ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.minTemp);
    }

    public static boolean canDisplayRainfall(EntityPlayer player) {
        return ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.rainfall);
    }

    public static boolean canDisplayDate(EntityPlayer player) {
        return ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.date);
    }

    public static boolean canDisplayTime(EntityPlayer player) {
        return Utils.hasOreDictItem(Stages.time.base(), player.inventory);
    }

    public static boolean canDisplayX(EntityPlayer player) {
        return ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.longitudinal);
    }

    public static boolean canDisplayY(EntityPlayer player) {
        return ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.depth);
    }

    public static boolean canDisplayZ(EntityPlayer player) {
        return ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.constellation);
    }

    public static boolean canDisplayMonsterFerocity(EntityPlayer player) {
        return ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.localDifficulty);
    }

    public static boolean canDisplayMoonPhase(EntityPlayer player) {
        return Utils.hasOreDictItem(Stages.moonPhase.base(), player.inventory);
    }

    public static boolean canDisplaySlimeChunks(EntityPlayer player) {
        return Utils.hasOreDictItem(Stages.slimeChunk.base(),player.inventory);
    }

    public static boolean canDisplaySpawnProtectionTimer(EntityPlayer player) {
        return ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.spawnProtectionTimer);
    }

    public static boolean canDisplayLightLevel(EntityPlayer player) {
        return Utils.hasOreDictItem(Stages.lightLevel.base(),player.inventory);
    }
}
