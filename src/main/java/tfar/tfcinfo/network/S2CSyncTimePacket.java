package tfar.tfcinfo.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tfar.tfcinfo.TimeData;
import tfar.tfcinfo.event.TooltipHandler;

// not threadsafe!
public class S2CSyncTimePacket implements IMessage {


  private TimeData timeData = new TimeData();


  public S2CSyncTimePacket() {
  }

  public S2CSyncTimePacket(TimeData timeData) {
    this.timeData = timeData;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    this.timeData.deserializeNBT(ByteBufUtils.readTag(buf));
  }

  @Override
  public void toBytes(ByteBuf buf) {
    ByteBufUtils.writeTag(buf,timeData.serializeNBT());
  }

  public static class Handler implements IMessageHandler<S2CSyncTimePacket, IMessage> {
    @Override
    public IMessage onMessage(S2CSyncTimePacket message, MessageContext ctx) {

      Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
      return null;
    }

    private void handle(S2CSyncTimePacket message, MessageContext ctx) {
      TooltipHandler.timeData.deserializeNBT(message.timeData.serializeNBT());
    }
  }
}