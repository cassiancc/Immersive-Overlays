package cc.cassian.immersiveoverlays.compat;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.multiplayer.ClientLevel;

public class TerrafirmacraftCompat {
    @ExpectPlatform
    public static String getSeason(ClientLevel level) {
        return null;
    }
}
