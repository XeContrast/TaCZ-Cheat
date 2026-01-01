package cn.xeks.tazc.mixin;

import cn.xeks.tazc.GunAura;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GunData.class,remap = false)
public abstract class GunDataMixin {

    @Inject(method = {"getBoltActionTime", "getBoltFeedTime", "getPutAwayTime", "getDrawTime"}, at = @At("RETURN"), cancellable = true, remap = false)
    public void cooldown(CallbackInfoReturnable<Float> cir) {
        if (GunAura.ENABLED.get())
            cir.setReturnValue((float) (cir.getReturnValue() / GunAura.RELOAD_TIME.get()));
    }

    @Inject(method = "getAimTime",at = @At("RETURN"),cancellable = true)
    public void aimTime(CallbackInfoReturnable<Float> cir) {
        if (GunAura.ENABLED.get())
            cir.setReturnValue((float) (cir.getReturnValue() / GunAura.AIMTIMER.get()));
    }
}
