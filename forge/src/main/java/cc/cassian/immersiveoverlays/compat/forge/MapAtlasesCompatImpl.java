package cc.cassian.immersiveoverlays.compat.forge;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import pepjebs.mapatlases.client.MapAtlasesClient;
import pepjebs.mapatlases.config.MapAtlasesClientConfig;

public class MapAtlasesCompatImpl {

    public static boolean showingCoords(Player player) {
        return !MapAtlasesClient.getCurrentActiveAtlas().isEmpty() && MapAtlasesClientConfig.drawMinimapCoords.get();
    }
}
