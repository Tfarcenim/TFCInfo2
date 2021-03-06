package tfar.tfcinfo.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

  public static SimpleNetworkWrapper INSTANCE = null;

  public PacketHandler() {
  }

  public static void registerMessages(String channelName) {
    INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
    registerMessages();
  }
  public static void registerMessages() {
    // Register messages which are sent from the client to the server here:
    INSTANCE.registerMessage(S2CSyncTimePacket.Handler.class, S2CSyncTimePacket.class, 0, Side.CLIENT);
    INSTANCE.registerMessage(S2CSyncSlimeChunkPacket.Handler.class, S2CSyncSlimeChunkPacket.class, 1, Side.CLIENT);
  }
}