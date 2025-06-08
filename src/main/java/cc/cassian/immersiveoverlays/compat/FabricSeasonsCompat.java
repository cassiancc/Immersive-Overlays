package cc.cassian.immersiveoverlays.compat;

import io.github.lucaargolo.seasons.FabricSeasons;
import net.minecraft.client.multiplayer.ClientLevel;

public class FabricSeasonsCompat {
    public static String getSeason(ClientLevel level) {
        return FabricSeasons.getCurrentSeason(level).name();
    }
}
