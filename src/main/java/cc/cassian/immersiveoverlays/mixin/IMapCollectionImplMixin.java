package cc.cassian.immersiveoverlays.mixin;

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
//? if neoforge {
/*import pepjebs.mapatlases.item.MapAtlasItem;
import pepjebs.mapatlases.map_collection.MapCollection;
*///?} else if forge {
/*import pepjebs.mapatlases.map_collection.IMapCollection;
import pepjebs.mapatlases.map_collection.forge.CapStuff;
import pepjebs.mapatlases.map_collection.forge.IMapCollectionImpl;
*///?} else if fabric && =1.20.1 {
/*import pepjebs.mapatlases.map_collection.fabric.IMapCollectionImpl;
import org.spongepowered.asm.mixin.injection.ModifyArg;
*///?}
import java.util.List;
import java.util.Optional;
@Pseudo
//? if (fabric && =1.20.1) || forge {
/*@Mixin(IMapCollectionImpl.class)
*///?} else if neoforge {
/*@Mixin(MapAtlasItem.class)
*///?} else {
@Mixin(MapAtlasesMod.class)
//?}
public class IMapCollectionImplMixin {
    //? if neoforge {
    /*@Inject(method = "getMaps", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void mixin(ItemStack stack, Level level, CallbackInfoReturnable<MapCollection> cir) {
        if (ModConfig.get().compat_map_atlases) {
            if (OverlayHelpers.isContainer(stack)) {
                List<ItemStack> contents = OverlayHelpers.getContainerContents(stack).toList();
                for (ItemStack content : contents) {
                    if (content.is(MapAtlasesMod.MAP_ATLAS.get())) {
                        cir.setReturnValue(MapAtlasItem.getMaps(content, level));
                    }
                }
            }
        }
    }
    *///?} else if forge {
    /*@Inject(method = "get", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void mixin(ItemStack stack, Level level, CallbackInfoReturnable<IMapCollection> cir) {
        if (ModConfig.get().compat_map_atlases) {
            if (OverlayHelpers.isContainer(stack)) {
                List<ItemStack> contents = OverlayHelpers.getContainerContents(stack).toList();
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
    *///?}

    //? if fabric && =1.20.1 {

    /*@ModifyArg(
            method = "get",
            at = @At(value = "INVOKE", target = "Ldev/onyxstudios/cca/api/v3/component/ComponentKey;maybeGet(Ljava/lang/Object;)Ljava/util/Optional;")
    )
    private static Object mixin(Object provider) {
        if (ModConfig.get().compat_map_atlases) {
            if (provider instanceof ItemStack stack) {
                if (OverlayHelpers.isContainer(stack)) {
                    List<ItemStack> contents = OverlayHelpers.getContainerContents(stack).toList();
                    for (ItemStack content : contents) {
                        if (content.is(MapAtlasesMod.MAP_ATLAS.get())) {
                            return content;
                        }
                    }
                }
            }
        }
        return provider;
    }
    *///?}
}
