package tfar.tfcinfo.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.IConfigElement;
import tfar.tfcinfo.TerrafirmaCraftInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShowConfigItem extends Item {

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) {
            Minecraft.getMinecraft().player.openGui(TerrafirmaCraftInfo.INSTANCE,0,worldIn,0,0,0);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private static List<IConfigElement> collectConfigElements(Class<?>[] configClasses)
    {
        List<IConfigElement> toReturn;
        if(configClasses.length == 1)
        {
            toReturn = ConfigElement.from(configClasses[0]).getChildElements();
        }
        else
        {
            toReturn = new ArrayList<IConfigElement>();
            for(Class<?> clazz : configClasses)
            {
                toReturn.add(ConfigElement.from(clazz));
            }
        }
        toReturn.sort(Comparator.comparing(e -> I18n.format(e.getLanguageKey())));
        return toReturn;
    }
}
