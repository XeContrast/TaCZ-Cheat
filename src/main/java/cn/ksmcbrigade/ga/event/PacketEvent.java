package cn.ksmcbrigade.ga.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.network.protocol.Packet;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class PacketEvent extends Event {
    @Getter
    public Packet<?> packet;
    public EnumPacketType enumPacketType;
    public PacketEvent(Packet<?> packet,EnumPacketType enumPacketType) {
        this.packet = packet;
        this.enumPacketType = enumPacketType;
    }

}
