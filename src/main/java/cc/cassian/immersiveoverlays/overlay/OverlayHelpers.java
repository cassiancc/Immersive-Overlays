package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.compat.*;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import cc.cassian.immersiveoverlays.config.ModConfig;
import net.minecraft.client.gui.Font;
import net.minecraft.client.Minecraft;
//? if >1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import com.mojang.blaze3d.vertex.PoseStack;
 *///?}
//? if >1.21.6 {
/*import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.ARGB;
*///?}
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
//? if >1.21.4
/*import net.minecraft.world.entity.EquipmentSlot;*/
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
    public static void renderBackground(GuiGraphics guiGraphics, int windowWidth, int fontWidth, int xPlacement, int xOffset, int yPlacement, int tooltipSize, boolean leftAlign) {
        //?} else {
        /*public static void renderBackground(PoseStack guiGraphics, int windowWidth, int fontWidth, int xPlacement, int xOffset, int yPlacement, int tooltipSize, boolean leftAlign) {
         *///?}
        if (ModConfig.get().render_background) {
            int textureOffset = OverlayHelpers.getTextureOffsetFromSize(tooltipSize);
            //? if <1.20
            /*RenderSystem.setShaderTexture(0, OverlayHelpers.TEXTURE);*/
            final int yPlacementWithOffset = yPlacement-4;
            final int endCapOffset = 197;
            final int xPlacementWithOffset = xPlacement-xOffset-4;
            final int endCapXPlacement = OverlayHelpers.getEndCapPlacement(windowWidth, fontWidth, leftAlign);
            final int uWidth = fontWidth+xOffset+4;
            OverlayHelpers.blit(guiGraphics, xPlacementWithOffset, yPlacementWithOffset, 0, textureOffset, uWidth, tooltipSize, OverlayHelpers.textureSize, OverlayHelpers.textureSize);
            // render endcap
            OverlayHelpers.blit(guiGraphics, endCapXPlacement, yPlacementWithOffset, endCapOffset, textureOffset, 3, tooltipSize, OverlayHelpers.textureSize, OverlayHelpers.textureSize);
        }
    }

    public static int getTextureOffsetFromSize(int textureSize) {
        if (textureSize == 16) {
            return 7;
        } else if (textureSize == 21) {
            return 111;
        } else if (textureSize == 25) {
            return 25;
        } else if (textureSize == 35) {
            return 51;
        } else if (textureSize == 36) {
            return 131;
        }
        else return 0;
    }

    public static void checkInventoryForOverlays(Minecraft minecraft){
        if ((ModConfig.get().compass_enable || ModConfig.get().clock_enable || ModConfig.get().biome_enable || ModConfig.get().temperature_enable)  && minecraft.level != null) {
            OverlayHelpers.checkInventoryForItems(minecraft.player);
        }
    }

    public static boolean playerHasPotions(Player player, boolean leftAlign) {
        if (!ModConfig.get().moved_by_effects) return false;
        if (!leftAlign) return false;
        // Technically, we should check whether these are ambient,
        // but Map Atlases doesn't and still covers our overlay.
        // return Player.areAllEffectsAmbient(player.getActiveEffects());
        return !player.getActiveEffects().isEmpty();
    }

    public static int moveBy(Player player) {
        //? if >1.21 {
        boolean hasBeneficial =
                player.getActiveEffects().stream().anyMatch(p -> p.getEffect().value().isBeneficial());
        boolean hasNegative =
                player.getActiveEffects().stream().anyMatch(p -> !p.getEffect().value().isBeneficial());
        //?} else {
        /*boolean hasBeneficial =
                player.getActiveEffects().stream().anyMatch(p -> p.getEffect().isBeneficial());
        boolean hasNegative =
                player.getActiveEffects().stream().anyMatch(p -> !p.getEffect().isBeneficial());

        *///?}
        if (hasNegative) {
            return 42;
        } else if (hasBeneficial) {
            return 16;
        }
        else return 0;
    }

    public static boolean shouldCancelRender(Minecraft mc) {
        if (mc.options.hideGui) return true;
        if (ModConfig.get().hide_from_debug) {
            //? if >1.21 {
            var debug = mc.getDebugOverlay().showDebugScreen();
             //?} else {
            /*var debug = mc.options.renderDebug;
            *///?}
            return debug;
        }
        return false;
    }

    private static void findImportantContainerContents(ItemStack container) {
        List<ItemStack> list = getContainerContents(container).toList();
        for (ItemStack itemStack : list) {
            if (ModConfig.get().search_containers_for_containers) {
                isImportantItemOrContainer(itemStack);
            } else {
                isImportantItem(itemStack);
            }
        }
    }

    private static void isImportantItem(ItemStack itemStack) {
        if (itemStack.isEmpty())
            return;
        var item = itemStack.getItem();
        if (ModLists.compass_x_items.contains(item))
            CompassOverlay.showX = true;
        if (ModLists.compass_y_items.contains(item))
            CompassOverlay.showY = true;
        if (ModLists.compass_z_items.contains(item))
            CompassOverlay.showZ = true;
        if (ModLists.clock_items.contains(item))
            ClockOverlay.showTime = true;
        if (ModLists.weather_items.contains(item))
            ClockOverlay.showWeather = true;
        if (ModLists.biome_items.contains(item))
            BiomeOverlay.showBiome = true;
        if (ModLists.season_items.contains(item))
            ClockOverlay.showSeason = true;
        if (ModLists.temperature_items.contains(item))
            TemperatureOverlay.showTemperature = true;
        if (ModLists.speed_items.contains(item))
            SpeedOverlay.showSpeed = true;
    }

    public static void checkInventoryForItems(Player player) {
        if (ModConfig.get().require_item) {
            setOverlays(false);
            if (player == null)
                return;
            isImportantItemOrContainer(player.getOffhandItem());
            if (ModConfig.get().require_item_in_hand) {
                isImportantItemOrContainer(player.getMainHandItem());
            } else {
                //? if <1.21.5 {
                player.getArmorSlots().forEach((OverlayHelpers::isImportantItemOrContainer));
                //?} else {
                /*for (EquipmentSlot value : EquipmentSlot.values()) {
                    isImportantItemOrContainer(player.getItemBySlot(value));
                }
                *///?}
                //? if >1.20 {
                if (ModCompat.ACCESSORIES)
                    AccessoriesCompat.checkForImportantAccessories(player);
                //?}
                if (ModCompat.CURIOS)
                    CuriosCompat.checkForImportantAccessories(player);
                if (ModCompat.TRAVELERS_BACKPACK)
                    TravelersBackpackCompat.checkForImportantAccessories(player);
                checkInventoryForStack(player.getInventory());
            }
        } else {
            setOverlays(true);
        }
    }

    private static void setOverlays(boolean b) {
        CompassOverlay.showX = b;
        CompassOverlay.showY = b;
        CompassOverlay.showZ = b;
        ClockOverlay.showTime = b;
        ClockOverlay.showWeather = b;
        BiomeOverlay.showBiome = b;
        ClockOverlay.showSeason = b;
        TemperatureOverlay.showTemperature = b;
        SpeedOverlay.showSpeed = b;
    }

    public static void isImportantItemOrContainer(ItemStack stack) {
        isImportantItem(stack);
        if (isContainer(stack)) {
            findImportantContainerContents(stack);
        }
        if (ModCompat.SOPHISTICATED_BACKPACKS) {
            SophisticatedBackpacksCompat.checkBackpackContents(stack);
        }
    }

    public static Stream<ItemStack> getContainerContents(ItemStack stack) {
        if (!isContainer(stack)) return Stream.empty();
        //? if >1.20.5 {
        var components = stack.getComponents();
        if (components.has(DataComponents.BUNDLE_CONTENTS)) {
            BundleContents bundleContents = components.get(DataComponents.BUNDLE_CONTENTS);
            if (bundleContents != null)
                return bundleContents.itemCopyStream();
        }
        else if (components.has(DataComponents.CONTAINER)) {
            ItemContainerContents containerContents = components.get(DataComponents.CONTAINER);
            if (containerContents != null)
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

    public static boolean isContainer(ItemStack stack) {
        if (!ModConfig.get().search_containers) return false;
        if (stack.isEmpty()) return false;
        //? if >1.20.5 {
        var components = stack.getComponents();
        if (components.has(DataComponents.BUNDLE_CONTENTS)) {
            return true;
        }
        else if (components.has(DataComponents.CONTAINER)) {
            return true;
        }
        //?} else {
        /*CompoundTag compoundtag = stack.getTag();
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
        *///?}
        return true;
    }

    public static boolean checkInventoryForItem(Inventory inventory, Item item, boolean value) {
        if (value) return true;
        else return checkInventoryForStack(inventory, item) != ItemStack.EMPTY;
    }

    public static void checkInventoryForStack(Inventory inventory) {
        for (ItemStack stack :
            //? if <1.21.5 {
             inventory.items
            //?} else {
                /*inventory.getNonEquipmentItems()
            *///?}
        ) {
            isImportantItem(stack);
            if (isContainer(stack)) {
                findImportantContainerContents(stack);
            }
        }
    }

    public static ItemStack checkInventoryForStack(Inventory inventory, Item item) {
        for (ItemStack stack :
            //? if <1.21.5 {
             inventory.items
            //?} else {
        /*inventory.getNonEquipmentItems()
        *///?}
        ) {
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

    public static int getPlacement(int windowWidth, int fontWidth, boolean leftAlign) {
        if (leftAlign) {
            return 9;
        } else {
            return windowWidth-2-fontWidth;
        }
    }

    public static int getEndCapPlacement(int windowWidth, int fontWidth, boolean leftAlign) {
        if (leftAlign) {
            return fontWidth+8;
        } else {
            return windowWidth-4;
        }
    }

    public static void drawString(
           //? if >1.20 {
          GuiGraphics
          //?} else {
          /*PoseStack
          *///?}
          poseStack, Font font, Component text, int x, int y, Integer color) {
        //? if >1.21.6
        /*color = ARGB.opaque(color);*/
        //? if >1.20 {
        poseStack.drawString(font, text, x, y, color);
        //?} else {
        /*GuiComponent.drawString(poseStack, font, text, x, y, color);
         *///?}
    }

    public static void drawString(
          //? if >1.20 {
          GuiGraphics
          //?} else {
          /*PoseStack
          *///?}
         poseStack, Font font, String text, int x, int y, Integer color) {
        //? if >1.21.6
        /*color = ARGB.opaque(color);*/
        //? if >1.20 {
        poseStack.drawString(font, text, x, y, color);
        //?} else {
        /*GuiComponent.drawString(poseStack, font, text, x, y, color);
         *///?}
    }

    public static void renderOverlays(
            //? if >1.20 {
            GuiGraphics
            //?} else {
            /*PoseStack
             *///?}
                    hud) {
        CompassOverlay.renderGameOverlayEvent(hud);
        ClockOverlay.renderGameOverlayEvent(hud);
        BiomeOverlay.renderGameOverlayEvent(hud);
        TemperatureOverlay.renderGameOverlayEvent(hud);
        SpeedOverlay.renderGameOverlayEvent(hud);
    }

    public static void blit(
           //? if >1.20 {
          GuiGraphics
          //?} else {
          /*PoseStack
          *///?}
          guiGraphics, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        OverlayHelpers.blit(guiGraphics, TEXTURE, x, y, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
    }

    public static void blit(
           //? if >1.20 {
          GuiGraphics
          //?} else {
          /*PoseStack
          *///?}
          guiGraphics, ResourceLocation texture, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        //? if >1.21.5 {
        /*guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture,
        *///?} else if >1.21.2 {
        /*guiGraphics.blit(RenderType::guiTextured, texture,
         *///?} else if >1.20 {
        guiGraphics.blit(texture,
                //?} else {
            /*RenderSystem.setShaderTexture(0, texture);
               GuiComponent.blit(guiGraphics,
             *///?}
                x, y,
                //? if <1.21.2
                0, //z
                //?
                uOffset,
                vOffset, uWidth, vHeight,
                textureWidth, textureHeight);
    }

    public static void blitSprite(
            //? if >1.20 {
            GuiGraphics
                    //?} else {
                    /*PoseStack
                     *///?}
                    guiGraphics, ResourceLocation texture, int x, int y) {
        blitSprite(guiGraphics, texture, x, y, 16);
    }

    public static void blitSprite(
            //? if >1.20 {
            GuiGraphics
                    //?} else {
                    /*PoseStack
                     *///?}
                    guiGraphics, ResourceLocation texture, int x, int y, int size) {
        blit(guiGraphics, texture, x, y, 0, 0, size, size, size, size);
    }
}
