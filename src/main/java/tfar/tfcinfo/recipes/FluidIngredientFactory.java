package tfar.tfcinfo.recipes;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidIngredientFactory implements IIngredientFactory {
    @Override
    public Ingredient parse(JsonContext context, JsonObject json) {
        String fluidName = json.get("fluid").getAsString();
        FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid(fluidName),1000);
        return new FluidIngredient(fluidStack);
    }
}
