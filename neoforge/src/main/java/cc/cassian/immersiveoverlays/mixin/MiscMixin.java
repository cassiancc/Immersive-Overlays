package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.common.misc.Misc;

@Pseudo
@Mixin(Misc.class)
public class MiscMixin {
    @Inject(
            method = "hasItem(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/Item;)Z",
            at = @At(value = "RETURN"),
            cancellable = true, remap = false)
    private static void allowSpyglassesInBundles(Player player, Item item, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(OverlayHelpers.checkInventoryForItem(player.getInventory(), item, cir.getReturnValue()));
    }
}
