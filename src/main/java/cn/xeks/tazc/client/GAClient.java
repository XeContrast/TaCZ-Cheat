package cn.xeks.tazc.client;

import cn.xeks.tazc.GunAura;
import cn.xeks.tazc.event.PacketEvent;
import com.mojang.blaze3d.platform.InputConstants;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.ClientMessagePlayerShoot;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = GunAura.MODID,value = Dist.CLIENT)
public class GAClient implements MinecraftIns {
    public static KeyMapping key = new KeyMapping("GunAura", InputConstants.KEY_F12, KeyMapping.CATEGORY_GAMEPLAY);

    @SubscribeEvent
    public static void input(InputEvent.Key event) {
        if (key.isDown()) {
            GunAura.ENABLED.set(!GunAura.ENABLED.get());
            if (mc.player != null)
                mc.player.displayClientMessage(Component.literal("GunAura: ").append(String.valueOf(GunAura.ENABLED.get())), true);
        }
    }

    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent event) {
        if (GunAura.ENABLED.get() && GunAura.AURA.get()) {
            if (mc.player == null)
                return;
            
            if (IGun.mainHandHoldGun(mc.player)) {
                if (mc.getConnection() != null) {
                    if (findClosestTarget() instanceof LivingEntity hurt && findClosestTarget() != null) {
                        final boolean last = mc.player.isShiftKeyDown();
                        faceEntity(mc.player, hurt, mc.getConnection().getConnection(), mc.player.onGround());
//                        NetworkHandler.sendToTrackingEntity(new ServerMessageGunShoot(mc.player.getId(), mc.player.getItemInHand(InteractionHand.MAIN_HAND)), mc.player);
                        NetworkHandler.CHANNEL.sendToServer(new ClientMessagePlayerShoot());
                        mc.player.setShiftKeyDown(last);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void PacketReceive(PacketEvent event) {
        if (event.getPacket() instanceof ServerboundChatPacket) {
            System.out.println("Working");
        }
    }

    public static Entity findClosestTarget() {
        if (mc.player == null || mc.level == null)
            return null;

        // 使用 Forge 提供的安全方法来获取实体
        // 这个方法返回的是不可修改的列表，更安全
        List<Entity> entities = mc.level.getEntities(
                mc.player,
                mc.player.getBoundingBox().inflate(GunAura.RANGE.get())
        );

        // 仍然创建副本以确保安全
        List<Entity> entitiesCopy = new ArrayList<>(entities);

        Entity closestTarget = null;
        double cloestDistance = GunAura.RANGE.get() * GunAura.RANGE.get();

        for (Entity entity : entitiesCopy) {
            // 跳过无效实体
            if (!isValidTarget(entity))
                continue;

            double distance = mc.player.distanceToSqr(entity);
            if (distance < cloestDistance) {
                cloestDistance = distance;
                closestTarget = entity;
            }
        }

        return closestTarget;
    }

    private static boolean isValidTarget(Entity entity) {
        if (entity == null || entity.isRemoved())
            return false;

        if (!(entity instanceof LivingEntity livingEntity))
            return false;

        if (livingEntity.isDeadOrDying())
            return false;

        return entity != mc.player;
    }

    public static float wrapAngleTo180_float(float value)
    {
        value = value % 360.0F;

        if (value >= 180.0F)
        {
            value -= 360.0F;
        }

        if (value < -180.0F)
        {
            value += 360.0F;
        }

        return value;
    }


    private static float sqrt_double(double sqrt) {
        return (float) Math.sqrt(sqrt);
    }

    public static void faceEntity(Entity player,Entity target,Connection connection, boolean on) {
        if (target == null || mc.player == null)
            return;

        double xSize = target.getX() - player.getX();
        double ySize = target.getY() + (double)(target.getEyeHeight() / 2.0f) - (player.getY() + (double)player.getEyeHeight());
        double zSize = target.getZ() - player.getZ();
        double theta = sqrt_double(xSize * xSize + zSize * zSize);
        float yaw = (float)(Math.atan2(zSize, xSize) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(ySize, theta) * 180.0 / Math.PI));

        float yaw1 = (player.xRotO + wrapAngleTo180_float(yaw - player.xRotO)) % 360.0f;
        float pitch1 = (player.yRotO + wrapAngleTo180_float(pitch - player.yRotO)) % 360.0f;

        mc.player.yHeadRot = yaw1;
        connection.send(new ServerboundMovePlayerPacket.Rot(yaw1, pitch1, on));
    }

}
