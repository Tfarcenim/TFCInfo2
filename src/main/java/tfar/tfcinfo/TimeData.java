package tfar.tfcinfo;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.lang.reflect.Field;

public class TimeData implements INBTSerializable<NBTTagCompound> {

	public long avg_temp_knowledge_start = -1;
	public long max_temp_knowledge_start = -1;
	public long min_temp_knowledge_start = - 1;

	public long rainfall_knowledge_start = -1;
	public long monster_ferocity_knowledge_start = -1;
	public long monster_migration_knowledge_start = -1;
	public long date_memory_start = -1;
	public long time_knowledge_start = -1;

	public long longitudinal_knowledge_start = -1;
	public long constellation_knowledge_start = -1;
	public long depth_knowledge_start = -1;
	public long biome_knowledge_start = -1;
	public long nutrition_knowledge_start = -1;
	public long hwyla_knowledge_start = -1;
	public long skill_knowledge_start = - 1;
	public long flora_knowledge_start = -1;
	public long arboreal_knowledge_start = -1;

	protected static final Field[] fields = TimeData.class.getFields();

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		for (Field field : fields) {
			try {
				nbtTagCompound.setLong(field.getName(), field.getLong(this));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return nbtTagCompound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		for (Field field : fields) {
			try {
				field.setLong(this,nbt.getLong(field.getName()));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
