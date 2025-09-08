package cc.cassian.immersiveoverlays.compat.neoforge;

import galena.oreganized.client.tooltips.ClientThermometerTooltip;
import galena.oreganized.content.item.ThermometerItem;
import galena.oreganized.world.IMotionHolder;
import net.minecraft.client.player.LocalPlayer;

public class OreganizedCompatImpl {
    public static int getAmbientTemperatureFromThermometer(LocalPlayer player) {
        return ThermometerItem.ambientMeasurement(player);
    }

    public static int getTemperatureColourFromThermometer(int temperature) {
        return ClientThermometerTooltip.getColor(temperature);
    }

    public static double getSpeed(LocalPlayer player) {
        if (player.getRootVehicle() instanceof IMotionHolder motionHolder) {
            return motionHolder.oreganised$getMotion();
        }
       return 0;
    }
}