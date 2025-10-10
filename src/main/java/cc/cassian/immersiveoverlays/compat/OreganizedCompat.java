package cc.cassian.immersiveoverlays.compat;

import net.minecraft.client.player.LocalPlayer;
//? if forge || neoforge {
/*import galena.oreganized.client.tooltips.ClientThermometerTooltip;
import galena.oreganized.content.item.ThermometerItem;
import galena.oreganized.world.IMotionHolder;
*///?}

public class OreganizedCompat {

    public static int getAmbientTemperatureFromThermometer(LocalPlayer player) {
        //? if forge || neoforge {
        /*return ThermometerItem.ambientMeasurement(player);
        *///?} else {
        return 0;
        //?}
    }

    public static int getTemperatureColourFromThermometer(int temperature) {
        //? if forge || neoforge {
        /*return ClientThermometerTooltip.getColor(temperature);
        *///?} else {
        return 0;
        //?}

    }

    public static double getSpeed(LocalPlayer player) {
        //? if forge || neoforge {
        /*if (player.getRootVehicle() instanceof IMotionHolder motionHolder) {
            return motionHolder.oreganised$getMotion();
        }
        *///?}
        return 0;
    }
}
