package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import snownee.jade.overlay.OverlayRenderer;

@Mixin(OverlayRenderer.class)
public class JadeMixin {
    @Inject(method = "shouldShow", remap = false, at = @At(value = "HEAD"), cancellable = true)
    private static void mixin(CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.get().compat_jade && Minecraft.getInstance().player != null) {
            if (!OverlayHelpers.showWaila) {
                cir.setReturnValue(false);
            }
        }
    }
}
