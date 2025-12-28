package cn.xeks.tazc.event;

import lombok.Getter;
import net.minecraft.network.protocol.Packet;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Getter
@Cancelable
public class PacketEvent extends Event {
    private final Packet<?> packet;
    private final EnumPacketType enumPacketType;
    public PacketEvent(Packet<?> packet,EnumPacketType enumPacketType) {
        this.packet = packet;
        this.enumPacketType = enumPacketType;
    }

}
