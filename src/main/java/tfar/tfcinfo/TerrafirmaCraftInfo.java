package tfar.tfcinfo;

import net.dries007.tfc.objects.items.itemblock.ItemBlockMetalLamp;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;
import tfar.tfcinfo.command.ModCommand;
import tfar.tfcinfo.network.PacketHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod(modid = TerrafirmaCraftInfo.MODID, name = TerrafirmaCraftInfo.NAME, version = TerrafirmaCraftInfo.VERSION,
        dependencies = "")
@Mod.EventBusSubscriber
public class TerrafirmaCraftInfo {
    public static final String MODID = "tfcinfo";
    public static final String NAME = "Terrafirmacraft Info";
    public static final String VERSION = "@VERSION@";

    private static Logger logger;

    @EventHandler
    public void common(FMLInitializationEvent e) {
        PacketHandler.registerMessages(MODID);
        registerHolding();
        registerUnlockTriggers();
        registerKnowledgeHolding();
    }

    //these show debug items directly
    public static void registerHolding() {
        registerOres(Stages.moonPhase.base(),ModItems.ALMANAC,ModItems.MOON_CHART,ModItems.NAVIGATOR_BUNDLE,ModItems.PDA);
        registerOres(Stages.time.base(),ModItems.NAVIGATOR_BUNDLE,ModItems.TIME_BUNDLE,ModItems.PDA);
        registerOres(Stages.date.base(),ModItems.CALENDAR,ModItems.NAVIGATOR_BUNDLE,ModItems.TIME_BUNDLE,ModItems.PDA);
        registerOres(Stages.rainfall.base(),ModItems.RANGER_BUNDLE,ModItems.PIONEER_GEAR,ModItems.PDA);
        registerOres(Stages.averageTemp.base(),ModItems.ENHANCED_TEMPERATURE_BUNDLE,ModItems.TRAILBLAZER_GEAR,ModItems.PDA);
        registerOres(Stages.maxTemp.base(),ModItems.ENHANCED_TEMPERATURE_BUNDLE,ModItems.PDA);
        registerOres(Stages.minTemp.base(),ModItems.ENHANCED_TEMPERATURE_BUNDLE,ModItems.PDA);
        registerOres(Stages.slimeChunk.base(),ModItems.RANGER_BUNDLE,ModItems.ENHANCED_RANGER_BUNDLE,ModItems.SLIME_SAMPLE,ModItems.PDA);
        registerOres(Stages.monsterMigration.base(),ModItems.RANGER_BUNDLE,ModItems.PDA);
        registerOres(Stages.monsterFerocity.base(),ModItems.ENHANCED_RANGER_BUNDLE,ModItems.PDA);
        registerOres(Stages.facing.base(), Items.COMPASS,ModItems.ENHANCED_SPACE_BUNDLE,ModItems.PDA);
        registerOres(Stages.hwyla.base(),ModItems.PDA,ModItems.SURVIVAL_GUIDE,ModItems.PDA);
        List<Item> lamps = new ArrayList<>();
        for (Item item : Item.REGISTRY) {
            if (item instanceof ItemBlockMetalLamp) {
                lamps.add(item);
            }
        }
        lamps.add(ModItems.PDA);
        registerOres(Stages.lightLevel.base(),lamps.toArray(new Item[0]));
        registerOres(Stages.monsterMigration.base(),ModItems.ENHANCED_TIME_BUNDLE,ModItems.PDA);
    }

    //picking these up for the first time starts knowledge timer
    public static void registerUnlockTriggers() {
        registerOres(Stages.averageTemp.unlocksKnowledge(),ModItems.SPIRIT_THERMOMETER);
        registerOres(Stages.maxTemp.unlocksKnowledge(),ModItems.RECENT_TEMPERATURE_RECORDS);
        registerOres(Stages.minTemp.unlocksKnowledge(),ModItems.RECENT_TEMPERATURE_RECORDS);
        //unlocks x
        registerOres(Stages.longitudinal.unlocksKnowledge(),ModItems.NAVIGATOR_BUNDLE);
        //unlocks y
        registerOres(Stages.depth.unlocksKnowledge(),ModItems.PLUMB_BOB);
        //unlocks z
        registerOres(Stages.constellation.unlocksKnowledge(),ModItems.ALMANAC);
        //unlocks date
        registerOres(Stages.date.unlocksKnowledge(),ModItems.CALENDAR);
        registerOres(Stages.time.unlocksKnowledge(),Items.CLOCK);

        List<Item> empty_buckets = new ArrayList<>();
        for (Item item : Item.REGISTRY) {
            if (item.getRegistryName().getPath().contains("bucket")) {
                empty_buckets.add(item);
            }
        }
        registerOres(Stages.rainfall.unlocksKnowledge(),empty_buckets.toArray(new Item[0]));
        registerOres(Stages.monsterFerocity.unlocksKnowledge(),ModItems.MONSTER_FEROCITY_SAMPLE);
        registerOres(Stages.monsterMigration.unlocksKnowledge(),ModItems.MONSTER_MIGRATION_SAMPLE);

    }

    //these items require both knowledge AND the item in order to add to debug menu
    public static void registerKnowledgeHolding() {
        registerOres(Stages.averageTemp.knowledge(),ModItems.TEMPERATURE_BUNDLE,ModItems.PIONEER_GEAR);
        registerOres(Stages.maxTemp.knowledge(),ModItems.RECENT_TEMPERATURE_RECORDS,ModItems.TEMPERATURE_BUNDLE,ModItems.PIONEER_GEAR);
        registerOres(Stages.minTemp.knowledge(),ModItems.RECENT_TEMPERATURE_RECORDS,ModItems.TEMPERATURE_BUNDLE,ModItems.PIONEER_GEAR);

        registerOres(Stages.monsterMigration.knowledge(),ModItems.MONSTER_MIGRATION_SAMPLE,ModItems.RANGER_BUNDLE,ModItems.ENHANCED_RANGER_BUNDLE);

        registerOres(Stages.time.knowledge(),ModItems.NAVIGATOR_BUNDLE);

        registerOres(Stages.date.knowledge(), ModItems.CALENDAR,ModItems.ALMANAC,ModItems.NAVIGATOR_BUNDLE);

        //x
        registerOres(Stages.longitudinal.knowledge(),ModItems.SEXTANT,ModItems.NAVIGATOR_BUNDLE,ModItems.SPACE_BUNDLE,ModItems.SPACE_TIME_GEAR);

        //y
        registerOres(Stages.depth.knowledge(),ModItems.PLUMB_BOB,ModItems.SPACE_BUNDLE,ModItems.SPACE_TIME_GEAR);

        //z
        registerOres(Stages.constellation.knowledge(),ModItems.NAVIGATOR_BUNDLE,ModItems.SPACE_BUNDLE,ModItems.SPACE_TIME_GEAR);
    }

    public static void registerOres(String ore, Item... items) {
        Arrays.stream(items).forEach(item -> OreDictionary.registerOre(ore,item));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> e) {
        ModItems.registerItems(e);
    }

    @EventHandler
    public void serverStart(FMLServerStartingEvent e) {
        e.getServer().getWorld(0).getGameRules().setOrCreateGameRule("reducedDebugInfo", String.valueOf(true));
        e.registerServerCommand(new ModCommand());
    }
}
