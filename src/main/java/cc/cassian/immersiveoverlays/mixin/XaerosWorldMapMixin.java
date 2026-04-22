package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.map.misc.Misc;

@IfModLoaded("xaeroworldmap")
@Mixin(Misc.class)
public class XaerosWorldMapMixin {
    @Inject(
            method = "hasItem(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/Item;)Z",
            at = @At(value = "RETURN"), remap = false,
            cancellable = true)
    private static void allowMapInBundles(Player player, Item item, CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.get().compat_xaeros && ModConfig.get().search_containers)
            cir.setReturnValue(OverlayHelpers.checkInventoryForItem(player.getInventory(), item, cir.getReturnValue()));
    }
}
