package cn.xeks.tazc.event;

import net.minecraft.network.protocol.Packet;
import net.minecraftforge.eventbus.api.Event;

public class PacketEvent extends Event {
    public Packet<?> getPacket() {
        return packet;
    }

    private final Packet<?> packet;

    private final EnumPacketType enumPacketType;
    public PacketEvent(Packet<?> packet,EnumPacketType enumPacketType) {
        this.packet = packet;
        this.enumPacketType = enumPacketType;
    }

}
