package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import com.swacky.ohmega.api.AccessoryHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class OhmegaCompat {
    public static void checkForImportantAccessories(Player player) {
        var container = AccessoryHelper.getContainer(player);
        if (container != null) {
            for (ItemStack slotEntryReference : container.getStacks()) {
                if (slotEntryReference != null && AccessoryHelper.isActive(slotEntryReference)) {
                    OverlayHelpers.isImportantItemOrContainer(slotEntryReference);
                }
            }
        }
    }
}
