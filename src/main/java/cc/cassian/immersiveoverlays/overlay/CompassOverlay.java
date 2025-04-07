package cc.cassian.immersiveoverlays.overlay;


import cc.cassian.immersiveoverlays.ModCompat;
import cc.cassian.immersiveoverlays.compat.MapAtlasesCompat;
import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.helpers.ModHelpers;
import net.minecraft.client.Minecraft;
//? if >1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import net.minecraft.client.gui.GuiComponent;
*///?}
import net.minecraft.core.BlockPos;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class CompassOverlay {
    public static boolean hasCompass = false;
    public static boolean hasDepthGauge = false;

    //? if >1.20 {
    public static void renderGameOverlayEvent(GuiGraphics poseStack) {
    //?} else {
        /*public static void renderGameOverlayEvent(PoseStack poseStack) {
     *///?}
        if (!hasCompass && !hasDepthGauge)
            return;
        if (!ModConfig.get().compass_enable)
            return;
        if (ModConfig.get().compass_hide_when_similar_mods_present && ModCompat.MAP_ATLASES && MapAtlasesCompat.showingCoords())
            return;
        var mc = Minecraft.getInstance();
        if (OverlayHelpers.debug(mc))
            return;

        ArrayList<String> coords = new ArrayList<>();

        BlockPos pos;
        if (mc.player != null) pos = mc.player.blockPosition();
        else return;

        String x = String.format("%d", pos.getX());
        String y = String.format("%d", pos.getY());
        String z = String.format("%d", pos.getZ());
        var width = Integer.max(x.length(), z.length());
        width = Integer.max(width, y.length());
        x = StringUtils.leftPad(x, width);
        y = StringUtils.leftPad(y, width);
        z = StringUtils.leftPad(z, width);
        int xOffset = 3;
        int yPlacement = ModConfig.get().compass_vertical_position;
        int fontWidth = mc.font.width(StringUtils.repeat("a", width+2));

        if (hasCompass) {
            if (!ModConfig.get().compass_require_y_item) {
                hasDepthGauge = true;
            }
            coords.add("§%sX:§f %s".formatted(ModHelpers.getColour(ModConfig.get().compass_x_colour), x));
            if (hasDepthGauge) {
                coords.add("§%sY:§f %s".formatted(ModHelpers.getColour(ModConfig.get().compass_y_colour), y));
            }
            coords.add("§%sZ:§f %s".formatted(ModHelpers.getColour(ModConfig.get().compass_z_colour), z));
        }
        else if (hasDepthGauge) {
            coords.add("§%sY:§f %s".formatted(ModHelpers.getColour(ModConfig.get().compass_y_colour), y));
        }
        if (!ClockOverlay.hasClock || !ModConfig.get().clock_enable) {
            yPlacement = yPlacement - 15;
        }
        if (ClockOverlay.hasBarometer) {
            yPlacement = yPlacement + 9;
        }
        if (OverlayHelpers.playerHasPotions(mc.player)) {
            yPlacement = yPlacement + 16;
        }

        int textureOffset = 7;  // only depth gauge
        int tooltipSize = 16;  // only depth gauge
        if (hasCompass & hasDepthGauge) { // depth gauge and compass
            textureOffset = 51;
            tooltipSize = 33;
        }
        else if (hasCompass) { // only compass
            textureOffset = 27;
            tooltipSize = 23;
        }

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth);
        //? if <1.20
        /*RenderSystem.setShaderTexture(0, OverlayHelpers.TEXTURE);*/
        // render background
        OverlayHelpers.renderBackground(poseStack, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, textureOffset, tooltipSize);
        // render text
        for (String text : coords) {
            //? if >1.20 {
            poseStack.drawString(mc.font, text, xPlacement-xOffset, yPlacement, 14737632);
            //?} else {
            /*GuiComponent.drawString(poseStack, mc.font, text, xPlacement-xOffset, yPlacement, 14737632);
             *///?}
            yPlacement += mc.font.lineHeight;
        }
    }
}