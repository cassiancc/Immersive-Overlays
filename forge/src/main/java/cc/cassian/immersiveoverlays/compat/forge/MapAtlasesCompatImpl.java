package cc.cassian.immersiveoverlays.compat.forge;

import pepjebs.mapatlases.config.MapAtlasesClientConfig;

public class MapAtlasesCompatImpl {

    public static boolean showingCoords() {
        return MapAtlasesClientConfig.drawMinimapCoords.get();
    }
}
