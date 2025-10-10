package cc.cassian.immersiveoverlays.compat;

import net.minecraft.client.player.LocalPlayer;
//? if >1.20 {
import toughasnails.api.temperature.TemperatureHelper;
import toughasnails.api.temperature.TemperatureLevel;
//?}


public class ToughAsNailsCompat {
    public static String getAmbientTemperature(LocalPlayer player) {
        //? if >1.20 {
              TemperatureLevel temperatureForPlayer = TemperatureHelper.getTemperatureForPlayer(player);
        return temperatureForPlayer.name();
        //?} else {
        /*return "";
        *///?}

    }

    public static boolean isTemperatureEnabled() {
        //? if >1.20 {
              return TemperatureHelper.isTemperatureEnabled();
        //?} else {
        /*return false;
        *///?}

    }
}
