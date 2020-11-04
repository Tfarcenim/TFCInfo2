package tfar.tfcinfo;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.itemblock.ItemBlockMetalLamp;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import tfar.tfcinfo.command.ModCommand;
import tfar.tfcinfo.event.Stages;
import tfar.tfcinfo.network.PacketHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod(modid = TerrafirmaCraftInfo.MODID, name = TerrafirmaCraftInfo.NAME, version = TerrafirmaCraftInfo.VERSION,
        dependencies = "required:gamestages")
@Mod.EventBusSubscriber
public class TerrafirmaCraftInfo {
    public static final String MODID = "tfcinfo";
    public static final String NAME = "Terrafirmacraft Info";
    public static final String VERSION = "@VERSION@";

    public static TerrafirmaCraftInfo INSTANCE;

    public TerrafirmaCraftInfo() {
        INSTANCE = this;
    }

    @EventHandler
    public void common(FMLInitializationEvent e) {
        PacketHandler.registerMessages(MODID);
        registerHolding();
        registerUnlockTriggers();
        registerKnowledgeHolding();

        Utils.copyOresFromItems(ModItems.RECENT_TEMPERATURE_RECORDS,ModItems.RECORD_HIGHS,ModItems.RECORD_LOWS);

        Utils.copyOresFromItems(ModItems.ENHANCED_TEMPERATURE_BUNDLE,ModItems.TEMPERATURE_BUNDLE);
        Utils.copyOresFromItems(ModItems.ENHANCED_RANGER_BUNDLE,ModItems.RANGER_BUNDLE,ModItems.MONSTER_FEROCITY_SAMPLE);
        Utils.copyOresFromItems(ModItems.ENHANCED_SPACE_BUNDLE,ModItems.SPACE_BUNDLE);
        Utils.copyOresFromItems(ModItems.ENHANCED_TIME_BUNDLE,ModItems.TIME_BUNDLE);

        Utils.copyOresFromItems(ModItems.NAVIGATOR_BUNDLE,Items.CLOCK,ModItems.SEXTANT,ModItems.ALMANAC);

        Utils.copyOresFromItems(ModItems.PIONEER_GEAR,ModItems.RANGER_BUNDLE,ModItems.TEMPERATURE_BUNDLE);
        Utils.copyOresFromItems(ModItems.TRAILBLAZER_GEAR,ModItems.ENHANCED_RANGER_BUNDLE,ModItems.ENHANCED_TEMPERATURE_BUNDLE);

        Utils.copyOresFromItems(ModItems.TIME_BUNDLE,Items.CLOCK,ModItems.CALENDAR);

        Utils.copyOresFromItems(ModItems.SPACE_TIME_GEAR,ModItems.SPACE_BUNDLE,ModItems.TIME_BUNDLE);
        Utils.copyOresFromItems(ModItems.FOURD_GEAR,ModItems.ENHANCED_SPACE_BUNDLE,ModItems.ENHANCED_TIME_BUNDLE);

        Utils.copyOresFromItems(ModItems.ENCYCLOPEDIA,ModItems.TRAILBLAZER_GEAR,ModItems.FOURD_GEAR,ModItems.CLIMATE_CHART,ModItems.WORLD_MAP);
        Utils.copyOresFromItems(ModItems.SURVIVAL_GUIDE,ModItems.SPACE_TIME_GEAR,ModItems.PIONEER_GEAR);
        Utils.copyOresFromItems(ModItems.TWENTY_FOURS_SURVIVAL_GUIDE,ModItems.SURVIVAL_GUIDE);

        for (Item item : ForgeRegistries.ITEMS) {
            if (item.getRegistryName().getPath().contains("bucket")) {
                OreDictionary.registerOre("bucket", item);
            }
        }

        NetworkRegistry.INSTANCE.registerGuiHandler(this,new GuiHandler());
    }

    //these show debug items directly
    public static void registerHolding() {
        registerOres(Stages.currentTemp.base(),ModItems.ENCYCLOPEDIA,ModItems.SURVIVAL_GUIDE,ModItems.SPIRIT_THERMOMETER,ModItems.PDA);
        registerOres(Stages.moonPhase.base(),ModItems.MOON_CHART,ModItems.ENCYCLOPEDIA);
        registerOres(Stages.time.base(),Items.CLOCK,ModItems.ENCYCLOPEDIA,ModItems.PDA);

        Utils.copyOresFromItems(ModItems.ALMANAC,ModItems.MOON_CHART);

        registerOres(Stages.date.base(),ModItems.ALMANAC,ModItems.CALENDAR,ModItems.ENCYCLOPEDIA,ModItems.PDA);

        registerOres(Stages.rainfall.base(),
                ModItems.CLIMATE_CHART,
                ModItems.SURVIVAL_GUIDE,
                ModItems.PDA);

        registerOres(Stages.flora.base(),ModItems.ENCYCLOPEDIA,ModItems.PDA);

        registerOres(Stages.averageTemp.base(),
                ModItems.CLIMATE_CHART,
                ModItems.SURVIVAL_GUIDE,
                ModItems.PDA);

        registerOres(Stages.maxTemp.base(),
                ModItems.CLIMATE_CHART,
                ModItems.SURVIVAL_GUIDE,
                ModItems.PDA);

        registerOres(Stages.minTemp.base(),
                ModItems.CLIMATE_CHART,
                ModItems.SURVIVAL_GUIDE,
                ModItems.PDA);

        registerOres(Stages.slimeChunk.base(),
                ModItems.RANGER_BUNDLE,
                ModItems.SLIME_SAMPLE,
                ModItems.SLIME_COMPASS,
                ModItems.PDA);

        registerOres(Stages.localDifficulty.base(),ModItems.ENCYCLOPEDIA,ModItems.PDA);

        registerOres(Stages.facing.base(), Items.COMPASS,ModItems.ENHANCED_SPACE_BUNDLE,ModItems.PDA);

        registerOres(Stages.hwyla.base(),ModItems.SURVIVAL_GUIDE,ModItems.ENCYCLOPEDIA,ModItems.PDA);

        registerOres(Stages.arboreal.base(),ModItems.ENHANCED_TEMPERATURE_BUNDLE,ModItems.PDA);

        List<Item> lamps = new ArrayList<>();
        for (Item item : Item.REGISTRY) {
            if (item instanceof ItemBlockMetalLamp) {
                lamps.add(item);
            }
        }

        registerOres("lamp",lamps.toArray(new Item[0]));


        lamps.add(ModItems.PDA);


        registerOres(Stages.lightLevel.base(),lamps.toArray(new Item[0]));
        registerOres(Stages.lightLevel.base(),ModItems.ENHANCED_RANGER_BUNDLE);
        registerOres(Stages.spawnProtectionTimer.base(),ModItems.SURVIVAL_GUIDE,ModItems.ENCYCLOPEDIA,ModItems.PDA);

        //x
        registerOres(Stages.longitudinal.base(),ModItems.WORLD_MAP,ModItems.SURVIVAL_GUIDE,ModItems.PDA);

        //x
        registerOres(Stages.depth.base(),ModItems.ENCYCLOPEDIA,ModItems.SURVIVAL_GUIDE,ModItems.PDA);

        //z
        registerOres(Stages.constellation.base(),ModItems.WORLD_MAP,ModItems.SURVIVAL_GUIDE,ModItems.PDA);

        registerOres(Stages.misc.base(),ModItems.DIVINING_ROD,ModItems.PDA);

    }

    //picking these up for the first time starts knowledge timer
    public static void registerUnlockTriggers() {
        registerOres(Stages.averageTemp.unlocksKnowledge(),ModItems.SPIRIT_THERMOMETER);
        registerOres(Stages.maxTemp.unlocksKnowledge(),ModItems.RECORD_HIGHS);
        registerOres(Stages.minTemp.unlocksKnowledge(),ModItems.RECORD_LOWS);
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
        registerOres(Stages.localDifficulty.unlocksKnowledge(),ModItems.MONSTER_FEROCITY_SAMPLE);
        registerOres(Stages.spawnProtectionTimer.unlocksKnowledge(),ModItems.MONSTER_MIGRATION_SAMPLE);
        registerOres(Stages.hwyla.unlocksKnowledge(),ModItems.SURVIVAL_GUIDE,ModItems.ENCYCLOPEDIA,ModItems.PDA);

    }

    //these items require both knowledge AND the item in order to add to debug menu
    public static void registerKnowledgeHolding() {
        registerOres(Stages.averageTemp.knowledge(),ModItems.SPIRIT_THERMOMETER,ModItems.TEMPERATURE_BUNDLE,ModItems.PIONEER_GEAR);
        registerOres(Stages.maxTemp.knowledge(),ModItems.RECENT_TEMPERATURE_RECORDS,ModItems.TEMPERATURE_BUNDLE,ModItems.PIONEER_GEAR);
        registerOres(Stages.minTemp.knowledge(),ModItems.RECENT_TEMPERATURE_RECORDS,ModItems.TEMPERATURE_BUNDLE,ModItems.PIONEER_GEAR);
        registerOres(Stages.localDifficulty.knowledge(),ModItems.MONSTER_FEROCITY_SAMPLE);
        registerOres(Stages.minTemp.knowledge(),ModItems.RECORD_LOWS);
        registerOres(Stages.maxTemp.knowledge(),ModItems.RECORD_HIGHS);

        registerOres(Stages.spawnProtectionTimer.knowledge(),ModItems.MONSTER_MIGRATION_SAMPLE,ModItems.RANGER_BUNDLE);

        List<Item> empty_buckets = new ArrayList<>();
        for (Item item : Item.REGISTRY) {
            if (item.getRegistryName().getPath().contains("bucket")) {
                empty_buckets.add(item);
            }
        }
        registerOres(Stages.rainfall.knowledge(),ModItems.RANGER_BUNDLE);
        registerOres(Stages.rainfall.knowledge(),empty_buckets.toArray(new Item[0]));

        registerOres(Stages.date.knowledge(), ModItems.CALENDAR,ModItems.ALMANAC,ModItems.NAVIGATOR_BUNDLE);

        //x
        registerOres(Stages.longitudinal.knowledge(),ModItems.SEXTANT,ModItems.NAVIGATOR_BUNDLE,ModItems.SPACE_BUNDLE,ModItems.ENHANCED_SPACE_BUNDLE,ModItems.SPACE_TIME_GEAR);

        //y
        registerOres(Stages.depth.knowledge(),ModItems.PLUMB_BOB,ModItems.SPACE_BUNDLE,ModItems.ENHANCED_SPACE_BUNDLE,ModItems.SPACE_TIME_GEAR);

        //z
        registerOres(Stages.constellation.knowledge(),ModItems.NAVIGATOR_BUNDLE,ModItems.SPACE_BUNDLE,ModItems.ENHANCED_SPACE_BUNDLE,ModItems.SPACE_TIME_GEAR);
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
        e.registerServerCommand(new ModCommand());
    }
}
