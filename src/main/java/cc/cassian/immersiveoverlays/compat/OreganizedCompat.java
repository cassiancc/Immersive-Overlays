package cc.cassian.immersiveoverlays.compat;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class OreganizedCompat {

    @ExpectPlatform
    public static int getAmbientTemperatureFromThermometer(LocalPlayer player) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getTemperatureColourFromThermometer(int temperature) {
        throw new AssertionError();
    }
}
