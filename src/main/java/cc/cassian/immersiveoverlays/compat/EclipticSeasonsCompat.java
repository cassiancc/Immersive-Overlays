package cc.cassian.immersiveoverlays.compat;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;

public class EclipticSeasonsCompat {
    @ExpectPlatform
    public static String getSeason(ClientLevel level, BlockPos pos) {
        throw new AssertionError();
    }
}
