package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;

//? if neoforge {
/*import net.neoforged.neoforge.items.ItemStackHandler;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
*///?} else if fabric {
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
//?} else if forge {
/*import net.minecraftforge.items.ItemStackHandler;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
*///?}
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import java.util.Optional;

public class SophisticatedBackpacksCompat {

    public static void checkBackpackContents(ItemStack stack) {
        //? if neoforge {
        /*Optional<IBackpackWrapper> backpackWrapper = BackpackWrapper.fromExistingData(stack);
        if (backpackWrapper.isPresent()) {
            ItemStackHandler inventory = backpackWrapper.get().getInventoryHandler();
            for (int i =0; i < inventory.getSlots(); i++) {
                OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
            }
        }
    *///?}
    //? if >1.21 && fabric {
    Optional<IBackpackWrapper> backpackWrapper = BackpackWrapper.fromExistingData(stack);
    if (backpackWrapper.isPresent()) {
        InventoryHandler inventory = backpackWrapper.get().getInventoryHandler();
        for (SingleSlotStorage<ItemVariant> slot : inventory.getSlots()) {
            OverlayHelpers.isImportantItemOrContainer(slot.getResource().toStack());
        }
    }
    //?} else if fabric && >1.19 {
    /*if (stack.getItem() instanceof BackpackItem) {
        IBackpackWrapper backpackWrapper = new BackpackWrapper(stack);
        InventoryHandler inventory = backpackWrapper.getInventoryHandler();
        for (int i =0; i < inventory.getBaseSlotLimit(); i++) {
            OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
        }
    }
    *///?} else if forge {
        /*if (stack.getItem() instanceof BackpackItem) {
            IBackpackWrapper backpackWrapper = new BackpackWrapper(stack);
            ItemStackHandler inventory = backpackWrapper.getInventoryHandler();
            for (int i =0; i < inventory.getSlots(); i++) {
                OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
            }
        }
        *///?}
    }
}

