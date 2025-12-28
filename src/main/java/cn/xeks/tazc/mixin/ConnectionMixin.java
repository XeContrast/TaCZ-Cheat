package cn.xeks.tazc.mixin;

import cn.xeks.tazc.event.EnumPacketType;
import cn.xeks.tazc.event.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Connection.class)
public class ConnectionMixin {
    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;)V",at = @At("HEAD"))
    public void send(Packet<?> p_129513_, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new PacketEvent(p_129513_,EnumPacketType.SEND));
    }

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;)V",at = @At(value = "HEAD"), cancellable = true)
    public void receive(ChannelHandlerContext p_129487_, Packet<?> p_129488_, CallbackInfo ci) {
        PacketEvent packetReceiveEvent = new PacketEvent(p_129488_, EnumPacketType.RECEIVE);
        MinecraftForge.EVENT_BUS.post(packetReceiveEvent);

        if (packetReceiveEvent.isCanceled()) {
            ci.cancel();
        }
    }
}
