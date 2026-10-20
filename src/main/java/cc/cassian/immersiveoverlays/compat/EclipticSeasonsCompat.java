package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.ClockOverlay;
//? if neoforge || forge || (fabric && >26)
//import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;

public class EclipticSeasonsCompat {

    public static ClockOverlay.Season getSeason(ClientLevel level, BlockPos pos) {
        //? if fabric && >26 {
        /*var seasonSignal = EclipticSeasonsApi.getInstance().getSeasonSignal(level, pos);
        return new ClockOverlay.Season(seasonSignal.name(), seasonSignal.getTranslation());
        *///?} else if neoforge || forge{
        /*var season = EclipticSeasonsApi.getInstance().getAgroSeason(level, pos);
        return new ClockOverlay.Season(season.name(), season.getTranslation());
        *///?} else {
        return ClockOverlay.Season.UNKNOWN;
        //?}
    }
}
