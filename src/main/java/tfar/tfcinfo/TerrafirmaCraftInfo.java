package tfar.tfcinfo;

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

import java.util.Arrays;

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

        //holding these grants knowledge
        registerOres(Stages.moonPhase.base(),ModItems.ALMANAC,ModItems.MOON_CHART,ModItems.NAVIGATOR_BUNDLE);
        registerOres(Stages.time.base(),ModItems.NAVIGATOR_BUNDLE);
        registerOres(Stages.date.base(),ModItems.NAVIGATOR_BUNDLE);
        registerOres(Stages.rainfall.base(),ModItems.RANGER_BUNDLE);
        registerOres(Stages.averageTemp.base(),ModItems.ENHANCED_TEMPERATURE_BUNDLE);
        registerOres(Stages.maxTemp.base(),ModItems.ENHANCED_TEMPERATURE_BUNDLE);
        registerOres(Stages.minTemp.base(),ModItems.ENHANCED_TEMPERATURE_BUNDLE);
        registerOres(Stages.slimeChunk.base(),ModItems.RANGER_BUNDLE);
        registerOres(Stages.spawnProtectionTimer.base(),ModItems.RANGER_BUNDLE);

        //holding these unlocks knowledge
        registerOres(Stages.maxTemp.knowledge(),ModItems.RECENT_TEMPERATURE_RECORDS,ModItems.TEMPERATURE_BUNDLE);
        registerOres(Stages.minTemp.knowledge(),ModItems.RECENT_TEMPERATURE_RECORDS,ModItems.TEMPERATURE_BUNDLE);


        registerOres(Stages.spawnProtectionTimer.knowledge(),ModItems.MONSTER_MIGRATION_SAMPLE);

        registerOres(Stages.time.knowledge(),ModItems.NAVIGATOR_BUNDLE);

        registerOres(Stages.date.knowledge(),
                ModItems.CALENDAR,ModItems.ALMANAC,ModItems.NAVIGATOR_BUNDLE);

        //x
        registerOres(Stages.longitudinal.knowledge(),ModItems.SEXTANT,ModItems.NAVIGATOR_BUNDLE);

        //y
        registerOres(Stages.depth.knowledge(),ModItems.PLUMB_BOB);

        //z
        registerOres(Stages.constellation.knowledge(),ModItems.NAVIGATOR_BUNDLE);

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
