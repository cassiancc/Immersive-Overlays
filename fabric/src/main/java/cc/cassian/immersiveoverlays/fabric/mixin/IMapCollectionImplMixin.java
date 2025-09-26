package cc.cassian.immersiveoverlays.fabric.mixin;

import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import pepjebs.mapatlases.MapAtlasesMod;
//? if =1.20.1 {
/*import pepjebs.mapatlases.map_collection.fabric.IMapCollectionImpl;

import java.util.List;

@Pseudo
@Mixin(IMapCollectionImpl.class)
*///?} else {
@Mixin(MapAtlasesMod.class)
//?}
public class IMapCollectionImplMixin {
//? if =1.20.1 {

    /*@ModifyArg(
            method = "get",
            at = @At(value = "INVOKE", target = "Ldev/onyxstudios/cca/api/v3/component/ComponentKey;maybeGet(Ljava/lang/Object;)Ljava/util/Optional;")
    )
    private static Object mixin(@Nullable Object provider) {
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
