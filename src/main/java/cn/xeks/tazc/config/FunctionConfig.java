//package cn.ksmcbrigade.ga.config;
//
//import net.minecraftforge.common.ForgeConfigSpec;
//
//public class FunctionConfig {
//    public static ForgeConfigSpec.BooleanValue AURA;
//    public static ForgeConfigSpec.BooleanValue AMMO_FREE;
//    public static ForgeConfigSpec.BooleanValue NO_COOL_DOWN;
//    public static ForgeConfigSpec.BooleanValue NO_RECOIL;
//    public static ForgeConfigSpec.BooleanValue NO_SCATTERING;
//    public static ForgeConfigSpec.BooleanValue NO_ADS_DELAY;
//    public static ForgeConfigSpec.BooleanValue SPRINTING_SHOOT;
//    public static void init(ForgeConfigSpec.Builder builder) {
//        builder.push("Function");
//
//        builder.comment("The no Recoil").comment("杀戮光环");
//        AURA = builder.define("KillAura",false);
//
//        builder.comment("This module need both install this mod.").comment("The ammo free module of the GunAura module,the gun's the counts of ammo will at least 1 when this module enabled.").comment("无限火力");
//        AMMO_FREE = builder.define("ammoFree",false);
//
//        builder.comment("This module need both install this mod.").comment("The no cool down module of the GunAura module,the gun's fire cool download will be to 0 when this module enabled.").comment("无攻击冷却");
//        NO_COOL_DOWN = builder.define("noCooldown",false);
//
//        builder.comment("The no Recoil").comment("无后坐力");
//        NO_RECOIL = builder.define("NoRecoil",false);
//
//        builder.comment("The no scattering").comment("无散射");
//        NO_SCATTERING = builder.define("NoScattering",false);
//
//        builder.comment("United We Stand").comment("无跑射延迟");
//        NO_ADS_DELAY = builder.define("NoADSDelay",false);
//
//        builder.comment("United We Stand").comment("跑步时射击");
//        SPRINTING_SHOOT = builder.define("SprintingShoot",false);
//
//
//        builder.pop();
//    }
//}
