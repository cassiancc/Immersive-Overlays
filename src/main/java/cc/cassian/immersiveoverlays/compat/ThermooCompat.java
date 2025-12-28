package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.helpers.TextHelpers;
import cc.cassian.immersiveoverlays.overlay.TemperatureOverlay;
//? if fabric && <26
//import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;


public class ThermooCompat {

    public static TemperatureOverlay.TemperaturePair getTemperaturePair(LocalPlayer player) {
        //? if fabric && <26 {
        /*var temperatureAware = TemperatureAware.getNullable(player);
        if (temperatureAware != null) {
            var scale = temperatureAware.thermoo$getTemperatureScale();
            int temperature = (int) (scale*100);
            String sprite;
            String text;
            int colour;

            if (scale >= .9) {
                text = "gui.c.temperature.hot";
                sprite = "heat_8";
                colour = 15545147;
            } else if (scale > .75) {
                text = "gui.c.temperature.hot";
                sprite = "heat_7";
                colour = 12539712;
            }  else if (scale > .5) {
                text = "gui.c.temperature.hot";
                sprite = "heat_6";
                colour = 16742400;
            }  else if (scale > .4) {
                text = "gui.c.temperature.hot";
                sprite = "heat_5";
                colour = 12545600;
            }  else if (scale > .3) {
                text = "gui.c.temperature.hot";
                sprite = "heat_4";
                colour = 16175917;
            }  else if (scale > .1) {
                text = "gui.c.temperature.warm";
                sprite = "heat_3";
                colour = 16311388;
            }  else if (scale > -.2) {
                text = "gui.c.temperature.temperate";
                sprite = "heat_2";
                colour = -1;
            }  else if (scale > -0.3) {
                text = "gui.c.temperature.cold";
                sprite = "heat_1";
                colour = 7906009;
            }  else {
                text = "gui.c.temperature.cold";
                sprite = "heat_0";
                colour = 4169980;
            }

            return new TemperatureOverlay.TemperaturePair(TextHelpers.translatable(text).append(TextHelpers.literal(" (" + temperature + "%)")), colour, sprite);
        }
        *///?}
        return null;

    }
}
