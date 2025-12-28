package cn.xeks.tazc.mixin;

import cn.xeks.tazc.GunAura;
import com.tacz.guns.entity.shooter.LivingEntitySprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntitySprint.class,remap = false)
public class LivingEntitySprintMixin {

    @Inject(method = "getProcessedSprintStatus",at = @At("HEAD"), cancellable = true)
    public void getProcessedSprintStatus(boolean sprint, CallbackInfoReturnable<Boolean> cir) {
        if (GunAura.ENABLED.get() && GunAura.SPRINTING_SHOOT.get()) {
            cir.setReturnValue(sprint);
            cir.cancel();
        }
    }

}
