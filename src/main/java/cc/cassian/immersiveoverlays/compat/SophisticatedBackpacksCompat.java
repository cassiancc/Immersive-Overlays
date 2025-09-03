package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SophisticatedBackpacksCompat {

    @ExpectPlatform
    public static void checkBackpackContents(ItemStack stack) {
        throw new AssertionError();
    }
}
