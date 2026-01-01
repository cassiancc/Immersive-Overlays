package cc.cassian.immersiveoverlays.compat;

//? if neoforge || forge
import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;

public class EclipticSeasonsCompat {

    public static String getSeason(ClientLevel level, BlockPos pos) {
        //? if neoforge || forge {
        return EclipticSeasonsApi.getInstance().getAgroSeason(level, pos).name();
        //?} else {
        /*return null;
        *///?}
    }
}
