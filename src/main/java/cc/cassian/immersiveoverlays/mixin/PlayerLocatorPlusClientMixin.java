package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.CompassOverlay;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.UUID;

@IfModLoaded(value = "player-locator-plus", aliases = "playerlocatorplusreforged")
//? if fabric || neoforge {
@Mixin(sh.sit.plp.PlayerLocatorPlusClient.class)
//?} else if (forge){
/*@Mixin(com.myangel.playerlocatorplus.client.ClientTracker.class)
*///?}
public class PlayerLocatorPlusClientMixin {
    //? if fabric {
    @Inject(method = "isBarVisible", at = @At(value = "HEAD"), cancellable = true)
    private void hideHud(Minecraft client, CallbackInfoReturnable<Boolean> cir) {
		if (ModConfig.get().compat_playerlocatorplus && !CompassOverlay.showX) {
            cir.setReturnValue(false);
        }
    }
    //?}

    //? if forge {
    /*@Inject(method = "isHudVisible", remap = false, at = @At(value = "HEAD"), cancellable = true)
    private static void hideHud(Minecraft mc, Player player, Map<UUID, com.myangel.playerlocatorplus.network.RelativePlayerLocation> snapshot, boolean visibleEmpty, CallbackInfoReturnable<Boolean> cir) {
		if (ModConfig.get().compat_playerlocatorplus && !CompassOverlay.showX) {
			cir.setReturnValue(false);
		}
    }
    *///?}
}
