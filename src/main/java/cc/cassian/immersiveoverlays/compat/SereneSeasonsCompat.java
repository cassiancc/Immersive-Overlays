package cc.cassian.immersiveoverlays.compat;

import net.minecraft.client.multiplayer.ClientLevel;
import sereneseasons.api.season.SeasonHelper;

public class SereneSeasonsCompat {
    public static String getSeason(ClientLevel level) {
        return SeasonHelper.getSeasonState(level).getSeason().name();
    }
}
