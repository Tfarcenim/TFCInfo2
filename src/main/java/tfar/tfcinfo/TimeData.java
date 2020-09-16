package tfar.tfcinfo;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Objects;

public class TimeData implements INBTSerializable<NBTTagCompound> {

	public long avg_temp_knowledge_start = -1;
	public long max_temp_knowledge_start = -1;
	public long rainfall_knowledge_start = -1;
	public long monster_ferocity_knowledge_start = -1;
	public long constellation_knowledge_start = -1;
	public long date_knowledge_start = -1;
	public long time_knowledge_start = -1;

	public long longitudinal_memorization_start = -1;

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		nbtTagCompound.setLong("avg_temp_knowledge_start", avg_temp_knowledge_start);
		nbtTagCompound.setLong("max_temp_knowledge_start", max_temp_knowledge_start);
		nbtTagCompound.setLong("rainfall_knowledge_start", rainfall_knowledge_start);
		nbtTagCompound.setLong("monster_ferocity_knowledge_start",monster_ferocity_knowledge_start);
		nbtTagCompound.setLong("constellation_knowledge_start",constellation_knowledge_start);
		nbtTagCompound.setLong("time_knowledge_start",time_knowledge_start);
		nbtTagCompound.setLong("date_knowledge_start",date_knowledge_start);

		nbtTagCompound.setLong("longitudinal_memorization_start",longitudinal_memorization_start);

		return nbtTagCompound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		avg_temp_knowledge_start = nbt.getLong("avg_temp_knowledge_start");
		max_temp_knowledge_start = nbt.getLong("max_temp_knowledge_start");
		rainfall_knowledge_start = nbt.getLong("rainfall_knowledge_start");
		monster_ferocity_knowledge_start = nbt.getLong("monster_ferocity_knowledge_start");
		constellation_knowledge_start = nbt.getLong("constellation_knowledge_start");
		date_knowledge_start = nbt.getLong("date_knowledge_start");
		time_knowledge_start = nbt.getLong("time_knowledge_start");

		longitudinal_memorization_start = nbt.getLong("longitudinal_memorization_start");
	}
}
