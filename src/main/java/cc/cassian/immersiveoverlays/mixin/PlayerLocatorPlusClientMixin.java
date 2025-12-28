package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.CompassOverlay;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//? if fabric && <26
//import sh.sit.plp.PlayerLocatorPlusClient;

@Pseudo
//? if fabric && <26 {
/*@Mixin(PlayerLocatorPlusClient.class)
*///?} else {
@Mixin(Minecraft.class)
 //?}
public class PlayerLocatorPlusClientMixin {
    //? if fabric && <26 {
    /*@Inject(method = "isBarVisible", at = @At(value = "HEAD"), cancellable = true)
    private void mixin(Minecraft client, CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.get().compat_playerlocatorplus) {
            if (!CompassOverlay.showX) {
                cir.setReturnValue(false);
            }
        }
    }
    *///?}
}
