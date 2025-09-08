package cc.cassian.immersiveoverlays.compat.forge;

import net.minecraft.client.player.LocalPlayer;
//? if >1.20 {
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureEnum;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureUtil;
//?}
public class LegendarySurvivalOverhaulCompatImpl {

    public static String getAmbientTemperature(LocalPlayer player) {
        //? if >1.20 {
        TemperatureEnum temperatureEnum = TemperatureUtil.getTemperatureEnum(TemperatureUtil.getPlayerTargetTemperature(player));
        return temperatureEnum.name();
        //?} else {
        /*return "";
        *///?}
    }
}
