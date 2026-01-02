package cc.cassian.immersiveoverlays.overlay;


import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.helpers.ModHelpers;
import cc.cassian.immersiveoverlays.helpers.TextHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import static cc.cassian.immersiveoverlays.overlay.BiomeOverlay.*;

public class CompassOverlay {
    public static boolean showX = false;
    public static boolean showY = false;
    public static boolean showZ = false;

    public static void renderGameOverlayEvent(GuiGraphics guiGraphics
            //? if >1.21 {
            /*, net.minecraft.client.DeltaTracker deltaTracker
            *///?} else {
            , float deltaTracker
             //?}
    ) {
        boolean showBiomeIcon = ModConfig.get().biome_enable && BiomeOverlay.showBiome && ModConfig.get().biome_reduced_info && ModConfig.get().biome_icons;
        if (!showX && !showY && !showZ)
            return;
        if (!ModConfig.get().compass_enable)
            return;

        var mc = Minecraft.getInstance();
        if (OverlayHelpers.shouldCancelRender(mc))
            return;

        ArrayList<Component> coords = new ArrayList<>();

        BlockPos pos;
        if (mc.player != null) pos = mc.player.blockPosition();
        else return;

        String x = String.format("%d", pos.getX());
        String y = String.format("%d", pos.getY());
        String z = String.format("%d", pos.getZ());
        var width = Integer.max(x.length(), z.length());
        width = Integer.max(width, y.length());
        if (showX) {
            x = StringUtils.leftPad(x, width);
            coords.add(Component.empty().append(Component.literal("X: ").withStyle(Style.EMPTY.withColor(ModConfig.get().compass_x_colour))).append(x));
        }
        if (showY) {
            y = StringUtils.leftPad(y, width);
            coords.add(Component.empty().append(Component.literal("Y: ").withStyle(Style.EMPTY.withColor(ModConfig.get().compass_y_colour))).append(y));
        }
        if (showZ) {
            z = StringUtils.leftPad(z, width);
            coords.add(Component.empty().append(Component.literal("Z: ").withStyle(Style.EMPTY.withColor(ModConfig.get().compass_z_colour))).append(z));
        }
        int xOffset = 3;
        int yPlacement = ModConfig.get().compass_vertical_position;
        int iconXOffset = 0;
        int iconYOffset = 0;
        if (showBiomeIcon) {
            iconXOffset += 20;
        }
        int fontWidth = mc.font.width(StringUtils.repeat("a", width+2))+iconXOffset;

        if (ModConfig.get().avoid_overlapping) {
            if (!(ClockOverlay.showTime || ClockOverlay.showWeather) || !ModConfig.get().clock_enable || (ModConfig.get().clock_horizontal_position_left != ModConfig.get().compass_horizontal_position_left)) {
                yPlacement = yPlacement - 15;
            }
            if ((ClockOverlay.showTime || ClockOverlay.showWeather) && (ModConfig.get().clock_horizontal_position_left == ModConfig.get().compass_horizontal_position_left)) {
                yPlacement = yPlacement + 9;
            }
            if (!BiomeOverlay.showBiome || !ModConfig.get().biome_enable || ModConfig.get().biome_reduced_info || (ModConfig.get().biome_horizontal_position_left != ModConfig.get().compass_horizontal_position_left)) {
                yPlacement = yPlacement - 20;
            }
            if (!ClockOverlay.shouldShowSeasons()) {
                yPlacement = yPlacement - 15;
            }
        }
        if (OverlayHelpers.playerHasPotions(mc.player, ModConfig.get().compass_horizontal_position_left)) {
            yPlacement += OverlayHelpers.moveBy(mc.player);
        }

        int tooltipSize = 16;  // only depth gauge
        if (coords.size() == 2 || (coords.size() == 1 && showBiomeIcon)) {
            tooltipSize = 25;
            iconYOffset = 3;
        } else if (coords.size() == 3) {
            tooltipSize = 35;
            iconYOffset = 5;
        }

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().compass_horizontal_position_left);
        // render background
        OverlayHelpers.renderBackground(guiGraphics, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, tooltipSize, ModConfig.get().compass_horizontal_position_left);
        if (showBiomeIcon) {
            var sprite = getBiomeSprite(getId(getBiome(mc.player)), true);
            OverlayHelpers.blit(guiGraphics, sprite, xPlacement-xOffset-1, yPlacement+iconYOffset, 0, 0, 16, 16, 16, 16);
            xOffset -= 16;
        }
        // render text
        for (Component text : coords) {
            OverlayHelpers.drawString(guiGraphics, mc.font, text, xPlacement-xOffset, yPlacement, ModConfig.get().compass_text_colour);
            yPlacement += mc.font.lineHeight;
        }
    }
}