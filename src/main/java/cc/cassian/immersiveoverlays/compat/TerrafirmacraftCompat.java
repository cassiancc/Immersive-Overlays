package cc.cassian.immersiveoverlays.compat;

//? if neoforge
/*import net.dries007.tfc.util.calendar.Calendars;*/
import net.minecraft.client.multiplayer.ClientLevel;

public class TerrafirmacraftCompat {

    public static String getSeason(ClientLevel level) {
        //? if neoforge {
        /*return Calendars.get(true).getAbsoluteCalendarMonthOfYear().getSeason().getSerializedName();
        *///?} else {
        return null;
        //?}
    }
}
