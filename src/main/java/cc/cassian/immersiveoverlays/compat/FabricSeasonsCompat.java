package cc.cassian.immersiveoverlays.compat;

import net.minecraft.client.multiplayer.ClientLevel;

public class FabricSeasonsCompat {
    public static String getSeason(ClientLevel level) {
        //? if <26 {
        return io.github.lucaargolo.seasons.FabricSeasons.getCurrentSeason(level).name();
        //?} else {
        /*return null;
        *///?}
    }
}
