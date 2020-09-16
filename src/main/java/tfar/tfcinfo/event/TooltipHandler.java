package tfar.tfcinfo.event;

import net.darkhax.gamestages.GameStageHelper;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tfar.tfcinfo.Stages;
import tfar.tfcinfo.TFCInfoConfig;

import java.util.List;

public class TooltipHandler {

    public static final Minecraft mc = Minecraft.getMinecraft();
    public static long avg_temp_knowledge_start = -1;
    public static long max_temp_knowledge_start = -1;
    public static long rainfall_knowledge_start = -1;
    public static long monster_ferocity_knowledge_start = -1;
    public static long constellation_knowledge_start = -1;

    public static boolean slime_chunk;

    public static void addTemperatureKnowledgeRequirements(ItemStack stack, EntityPlayer player, List<String> tooltip) {
        long current_time = player.world.getTotalWorldTime();
        if (OreDictionaryHelper.doesStackMatchOre(stack, Stages.averageTemp.knowledge())) {
            if (!GameStageHelper.hasStage(player, Stages.averageTemp.knowledge()) && avg_temp_knowledge_start > -1) {
                long remaining_time = (long) (TFCInfoConfig.avg_temp_knowledge_requirement - current_time + avg_temp_knowledge_start);
                long days = (long) (remaining_time / TFCInfoConfig.ticksToDays);
                tooltip.add("Remaining Time to unlock Average Temperature knowledge: " + days + " days");
            } else if (!GameStageHelper.hasStage(player, Stages.averageTemp.memory()) && avg_temp_knowledge_start > -1) {
                long remaining_time = (long) (TFCInfoConfig.avg_temp_memory_requirement - current_time + avg_temp_knowledge_start);
                long days = (long) (remaining_time / TFCInfoConfig.ticksToDays);
                tooltip.add("Remaining Time to unlock Average Temperature memorization: " + days + " days");
            }
        }

        if (OreDictionaryHelper.doesStackMatchOre(stack, Stages.maxTemp.knowledge())) {
            if (!GameStageHelper.hasStage(player, Stages.maxTemp.knowledge()) && max_temp_knowledge_start > -1) {
                long remaining_time = (long) (TFCInfoConfig.max_temp_knowledge_requirement - current_time + max_temp_knowledge_start);
                long days = (long) (remaining_time / TFCInfoConfig.ticksToDays);
                tooltip.add("Remaining Time to unlock Maximum Temperature knowledge: " + days + " days");
            } else if (!GameStageHelper.hasStage(player, Stages.maxTemp.memory()) && max_temp_knowledge_start > -1) {
                long remaining_time = (long) (TFCInfoConfig.max_temp_memorization_requirement - current_time + max_temp_knowledge_start);
                long days = (long) (remaining_time / TFCInfoConfig.ticksToDays);
                tooltip.add("Remaining Time to unlock Maximum Temperature memorization: " + days + " days");
            }
        }

        if (OreDictionaryHelper.doesStackMatchOre(stack, Stages.minTemp.knowledge())) {
            if (!GameStageHelper.hasStage(player, Stages.minTemp.knowledge()) && max_temp_knowledge_start > -1) {
                long remaining_time = (long) (TFCInfoConfig.max_temp_knowledge_requirement - current_time + max_temp_knowledge_start);
                long days = (long) (remaining_time / TFCInfoConfig.ticksToDays);
                tooltip.add("Remaining Time to unlock Minimum Temperature knowledge: " + days + " days");
            } else if (!GameStageHelper.hasStage(player, Stages.minTemp.memory()) && max_temp_knowledge_start > -1) {
                long remaining_time = (long) (TFCInfoConfig.min_temp_memory_requirement - current_time + max_temp_knowledge_start);
                long days = (long) (remaining_time / TFCInfoConfig.ticksToDays);
                tooltip.add("Remaining Time to unlock Minimum Temperature memorization: " + days + " days");
            }
        }
    }
}
