package cc.cassian.immersiveoverlays.compat.fabric;

//? if >1.20 {
import pepjebs.mapatlases.config.MapAtlasesClientConfig;
//?} else {
/*import pepjebs.mapatlases.MapAtlasesMod;
*///?}

public class MapAtlasesCompatImpl {

    public static boolean showingCoords() {
        //? if >1.20 {
        return MapAtlasesClientConfig.drawMinimapCoords.get();
        //?} else {
        /*return MapAtlasesMod.CONFIG.drawMinimapCoords;
         *///?}
    }
}
