package tfar.tfcinfo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tfar.tfcinfo.clent.CheckboxScreen;
import tfar.tfcinfo.clent.CheckboxScreenEx;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:return new CheckboxScreen();
            case 1:return new CheckboxScreenEx();
        }
        return null;
    }
}
