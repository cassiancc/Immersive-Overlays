package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.compat.ModCompat;
import cc.cassian.immersiveoverlays.compat.OreganizedCompat;
import cc.cassian.immersiveoverlays.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import oshi.util.tuples.Pair;
//? if >1.20 {

//?} else {
//?}

public class TemperatureOverlay {
    public static boolean showTemperature = false;


    //? if >1.20 {
    public static void renderGameOverlayEvent(GuiGraphics poseStack) {
    //?} else {
        /*public static void renderGameOverlayEvent(PoseStack poseStack) {
     *///?}
        if (!showTemperature)
            return;
        if (!ModConfig.get().temperature_enable)
            return;
        var mc = Minecraft.getInstance();

        Pair<Component, Integer> temperature = getTemperature(mc.player);

        int xOffset = 3;
        // The amount of offset needed to display the biome icons.
        int iconOffset = 0;
        int textureOffset = 111;
        int tooltipSize = 21;
        int yPlacement = ModConfig.get().temperature_vertical_position;
        int textYPlacement = yPlacement+2;

        int fontWidth = mc.font.width(temperature.getA())+iconOffset;

        if (mc.player == null) return;

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().temperature_horizontal_position_left);
        //? if <1.20
        /*RenderSystem.setShaderTexture(0, OverlayHelpers.TEXTURE);*/
        OverlayHelpers.renderBackground(poseStack, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, textureOffset, tooltipSize, ModConfig.get().temperature_horizontal_position_left);
        // render text
        //? if >1.20 {
        poseStack.drawString(mc.font, temperature.getA(), xPlacement-xOffset+iconOffset, textYPlacement, temperature.getB());
        //?} else {
        /*GuiComponent.drawString(poseStack, mc.font, temperature, xPlacement-xOffset+iconOffset, textYPlacement, 14737632);
         *///?}
    }

    public static Pair<Component, Integer> getTemperature(LocalPlayer player) {
        if (ModCompat.OREGANIZED && ModConfig.get().compat_oreganized_temperature && ModClient.loader.equals("forge")) {
            int temperature =  OreganizedCompat.getAmbientTemperatureFromThermometer(player);
            return new Pair<>(Component.translatable("tooltip.oreganized.heat_"+temperature), OreganizedCompat.getTemperatureColourFromThermometer(temperature));
        }
        //? if >1.20 {
        var level = player.level();
        //?} else {
        /*var level = player.level;
         *///?}
        return new Pair<>(Component.translatable(getBiomeTemperature(level.getBiome(player.blockPosition()).value().getBaseTemperature())), -1);
    }

    public static String getBiomeTemperature(float temperature) {
        if (temperature >= 2) {
            return I18n.get("tag.worldgen.biome.c.is_hot");
        } else if (temperature < 0) {
            return I18n.get("tag.worldgen.biome.c.is_cold");
        } else return I18n.get("tag.worldgen.biome.c.is_temperate");
    }
}
