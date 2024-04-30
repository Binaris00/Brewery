package net.satisfy.brewery.networking.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.satisfy.brewery.util.rope.RopeHelper;

public class SyncRopeS2CPacket implements NetworkManager.NetworkReceiver {
    @Override
    public void receive(FriendlyByteBuf buf, NetworkManager.PacketContext context) {
        int fromId = buf.readInt();
        int[] toIds = buf.readVarIntArray();
        context.queue(() -> RopeHelper.createConnections(Minecraft.getInstance(), fromId, toIds));
    }
}
