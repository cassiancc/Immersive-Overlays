package cc.cassian.immersiveoverlays.overlay;


import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.ModCompat;
import cc.cassian.immersiveoverlays.ModTags;
import cc.cassian.immersiveoverlays.config.ModConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
//? if >1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import net.minecraft.client.gui.GuiComponent;
 *///?}
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
//? if >1.20.5 {
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.core.component.DataComponents;
//?}


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
        if (ModConfig.get().overlay_renderBackground) {
            final int yPlacementWithOffset = yPlacement-4;
            final int endCapOffset = 197;
            // render background
            //? if >1.20 {
            guiGraphics.blit(TEXTURE,
            //?} else {
               /*GuiComponent.blit(poseStack,
             *///?}
                    xPlacement-xOffset-4, yPlacementWithOffset,
                    0, 0,
                    textureOffset, fontWidth+xOffset+4, tooltipSize,
                    OverlayHelpers.textureSize, OverlayHelpers.textureSize);
            // render endcap
            //? if >1.20 {
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
        if ((ModConfig.get().overlay_compass_enable || ModConfig.get().overlay_clock_enable) && minecraft.level != null) {
            OverlayHelpers.checkInventoryForItems(minecraft.player);
        }
    }

    public static boolean playerHasPotions(Player player) {
        // Technically, we should check whether these are ambient,
        // but Map Atlases doesn't and still covers our overlay.
        // return Player.areAllEffectsAmbient(player.getActiveEffects());
        return !player.getActiveEffects().isEmpty();
    }

    public static boolean debug(Minecraft mc) {
        //? if >1.21 {
        var debug = mc.getDebugOverlay().showDebugScreen();
        //?} else {
        /*var debug = mc.options.renderDebug;
         *///?}
        return debug && !mc.options.reducedDebugInfo().get();
    }

    public static void checkInventoryForItems(Player player) {
        if (player == null)
            return;
        if (ModConfig.get().overlay_compass_enable || ModConfig.get().overlay_clock_enable) {
            var y = ModTags.SHOWS_Y;
            if (!ModCompat.SPELUNKERY && !ModCompat.CAVERNS_AND_CHASMS && !ModCompat.SUPPLEMENTARIES)
                y = ModTags.SHOWS_XZ;
            var barometer = ModTags.SHOWS_WEATHER;
            if (!ModCompat.CAVERNS_AND_CHASMS) {
                barometer = ModTags.SHOWS_TIME;
            }
            var inventory = player.getInventory();
            if (ModConfig.get().overlay_requireItemInHand) {
                var main = player.getMainHandItem();
                var offhand = player.getOffhandItem();
                CompassOverlay.hasCompass = main.is(ModTags.SHOWS_XZ) || offhand.is(ModTags.SHOWS_XZ);
                CompassOverlay.hasDepthGauge = main.is(y) || offhand.is(y);
                ClockOverlay.hasClock = main.is(ModTags.SHOWS_TIME) || offhand.is(ModTags.SHOWS_TIME);
                ClockOverlay.hasBarometer = main.is(barometer) || offhand.is(barometer);
            } else {
                CompassOverlay.hasCompass = checkInventoryForItem(inventory, ModTags.SHOWS_XZ);
                CompassOverlay.hasDepthGauge = checkInventoryForItem(inventory, y);
                ClockOverlay.hasClock = checkInventoryForItem(inventory, ModTags.SHOWS_TIME);
                ClockOverlay.hasBarometer = checkInventoryForItem(inventory, barometer);
            }
        } else {
            CompassOverlay.hasCompass = false;
            CompassOverlay.hasDepthGauge = false;
        }
    }

    public static Stream<ItemStack> getContents(ItemStack stack) {
        //? if >1.20.5 {
        var components = stack.getComponents();
        if (components.has(DataComponents.BUNDLE_CONTENTS)) {
            BundleContents bundleContents = components.get(DataComponents.BUNDLE_CONTENTS);
            return bundleContents.itemCopyStream();
        }
        else if (components.has(DataComponents.CONTAINER)) {
            ItemContainerContents containerContents = components.get(DataComponents.CONTAINER);
            return containerContents.stream();
        }
        //?} else {
        /*CompoundTag compoundtag = stack.getTag();
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
        *///?}
        return Stream.empty();
    }


    public static boolean checkInventoryForItem(Inventory inventory, TagKey<Item> item) {
        if (inventory.contains(item)) {
            return true;
        }
        else return checkInventoryForStack(inventory, item, null) != ItemStack.EMPTY;
    }

    public static ItemStack checkInventoryForStack(Inventory inventory, TagKey<Item> key, Item item) {
        if (ModConfig.get().overlay_searchContainers && inventory.contains(ModTags.CONTAINERS)) {
            for (ItemStack stack : inventory.items) {
                if (stack.is(ModTags.CONTAINERS)) {
                    List<ItemStack> contents = getContents(stack).toList();
                    for (ItemStack content : contents) {
                        if (key != null && content.is(key))
                            return stack;
                        else if (item != null && content.is(item))
                            return stack;
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

   public static int getPlacement(int windowWidth, int fontWidth) {
        if (ModConfig.get().overlay_leftalign) {
            return 9;
        } else {
            return windowWidth-2-fontWidth;
        }
    }

    public static int getEndCapPlacement(int windowWidth, int fontWidth) {
        if (ModConfig.get().overlay_leftalign) {
            return fontWidth+8;
        } else {
            return windowWidth-4;
        }
    }
}
