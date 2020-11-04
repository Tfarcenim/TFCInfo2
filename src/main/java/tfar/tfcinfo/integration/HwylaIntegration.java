package tfar.tfcinfo.integration;

import mcp.mobius.waila.api.event.WailaTooltipEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tfar.tfcinfo.event.ClientHelper;

import java.util.List;

@Mod.EventBusSubscriber
public class HwylaIntegration {

    @SubscribeEvent
    public static void hwyla(WailaTooltipEvent e) {
        List<String> tooltip = e.getCurrentTip();
        if (!ClientHelper.canDisplayHwyla(Minecraft.getMinecraft().player)) {
            tooltip.clear();
            tooltip.add("Obtain a survival guide or better to learn about this.");
        }
    }
}
