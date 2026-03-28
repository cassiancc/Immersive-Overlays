package cc.cassian.immersiveoverlays.overlay;


import cc.cassian.immersiveoverlays.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
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
            , net.minecraft.client.DeltaTracker deltaTracker
            //?} else {
            /*, float deltaTracker
             *///?}
    ) {
        boolean showBiomeIcon = ModConfig.get().biome_enable && BiomeOverlay.showBiome && ModConfig.get().biome_reduced_info && ModConfig.get().biome_icons;
        if (!showX && !showY && !showZ)
            return;
        if (!ModConfig.get().compass_enable)
            return;

        var mc = Minecraft.getInstance();
        if (OverlayHelpers.shouldCancelRender(mc))
            return;

        ArrayList<Component> lines = new ArrayList<>();

        BlockPos pos;
        if (mc.player != null) pos = mc.player.blockPosition();
        else return;

        String x = String.format("%d", pos.getX());
        String y = String.format("%d", pos.getY());
        String z = String.format("%d", pos.getZ());
        var width = Integer.max(x.length(), z.length());
        width = Integer.max(width, y.length());
        boolean showDirection = ModConfig.get().compass_direction && mc.getCameraEntity() != null;
        if (showDirection) {
            var direction = mc.getCameraEntity().getDirection().getName();
            lines.add(Component.translatable("gui.c.direction."+ direction).withStyle(Style.EMPTY.withColor(ModConfig.get().compass_direction_text_colour)));
		}
        var line = Component.empty();
        Style textStyle = Style.EMPTY.withColor(ModConfig.get().compass_text_colour);
        if (ModConfig.get().compass_single_line) {
            if (showX) {
                line.append(xText(x + ", ", textStyle));
            }
            if (showY) {
                line.append(yText(y + ", ", textStyle));
            }
            if (showZ) {
                line.append(zText(z, textStyle));
            }
            lines.add(line);
        } else {
            if (showX) {
                lines.add(xText(StringUtils.leftPad(x, width), textStyle));
            }
            if (showY) {
                lines.add(yText(StringUtils.leftPad(y, width), textStyle));
            }
            if (showZ) {
                lines.add(zText(StringUtils.leftPad(z, width), textStyle));
            }
        }

        int xOffset = 3;
        int yPlacement = ModConfig.get().compass_vertical_position;
        int iconXOffset = 0;
        int iconYOffset = 0;
        if (showBiomeIcon) {
            iconXOffset += 20;
        }

        int fontWidth = mc.font.width(StringUtils.repeat("a", width+2));
        if (showDirection) {
            fontWidth = Integer.max(fontWidth, mc.font.width(I18n.get("gui.c.direction.south")));
            fontWidth = Math.max(fontWidth, mc.font.width(mc.getCameraEntity().getDirection().getName()));
		}
        if (ModConfig.get().compass_single_line) {
            fontWidth = Math.max(fontWidth, mc.font.width(line));
        }
        fontWidth = fontWidth+iconXOffset;

        if (ModConfig.get().avoid_overlapping) {
            if (!(ClockOverlay.isVisible()) || !ModConfig.get().clock_enable || (ModConfig.get().clock_horizontal_position_left != ModConfig.get().compass_horizontal_position_left)) {
                yPlacement = yPlacement - 15;
            }
            if ((ClockOverlay.isVisible()) && (ModConfig.get().clock_horizontal_position_left == ModConfig.get().compass_horizontal_position_left)) {
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
        if (lines.size() == 2 || (lines.size() == 1 && showBiomeIcon)) {
            tooltipSize = 25;
            iconYOffset = 3;
        } else if (lines.size() == 3) {
            tooltipSize = 35;
            iconYOffset = 5;
        }
        else if (lines.size() == 4) {
            tooltipSize = 45;
            iconYOffset = 7;
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
        for (Component text : lines) {
            OverlayHelpers.drawString(guiGraphics, mc.font, text, xPlacement-xOffset, yPlacement, ModConfig.get().compass_text_colour);
            yPlacement += mc.font.lineHeight;
        }
    }

	private static Component xText(String x, Style textStyle) {
        var xLiteral = Component.literal(x).withStyle(textStyle);
		return Component.translatable("gui.immersiveoverlays.coordinates.x", xLiteral).withStyle(Style.EMPTY.withColor(ModConfig.get().compass_x_colour));
	}

    private static Component yText(String y, Style textStyle) {
        var yLiteral = Component.literal(y).withStyle(textStyle);
        return Component.translatable("gui.immersiveoverlays.coordinates.y", yLiteral).withStyle(Style.EMPTY.withColor(ModConfig.get().compass_y_colour));
    }

    private static Component zText(String z, Style textStyle) {
        var zLiteral = Component.literal(z).withStyle(textStyle);
        return Component.translatable("gui.immersiveoverlays.coordinates.z", zLiteral).withStyle(Style.EMPTY.withColor(ModConfig.get().compass_z_colour));
    }
}