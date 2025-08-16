package cc.cassian.immersiveoverlays.compat;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;

public class LegendarySurvivalOverhaulCompat {

    @ExpectPlatform
    public static String getAmbientTemperature(LocalPlayer player) {
        throw new AssertionError();
    }
}
