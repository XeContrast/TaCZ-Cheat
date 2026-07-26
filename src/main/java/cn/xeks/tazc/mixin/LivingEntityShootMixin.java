package cn.xeks.tazc.mixin;

import cn.xeks.tazc.GunAura;
import cn.xeks.tazc.network.GetClientConfigs;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.entity.shooter.LivingEntityShoot;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import net.minecraft.world.entity.LivingEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntityShoot.class,remap = false)
public class LivingEntityShootMixin {
    @Shadow
    @Final
    private LivingEntity shooter;

    @Inject(method = "getShootCoolDown*", at = @At("RETURN"), cancellable = true)
    public void get(CallbackInfoReturnable<Long> cir) {
        if (!IGun.mainHandHoldGun(this.shooter)) return;
        if (GunAura.NO_COOL_DOWN.get() && GetClientConfigs.getEnabled(this.shooter))
            cir.setReturnValue(0L);
    }

    @Inject(method = "getShootCoolDown(J)J",at = @At("RETURN"),cancellable = true,remap = false)
    public void cooldown(long timestamp, CallbackInfoReturnable<Long> cir){
        if (!IGun.mainHandHoldGun(this.shooter)) return;
        if (GunAura.ENABLED.get())
            cir.setReturnValue((long) (((double)cir.getReturnValue()) / GunAura.SHOOTTIME.get()));
    }

    @ModifyVariable(method = "shoot(Ljava/util/function/Supplier;Ljava/util/function/Supplier;JFZ)Lcom/tacz/guns/api/entity/ShootResult;",at =@At("STORE"),name = "ammoCount")
    private int shoot(int ammoCount) {
        int count = ammoCount;
        if (IGun.mainHandHoldGun(this.shooter) && GunAura.AMMO_FREE.get() && GetClientConfigs.getEnabled(this.shooter))
            count = Math.max(1, count);
        return count;
    }

    @Redirect(method = "shoot(Ljava/util/function/Supplier;Ljava/util/function/Supplier;JFZ)Lcom/tacz/guns/api/entity/ShootResult;",at = @At(value = "FIELD", target = "Lcom/tacz/guns/entity/shooter/ShooterDataHolder;sprintTimeS:F",opcode = Opcodes.GETFIELD))
    public float shoot(ShooterDataHolder instance) {
        if (GunAura.ENABLED.get() && GunAura.NO_ADS_DELAY.get())
            return 0;
        else
            return instance.sprintTimeS;
    }
}
