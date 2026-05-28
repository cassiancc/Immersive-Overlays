package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;

//? if neoforge {
/*import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;
*///?} else if fabric {
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
//?} else if forge {
/*import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
*///?}
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;

import net.minecraft.world.item.ItemStack;
import java.util.List;
import java.util.Set;

public class SophisticatedBackpacksCompat {

    public static void checkBackpackContents(ItemStack stack) {
        //? if >1.21 {
        BackpackWrapper.fromExistingData(stack).ifPresent(backpackWrapper -> {
            var inventory = backpackWrapper.getInventoryHandler();
            //? fabric || (neoforge && >1.21.9) {
            for (var slot : getSlots(inventory)) {
                OverlayHelpers.isImportantItemOrContainer(getStack(slot));
            }
            //?} else {
            /*for (int i = 0; i < inventory.getSlots(); i++) {
                OverlayHelpers.isImportantItemOrContainer(getStack(inventory, i));
            }
            *///?}
        });
        //?} else if =1.20.1 {
        /*if (stack.getItem() instanceof BackpackItem) {
            var backpackWrapper = new BackpackWrapper(stack);
            var inventory = backpackWrapper.getInventoryHandler();
            for (int i = 0; i < getSlots(inventory); i++) {
                OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
            }
        }
        *///?}
    }

    //? fabric && >1.21 && <26 {
    private static ItemStack getStack(SingleSlotStorage<ItemVariant> slot) {
        return slot.getResource().toStack();
    }
    private static List<SingleSlotStorage<ItemVariant>> getSlots(InventoryHandler inventory) {
        return inventory.getSlots();
    }
    //?} else if neoforge && >1.21.9 {
    /*private static ItemStack getStack(ItemStackKey slot) {
        return slot.stack();
    }
    private static Set<ItemStackKey> getSlots(InventoryHandler inventory) {
        return inventory.getTrackedStacks();
    }
    *///?} else if neoforge {
    /*private static int getSlots(InventoryHandler inventory) {
        return inventory.getSlots();
    }
    private static ItemStack getStack(InventoryHandler inventory, int i) {
        return inventory.getStackInSlot(i);
    }
    *///?} else if fabric && =1.20.1 {
    /*private static int getSlots(InventoryHandler inventory) {
        return inventory.getBaseSlotLimit();
    }
    *///?} else if forge && =1.20.1 {
    /*private static int getSlots(InventoryHandler inventory) {
        return inventory.getSlots();
    }
    *///?} else {
    /*private static ItemStack getStack(Object slot) {
        return ItemStack.EMPTY;
    }
    private static List<Object> getSlots(InventoryHandler inventory) {
        return List.of();
    }
    *///?}



    public static void readAnchorFromBackpack(ItemStack stack) {
        //? if >1.21 {
        BackpackWrapper.fromExistingData(stack).ifPresent(backpackWrapper -> {
            var inventory = backpackWrapper.getInventoryHandler();
            //? fabric || (neoforge && >1.21.9) {
            for (var slot : getSlots(inventory)) {
                OverlayHelpers.readAnchorItemOrContainer(getStack(slot));
            }
            //?} else {
            /*for (int i = 0; i < inventory.getSlots(); i++) {
                OverlayHelpers.readAnchorItemOrContainer(getStack(inventory, i));
            }
            *///?}
        });
        //?} else {
        /*if (stack.getItem() instanceof BackpackItem) {
            var backpackWrapper = new BackpackWrapper(stack);
            var inventory = backpackWrapper.getInventoryHandler();
            for (int i = 0; i < getSlots(inventory); i++) {
                OverlayHelpers.readAnchorItemOrContainer(inventory.getStackInSlot(i));
            }
        }
        *///?}
    }
}

