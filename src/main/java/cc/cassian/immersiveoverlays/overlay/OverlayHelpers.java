package cc.cassian.immersiveoverlays.overlay;


import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.ModTags;
import cc.cassian.immersiveoverlays.config.ModConfig;

import net.minecraft.client.Minecraft;
//? if >1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import net.minecraft.client.gui.GuiComponent;
import com.mojang.blaze3d.vertex.PoseStack;
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
/*import net.minecraft.world.item.component.BundleContents;
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
        // Technically, we should check whether these are ambient,
        // but Map Atlases doesn't and still covers our overlay.
        // return Player.areAllEffectsAmbient(player.getActiveEffects());
        return !player.getActiveEffects().isEmpty();
    }

    public static boolean debug(Minecraft mc) {
        //? if >1.21 {
        /*var debug = mc.getDebugOverlay().showDebugScreen();
        *///?} else {
        var debug = mc.options.renderDebug;
         //?}
        return debug && !mc.options.reducedDebugInfo().get();
    }

    private static void getHandContainerContents(ItemStack handItem) {
        List<ItemStack> list = getContents(handItem).toList();
        for (ItemStack itemStack : list) {
            if (itemStack.is(ModTags.SHOWS_XZ))
                CompassOverlay.showXZ = true;
            else if (itemStack.is(ModTags.SHOWS_Y))
                CompassOverlay.showY = true;
            else if (itemStack.is(ModTags.SHOWS_TIME))
                ClockOverlay.showTime = true;
            else if (itemStack.is(ModTags.SHOWS_WEATHER))
                ClockOverlay.showWeather = true;
        }
    }

    public static void checkInventoryForItems(Player player) {
        if (player == null)
            return;
        if (ModConfig.get().compass_enable || ModConfig.get().clock_enable) {
            var inventory = player.getInventory();
            var mainhand = player.getMainHandItem();
            var offhand = player.getOffhandItem();
            if (ModConfig.get().require_item_in_hand) {
                CompassOverlay.showXZ = mainhand.is(ModTags.SHOWS_XZ) || offhand.is(ModTags.SHOWS_XZ);
                CompassOverlay.showY = mainhand.is(ModTags.SHOWS_Y) || offhand.is(ModTags.SHOWS_Y);
                ClockOverlay.showTime = mainhand.is(ModTags.SHOWS_TIME) || offhand.is(ModTags.SHOWS_TIME);
                ClockOverlay.showWeather = mainhand.is(ModTags.SHOWS_WEATHER) || offhand.is(ModTags.SHOWS_WEATHER);
                if (mainhand.is(ModTags.CONTAINERS)) {
                    getHandContainerContents(mainhand);
                }
            } else {
                CompassOverlay.showXZ = checkInventoryForItem(inventory, ModTags.SHOWS_XZ, offhand.is(ModTags.SHOWS_XZ));
                CompassOverlay.showY = checkInventoryForItem(inventory, ModTags.SHOWS_Y, offhand.is(ModTags.SHOWS_Y));
                ClockOverlay.showTime = checkInventoryForItem(inventory, ModTags.SHOWS_TIME, offhand.is(ModTags.SHOWS_TIME));
                ClockOverlay.showWeather = checkInventoryForItem(inventory, ModTags.SHOWS_WEATHER, offhand.is(ModTags.SHOWS_WEATHER));
            }
            if (offhand.is(ModTags.CONTAINERS)) {
                getHandContainerContents(offhand);
            }
        } else {
            CompassOverlay.showXZ = false;
            CompassOverlay.showY = false;
            ClockOverlay.showWeather = false;
            ClockOverlay.showTime = false;
        }
    }

    public static Stream<ItemStack> getContents(ItemStack stack) {
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


    public static boolean checkInventoryForItem(Inventory inventory, TagKey<Item> item, boolean value) {
        if (value) return true;
        if (inventory.contains(item)) return true;
        else return checkInventoryForStack(inventory, item, null) != ItemStack.EMPTY;
    }

    public static ItemStack checkInventoryForStack(Inventory inventory, TagKey<Item> key, Item item) {
        if (ModConfig.get().search_containers && inventory.contains(ModTags.CONTAINERS)) {
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
