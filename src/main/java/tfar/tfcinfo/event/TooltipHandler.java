package tfar.tfcinfo.event;

import net.darkhax.gamestages.GameStageHelper;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tfar.tfcinfo.Stages;
import tfar.tfcinfo.TFCInfoConfig;
import tfar.tfcinfo.TimeData;
import tfar.tfcinfo.util.KnowledgeMemoryPair;

import javax.annotation.Nullable;
import java.util.List;

public class TooltipHandler {

    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final TimeData timeData = new TimeData();

    public static boolean slime_chunk;

    public static void addTemperatureKnowledgeRequirements(ItemStack stack, EntityPlayer player, List<String> tooltip) {
        long current_time = player.world.getTotalWorldTime();
        if (OreDictionaryHelper.doesStackMatchOre(stack, Stages.averageTemp.unlocksKnowledge())) {
            if (!GameStageHelper.hasStage(player, Stages.averageTemp.knowledge()) && timeData.avg_temp_knowledge_start > -1) {
                long remaining_time = (long) (TFCInfoConfig.avg_temp_knowledge_requirement - current_time + timeData.avg_temp_knowledge_start);
                long days = (long) (remaining_time / TFCInfoConfig.ticksToDays);
                tooltip.add("Remaining Time to unlock Average Temperature knowledge: " + days + " days");

            } else if (!GameStageHelper.hasStage(player, Stages.averageTemp.memory()) && timeData.avg_temp_knowledge_start > -1) {
                long remaining_time = (long) (TFCInfoConfig.avg_temp_memory_requirement - current_time + timeData.avg_temp_knowledge_start);
                long days = (long) (remaining_time / TFCInfoConfig.ticksToDays);
                tooltip.add("Remaining Time to unlock Average Temperature memorization: " + days + " days");
            }
        }

        if (OreDictionaryHelper.doesStackMatchOre(stack, Stages.maxTemp.unlocksKnowledge())) {
            if (!GameStageHelper.hasStage(player, Stages.maxTemp.knowledge()) && timeData.max_temp_knowledge_start > -1) {
                long remaining_time = (long) (TFCInfoConfig.max_temp_knowledge_requirement - current_time + timeData.max_temp_knowledge_start);
                long days = (long) (remaining_time / TFCInfoConfig.ticksToDays);
                tooltip.add("Remaining Time to unlock Maximum Temperature knowledge: " + days + " days");

            } else if (!GameStageHelper.hasStage(player, Stages.maxTemp.memory()) && timeData.max_temp_knowledge_start > -1) {
                long remaining_time = (long) (TFCInfoConfig.max_temp_memorization_requirement - current_time + timeData.max_temp_knowledge_start);
                long days = (long) (remaining_time / TFCInfoConfig.ticksToDays);
                tooltip.add("Remaining Time to unlock Maximum Temperature memorization: " + days + " days");
            }
        }

        if (OreDictionaryHelper.doesStackMatchOre(stack, Stages.minTemp.unlocksKnowledge())) {
            if (!GameStageHelper.hasStage(player, Stages.minTemp.knowledge()) && timeData.min_temp_knowledge_start > -1) {
                long remaining_time = (long) (TFCInfoConfig.min_temp_knowledge_requirement - current_time + timeData.min_temp_knowledge_start);
                long days = (long) (remaining_time / TFCInfoConfig.ticksToDays);
                tooltip.add("Remaining Time to unlock Minimum Temperature knowledge: " + days + " days");

            } else if (!GameStageHelper.hasStage(player, Stages.minTemp.memory()) && timeData.min_temp_knowledge_start > -1) {
                long remaining_time = (long) (TFCInfoConfig.min_temp_memory_requirement - current_time + timeData.min_temp_knowledge_start);
                long days = (long) (remaining_time / TFCInfoConfig.ticksToDays);
                tooltip.add("Remaining Time to unlock Minimum Temperature memorization: " + days + " days");
            }
        }


        //xyz

        addTip(stack,player,current_time,tooltip,Stages.longitudinal,
                (long) TFCInfoConfig.longitudinal_knowledge_requirement,
                (long) TFCInfoConfig.longitudinal_memory_requirement,
                timeData.longitudinal_knowledge_start);

        addTip(stack,player,current_time,tooltip,Stages.depth,
                (long) TFCInfoConfig.depth_knowledge_requirement,
                (long) TFCInfoConfig.depth_memory_requirement,
                timeData.depth_knowledge_start);

        addTip(stack,player,current_time,tooltip,Stages.constellation,
                (long) TFCInfoConfig.constellation_knowledge_requirement,
                (long) TFCInfoConfig.constellation_memory_requirement,
                timeData.constellation_knowledge_start);

        //rain

        addTip(stack,player,current_time,tooltip,Stages.rainfall,
                (long) TFCInfoConfig.rainfall_knowledge_requirement,
                (long) TFCInfoConfig.rainfall_memory_requirement,
                timeData.rainfall_knowledge_start);

        //date

        addTip(stack,player,current_time,tooltip,Stages.date,
                 null,
                (long) TFCInfoConfig.rainfall_memory_requirement,
                timeData.rainfall_knowledge_start);

        //time

        addTip(stack,player,current_time,tooltip,Stages.time,
                null,
                (long) TFCInfoConfig.time_memory_requirement,
                timeData.time_knowledge_start);
        //biome

        addTip(stack,player,current_time,tooltip,Stages.rainfall,
                null,
                (long) TFCInfoConfig.biome_memory_requirement,
                timeData.biome_knowledge_start);
    }

    public static void addTip(ItemStack stack, EntityPlayer player, long current_time, List<String> tooltip,
                              KnowledgeMemoryPair type, @Nullable Long knowledge_config_requirement, long memory_config_requirement, long time_start) {
        if (!OreDictionaryHelper.doesStackMatchOre(stack,type.unlocksKnowledge())) {
            return;
        }

        boolean memoryOnly = knowledge_config_requirement == null;


        State state = getState(player, type,memoryOnly);
        if (state == State.MEMORY) {
            return;
        }
        String s = "Remaining Time to unlock ";

        if (time_start > -1) {

            long remaining_time = 0;

            switch (state) {
                case KNOWLEDGE:
                    remaining_time = memory_config_requirement - current_time + time_start;
                    s += type.memory();
                    break;
                case NONE:
                    remaining_time = (memoryOnly ? memory_config_requirement : knowledge_config_requirement) - current_time + time_start;
                    s += memoryOnly ? type.memory() : type.knowledge();
            }

            long days = (long) (remaining_time / TFCInfoConfig.ticksToDays);
            s += " "+ days + " Days";
            tooltip.add(s);
        }
    }

    public static State getState(EntityPlayer player, KnowledgeMemoryPair type,boolean memoryOnly) {
        if (GameStageHelper.hasStage(player,type.memory())) {
            return State.MEMORY;
        } else if (!memoryOnly && GameStageHelper.hasStage(player,type.knowledge())) {
            return State.KNOWLEDGE;
        }
        return State.NONE;
    }

    public enum State {
        NONE,KNOWLEDGE,MEMORY
    }
}
