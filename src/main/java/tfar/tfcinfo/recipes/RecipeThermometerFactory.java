package tfar.tfcinfo.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tfar.tfcinfo.TerrafirmaCraftInfo;

import javax.annotation.Nonnull;

public class RecipeThermometerFactory implements IRecipeFactory {

	public static final ResourceLocation ID = new ResourceLocation(TerrafirmaCraftInfo.MODID, "thermometer");

	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		ShapelessOreRecipe recipe = ShapelessOreRecipe.factory(context, json);
		return new ThermometerRecipe(ID, recipe.getIngredients(), recipe.getRecipeOutput());
	}

	public static class ThermometerRecipe extends ShapelessOreRecipe {

		public ThermometerRecipe(ResourceLocation group, NonNullList<Ingredient> input, @Nonnull ItemStack result) {
			super(group, input, result);
		}

		@Override
		public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
			NonNullList<ItemStack> remains = super.getRemainingItems(inv);
			for (int i = 0; i < remains.size(); i++) {
				ItemStack s = inv.getStackInSlot(i);
				ItemStack remain = remains.get(i);
				if (!s.isEmpty() && remain.isEmpty() && s.getItem() instanceof UniversalBucket) {
					ItemStack empty = ((UniversalBucket) s.getItem()).getEmpty();
					if (!empty.isEmpty())
						remains.set(i, empty.copy());
				}
			}
			return remains;
		}
	}
}
