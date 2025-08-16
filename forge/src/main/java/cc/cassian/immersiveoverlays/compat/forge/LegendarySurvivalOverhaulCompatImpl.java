package cc.cassian.immersiveoverlays.compat.forge;

import net.minecraft.client.player.LocalPlayer;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureEnum;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureUtil;

public class LegendarySurvivalOverhaulCompatImpl {

    public static String getAmbientTemperature(LocalPlayer player) {
        TemperatureEnum temperatureEnum = TemperatureUtil.getTemperatureEnum(TemperatureUtil.getPlayerTargetTemperature(player));
        return temperatureEnum.name();
    }
}
