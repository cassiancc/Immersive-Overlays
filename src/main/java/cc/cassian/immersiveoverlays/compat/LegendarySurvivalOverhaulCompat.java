package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.TemperatureOverlay;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
//? if (forge) || (neoforge && =1.21.1) {
/*import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureEnum;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureUtil;
*///?}
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;

public class LegendarySurvivalOverhaulCompat {

    public static @Nullable String getAmbientTemperature(LocalPlayer player) {
        //? if (forge) || (neoforge && =1.21.1) {
        /*TemperatureEnum temperatureEnum = TemperatureUtil.getTemperatureEnum(TemperatureUtil.getPlayerTargetTemperature(player));
        return temperatureEnum.name();
        *///?} else {
        return null;
         //?}
    }

    public static TemperatureOverlay.TemperaturePair getTemperaturePair(LocalPlayer player) {
        String temperature = getAmbientTemperature(player);
        if (temperature == null) {return null;}
        var sprite = switch (temperature) {
            case "FROSTBITE" -> "heat_0";
            case "COLD" -> "heat_1";
            case "HOT" -> "heat_4";
            case "HEAT_STROKE" -> "heat_6";
            default -> "heat_2";
        };
        return new TemperatureOverlay.TemperaturePair(Component.translatableWithFallback("gui.c.temperature.%s".formatted(temperature.toLowerCase()), WordUtils.capitalizeFully(temperature.replace("_", " "))), -1, sprite);
    }
}
