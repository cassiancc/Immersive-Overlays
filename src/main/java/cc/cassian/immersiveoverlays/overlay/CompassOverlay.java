package cc.cassian.immersiveoverlays.overlay;


import cc.cassian.immersiveoverlays.compat.ModCompat;
import cc.cassian.immersiveoverlays.compat.MapAtlasesCompat;
import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.helpers.ModHelpers;
import net.minecraft.client.Minecraft;
//? if >1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import net.minecraft.client.gui.GuiComponent;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
*///?}
import net.minecraft.core.BlockPos;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import static cc.cassian.immersiveoverlays.overlay.BiomeOverlay.getBiome;
import static cc.cassian.immersiveoverlays.overlay.BiomeOverlay.getBiomeSprite;

public class CompassOverlay {
    public static boolean showXZ = false;
    public static boolean showY = false;

    //? if >1.20 {
    public static void renderGameOverlayEvent(GuiGraphics poseStack) {
    //?} else {
        /*public static void renderGameOverlayEvent(PoseStack poseStack) {
     *///?}
        boolean showBiomeIcon = ModConfig.get().biome_enable && BiomeOverlay.showBiome && ModConfig.get().biome_reduced_info;
        if (!showXZ && !showY)
            return;
        if (!ModConfig.get().compass_enable)
            return;

        var mc = Minecraft.getInstance();
        if (OverlayHelpers.debug(mc))
            return;

        ArrayList<String> coords = new ArrayList<>();

        BlockPos pos;
        if (mc.player != null) pos = mc.player.blockPosition();
        else return;
        if (ModConfig.get().compass_hide_when_similar_mods_present && ModCompat.MAP_ATLASES && MapAtlasesCompat.showingCoords(mc.player))
            return;

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
        int iconXOffset = 0;
        int iconYOffset = 0;
        if (showBiomeIcon) {
            iconXOffset += 20;
        }
        int fontWidth = mc.font.width(StringUtils.repeat("a", width+2))+iconXOffset;

        if (!(ClockOverlay.showTime || ClockOverlay.showWeather) || !ModConfig.get().clock_enable) {
            yPlacement = yPlacement - 15;
        }
        if (ClockOverlay.showWeather) {
            yPlacement = yPlacement + 9;
        }
        if (!BiomeOverlay.showBiome || ModConfig.get().biome_reduced_info) {
            yPlacement = yPlacement - 20;
        }
        if (OverlayHelpers.playerHasPotions(mc.player)) {
            yPlacement += OverlayHelpers.moveBy(mc.player);
        }

        int textureOffset = 7;  // only depth gauge
        int tooltipSize = 16;  // only depth gauge
        if (showXZ) {
            coords.add("§%sX:§r %s".formatted(ModHelpers.getColour(ModConfig.get().compass_x_colour), x));
            if (showY) { // depth gauge and compass
                coords.add("§%sY:§r %s".formatted(ModHelpers.getColour(ModConfig.get().compass_y_colour), y));
                textureOffset = 51;
                tooltipSize = 35;
                iconYOffset = 5;
            } else { // only compass
                textureOffset = 25;
                tooltipSize = 25;
                iconYOffset = 3;
            }
            coords.add("§%sZ:§r %s".formatted(ModHelpers.getColour(ModConfig.get().compass_z_colour), z));
        } else if (showY) {
            coords.add("§%sY:§r %s".formatted(ModHelpers.getColour(ModConfig.get().compass_y_colour), y));
            if (showBiomeIcon) {
                textureOffset = 25;
                tooltipSize = 25;
                iconYOffset = 3;
            }
        }

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().compass_horizontal_position_left);
        //? if <1.20
        /*RenderSystem.setShaderTexture(0, OverlayHelpers.TEXTURE);*/
        // render background
        OverlayHelpers.renderBackground(poseStack, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, textureOffset, tooltipSize, ModConfig.get().compass_horizontal_position_left);
        if (showBiomeIcon) {
            var sprite = getBiomeSprite(getBiome(mc.player), true);

            //? if >1.21.2 {
            /*poseStack.blit(RenderType::guiTextured, sprite,
             *///?} else if >1.20 {
            poseStack.blit(sprite,
                    //?} else {

        /*RenderSystem.setShaderTexture(0, sprite);
           GuiComponent.blit(poseStack,
         *///?}
                    xPlacement-xOffset-1, yPlacement+iconYOffset,
                    0, 0,
                    0, 16, 16,
                    16, 16);
        }
        // render text
        for (String text : coords) {
            //? if >1.20 {
            poseStack.drawString(mc.font, text, xPlacement-xOffset+iconXOffset, yPlacement, 14737632);
            //?} else {
            /*GuiComponent.drawString(poseStack, mc.font, text, xPlacement-xOffset, yPlacement, 14737632);
             *///?}
            yPlacement += mc.font.lineHeight;
        }
    }
}