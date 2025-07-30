package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.CompassOverlay;
import net.minecraft.client.DeltaTracker;
import org.spongepowered.asm.mixin.Mixin;
//? if >1.21.5 {
/*import net.minecraft.client.gui.contextualbar.LocatorBarRenderer;
 *///?} else {
import net.bichal.bplb.client.BetterPlayerLocatorBarHud;
        //?}
//? if >1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
//? if >1.21.5 {
/*@Mixin(LocatorBarRenderer.class)
 *///?} else {
@Mixin(BetterPlayerLocatorBarHud.class)
//?}
public class BetterPlayerLocatorBarHudMixin {
    //? if >1.21.5 {
    /*@Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
    private void mixin(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (ModConfig.get().compat_playerlocatorplus) {
            if (!CompassOverlay.showX) {
                ci.cancel();
            }
        }
    }    *///?} else {
    @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
    private static void mixin(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (ModConfig.get().compat_playerlocatorplus) {
            if (!CompassOverlay.showX) {
                ci.cancel();
            }
        }
    }    //?}
}