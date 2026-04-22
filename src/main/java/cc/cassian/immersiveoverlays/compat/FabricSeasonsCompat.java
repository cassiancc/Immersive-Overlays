package cc.cassian.immersiveoverlays.compat;

//? if fabric && <26 {
import io.github.lucaargolo.seasons.FabricSeasons;
//?}
import net.minecraft.client.multiplayer.ClientLevel;

public class FabricSeasonsCompat {
    public static String getSeason(ClientLevel level) {
        //? if fabric && <26 {
        return FabricSeasons.getCurrentSeason(level).name();
        //?} else {
        /*return null;
        *///?}
    }
}
