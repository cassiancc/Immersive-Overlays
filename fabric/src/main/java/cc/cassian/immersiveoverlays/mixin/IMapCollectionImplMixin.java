package cc.cassian.immersiveoverlays.mixin;

import cc.cassian.immersiveoverlays.ModTags;
import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import pepjebs.mapatlases.MapAtlasesMod;
//? if =1.20.1 {
import pepjebs.mapatlases.map_collection.fabric.IMapCollectionImpl;

import java.util.List;
import java.util.Optional;

@Pseudo
@Mixin(IMapCollectionImpl.class)
//?} else {
/*@Mixin(MapAtlasesMod.class)
*///?}
public class IMapCollectionImplMixin {
//? if =1.20.1 {

    @ModifyArg(
            method = "get",
            at = @At(value = "INVOKE", target = "Ldev/onyxstudios/cca/api/v3/component/ComponentKey;maybeGet(Ljava/lang/Object;)Ljava/util/Optional;")
    )
    private static Object mixin(@Nullable Object provider) {
        if (provider instanceof ItemStack stack) {
            if (stack.is(ModTags.CONTAINERS)) {
                List<ItemStack> contents = OverlayHelpers.getContents(stack).toList();
                for (ItemStack content : contents) {
                    if (content.is(MapAtlasesMod.MAP_ATLAS.get())) {
                        return content;
                    }
                }
            }
        }
        return provider;
    }
    //?}
}
