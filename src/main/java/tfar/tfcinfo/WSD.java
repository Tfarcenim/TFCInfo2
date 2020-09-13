package tfar.tfcinfo;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.*;

public class WSD extends WorldSavedData {

    public Map<UUID, TimeData> uuidTimeHashMap = new HashMap<>();



    //this is called via reflection, do not remove
    public WSD(String name) {
        super(name);
    }

    public static WSD getInstance(int dimension) {
        return get(FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(dimension));
    }


    public static WSD getDefaultInstance() {
        return getInstance(0);
    }

    public static WSD get(WorldServer world) {
        MapStorage storage = world.getPerWorldStorage();
        String name = TerrafirmaCraftInfo.MODID+":"+world.provider.getDimension();
        WSD instance = (WSD) storage.getOrLoadData(WSD.class, name);

        if (instance == null) {
            WSD wsd = new WSD(name);
            storage.setData(name, wsd);
            instance = (WSD) storage.getOrLoadData(WSD.class, name);
        }
        return instance;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList list = nbt.getTagList("data", Constants.NBT.TAG_COMPOUND);
        for (NBTBase nbtBase : list) {
            NBTTagCompound compound = (NBTTagCompound)nbtBase;
            UUID uuid = compound.getUniqueId("uuid");
            TimeData timeData = new TimeData();
            timeData.deserializeNBT((NBTTagCompound)compound.getTag("timedata"));
            uuidTimeHashMap.put(uuid,timeData);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        for (Map.Entry<UUID,TimeData> entry: uuidTimeHashMap.entrySet()) {
            UUID uuid = entry.getKey();
            TimeData timeData = entry.getValue();
            NBTTagCompound compound1 = new NBTTagCompound();
            compound1.setUniqueId("uuid",uuid);
            compound1.setTag("timedata",timeData.serializeNBT());
            list.appendTag(compound1);
        }
        compound.setTag("data",list);
        return compound;
    }
}
