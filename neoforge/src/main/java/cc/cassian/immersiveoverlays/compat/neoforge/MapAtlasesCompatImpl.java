package cc.cassian.immersiveoverlays.compat.neoforge;

import net.minecraft.world.entity.player.Player;
import pepjebs.mapatlases.client.MapAtlasesClient;
import pepjebs.mapatlases.config.MapAtlasesClientConfig;

public class MapAtlasesCompatImpl {

    public static boolean showingCoords(Player player) {
        return MapAtlasesClient.getCurrentActiveAtlas() != null && MapAtlasesClientConfig.drawMinimapCoords.get();
    }
}
