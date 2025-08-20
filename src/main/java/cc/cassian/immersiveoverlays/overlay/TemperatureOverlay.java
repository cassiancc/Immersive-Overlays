package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.compat.LegendarySurvivalOverhaulCompat;
import cc.cassian.immersiveoverlays.compat.ModCompat;
import cc.cassian.immersiveoverlays.compat.OreganizedCompat;
import cc.cassian.immersiveoverlays.compat.ToughAsNailsCompat;
import cc.cassian.immersiveoverlays.config.ModConfig;
import net.minecraft.client.Minecraft;
//? if >1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import com.mojang.blaze3d.vertex.PoseStack;
*///?}
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.lang3.text.WordUtils;
import oshi.util.tuples.Pair;

public class TemperatureOverlay {
    public static boolean showTemperature = false;


    //? if >1.20 {
    public static void renderGameOverlayEvent(GuiGraphics poseStack) {
    //?} else {
        /*public static void renderGameOverlayEvent(PoseStack poseStack) {
     *///?}
        if (!showTemperature || !ModConfig.get().temperature_enable)
            return;
        var mc = Minecraft.getInstance();
        if (OverlayHelpers.shouldCancelRender(mc))
            return;

        Pair<Component, Integer> temperature = getTemperature(mc.player);

        int xOffset = 3;
        // The amount of offset needed to display the biome icons.
        int iconOffset = 0;
        if (ModConfig.get().temperature_icons) {
            iconOffset = 17;
        }
        int tooltipSize = 21;
        int yPlacement = ModConfig.get().temperature_vertical_position;
        int textYPlacement = yPlacement+2;

        int fontWidth = mc.font.width(temperature.getA())+iconOffset;

        if (mc.player == null) return;

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().temperature_horizontal_position_left);
        // render background
        OverlayHelpers.renderBackground(poseStack, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, tooltipSize, ModConfig.get().temperature_horizontal_position_left);
        // render text
        OverlayHelpers.drawString(poseStack, mc.font, temperature.getA(), xPlacement-xOffset+iconOffset, textYPlacement, temperature.getB());
        // render sprite
        if (showTemperature && ModConfig.get().temperature_icons && temperature.getA().getContents() instanceof TranslatableContents translatableContents) {
            ResourceLocation sprite;
            if (ModCompat.OREGANIZED && ModConfig.get().compat_oreganized_temperature) {
                sprite = ModClient.locate("textures/gui/"+translatableContents.getKey().replace("tooltip.oreganized.", "") + ".png");
            } else {
                var key = translatableContents.getKey().replace("gui.c.temperature.", "");
                if (key.equals("hot")) {
                    sprite = ModClient.locate("textures/gui/heat_4.png");
                } else if (key.equals("cold")) {
                    sprite = ModClient.locate("textures/gui/heat_0.png");
                } else {
                    sprite = ModClient.locate("textures/gui/heat_1.png");
                }
            }
            OverlayHelpers.blitSprite(poseStack, sprite, xPlacement-xOffset-1, textYPlacement-3);
        }
    }

    public static Pair<Component, Integer> getTemperature(LocalPlayer player) {
        //? if =1.20.1 || =1.21.1 {
        if (ModCompat.OREGANIZED && ModConfig.get().compat_oreganized_temperature && ModClient.loader.contains("forge")) {
            int temperature =  OreganizedCompat.getAmbientTemperatureFromThermometer(player);
            return new Pair<>(Component.translatable("tooltip.oreganized.heat_"+temperature), OreganizedCompat.getTemperatureColourFromThermometer(temperature));
        }
        //?}
        if (ModCompat.TOUGH_AS_NAILS && ModConfig.get().compat_tough_as_nails_temperature) {
            if (ToughAsNailsCompat.isTemperatureEnabled()) {
                String temperature = ToughAsNailsCompat.getAmbientTemperature(player);
                return new Pair<>(Component.literal(WordUtils.capitalizeFully(temperature)), -1);
            }
        }
        if (ModCompat.LEGENDARY_SURVIVAL_OVERHAUL && ModConfig.get().compat_legendary_survival_overhaul_temperature) {
            String temperature = LegendarySurvivalOverhaulCompat.getAmbientTemperature(player);
            return new Pair<>(Component.literal(WordUtils.capitalizeFully(temperature)), -1);
        }
        //? if >1.20 {
        var level = player.level();
        //?} else {
        /*var level = player.level;
         *///?}
        return getBiomeTemperature(level.getBiome(player.blockPosition()).value());
    }

    public static Pair<Component, Integer> getBiomeTemperature(Biome biome) {
        return getBiomeTemperature(biome.getBaseTemperature());
    }

    public static Pair<Component, Integer> getBiomeTemperature(float temperature) {
        if (temperature >= 2) {
            return new Pair<>(Component.translatable("gui.c.temperature.hot"), ModConfig.get().temperature_hot_colour);
        } else if (temperature <= 0.2) {
            return new Pair<>(Component.translatable("gui.c.temperature.cold"), ModConfig.get().temperature_cold_colour);
        } else return new Pair<>(Component.translatable("gui.c.temperature.temperate"), ModConfig.get().temperature_temperate_colour);
    }
}
