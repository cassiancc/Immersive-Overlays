package cc.cassian.immersiveoverlays.mixin;


import cc.cassian.immersiveoverlays.ModTags;
import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.utils.MapAtlasesAccessUtils;

@Pseudo
@Mixin(MapAtlasesAccessUtils.class)
public class MapAtlasAccessUtilsMixin {
    //? if >1.20 {
    @Inject(method = "getAtlasFromInventory", at = @At(value = "RETURN"), remap = false, cancellable = true)
    private static void getAtlasFromBundle(Inventory inventory, boolean onlyHotbar, CallbackInfoReturnable<ItemStack> cir) {
        if (cir.getReturnValue().equals(ItemStack.EMPTY)) {
            cir.setReturnValue(OverlayHelpers.checkInventoryForStack(inventory, null, MapAtlasesMod.MAP_ATLAS.get()));
        }
    }
    @Inject(method = "getAtlasFromPlayerByConfig", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void getAtlasFromBundle(Player player, CallbackInfoReturnable<ItemStack> cir) {
        if (player.getOffhandItem().is(ModTags.CONTAINERS)) {
            cir.setReturnValue(OverlayHelpers.checkInventoryForStack(player.getOffhandItem(), null, MapAtlasesMod.MAP_ATLAS.get()));
        }
    }
    //?} else {
    /*@Inject(method = "getAtlasFromInventory", at = @At(value = "RETURN"), remap = false, cancellable = true)
    private static void getAtlasFromBundle(Inventory inventory, CallbackInfoReturnable<ItemStack> cir) {
        if (cir.getReturnValue() == null) {
            cir.setReturnValue(OverlayHelpers.checkInventoryForStack(inventory, null, MapAtlasesMod.MAP_ATLAS));
        }
    }
    @Inject(method = "getAtlasFromPlayerByConfig", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void getAtlasFromBundle(Player player, CallbackInfoReturnable<ItemStack> cir) {
        if (player.getOffhandItem().is(ModTags.CONTAINERS)) {
            cir.setReturnValue(OverlayHelpers.checkInventoryForStack(player.getOffhandItem(), null, MapAtlasesMod.MAP_ATLAS));
        }
    }
    *///?}
}