package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.config.ModConfigFactory;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.mru.client.util.HudUtils;
import cc.cassian.mru.util.ItemContainerUtils;
import net.minecraft.world.item.Items;
//? if >1.21 {
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.LodestoneTracker;
//?} else {
/*import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.CompassItem;
*///?}
import net.minecraft.client.Minecraft;
import net.minecraft.core.GlobalPos;
import net.minecraft.client.gui.GuiGraphics;
//? if >1.21.6 {
/*import net.minecraft.client.renderer.RenderPipelines;
*///?}
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class OverlayHelpers {
    public static boolean showWaila = false;
    private static boolean hasSeenAnchorStack = false;

    @SuppressWarnings("unused")
    public static void renderBackground(GuiGraphics guiGraphics, int windowWidth, int fontWidth, int xPlacement, int xOffset, int yPlacement, int tooltipSize, boolean leftAlign) {
        if (ModConfig.get().render_background) {
            final int yPlacementWithOffset = yPlacement-4;
            final int xPlacementWithOffset = xPlacement-xOffset-4;
            final int uWidth = fontWidth+xOffset+4;
            //? if >1.21 {
            guiGraphics.blitSprite(
                    //? if >1.21.2
                    //RenderPipelines.GUI_TEXTURED,
                    ModClient.locate("background"), xPlacementWithOffset, yPlacementWithOffset, uWidth, tooltipSize);
            //?} else {
            
            /*int textureOffset = OverlayHelpers.getTextureOffsetFromSize(tooltipSize);
            final int endCapXPlacement = OverlayHelpers.getEndCapPlacement(windowWidth, fontWidth, leftAlign);
            final int endCapOffset = 197;
            OverlayHelpers.blit(guiGraphics, xPlacementWithOffset, yPlacementWithOffset, 0, textureOffset, uWidth, tooltipSize, 256, 256);
            // render endcap
            if (ModConfig.get().render_endcap)
                OverlayHelpers.blit(guiGraphics, endCapXPlacement, yPlacementWithOffset, endCapOffset, textureOffset, 3, tooltipSize, 256, 256);
            *///?}
        }
    }

    //? if <1.21 {
    /*public static int getTextureOffsetFromSize(int textureSize) {
        if (textureSize == 16) {
            return 7;
        } else if (textureSize == 21) {
            return 111;
        } else if (textureSize == 25) {
            return 25;
        } else if (textureSize == 35 || textureSize == 36) {
            return 51;
        } else if (textureSize == 45) {
            return 132;
        }
        else return 0;
    }
    *///?}

    public static void checkInventoryForOverlays(Minecraft minecraft){
        if ((ModConfig.get().compass_enable || ModConfig.get().clock_enable || ModConfig.get().biome_enable || ModConfig.get().temperature_enable || ModConfig.get().wind_enable) && minecraft.level != null) {
            OverlayHelpers.checkPlayerForOverlays(minecraft.player);
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
        return HudUtils.shouldCancelRender(mc, ModConfig.get().enabled, ModConfig.get().hide_from_debug);
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
        if (ModLists.day_count_items.contains(item))
            ClockOverlay.showDayCount = true;
        if (ModLists.temperature_items.contains(item))
            TemperatureOverlay.showTemperature = true;
        if (ModLists.speed_items.contains(item))
            SpeedOverlay.showSpeed = true;
        if (ModLists.wind_items.contains(item))
            WindOverlay.showWind = true;
        if (ModLists.waila_items.contains(item))
            showWaila = true;
        if (ModLists.compass_anchor_items.contains(item)){
            readAnchor(itemStack);
            hasSeenAnchorStack = true;
        }
    }

    public static void checkPlayerForOverlays(Player player) {
        boolean searchContainersForContainers = ModConfig.get().search_containers_for_containers;
        if (ModConfig.get().require_item) {
            setOverlays(false);
            if (player == null)
                return;
            hasSeenAnchorStack = false;
            ItemContainerUtils.isImportantItemOrContainer(player.getOffhandItem(), OverlayHelpers::isImportantItem, searchContainersForContainers);
            if (ModConfig.get().require_item_in_hand) {
                ItemContainerUtils.isImportantItemOrContainer(player.getMainHandItem(), OverlayHelpers::isImportantItem, searchContainersForContainers);
            } else {
                ItemContainerUtils.checkPlayerForImportantItems(player, OverlayHelpers::isImportantItem, ModConfig.get().search_containers, searchContainersForContainers);
            }
            if (ModConfig.get().allow_blocks && !ModLists.important_blocks.isEmpty()) {
                checkSurroundingsForOverlays(player);
            }
        } else {
            setOverlays(true);
            if (player == null || !ModConfig.get().compass_relative_pos)
                return;
        }
        ItemContainerUtils.checkPlayerForImportantItems(player, OverlayHelpers::tryReadAnchor, ModConfig.get().search_containers, searchContainersForContainers);
    }

    public static void checkSurroundingsForOverlays(Player player) {
        AABB box = new AABB(player.blockPosition()).inflate(ModConfig.get().allow_blocks_distance).expandTowards(0.0, ModConfig.get().allow_blocks_distance, 0.0);
        player.level().getBlockStatesIfLoaded(box).forEach(blockState -> {
            if (blockState.isAir())
                return;
            var item = blockState.getBlock();
            if (ModLists.compass_x_blocks.contains(item))
                CompassOverlay.showX = true;
            if (ModLists.compass_y_blocks.contains(item))
                CompassOverlay.showY = true;
            if (ModLists.compass_z_blocks.contains(item))
                CompassOverlay.showZ = true;
            if (ModLists.clock_blocks.contains(item))
                ClockOverlay.showTime = true;
            if (ModLists.weather_blocks.contains(item))
                ClockOverlay.showWeather = true;
            if (ModLists.biome_blocks.contains(item))
                BiomeOverlay.showBiome = true;
            if (ModLists.season_blocks.contains(item))
                ClockOverlay.showSeason = true;
            if (ModLists.day_count_blocks.contains(item))
                ClockOverlay.showDayCount = true;
            if (ModLists.temperature_blocks.contains(item))
                TemperatureOverlay.showTemperature = true;
            if (ModLists.speed_blocks.contains(item))
                SpeedOverlay.showSpeed = true;
            if (ModLists.wind_blocks.contains(item))
                WindOverlay.showWind = true;
            if (ModLists.waila_blocks.contains(item))
                showWaila = true;
        });
    }

    private static void setOverlays(boolean b) {
        CompassOverlay.showX = b;
        CompassOverlay.showY = b;
        CompassOverlay.showZ = b;
        ClockOverlay.showTime = b;
        ClockOverlay.showWeather = b;
        BiomeOverlay.showBiome = b;
        ClockOverlay.showSeason = b;
        ClockOverlay.showDayCount = b;
        TemperatureOverlay.showTemperature = b;
        SpeedOverlay.showSpeed = b;
        WindOverlay.showWind = b;
        showWaila = b;
        CompassOverlay.anchor = b ? CompassOverlay.anchor : null;
    }


    public static boolean isContainer(ItemStack stack) {
        if (!ModConfig.get().search_containers) return false;
        return ItemContainerUtils.isContainer(stack);
    }

    public static boolean checkInventoryForItem(Inventory inventory, Item item, boolean value) {
        if (value) return true;
        else return checkInventoryForStack(inventory, item) != ItemStack.EMPTY;
    }

    public static @NotNull ItemStack checkInventoryForStack(Inventory inventory, Item item) {
        return ItemContainerUtils.checkInventoryForStack(inventory, item);
    }

    public static int getPlacement(int windowWidth, int fontWidth, boolean leftAlign) {
        if (leftAlign) {
            return 9;
        } else {
            return windowWidth-2-fontWidth;
        }
    }

    //? if <1.21 {
    /*public static int getEndCapPlacement(int windowWidth, int fontWidth, boolean leftAlign) {
        if (leftAlign) {
            return fontWidth+8;
        } else {
            return windowWidth-4;
        }
    }
    *///?}

    /**
     * Cannot be removed until 1.20.1 gains nine-slicing or support is dropped.
	 */
    @Deprecated
    public static void blit(GuiGraphics guiGraphics, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        HudUtils.blit(guiGraphics, ModClient.locate("textures/gui/overlay.png"), x, y, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
    }

    public static void blitSprite(GuiGraphics guiGraphics, String texture, int x, int y) {
        HudUtils.blitSprite(guiGraphics, ModClient.locate("textures/gui/sprites/%s.png".formatted(texture)), x, y);
    }

    static boolean hasBeenToggled = false;

    public static void checkKeybind() {
        if (ModClient.overlayToggle.isDown()) {
            if (!hasBeenToggled) {
                ModConfig.get().enabled = !ModConfig.get().enabled;
                hasBeenToggled = true;
            }
        } else {
            hasBeenToggled = false;
        }
        if (ModClient.overlaySettings.isDown()) {
            HudUtils.setScreen(ModConfigFactory::create);
        }
    }

    public static void tryReadAnchor(ItemStack stack) {
        var item = stack.getItem();
        if (!ModLists.compass_anchor_items.contains(item)) return;
        readAnchor(stack);
        hasSeenAnchorStack = true;
    }


    public static void readAnchor(ItemStack stack) {
        readLodestoneAnchor(stack);
        readRecoveryCompassAnchor(stack);
    }

    private static void readLodestoneAnchor(ItemStack stack) {
        if (!ModConfig.get().compass_relative_pos || hasSeenAnchorStack) return;
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        //? if > 1.20.5 {
        LodestoneTracker tracker = stack.get(DataComponents.LODESTONE_TRACKER);
        if (tracker == null || !tracker.tracked() || tracker.target().isEmpty()) return;
        GlobalPos anchor = tracker.target().get();
        if (player.level().dimension() != anchor.dimension()) return;
        //?} else {
        /*CompoundTag compoundtag = stack.getTag();
        if (compoundtag == null) {
            return;
        }
        GlobalPos anchor = CompassItem.getLodestonePosition(compoundtag);
        if (anchor == null)
            return;
        if (player.level().dimension() != anchor.dimension())
            return;
        *///?}
        CompassOverlay.anchor = anchor;
    }

    private static void readRecoveryCompassAnchor(ItemStack stack) {
        if (!ModConfig.get().compass_relative_pos) return;
        var player = Minecraft.getInstance().player;
        if (player == null || !stack.is(Items.RECOVERY_COMPASS)) return;
        Optional<GlobalPos> anchor = player.getLastDeathLocation();
        if (anchor.isEmpty() || player.level().dimension() != anchor.get().dimension()) return;
        CompassOverlay.anchor = anchor.get();
    }
}
