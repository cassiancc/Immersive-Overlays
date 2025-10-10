package cc.cassian.immersiveoverlays.compat;

//? if >1.20 && forge {
/*import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureEnum;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureUtil;
*///?}
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;

public class LegendarySurvivalOverhaulCompat {

    public static String getAmbientTemperature(LocalPlayer player) {
        //? if >1.20 && forge {
        /*TemperatureEnum temperatureEnum = TemperatureUtil.getTemperatureEnum(TemperatureUtil.getPlayerTargetTemperature(player));
        return temperatureEnum.name();
        *///?} else {
        return "";
         //?}
    }
}
