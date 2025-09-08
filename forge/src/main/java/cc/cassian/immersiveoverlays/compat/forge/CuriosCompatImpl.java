package cc.cassian.immersiveoverlays.compat.forge;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosCompatImpl {
    public static void checkForImportantAccessories(Player player) {
        //? if >1.20 {
        var capability = CuriosApi.getCuriosInventory(player);
        if (capability.isPresent() && capability.resolve().isPresent()) {
            IItemHandlerModifiable allEquipped = capability.resolve().get().getEquippedCurios();
            for (int i =0; i < allEquipped.getSlots(); i++) {
                OverlayHelpers.isImportantItemOrContainer(allEquipped.getStackInSlot(i));
            }
        }
        //?}
    }
}
