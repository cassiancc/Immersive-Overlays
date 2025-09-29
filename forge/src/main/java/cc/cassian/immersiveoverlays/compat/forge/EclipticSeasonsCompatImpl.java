package cc.cassian.immersiveoverlays.compat.neoforge;

import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;

public class EclipticSeasonsCompatImpl {
    public static String getSeason(ClientLevel level, BlockPos pos) {
        return EclipticSeasonsApi.getInstance().getAgroSeason(level, pos).name();
    }
}
