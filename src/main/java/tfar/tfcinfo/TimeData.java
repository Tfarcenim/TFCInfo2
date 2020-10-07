package tfar.tfcinfo;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class TimeData implements INBTSerializable<NBTTagCompound> {

	public long avg_temp_knowledge_start = -1;
	public long max_temp_knowledge_start = -1;
	public long min_temp_knowledge_start = - 1;

	public long rainfall_knowledge_start = -1;
	public long monster_ferocity_knowledge_start = -1;
	public long monster_migration_knowledge_start = -1;
	public long date_memory_start = -1;
	public long time_knowledge_start = -1;

	public long longitudinal_knowledge_start;
	public long constellation_knowledge_start = -1;
	public long depth_knowledge_start = -1;
	public long biome_knowledge_start = -1;
	public long nutrition_knowledge_start = -1;


	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		nbtTagCompound.setLong("avg_temp_knowledge_start", avg_temp_knowledge_start);
		nbtTagCompound.setLong("max_temp_knowledge_start", max_temp_knowledge_start);
		nbtTagCompound.setLong("min_temp_knowledge_start", min_temp_knowledge_start);
		nbtTagCompound.setLong("rainfall_knowledge_start", rainfall_knowledge_start);
		nbtTagCompound.setLong("monster_ferocity_knowledge_start",monster_ferocity_knowledge_start);
		nbtTagCompound.setLong("monster_migration_knowledge_start",monster_migration_knowledge_start);
		nbtTagCompound.setLong("longitudinal_knowledge_start",longitudinal_knowledge_start);
		nbtTagCompound.setLong("depth_knowledge_start",depth_knowledge_start);
		nbtTagCompound.setLong("constellation_knowledge_start",constellation_knowledge_start);
		nbtTagCompound.setLong("time_knowledge_start",time_knowledge_start);
		nbtTagCompound.setLong("date_knowledge_start", date_memory_start);
		nbtTagCompound.setLong("biome_knowledge_start",biome_knowledge_start);
		nbtTagCompound.setLong("nutrition_knowledge_start",nutrition_knowledge_start);
			return nbtTagCompound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		avg_temp_knowledge_start = nbt.getLong("avg_temp_knowledge_start");
		max_temp_knowledge_start = nbt.getLong("max_temp_knowledge_start");
		min_temp_knowledge_start = nbt.getLong("min_temp_knowledge_start");
		rainfall_knowledge_start = nbt.getLong("rainfall_knowledge_start");
		monster_ferocity_knowledge_start = nbt.getLong("monster_ferocity_knowledge_start");
		monster_migration_knowledge_start = nbt.getLong("monster_migration_knowledge_start");
		longitudinal_knowledge_start = nbt.getLong("longitudinal_knowledge_start");
		depth_knowledge_start = nbt.getLong("depth_knowledge_start");
		constellation_knowledge_start = nbt.getLong("constellation_knowledge_start");
		date_memory_start = nbt.getLong("date_knowledge_start");
		time_knowledge_start = nbt.getLong("time_knowledge_start");
		biome_knowledge_start = nbt.getLong("biome_knowledge_start");
		nutrition_knowledge_start = nbt.getLong("nutrition_knowledge_start");
	}
}
