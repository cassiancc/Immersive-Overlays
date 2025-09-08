package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.TemperatureOverlay;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;


public class ColdSweatCompat {

    public static TemperatureOverlay.TemperaturePair getTemperaturePair(LocalPlayer player) {
        var temperature = Math.round(getAmbientTemperature(player));
        String sprite;
        int colour;
        if (temperature >= 121) {
            sprite = "heat_8";
            colour = 15545147;
        } else if (temperature > 115) {
            sprite = "heat_7";
            colour = 12539712;
        }  else if (temperature > 110) {
            sprite = "heat_6";
            colour = 16742400;
        }  else if (temperature > 100) {
            sprite = "heat_5";
            colour = 12545600;
        }  else if (temperature > 90) {
            sprite = "heat_4";
            colour = 16175917;
        }  else if (temperature > 78) {
            sprite = "heat_3";
            colour = 16311388;
        }  else if (temperature > 65) {
            sprite = "heat_2";
            colour = -1;
        }  else if (temperature > 30) {
            sprite = "heat_1";
            colour = 7906009;
        }  else {
            sprite = "heat_0";
            colour = 4169980;
        }
        return new TemperatureOverlay.TemperaturePair(Component.literal(String.valueOf(temperature)), colour, sprite);
    }

    @ExpectPlatform
    public static double getAmbientTemperature(LocalPlayer player) {
        throw new AssertionError();
    }
}
