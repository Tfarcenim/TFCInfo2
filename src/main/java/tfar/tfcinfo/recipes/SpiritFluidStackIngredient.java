package tfar.tfcinfo.recipes;

import net.dries007.tfc.objects.items.ItemsTFC;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
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
				FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid(fluid),1000);
				stacks.add(FluidUtil.getFilledBucket(fluidStack));
				ItemStack wooden = new ItemStack(ItemsTFC.WOODEN_BUCKET);
				wooden.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null).fill(fluidStack,true);
				stacks.add(wooden);
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
