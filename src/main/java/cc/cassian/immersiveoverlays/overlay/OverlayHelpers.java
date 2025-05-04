package cc.cassian.immersiveoverlays.overlay;


import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import cc.cassian.immersiveoverlays.helpers.ModTags;
import cc.cassian.immersiveoverlays.compat.ModCompat;
import cc.cassian.immersiveoverlays.config.ModConfig;

import net.minecraft.client.Minecraft;
//? if >1.20 {
import net.minecraft.client.gui.GuiGraphics;
import cc.cassian.immersiveoverlays.compat.AccessoriesCompat;
//?} else {
/*import net.minecraft.client.gui.GuiComponent;
import com.mojang.blaze3d.vertex.PoseStack;
 *///?}

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
//? if >1.20.5 {
/*import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.core.component.DataComponents;
*///?}


import java.util.List;
import java.util.stream.Stream;

public class OverlayHelpers {
    public static final int textureSize = 256;
    public static final ResourceLocation TEXTURE = ModClient.locate("textures/gui/overlay.png");

    //? if >1.20 {
    public static void renderBackground(GuiGraphics guiGraphics, int windowWidth, int fontWidth, int xPlacement, int xOffset, int yPlacement, int textureOffset, int tooltipSize) {
    //?} else {
        /*public static void renderBackground(PoseStack poseStack, int windowWidth, int fontWidth, int xPlacement, int xOffset, int yPlacement, int textureOffset, int tooltipSize) {
    *///?}
        if (ModConfig.get().render_background) {
            final int yPlacementWithOffset = yPlacement-4;
            final int endCapOffset = 197;
            // render background
            //? if >1.21.2 {
            /*guiGraphics.blit(RenderType::guiTextured, TEXTURE,
            *///?} else if >1.20 {
            guiGraphics.blit(TEXTURE,
            //?} else {
               /*GuiComponent.blit(poseStack,
             *///?}
                    xPlacement-xOffset-4, yPlacementWithOffset,
                    0, 0,
                    textureOffset, fontWidth+xOffset+4, tooltipSize,
                    OverlayHelpers.textureSize, OverlayHelpers.textureSize);
            // render endcap
            //? if >1.21.2 {
            /*guiGraphics.blit(RenderType::guiTextured, TEXTURE,
            *///?} else if >1.20 {
            guiGraphics.blit(TEXTURE,
            //?} else {
           /*GuiComponent.blit(poseStack,
             *///?}
                    OverlayHelpers.getEndCapPlacement(windowWidth, fontWidth), yPlacementWithOffset,
                    0, endCapOffset,
                    textureOffset, 3, tooltipSize,
                    OverlayHelpers.textureSize, OverlayHelpers.textureSize);
        }
    }

    public static void checkInventoryForOverlays(Minecraft minecraft){
        if ((ModConfig.get().compass_enable || ModConfig.get().clock_enable) && minecraft.level != null) {
            OverlayHelpers.checkInventoryForItems(minecraft.player);
        }
    }

    public static boolean playerHasPotions(Player player) {
        if (!ModConfig.get().moved_by_effects) return false;
        // Technically, we should check whether these are ambient,
        // but Map Atlases doesn't and still covers our overlay.
        // return Player.areAllEffectsAmbient(player.getActiveEffects());
        return !player.getActiveEffects().isEmpty();
    }

    public static int moveBy(Player player) {
        //? if >1.21 {
        /*boolean hasBeneficial =
                player.getActiveEffects().stream().anyMatch(p -> p.getEffect().value().isBeneficial());
        boolean hasNegative =
                player.getActiveEffects().stream().anyMatch(p -> !p.getEffect().value().isBeneficial());
        *///?} else {
        boolean hasBeneficial =
                player.getActiveEffects().stream().anyMatch(p -> p.getEffect().isBeneficial());
        boolean hasNegative =
                player.getActiveEffects().stream().anyMatch(p -> !p.getEffect().isBeneficial());
    
        //?}
        if (hasNegative) {
            return 42;
        } else if (hasBeneficial) {
            return 16;
        }
        else return 0;
    }

    public static boolean debug(Minecraft mc) {
        if (ModConfig.get().hide_from_debug) {
            //? if >1.21 {
            /*var debug = mc.getDebugOverlay().showDebugScreen();
             *///?} else {
            var debug = mc.options.renderDebug;
            //?}
            return debug && !mc.options.reducedDebugInfo().get();
        }
        return false;
    }

    private static void findImportantContainerContents(ItemStack container) {
        List<ItemStack> list = getContainerContents(container).toList();
        for (ItemStack itemStack : list) {
            isImportantItem(itemStack);
        }
    }

    private static void isImportantItem(ItemStack itemStack) {
        if (itemStack.isEmpty())
            return;
        var item = itemStack.getItem();
        if (ModLists.compass_items.contains(item)) {
            CompassOverlay.showXZ = true;
        }
        if (ModLists.compass_depth_items.contains(item))
            CompassOverlay.showY = true;
        if (ModLists.clock_items.contains(item))
            ClockOverlay.showTime = true;
        if (ModLists.weather_items.contains(item))
            ClockOverlay.showWeather = true;
    }

    public static void checkInventoryForItems(Player player) {
        CompassOverlay.showXZ = false;
        CompassOverlay.showY = false;
        ClockOverlay.showTime = false;
        ClockOverlay.showWeather = false;
        if (player == null)
            return;
        if (ModConfig.get().compass_enable || ModConfig.get().clock_enable) {
            isImportantItemOrContainer(player.getOffhandItem());
            if (ModConfig.get().require_item_in_hand) {
                isImportantItemOrContainer(player.getMainHandItem());
            } else {
                player.getArmorSlots().forEach((OverlayHelpers::isImportantItemOrContainer));
                //? if >1.20 {
                if (ModCompat.ACCESSORIES)
                    AccessoriesCompat.checkForImportantAccessories(player);
                //?}
                checkInventoryForStack(player.getInventory());
            }
        }
    }

    public static void isImportantItemOrContainer(ItemStack stack) {
        isImportantItem(stack);
        if (isContainer(stack)) {
            findImportantContainerContents(stack);
        }
    }

    public static Stream<ItemStack> getContainerContents(ItemStack stack) {
        if (!isContainer(stack)) return Stream.empty();
        //? if >1.20.5 {
        /*var components = stack.getComponents();
        if (components.has(DataComponents.BUNDLE_CONTENTS)) {
            BundleContents bundleContents = components.get(DataComponents.BUNDLE_CONTENTS);
            return bundleContents.itemCopyStream();
        }
        else if (components.has(DataComponents.CONTAINER)) {
            ItemContainerContents containerContents = components.get(DataComponents.CONTAINER);
            return containerContents.stream();
        }
        *///?} else {
        CompoundTag compoundtag = stack.getTag();
        if (compoundtag == null) {
            return Stream.empty();
        } else {
            if (compoundtag.contains("Items")) {
                ListTag listtag = compoundtag.getList("Items", 10);
                return listtag.stream().map(CompoundTag.class::cast).map(ItemStack::of);
            }
            else if (compoundtag.contains("BlockEntityTag")) {
                var compound = compoundtag.getCompound("BlockEntityTag");
                ListTag listtag = compound.getList("Items", 10);
                return listtag.stream().map(CompoundTag.class::cast).map(ItemStack::of);
            }
        }
        //?}
        return Stream.empty();
    }

    public static boolean isContainer(ItemStack stack) {
        if (!ModConfig.get().search_containers) return false;
        if (stack.isEmpty()) return false;
        if (stack.is(ModTags.CONTAINERS)) return true;
        //? if >1.20.5 {
        /*var components = stack.getComponents();
        if (components.has(DataComponents.BUNDLE_CONTENTS)) {
            return true;
        }
        else if (components.has(DataComponents.CONTAINER)) {
            return true;
        }
        *///?} else {
        CompoundTag compoundtag = stack.getTag();
        if (compoundtag == null) {
            return false;
        } else {
            if (compoundtag.contains("Items")) {
                return true;
            }
            else if (compoundtag.contains("BlockEntityTag")) {
                if (compoundtag.getCompound("BlockEntityTag").contains("Items")) {
                    return true;
                }
            }
        }
        //?}
        return true;
    }

    public static boolean checkInventoryForItem(Inventory inventory, Item item, boolean value) {
        if (value) return true;
        else return checkInventoryForStack(inventory, item) != ItemStack.EMPTY;
    }

    public static void checkInventoryForStack(Inventory inventory) {
        for (ItemStack stack : inventory.items) {
            isImportantItem(stack);
            if (isContainer(stack)) {
                findImportantContainerContents(stack);
            }
        }
    }

    public static ItemStack checkInventoryForStack(Inventory inventory, Item item) {
        for (ItemStack stack : inventory.items) {
            if (stack.is(item)) return stack;
            else if (item != null && stack.is(item))
                return stack;
            else if (isContainer(stack)) {
                List<ItemStack> contents = getContainerContents(stack).toList();
                for (ItemStack content : contents) {
                    if (item != null && content.is(item))
                        return content;
                }
            }
        }
        return ItemStack.EMPTY;
    }

   public static int getPlacement(int windowWidth, int fontWidth) {
        if (ModConfig.get().align_left) {
            return 9;
        } else {
            return windowWidth-2-fontWidth;
        }
    }

    public static int getEndCapPlacement(int windowWidth, int fontWidth) {
        if (ModConfig.get().align_left) {
            return fontWidth+8;
        } else {
            return windowWidth-4;
        }
    }
}
