package tfar.tfcinfo.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tfar.tfcinfo.event.TooltipHandler;

// not threadsafe!
public class S2CSyncSlimeChunkPacket implements IMessage {

  public boolean slime_chunk;

  public S2CSyncSlimeChunkPacket() {
  }

  public S2CSyncSlimeChunkPacket(boolean isSlimeChunk) {
    slime_chunk = isSlimeChunk;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    this.slime_chunk = buf.readBoolean();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeBoolean(slime_chunk);
  }

  public static class Handler implements IMessageHandler<S2CSyncSlimeChunkPacket, IMessage> {
    @Override
    public IMessage onMessage(S2CSyncSlimeChunkPacket message, MessageContext ctx) {

      Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
      return null;
    }

    private void handle(S2CSyncSlimeChunkPacket message, MessageContext ctx) {
      TooltipHandler.slime_chunk = message.slime_chunk;
    }
  }
}