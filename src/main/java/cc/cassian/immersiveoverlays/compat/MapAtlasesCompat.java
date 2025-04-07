package cc.cassian.immersiveoverlays.compat;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.player.Player;
import pepjebs.mapatlases.config.MapAtlasesConfig;

public class MapAtlasesCompat {

    @ExpectPlatform
    public static boolean showingCoords(Player player) {
        throw new AssertionError();
    }
}
