package tfar.tfcinfo.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tfar.tfcinfo.event.ClientForgeEvents;

// not threadsafe!
public class S2CSyncTimePacket implements IMessage {

  public long avg_temp_knowledge_start = -1;
  public long max_temp_knowledge_start = -1;


  public S2CSyncTimePacket() {
  }

  public S2CSyncTimePacket(long avg_temp_knowledge_start,long max_temp_knowledge_start) {
    this.avg_temp_knowledge_start = avg_temp_knowledge_start;
    this.max_temp_knowledge_start = max_temp_knowledge_start;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    this.avg_temp_knowledge_start = buf.readLong();
    this.max_temp_knowledge_start = buf.readLong();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeLong(avg_temp_knowledge_start);
    buf.writeLong(max_temp_knowledge_start);
  }

  public static class Handler implements IMessageHandler<S2CSyncTimePacket, IMessage> {
    @Override
    public IMessage onMessage(S2CSyncTimePacket message, MessageContext ctx) {

      Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
      return null;
    }

    private void handle(S2CSyncTimePacket message, MessageContext ctx) {
      ClientForgeEvents.avg_temp_knowledge_start = message.avg_temp_knowledge_start;
      ClientForgeEvents.max_temp_knowledge_start = message.max_temp_knowledge_start;
    }
  }
}