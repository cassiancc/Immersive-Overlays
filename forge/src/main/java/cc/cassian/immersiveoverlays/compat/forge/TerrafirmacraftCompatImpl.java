package cc.cassian.immersiveoverlays.compat.neoforge;

import net.dries007.tfc.util.calendar.Calendars;
import net.minecraft.client.multiplayer.ClientLevel;

public class TerrafirmacraftCompatImpl {

    public static String getSeason(ClientLevel level) {
        return Calendars.get(true).getAbsoluteCalendarMonthOfYear().getSeason().getSerializedName();
    }
}
