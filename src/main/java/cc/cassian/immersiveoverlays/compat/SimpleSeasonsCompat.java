package cc.cassian.immersiveoverlays.compat;

//? if fabric && <26 {
/*import io.github.steveplays28.simpleseasons.api.SimpleSeasonsApi;
*///?}
import net.minecraft.client.multiplayer.ClientLevel;

public class SimpleSeasonsCompat {
    public static String getSeason(ClientLevel level) {
        //? if fabric && <26 {
        /*return SimpleSeasonsApi.getSeason(level).name();
        *///?} else {
        return null;
        //?}
    }
}
