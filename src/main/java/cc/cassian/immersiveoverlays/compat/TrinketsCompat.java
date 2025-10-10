package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
//? if fabric {
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
//?}
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TrinketsCompat {
    public static void checkForImportantAccessories(Player player) {
        //? if fabric {
        var capability = TrinketsApi.getTrinketComponent(player);
        if (capability.isPresent()) {
            for (Tuple<SlotReference, ItemStack> slotReferenceItemStackTuple : capability.get().getAllEquipped()) {
                OverlayHelpers.isImportantItemOrContainer(slotReferenceItemStackTuple.getB());
            }
        }
        //?}
    }
}
