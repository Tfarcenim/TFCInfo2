package tfar.tfcinfo.event;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayer;
import tfar.tfcinfo.Stages;
import tfar.tfcinfo.Utils;
import tfar.tfcinfo.clent.TFCInfoClientConfig;

public class ClientHelper {

    public static boolean canDisplayAverageTemp(EntityPlayer player) {
        return TFCInfoClientConfig.show_avg_temp && ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.averageTemp);
    }

    public static boolean canDisplayMaxTemp(EntityPlayer player) {
        return TFCInfoClientConfig.show_max_temp && ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.maxTemp);
    }

    public static boolean canDisplayMinTemp(EntityPlayer player) {
        return TFCInfoClientConfig.show_min_temp && ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.minTemp);
    }

    public static boolean canDisplayRainfall(EntityPlayer player) {
        return TFCInfoClientConfig.show_rainfall && ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.rainfall);
    }

    public static boolean canDisplayDate(EntityPlayer player) {
        return TFCInfoClientConfig.show_date && Utils.hasOreDictItem(Stages.date.base(),player.inventory) || ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.date);
    }

    public static boolean canDisplayTime(EntityPlayer player) {
        return TFCInfoClientConfig.show_time && Utils.hasOreDictItem(Stages.time.base(), player.inventory);
    }

    public static boolean canDisplayX(EntityPlayer player) {
        return TFCInfoClientConfig.show_constellation && ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.longitudinal);
    }

    public static boolean canDisplayY(EntityPlayer player) {
        return TFCInfoClientConfig.show_depth && ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.depth);
    }

    public static boolean canDisplayZ(EntityPlayer player) {
        return TFCInfoClientConfig.show_longitudinal && ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.constellation);
    }

    public static boolean canDisplayMonsterFerocity(EntityPlayer player) {
        return TFCInfoClientConfig.show_local_difficulty && ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.localDifficulty);
    }

    public static boolean canDisplayMoonPhase(EntityPlayer player) {
        return TFCInfoClientConfig.show_moon_phase && Utils.hasOreDictItem(Stages.moonPhase.base(), player.inventory);
    }

    public static boolean canDisplaySlimeChunks(EntityPlayer player) {
        return TFCInfoClientConfig.show_slime_chunks && Utils.hasOreDictItem(Stages.slimeChunk.base(),player.inventory);
    }

    public static boolean canDisplaySpawnProtectionTimer(EntityPlayer player) {
        return TFCInfoClientConfig.show_spawn_protection_timer && ClientForgeEvents.hasMemoryOrKnowledge(player, Stages.spawnProtectionTimer);
    }

    public static boolean canDisplayLightLevel(EntityPlayer player) {
        return TFCInfoClientConfig.show_light_level && Utils.hasOreDictItem(Stages.lightLevel.base(),player.inventory);
    }

    public static boolean canDisplayBiome(EntityPlayer player) {
        return TFCInfoClientConfig.show_biome && (GameStageHelper.hasStage(player,Stages.biome.memory()) ||Utils.hasOreDictItem(Stages.hwyla.base(),player.inventory));
    }

    public static boolean canDisplayNutrition(EntityPlayer player) {
        return GameStageHelper.hasStage(player,Stages.nutrition.memory());
    }

    public static boolean canDisplayHwyla(EntityPlayer player) {
        return GameStageHelper.hasStage(player,Stages.hwyla.memory()) || Utils.hasOreDictItem(Stages.hwyla.base(),player.inventory);
    }

    public static boolean canDisplaySkills(EntityPlayer player) {
        return GameStageHelper.hasStage(player,Stages.skills.memory());
    }

    public static boolean canDisplayFlora(EntityPlayer player) {
        return TFCInfoClientConfig.show_flora && GameStageHelper.hasStage(player,Stages.flora.memory());
    }

    public static boolean canDisplayArboreal(EntityPlayer player) {
        return TFCInfoClientConfig.show_trees && GameStageHelper.hasStage(player,Stages.arboreal.memory());
    }
}
