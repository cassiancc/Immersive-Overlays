package cc.cassian.immersiveoverlays.compat;

//? if neoforge || forge
//import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;

public class EclipticSeasonsCompat {

    public static String getSeason(ClientLevel level, BlockPos pos) {
        //? if neoforge || forge && >1.20 {
        /*return EclipticSeasonsApi.getInstance().getAgroSeason(level, pos).name();
        *///?} else if forge {
        /*return EclipticSeasonsApi.getInstance().getSolarTerm(level).getSeason().name();
        *///?} else {
        return null;
        //?}
    }
}
