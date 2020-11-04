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

    private final int id;

    public ShowConfigItem(int id) {
        this.id = id;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) {
            Minecraft.getMinecraft().player.openGui(TerrafirmaCraftInfo.INSTANCE,id,worldIn,0,0,0);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
