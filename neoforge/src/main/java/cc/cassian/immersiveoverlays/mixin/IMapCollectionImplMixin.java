package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.ModTags;
import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.item.MapAtlasItem;
import pepjebs.mapatlases.map_collection.MapCollection;

import java.util.List;

@Pseudo
@Mixin(MapAtlasItem.class)
public class IMapCollectionImplMixin {
    @Inject(method = "getMaps", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void mixin(ItemStack stack, Level level, CallbackInfoReturnable<MapCollection> cir) {
        if (ModConfig.get().compat_map_atlases) {
            if (stack.is(ModTags.CONTAINERS)) {
                List<ItemStack> contents = OverlayHelpers.getContainerContents(stack).toList();
                for (ItemStack content : contents) {
                    if (content.is(MapAtlasesMod.MAP_ATLAS.get())) {
                        cir.setReturnValue(MapAtlasItem.getMaps(content, level));
                    }
                }
            }
        }
    }
}
