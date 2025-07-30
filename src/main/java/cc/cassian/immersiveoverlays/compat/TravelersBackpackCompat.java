package cc.cassian.immersiveoverlays.compat;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.player.Player;

public class TravelersBackpackCompat {
    @ExpectPlatform
    public static void checkForImportantAccessories(Player player) {
        throw new AssertionError();
    }
}
