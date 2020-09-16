package tfar.tfcinfo.item;

import net.darkhax.bookshelf.util.BlockUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class SlimeCompassItem extends Item {

    public SlimeCompassItem() {
        addPropertyOverride(new ResourceLocation("angle"), new IItemPropertyGetter() {
            double rotation;
            double rota;
            long lastUpdateTick;

            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (entityIn == null && !stack.isOnItemFrame() || !stack.hasTagCompound()) {
                    return 0.0F;
                } else {
                    boolean flag = entityIn != null;
                    Entity entity = flag ? entityIn : stack.getItemFrame();

                    if (worldIn == null) {
                        worldIn = entity.world;
                    }

                    double d0;

                    if (worldIn.provider.isSurfaceWorld()) {
                        double d1 = flag ? entity.rotationYaw : this.getFrameRotation((EntityItemFrame) entity);
                        d1 = MathHelper.positiveModulo(d1 / 360.0D, 1.0D);
                        double d2 = this.getSpawnToAngle(stack, entity) / (Math.PI * 2D);
                        d0 = 0.5D - d1 + 0.25D + d2;
                    } else {
                        d0 = Math.random();
                    }

                    if (flag) {
                        d0 = this.wobble(worldIn, d0);
                    }

                    return MathHelper.positiveModulo((float) d0, 1.0F);
                }
            }

            @SideOnly(Side.CLIENT)
            private double wobble(World worldIn, double p_185093_2_) {
                if (worldIn.getTotalWorldTime() != this.lastUpdateTick) {
                    this.lastUpdateTick = worldIn.getTotalWorldTime();
                    double d0 = p_185093_2_ - this.rotation;
                    d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
                    this.rota += d0 * 0.1D;
                    this.rota *= 0.8D;
                    this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
                }

                return this.rotation;
            }

            private double getFrameRotation(EntityItemFrame p_185094_1_) {
                return MathHelper.wrapDegrees(180 + p_185094_1_.facingDirection.getHorizontalIndex() * 90);
            }

            private double getSpawnToAngle(ItemStack stack, Entity p_185092_2_) {
                int[] pos = stack.getTagCompound().getIntArray("pos");
                return Math.atan2(pos[1] - p_185092_2_.posZ, pos[0] - p_185092_2_.posX);
            }
        });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        double dist = Double.MAX_VALUE;
        BlockPos playerPos = playerIn.getPosition();
        BlockPos chunkPos = null;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        if (!worldIn.isRemote) {
            for (int i = -7; i < 8; i++) {
                for (int j = -1; j < 8; j++) {
                    mutable.setPos(playerPos.getX() << 4, 64, playerPos.getZ() << 4);
                    if (BlockUtils.isSlimeChunk(worldIn, mutable)) {
                        if (chunkPos == null || mutable.distanceSq(playerPos) < dist) {
                            dist = mutable.distanceSq(playerPos);
                            chunkPos = mutable.toImmutable();
                        }
                    }
                }
            }
            if (chunkPos != null) {
                ItemStack stack = playerIn.getHeldItem(handIn);
                if (stack.getTagCompound() == null) {
                    stack.setTagCompound(new NBTTagCompound());
                }
                stack.getTagCompound().setIntArray("pos", new int[]{chunkPos.getX(), chunkPos.getZ()});
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
