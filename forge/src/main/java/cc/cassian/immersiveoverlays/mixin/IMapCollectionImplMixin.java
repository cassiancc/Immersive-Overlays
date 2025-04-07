package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.ModTags;
import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.map_collection.IMapCollection;
import pepjebs.mapatlases.map_collection.forge.CapStuff;
import pepjebs.mapatlases.map_collection.forge.IMapCollectionImpl;

import java.util.List;
import java.util.Optional;

@Pseudo
@Mixin(IMapCollectionImpl.class)
public class IMapCollectionImplMixin {
    @Inject(method = "get", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void mixin(ItemStack stack, Level level, CallbackInfoReturnable<IMapCollection> cir) {
        if (stack.is(ModTags.CONTAINERS)) {
            List<ItemStack> contents = OverlayHelpers.getContents(stack).toList();
            for (ItemStack content : contents) {
                if (content.is(MapAtlasesMod.MAP_ATLAS.get())) {
                    Optional<IMapCollectionImpl> resolve = content.getCapability(CapStuff.ATLAS_CAP_TOKEN, null).resolve();
                    if (resolve.isEmpty()) {
                        throw new AssertionError("Map Atlas capability was empty. How is this possible? Culprit itemstack " + stack);
                    } else {
                        IMapCollectionImpl cap = resolve.get();
                        if (!cap.isInitialized()) {
                            cap.initialize(level);
                        }
                        cir.setReturnValue(cap);
                    }
                }
            }
        }
    }
}
