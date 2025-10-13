package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.ClockOverlay;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
//? if >1.20 {
import sereneseasons.api.season.SeasonHelper;
//?}

public class SereneSeasonsCompat {
    public static String getSeason(ClientLevel level, BlockPos pos) {
        var biome = level.getBiome(pos);
        //? if >1.20 {
        var state = SeasonHelper.getSeasonState(level);
        if (SeasonHelper.usesTropicalSeasons(biome)) {
            return state.getTropicalSeason().name();
        } else {
            return state.getSubSeason().name();
        }
        //?} else {
        /*return "unknown";
        *///?}
    }
}
