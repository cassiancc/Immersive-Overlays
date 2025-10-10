package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.config.ModConfig;
//? if >1.20 {
import me.pajic.accessorify.gui.InfoOverlays;
//?} else {
/*import net.minecraft.client.Minecraft;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
//? if >1.20 {
@Mixin(InfoOverlays.class)
//?} else {
/*@Mixin(Minecraft.class)
*///?}
public class InfoOverlaysMixin {
    //? if >1.20 {
    @Inject(method = "prepareCompassOverlay", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private static void disableCompass(CallbackInfo ci) {
        if (ModConfig.get().compat_accessorify) {
            ci.cancel();
        }
    }

    @Inject(method = "prepareClockOverlay", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private static void disableClock(CallbackInfo ci) {
        if (ModConfig.get().compat_accessorify) {
            ci.cancel();
        }
    }
    //?}
}
