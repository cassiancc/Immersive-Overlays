package cc.cassian.immersiveoverlays.compat.forge;

import com.momosoftworks.coldsweat.client.gui.Overlays;
import net.minecraft.client.player.LocalPlayer;

public class ColdSweatCompatImpl {

    public static double getAmbientTemperature(LocalPlayer player) {
        return Overlays.WORLD_TEMP;
    }
}
