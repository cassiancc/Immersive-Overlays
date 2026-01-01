package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.CompassOverlay;
//? if fabric && <26
//import net.bichal.bplb.client.BetterPlayerLocatorBarHud;
//? if >1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?}
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
//? if fabric && <26 {
/*@Mixin(BetterPlayerLocatorBarHud.class)
*///?} else {
@Mixin(Minecraft.class)
//?}
public class BetterPlayerLocatorBarHudMixin {
    //? if fabric && <26 {
    /*@Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
    private static void mixin(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (ModConfig.get().compat_playerlocatorplus) {
            if (!CompassOverlay.showX) {
                ci.cancel();
            }
        }
    }
    *///?}
}