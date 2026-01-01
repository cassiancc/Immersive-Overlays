package cc.cassian.immersiveoverlays.compat;

import net.minecraft.client.player.LocalPlayer;
import toughasnails.api.temperature.TemperatureHelper;
import toughasnails.api.temperature.TemperatureLevel;

public class ToughAsNailsCompat {
    public static String getAmbientTemperature(LocalPlayer player) {
      TemperatureLevel temperatureForPlayer = TemperatureHelper.getTemperatureForPlayer(player);
        return temperatureForPlayer.name();
    }

    public static boolean isTemperatureEnabled() {
      return TemperatureHelper.isTemperatureEnabled();
    }
}
