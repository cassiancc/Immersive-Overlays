package cc.cassian.immersiveoverlays.fabric.mixin;

import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.CompassOverlay;
import net.bichal.bplb.client.BetterPlayerLocatorBarHud;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(BetterPlayerLocatorBarHud.class)
public class BetterPlayerLocatorBarHudMixin {
    //? if >1.20 {
    @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
    private static void mixin(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (ModConfig.get().compat_playerlocatorplus) {
            if (!CompassOverlay.showX) {
                ci.cancel();
            }
        }
    }
    //?}
}