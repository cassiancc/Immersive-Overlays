package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
//? if fabric && <26 {
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.item.ItemStack;
//?}
//? if >26 {
/*import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.TrinketsApi;
*///?}
//? if <26.2 {
import net.minecraft.util.Tuple;
//?}
import net.minecraft.world.entity.player.Player;

public class TrinketsCompat {
    public static void checkForImportantAccessories(Player player) {
        //? if >26 {
        /*TrinketsApi.getAttachment(player).allEquipped(false).stream().map(TrinketSlotAccess::get).forEach(OverlayHelpers::isImportantItemOrContainer);
        *///?} else if fabric {
        var capability = TrinketsApi.getTrinketComponent(player);
        if (capability.isPresent()) {
            for (Tuple<SlotReference, ItemStack> tuple : capability.get().getAllEquipped()) {
                OverlayHelpers.isImportantItemOrContainer(tuple.getB());
            }
        }
        //?}
    }
}
