package cc.cassian.immersiveoverlays.forge.mixin;


import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.world.entity.player.Inventory;
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
    @Inject(method = "getAtlasFromInventory", at = @At(value = "RETURN"), remap = false, cancellable = true)
    private static void mixin(Inventory inventory, boolean onlyHotbar, CallbackInfoReturnable<ItemStack> cir) {
        if (ModConfig.get().compat_map_atlases) {
            if (cir.getReturnValue().equals(ItemStack.EMPTY)) {
                cir.setReturnValue(OverlayHelpers.checkInventoryForStack(inventory, MapAtlasesMod.MAP_ATLAS.get()));
            }
        }
    }
}