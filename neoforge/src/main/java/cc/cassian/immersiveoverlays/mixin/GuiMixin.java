package cc.cassian.immersiveoverlays.mixin;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Gui.class)
public class GuiMixin {
    //? if >1.21.5 {
    /*@WrapOperation(method = "nextContextualInfoState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/waypoints/ClientWaypointManager;hasWaypoints()Z"))
    private boolean mixin(ClientWaypointManager instance, Operation<Boolean> original) {
        if (ModConfig.get().locator_bar) {
            if (!CompassOverlay.showX) {
                return false;
            }
        }
        return original.call(instance);
    }
    *///?}
}
