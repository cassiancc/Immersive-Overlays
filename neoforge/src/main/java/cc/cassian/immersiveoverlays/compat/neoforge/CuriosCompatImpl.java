package cc.cassian.immersiveoverlays.compat.neoforge;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosCompatImpl {
    public static void checkForImportantAccessories(Player player) {
        var capability = CuriosApi.getCuriosInventory(player);
        if (capability.isPresent()) {
            IItemHandlerModifiable allEquipped = capability.get().getEquippedCurios();
            for (int i = 0; i < allEquipped.getSlots(); i++) {
                OverlayHelpers.isImportantItemOrContainer(allEquipped.getStackInSlot(i));
            }
        }
    }
}
