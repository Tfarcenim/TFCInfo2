package tfar.tfcinfo;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import tfar.tfcinfo.command.ModCommand;
import tfar.tfcinfo.network.PacketHandler;

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
