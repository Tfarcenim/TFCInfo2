package tfar.tfcinfo.event;

import net.minecraft.entity.player.EntityPlayer;
import tfar.tfcinfo.Stages;
import tfar.tfcinfo.Utils;

public class Helper {
    public static boolean canSeeSlimeChunks(EntityPlayer player) {
        return Utils.hasOreDictItem(Stages.slimeChunk.base(),player.inventory);
    }
}
