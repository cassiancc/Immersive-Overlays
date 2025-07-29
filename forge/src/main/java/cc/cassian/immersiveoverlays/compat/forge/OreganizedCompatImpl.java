package cc.cassian.immersiveoverlays.compat.forge;

import galena.oreganized.client.tooltips.ClientThermometerTooltip;
import galena.oreganized.content.item.ThermometerItem;
import net.minecraft.client.player.LocalPlayer;

public class OreganizedCompatImpl {
    public static int getAmbientTemperatureFromThermometer(LocalPlayer player) {
        return ThermometerItem.ambientMeasurement(player);
    }

    public static int getTemperatureColourFromThermometer(int temperature) {
        return ClientThermometerTooltip.getColor(temperature);
    }
}
