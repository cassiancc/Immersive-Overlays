package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.helpers.TextHelpers;
import cc.cassian.immersiveoverlays.overlay.TemperatureOverlay;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
//? if neoforge || forge {
import com.momosoftworks.coldsweat.client.gui.Overlays;
import com.momosoftworks.coldsweat.config.ConfigSettings;
//?}

public class ColdSweatCompat {

    public static TemperatureOverlay.TemperaturePair getTemperaturePair(LocalPlayer player) {
        long temperature = Math.round(getAmbientTemperature(player));
        double comparableTemperature;
        String sprite;
        int colour;
        if (isCelsius()) {
            comparableTemperature = (temperature * 1.8) + 32;
        } else {
            comparableTemperature = temperature;
        }
        if (comparableTemperature >= 121) {
            sprite = "heat_8";
            colour = 15545147;
        } else if (comparableTemperature > 115) {
            sprite = "heat_7";
            colour = 12539712;
        }  else if (comparableTemperature > 110) {
            sprite = "heat_6";
            colour = 16742400;
        }  else if (comparableTemperature > 100) {
            sprite = "heat_5";
            colour = 12545600;
        }  else if (comparableTemperature > 90) {
            sprite = "heat_4";
            colour = 16175917;
        }  else if (comparableTemperature > 78) {
            sprite = "heat_3";
            colour = 16311388;
        }  else if (comparableTemperature > 65) {
            sprite = "heat_2";
            colour = -1;
        }  else if (comparableTemperature > 30) {
            sprite = "heat_1";
            colour = 7906009;
        }  else {
            sprite = "heat_0";
            colour = 4169980;
        }
        return new TemperatureOverlay.TemperaturePair(TextHelpers.literal(String.valueOf(temperature)), colour, sprite);
    }

    public static double getAmbientTemperature(LocalPlayer player) {
        //? if forge || neoforge {
        return Overlays.WORLD_TEMP;
        //?} else {
        /*return 0;
        *///?}
    }

    public static boolean isCelsius() {
        //? if forge || neoforge {
        return ConfigSettings.CELSIUS.get();
        //?} else {
        /*return false;
         *///?}
    }
}
