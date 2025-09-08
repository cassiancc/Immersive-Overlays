package cc.cassian.immersiveoverlays.compat.fabric;

import cc.cassian.immersiveoverlays.config.ModConfig;
import net.minecraft.world.entity.player.Player;
//? if >1.20 {
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.client.MapAtlasesClient;
import pepjebs.mapatlases.config.MapAtlasesClientConfig;
import pepjebs.mapatlases.config.MapAtlasesConfig;
//?} else {
/*import net.minecraft.world.entity.player.Player;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.utils.MapAtlasesAccessUtils;
*///?}

public class MapAtlasesCompatImpl {

    public static boolean showingCoords(Player player) {
        //? if >1.20 {
        return !MapAtlasesClient.getCurrentActiveAtlas().isEmpty() && MapAtlasesClientConfig.drawMinimapCoords.get();
        //?} else {
        /*return !MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(player).isEmpty() && MapAtlasesMod.CONFIG.drawMinimapCoords;
         *///?}
    }

    public static boolean couldCollide() {
        //? if >1.20 {
        var anchoring = MapAtlasesClientConfig.miniMapAnchoring.get();
        if (anchoring.isUp) {
            if (anchoring.isLeft && ModConfig.get().compass_horizontal_position_left) {
                return true;
            }
            if (!anchoring.isLeft && !ModConfig.get().compass_horizontal_position_left) {
                return true;
            }
        }
        //?} else {
        /*if (MapAtlasesMod.CONFIG.miniMapAnchoring.equals("TOP_RIGHT") && !ModConfig.get().compass_horizontal_position_left) {
            return true;
        }
        if (MapAtlasesMod.CONFIG.miniMapAnchoring.equals("TOP_LEFT") && ModConfig.get().compass_horizontal_position_left) {
            return true;
        }
        *///?}
        return false;

    }
}
