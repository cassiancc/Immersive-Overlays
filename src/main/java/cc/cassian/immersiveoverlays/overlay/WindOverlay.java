package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.compat.BreezyCompat;
import cc.cassian.immersiveoverlays.compat.ModCompat;
import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.helpers.TextHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

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
        WindOverlay.WindPair wind = getWind(mc.player);
        if (wind == null) return;

        int iconOffset = 0;
        if (ModConfig.get().wind_icons) {
            iconOffset = 17;
        }
        int xOffset = 3;
        int tooltipSize = 21;
        int yPlacement = ModConfig.get().wind_vertical_position;
        int textYPlacement = yPlacement+2;

        int fontWidth = mc.font.width(wind.direction) + iconOffset;

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().wind_horizontal_position_left);
        // render background
        OverlayHelpers.renderBackground(guiGraphics, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, tooltipSize, ModConfig.get().wind_horizontal_position_left);
        // render text
        OverlayHelpers.drawString(guiGraphics, mc.font, wind.direction, xPlacement-xOffset+iconOffset, textYPlacement, ModConfig.get().wind_colour);
        // render sprite
        if (showWind && ModConfig.get().wind_icons) {
            OverlayHelpers.blitSprite(guiGraphics, ModClient.locate("textures/gui/" + wind.texture() + ".png"), xPlacement-xOffset-1, textYPlacement-3);
        }
    }

    public static WindPair getWind(LocalPlayer player) {
        Component direction;
        String sprite;
        if (ModCompat.BREEZY && ModConfig.get().compat_breezy) {
            direction = BreezyCompat.getWindDirection(player);
            sprite = BreezyCompat.getSprite(player);
        } else {
            direction = TextHelpers.translatable("UNSET");
            sprite = "unknown";
        }
        return new WindPair(direction, sprite);
    }

    public record WindPair(Component direction, String texture) {

    }
}
