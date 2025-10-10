package cc.cassian.immersiveoverlays.compat;

//? if fabric {
import io.github.steveplays28.simpleseasons.api.SimpleSeasonsApi;
//?}
import net.minecraft.client.multiplayer.ClientLevel;

public class SimpleSeasonsCompat {
    public static String getSeason(ClientLevel level) {
        //? if fabric {
        return SimpleSeasonsApi.getSeason(level).name();
        //?} else {
        /*return null;
        *///?}
    }
}
