package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.world.entity.player.Player;
//? if neoforge
/*import net.neoforged.neoforge.items.IItemHandlerModifiable;*/
//? if forge
/*import net.minecraftforge.items.IItemHandlerModifiable;*/
//? if neoforge || forge
/*import top.theillusivec4.curios.api.CuriosApi;*/

public class CuriosCompat {
    public static void checkForImportantAccessories(Player player) {
        //? if (forge && >1.20) || neoforge {
        /*var capability = CuriosApi.getCuriosInventory(player);
        if (capability.isPresent()) {
            IItemHandlerModifiable allEquipped = capability
                    //? if forge
                    /^.resolve()^/
                    .get().getEquippedCurios();
            for (int i = 0; i < allEquipped.getSlots(); i++) {
                OverlayHelpers.isImportantItemOrContainer(allEquipped.getStackInSlot(i));
            }
        }
        *///?}
    }
}
