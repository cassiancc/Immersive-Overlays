package cc.cassian.immersiveoverlays.compat;

//? if (forge) || (neoforge && =1.21.1) {
/*import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureEnum;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureUtil;
*///?}
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;

public class LegendarySurvivalOverhaulCompat {

    public static String getAmbientTemperature(LocalPlayer player) {
        //? if (forge) || (neoforge && =1.21.1) {
        /*TemperatureEnum temperatureEnum = TemperatureUtil.getTemperatureEnum(TemperatureUtil.getPlayerTargetTemperature(player));
        return temperatureEnum.name();
        *///?} else {
        return "";
         //?}
    }
}
