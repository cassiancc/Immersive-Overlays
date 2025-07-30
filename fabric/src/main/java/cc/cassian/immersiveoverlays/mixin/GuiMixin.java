package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.CompassOverlay;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.Gui;
//? if >1.21.5 {
/*import net.minecraft.client.waypoints.ClientWaypointManager;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

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