package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.CompassOverlay;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import org.spongepowered.asm.mixin.Mixin;

@IfModLoaded("dead_reckoning")
@Mixin(net.funkpla.dead_reckoning.CompassOverlay.class)
public class DeadReckoningCompassOverlayMixin {
	@WrapMethod(method = "isCompassInInventory")
	private boolean mixin(Operation<Boolean> original) {
		if (ModConfig.get().compat_dead_reckoning) {
			return CompassOverlay.showX;
		}
		return original.call();
	}
}
