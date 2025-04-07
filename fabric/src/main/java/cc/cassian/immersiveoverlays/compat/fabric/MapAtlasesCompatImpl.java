package cc.cassian.immersiveoverlays.compat.fabric;

import net.minecraft.world.entity.player.Player;
//? if >1.20 {
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.client.MapAtlasesClient;
import pepjebs.mapatlases.config.MapAtlasesClientConfig;
//?} else {
/*import net.minecraft.world.entity.player.Player;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.utils.MapAtlasesAccessUtils;
*///?}

public class MapAtlasesCompatImpl {

    public static boolean showingCoords(Player player) {
        //? if >1.20 {
        return MapAtlasesClient.getCurrentActiveAtlas() != null && MapAtlasesClientConfig.drawMinimapCoords.get();
        //?} else {
        /*return !MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(player).isEmpty() && MapAtlasesMod.CONFIG.drawMinimapCoords;
         *///?}
    }
}
