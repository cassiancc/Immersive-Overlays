package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.compat.BreezyCompat;
import cc.cassian.immersiveoverlays.compat.ModCompat;
import cc.cassian.immersiveoverlays.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;

public class WindOverlay {
    public static boolean showWind = false;


    public static void renderGameOverlayEvent(
            GuiGraphics guiGraphics
            //? if >1.21 {
            /*, DeltaTracker deltaTracker
            *///?} else {
            , float deltaTracker
             //?}
    ) {
        if (!showWind || !ModConfig.get().wind_enable)
            return;
        var mc = Minecraft.getInstance();
        if (OverlayHelpers.shouldCancelRender(mc))
            return;

        if (mc.player == null) return;
        String direction = getDirection(mc.player);
        if (direction == null) return;

        int xOffset = 3;
        int tooltipSize = 21;
        int yPlacement = ModConfig.get().wind_vertical_position;
        int textYPlacement = yPlacement+2;

        int fontWidth = 40;

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().wind_horizontal_position_left);
        // render background
        OverlayHelpers.renderBackground(guiGraphics, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, tooltipSize, ModConfig.get().wind_horizontal_position_left);
        // render text
        OverlayHelpers.drawString(guiGraphics, mc.font, direction, xPlacement-xOffset+10, textYPlacement, ModConfig.get().wind_colour);
    }

    public static String getDirection(LocalPlayer player) {
        String direction;
        if (ModCompat.BREEZY && ModConfig.get().compat_breezy) {
            direction = BreezyCompat.getWindDirection(player);
        } else {
            direction = "UNSET";
        }
        return direction;
    }
}
