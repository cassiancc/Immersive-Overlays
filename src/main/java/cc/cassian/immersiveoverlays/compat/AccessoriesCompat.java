package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class AccessoriesCompat {
    public static void checkForImportantAccessories(Player player) {
        var capability = AccessoriesCapability.get(player);
        if (capability != null) {
            List<SlotEntryReference> allEquipped = capability.getAllEquipped();
            for (SlotEntryReference slotEntryReference : allEquipped) {
                OverlayHelpers.isImportantItemOrContainer(slotEntryReference.stack());
            }
        }
    }
}
