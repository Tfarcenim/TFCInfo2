package tfar.tfcinfo.event;

import net.darkhax.bookshelf.data.MoonPhase;
import net.darkhax.gamestages.GameStageHelper;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.client.button.GuiButtonPlayerInventoryTab;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendarFormatted;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateHelper;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import tfar.tfcinfo.Utils;
import tfar.tfcinfo.util.KnowledgeMemoryPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static net.dries007.tfc.util.calendar.ICalendarFormatted.*;
import static net.minecraft.util.text.TextFormatting.*;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientForgeEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void lessDebugInfo(RenderGameOverlayEvent.Text e) {

        EntityPlayer player = Minecraft.getMinecraft().player;
        List<String> left = e.getLeft();
        List<String> right = e.getRight();

        if (left.isEmpty()) {
            return;
        }

            //remove first
        deleteLeftLines(left, player);

        //then add new lines
        addLeftLines(left, player);

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

            if (s.contains("Difficulty")) {
                String s1 = ClientHelper.canDisplayLocalDifficulty(player) ? s.split("\\(")[0] : "";
                left.set(i, s1);
            }
        }
        //right hand side

        deleteRightLines(right, player);
        addRightLines(right,player);

        for (int i = 0; i < right.size(); i++) {
            String s = right.get(i);

            if (s.contains("Date")) {
                String s1 = "";
                if (ClientHelper.canDisplayDate(player)) {
                    s1 = getDate();
                }
                right.set(i, s1);
            }
        }
    }

    //disable calendar and nutrition tab
    @SubscribeEvent(priority = EventPriority.HIGHEST,receiveCanceled = true)
    public static void buttonClick(GuiScreenEvent.ActionPerformedEvent.Pre e) {
        if ((e.getButton() instanceof GuiButtonPlayerInventoryTab)) {
            GuiButtonPlayerInventoryTab tab = (GuiButtonPlayerInventoryTab) e.getButton();
            System.out.println(tab.getGuiType());
            if (tab.getGuiType() == TFCGuiHandler.Type.CALENDAR && !ClientHelper.hasCalendarTab(Minecraft.getMinecraft().player)) {
                e.setCanceled(true);
                System.out.println("cannot open "+ tab.getGuiType()+ " at this time");
                return;
            }
            if (tab.getGuiType() == TFCGuiHandler.Type.NUTRITION && !ClientHelper.canDisplayNutrition(Minecraft.getMinecraft().player)) {
                e.setCanceled(true);
                System.out.println("cannot open "+ tab.getGuiType()+ " at this time");
                return;
            }
            if (tab.getGuiType() == TFCGuiHandler.Type.SKILLS && !ClientHelper.canDisplaySkills(Minecraft.getMinecraft().player)) {
                e.setCanceled(true);
                System.out.println("cannot open "+ tab.getGuiType()+ " at this time");
            }
        }
    }

    //disable calendar and nutrition tab
    @SubscribeEvent(receiveCanceled = true)
    public static void buttonClick1(GuiScreenEvent.ActionPerformedEvent.Pre e) {
        if ((e.getButton() instanceof GuiButtonPlayerInventoryTab)) {
            GuiButtonPlayerInventoryTab tab = (GuiButtonPlayerInventoryTab) e.getButton();
            System.out.println(tab.getGuiType());
            if (tab.getGuiType() == TFCGuiHandler.Type.CALENDAR && !ClientHelper.hasCalendarTab(Minecraft.getMinecraft().player)) {
                e.setCanceled(true);
                System.out.println("cannot open "+ tab.getGuiType()+ " at this time");
                return;
            }
            if (tab.getGuiType() == TFCGuiHandler.Type.NUTRITION && !ClientHelper.canDisplayNutrition(Minecraft.getMinecraft().player)) {
                e.setCanceled(true);
                System.out.println("cannot open "+ tab.getGuiType()+ " at this time");
                return;
            }
            if (tab.getGuiType() == TFCGuiHandler.Type.SKILLS && !ClientHelper.canDisplaySkills(Minecraft.getMinecraft().player)) {
                e.setCanceled(true);
                System.out.println("cannot open "+ tab.getGuiType()+ " at this time");
            }
        }
    }

    //disable calendar and nutrition tab
    @SubscribeEvent(priority = EventPriority.LOWEST,receiveCanceled = true)
    public static void buttonClick2(GuiScreenEvent.ActionPerformedEvent.Pre e) {
        if ((e.getButton() instanceof GuiButtonPlayerInventoryTab)) {
            GuiButtonPlayerInventoryTab tab = (GuiButtonPlayerInventoryTab) e.getButton();
            System.out.println(tab.getGuiType());
            if (tab.getGuiType() == TFCGuiHandler.Type.CALENDAR && !ClientHelper.hasCalendarTab(Minecraft.getMinecraft().player)) {
                e.setCanceled(true);
                System.out.println("cannot open "+ tab.getGuiType()+ " at this time");
                return;
            }
            if (tab.getGuiType() == TFCGuiHandler.Type.NUTRITION && !ClientHelper.canDisplayNutrition(Minecraft.getMinecraft().player)) {
                e.setCanceled(true);
                System.out.println("cannot open "+ tab.getGuiType()+ " at this time");
                return;
            }
            if (tab.getGuiType() == TFCGuiHandler.Type.SKILLS && !ClientHelper.canDisplaySkills(Minecraft.getMinecraft().player)) {
                e.setCanceled(true);
                System.out.println("cannot open "+ tab.getGuiType()+ " at this time");
            }
        }
    }

    public static void deleteLeftLines(List<String> left, EntityPlayer player) {
        left.removeIf(s -> {
            if (s.toLowerCase().contains("light")) {
                return !ClientHelper.canDisplayLightLevel(player);
            } else if (s.contains("Biome")) {
                return !ClientHelper.canDisplayBiome(player);
            } else {
                return !s.contains("fps") && !s.contains("Minecraft") && !s.contains("Local");
            }
        });
    }

    public static void addLeftLines(List<String> left, EntityPlayer player) {
        if (ClientHelper.canDisplayX(player)) {
            left.add("X: " + player.posX);
        }

        if (ClientHelper.canDisplayY(player)) {
            left.add("Y: " + player.posY);
        }

        if (ClientHelper.canDisplayZ(player)) {
            left.add("Z: " + player.posZ);
        }
        if (canDisplayFacing(player)) {
            EnumFacing enumfacing = player.getHorizontalFacing();
            String s = "Invalid";

            switch (enumfacing) {
                case NORTH:
                    s = "negative Z";
                    break;
                case SOUTH:
                    s = "positive Z";
                    break;
                case WEST:
                    s = "negative X";
                    break;
                case EAST:
                    s = "positive X";
            }

            left.add("Facing Towards: " + s);
        }

        if (ClientHelper.canDisplayMoonPhase(player)) {
            String phaseName = MoonPhase.getCurrentPhase().getPhaseName();
            left.add("Moon Phase: " + phaseName);
        }

        if (ClientHelper.canDisplaySlimeChunks(player)) {
            left.add("Slime Chunk: " + TooltipHandler.slime_chunk);
        }
        if (ClientHelper.canDisplaySpawnProtectionTimer(player)) {
            ChunkDataTFC data = ChunkDataTFC.get(player.world, player.getPosition());
            left.add("Spawn Protection: " + data.getSpawnProtection());
        }
    }

    public static void deleteRightLines(List<String> right, EntityPlayer player) {
        right.removeIf(s -> {
            if (s.contains("Rainfall")) {
                return !ClientHelper.canDisplayRainfall(player);
            }

            if (s.contains("Spawn Protection")) {
                return !ClientHelper.canDisplaySpawnProtectionTimer(player);
            }

            if (s.contains("Java") || s.contains("Allocated") || s.contains("Mem") ||s.contains("MCP") || s.contains("active") ||s.contains("Forge")) {
                return false;
            }

            if (s.contains("Optifine")) {
                return !ClientHelper.canDisplayMisc(player);
            }

            return true;

        });
    }

    public static void addRightLines(List<String> right, EntityPlayer player) {
        BlockPos blockpos = new BlockPos(TooltipHandler.mc.getRenderViewEntity().posX, TooltipHandler.mc.getRenderViewEntity().getEntityBoundingBox().minY, TooltipHandler.mc.getRenderViewEntity().posZ);
        Chunk chunk = TooltipHandler.mc.world.getChunk(blockpos);
        if (TooltipHandler.mc.world.isBlockLoaded(blockpos) && !chunk.isEmpty()) {
            final int x = blockpos.getX() & 15, z = blockpos.getZ() & 15;
            ChunkDataTFC data = chunk.getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);

            List<String> tempStrings = new ArrayList<>();

            if (ClientHelper.canDisplayCurrentTemp(TooltipHandler.mc.player)) {
                tempStrings.add(String.format("Current: %s%.1f\u00b0C%s", WHITE, data.getRegionalTemp(), GRAY));
            }

            if (ClientHelper.canDisplayAverageTemp(TooltipHandler.mc.player)) {
                tempStrings.add(String.format("Avg: %s%.1f\u00b0C%s", WHITE, data.getAverageTemp(), GRAY));
            }

            if (ClientHelper.canDisplayMaxTemp(TooltipHandler.mc.player)) {
                tempStrings.add(String.format("Max: %s%.1f\u00b0C%s",
                        WHITE, ClimateHelper.monthFactor(data.getRegionalTemp(), Month.JULY.getTemperatureModifier(), blockpos.getZ()), GRAY));
            }



            if (ClientHelper.canDisplayMinTemp(TooltipHandler.mc.player)) {
                tempStrings.add(String.format("Min: %s%.1f\u00b0C%s",
                        WHITE, ClimateHelper.monthFactor(data.getRegionalTemp(), Month.JANUARY.getTemperatureModifier(), blockpos.getZ()), GRAY));
            }
            if (!tempStrings.isEmpty()) {
                right.add("Temperature");
                right.addAll(tempStrings);
            }

            if (ClientHelper.canDisplayTime(player)) {
                right.add(getTime());
            }

            if (ClientHelper.canDisplayFlora(TooltipHandler.mc.player)) {
                right.add("Flora Density: "+data.getFloraDensity());
                right.add("Flora Diversity: "+data.getFloraDiversity());
            }

            if (ClientHelper.canDisplayArboreal(player)) {
                right.add(new TextComponentString("Trees").setStyle(new Style().setColor(AQUA)).getFormattedText());
                for (Tree tree : data.getValidTrees()) {
                    right.add(tree.getRegistryName().toString());
                }
            }
        }
    }

    public static String getDate() {
        ICalendarFormatted iCalendarFormatted = CalendarTFC.CALENDAR_TIME;
        long time = iCalendarFormatted.getTicks();
        long daysInMonth = iCalendarFormatted.getDaysInMonth();
        return I18n.format("tfcinfo.tooltip.date",
                I18n.format("tfc.enum.month." + getMonthOfYear(time, daysInMonth).toString().toLowerCase(Locale.ROOT))
                , getDayOfMonth(time, daysInMonth), getTotalYears(time, daysInMonth));
    }

    public static String getTime() {
        ICalendarFormatted iCalendarFormatted = CalendarTFC.CALENDAR_TIME;
        long time = iCalendarFormatted.getTicks();
        int minute = getMinuteOfHour(time);
        return "Time: " + getHourOfDay(time) + ":" + (minute < 10 ? "0" : "") + minute;
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent e) {
        EntityPlayer player = e.getEntityPlayer();
        if (player != null) {
            ItemStack stack = e.getItemStack();
            TooltipHandler.addTemperatureKnowledgeRequirements(stack, player, e.getToolTip());
        }
    }


    public static boolean hasMemoryOrKnowledgeOrItem(EntityPlayer player, KnowledgeMemoryPair pair) {
        if (GameStageHelper.hasStage(player, pair.memory())) return true;
        if (hasItemWithKnowledge(player, pair.knowledge())) return true;
        if (Utils.hasOreDictItem(pair.base(), player.inventory)) return true;
        return false;
    }

    public static boolean hasItemWithKnowledge(EntityPlayer player, String stage) {
        return GameStageHelper.hasStage(player, stage) && Utils.hasOreDictItem(stage, player.inventory);
    }

    public static boolean canDisplayFacing(EntityPlayer player) {
        return hasMemoryOrKnowledgeOrItem(player,Stages.facing);
    }
}
