package tfar.tfcinfo.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import tfar.tfcinfo.TFCInfoConfig;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SpiritFluidStackIngredient extends Ingredient {

	public static final SpiritFluidStackIngredient INSTANCE = new SpiritFluidStackIngredient();

	private SpiritFluidStackIngredient(){}

	protected List<FluidStack> fluids;

	public ItemStack[] cachedStacks;

	@Override
	public ItemStack[] getMatchingStacks() {
		if (cachedStacks == null) {
			List<ItemStack> stacks = new ArrayList<>();
			for (String fluid : TFCInfoConfig.spirits) {
				stacks.add(FluidUtil.getFilledBucket(new FluidStack(FluidRegistry.getFluid(fluid),1000)));
			}
			cachedStacks = stacks.toArray(new ItemStack[0]);
		}
		return this.cachedStacks;
	}

	public void invalidateCache() {
		this.fluids = null;
		this.cachedStacks = null;
	}

	@Override
	public boolean apply(@Nullable ItemStack stack) {
		if (stack == null || stack.isEmpty()) {
			return false;
		} else {
			FluidStack fs = FluidUtil.getFluidContained(stack);

			if (this.fluids == null) {
				fluids = Arrays.stream(TFCInfoConfig.spirits).map(FluidRegistry::getFluid).map(fluid -> new FluidStack(fluid,1000)).collect(Collectors.toList());
			}

			return fs != null && fluids.stream().anyMatch(fs::containsFluid);
		}
	}
}
