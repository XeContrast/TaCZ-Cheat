package cn.xeks.tazc.mixin;

import cn.xeks.tazc.GunAura;
import com.tacz.guns.resource.pojo.data.gun.GunReloadTime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GunReloadTime.class,remap = false)
public class GunReloadTimeMixin {
    @Inject(method = {"getEmptyTime","getTacticalTime"},at = @At("RETURN"),cancellable = true)
    public void cooldown(CallbackInfoReturnable<Float> cir){
        if (GunAura.ENABLED.get())
            cir.setReturnValue((float) (cir.getReturnValue() / GunAura.RELOAD_TIME.get()));
    }
}
