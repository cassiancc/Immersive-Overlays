package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
//? if 1.20.1 || 1.21.1 {
import folk.sisby.antique_atlas.AntiqueAtlas;
import static cc.cassian.immersiveoverlays.overlay.OverlayHelpers.getContainerContents;
import static cc.cassian.immersiveoverlays.overlay.OverlayHelpers.isContainer;
import static folk.sisby.antique_atlas.AntiqueAtlas.getHandheldAtlas;
import static folk.sisby.antique_atlas.AntiqueAtlas.isHandheldAtlas;
//?}
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;


@Pseudo
//? if 1.20.1 || 1.21.1 {
@Mixin(AntiqueAtlas.class)
//?} else {
/*@Mixin(Player.class)
*///?}
public abstract class AntiqueAtlasMixin {
    //? if (1.20.1 || 1.21.1) {

    @Inject(
            method = "hasHandheldAtlas",
            at = @At(value = "RETURN"), remap = false,
            cancellable = true)
    private static void allowMapInBundles(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.get().compat_antique_atlas && ModConfig.get().search_containers) {
            var inventory = player.getInventory();
            for (ItemStack stack :
                //? if <1.21.5 {
                    inventory.items
                //?} else {
                /*inventory.getNonEquipmentItems()
                 *///?}
            ) {
                if (isHandheldAtlas(stack))
                    cir.setReturnValue(true);
                else if (isContainer(stack)) {
                    List<ItemStack> contents = getContainerContents(stack).toList();
                    for (ItemStack content : contents) {
                        if (isHandheldAtlas(content))
                            cir.setReturnValue(true);
                    }
                }
            }
        }
    }
    //?}
}
