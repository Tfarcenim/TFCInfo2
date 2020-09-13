package tfar.tfcinfo.recipes;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;

public class SpiritIngredientFactory implements IIngredientFactory {

	@Nonnull
	@Override
	public Ingredient parse(JsonContext context, JsonObject json) {
		return SpiritFluidStackIngredient.INSTANCE;
	}
}
