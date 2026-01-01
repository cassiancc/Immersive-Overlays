package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.ClockOverlay;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import sereneseasons.api.season.SeasonHelper;

public class SereneSeasonsCompat {
    public static String getSeason(ClientLevel level, BlockPos pos) {
        var biome = level.getBiome(pos);
        var state = SeasonHelper.getSeasonState(level);
        if (SeasonHelper.usesTropicalSeasons(biome)) {
            return state.getTropicalSeason().name();
        } else {
            return state.getSubSeason().name();
        }
    }
}
