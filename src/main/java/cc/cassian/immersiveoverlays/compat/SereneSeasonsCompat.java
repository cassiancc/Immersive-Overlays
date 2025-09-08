package cc.cassian.immersiveoverlays.compat;

import net.minecraft.client.multiplayer.ClientLevel;
//? if >1.20 {
  import sereneseasons.api.season.SeasonHelper;
//?}

public class SereneSeasonsCompat {
    public static String getSeason(ClientLevel level) {
        //? if >1.20 {
            return SeasonHelper.getSeasonState(level).getSeason().name();
        //?} else {
        /*return "";
        *///?}
    }
}
