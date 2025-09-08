package cc.cassian.immersiveoverlays.compat;

import io.github.steveplays28.simpleseasons.api.SimpleSeasonsApi;
import net.minecraft.client.multiplayer.ClientLevel;

public class SimpleSeasonsCompat {
    public static String getSeason(ClientLevel level) {
        return SimpleSeasonsApi.getSeason(level).name();
    }
}
