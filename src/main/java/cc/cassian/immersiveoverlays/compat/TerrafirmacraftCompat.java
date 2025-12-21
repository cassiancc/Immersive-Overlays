package cc.cassian.immersiveoverlays.compat;

//? if neoforge || (forge && >1.20)
//import net.dries007.tfc.util.calendar.Calendars;
import net.minecraft.client.multiplayer.ClientLevel;

public class TerrafirmacraftCompat {

    public static String getSeason(ClientLevel level) {
        //? if neoforge {
        /*return Calendars.get(true).getAbsoluteCalendarMonthOfYear().getSeason().getSerializedName();
        *///?} else if forge && >1.20 {
        /*return Calendars.get(true).getCalendarMonthOfYear().getSeason().getSerializedName();
        *///?} else {
        return null;
        //?}
    }
}
