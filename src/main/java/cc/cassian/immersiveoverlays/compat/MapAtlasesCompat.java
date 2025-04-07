package cc.cassian.immersiveoverlays.compat;

import dev.architectury.injectables.annotations.ExpectPlatform;
import pepjebs.mapatlases.config.MapAtlasesConfig;

public class MapAtlasesCompat {

    @ExpectPlatform
    public static boolean showingCoords() {
        throw new AssertionError();
    }
}
