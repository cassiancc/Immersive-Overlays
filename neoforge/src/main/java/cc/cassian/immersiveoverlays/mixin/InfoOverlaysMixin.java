package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.config.ModConfig;
import me.pajic.accessorify.gui.InfoOverlays;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(InfoOverlays.InfoOverlay.class)
public class InfoOverlaysMixin {
    @Inject(method = "prepareCompassOverlay", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void disableCompass(CallbackInfo ci) {
        if (ModConfig.get().compat_accessorify) {
            ci.cancel();
        }
    }

    @Inject(method = "prepareClockOverlay", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void disableClock(CallbackInfo ci) {
        if (ModConfig.get().compat_accessorify) {
            ci.cancel();
        }
    }
}
