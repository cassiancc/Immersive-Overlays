package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.compat.*;
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

        if (mc.player == null) return;
        TemperaturePair temperature = getTemperature(mc.player);

        int xOffset = 3;
        // The amount of offset needed to display the biome icons.
        int iconOffset = 0;
        if (ModConfig.get().temperature_icons) {
            iconOffset = 17;
        }
        int tooltipSize = 21;
        int yPlacement = ModConfig.get().temperature_vertical_position;
        int textYPlacement = yPlacement+2;

        int fontWidth = mc.font.width(temperature.component)+iconOffset;

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().temperature_horizontal_position_left);
        // render background
        OverlayHelpers.renderBackground(poseStack, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, tooltipSize, ModConfig.get().temperature_horizontal_position_left);
        // render text
        OverlayHelpers.drawString(poseStack, mc.font, temperature.component(), xPlacement-xOffset+iconOffset, textYPlacement, temperature.color());
        // render sprite
        if (showTemperature && ModConfig.get().temperature_icons) {
            OverlayHelpers.blitSprite(poseStack, ModClient.locate("textures/gui/" + temperature.texture() + ".png"), xPlacement-xOffset-1, textYPlacement-3);
        }
    }

    public static TemperaturePair getTemperature(LocalPlayer player) {
        //? if =1.20.1 || =1.21.1 {
        if (ModCompat.OREGANIZED && ModConfig.get().compat_oreganized_temperature && ModClient.loader.contains("forge")) {
            int temperature =  OreganizedCompat.getAmbientTemperatureFromThermometer(player);
            return new TemperaturePair(Component.translatable("tooltip.oreganized.heat_"+temperature), OreganizedCompat.getTemperatureColourFromThermometer(temperature), "heat_"+temperature);
        }
        //?}
        if (ModCompat.TOUGH_AS_NAILS && ModConfig.get().compat_tough_as_nails_temperature) {
            if (ToughAsNailsCompat.isTemperatureEnabled()) {
                String temperature = ToughAsNailsCompat.getAmbientTemperature(player);
                var sprite = switch (temperature) {
                    case "ICY" -> "heat_0";
                    case "COLD" -> "heat_1";
                    case "WARM" -> "heat_4";
                    case "HOT" -> "heat_6";
                    default -> "heat_2";
                };
                return new TemperaturePair(Component.literal(WordUtils.capitalizeFully(temperature)), -1, sprite);
            }
        }
        if (ModCompat.COLD_SWEAT && ModConfig.get().compat_cold_sweat_temperature) {
            return ColdSweatCompat.getTemperaturePair(player);
        }
        if (ModCompat.LEGENDARY_SURVIVAL_OVERHAUL && ModConfig.get().compat_legendary_survival_overhaul_temperature) {
            String temperature = LegendarySurvivalOverhaulCompat.getAmbientTemperature(player);
            var sprite = switch (temperature) {
                case "FROSTBITE" -> "heat_0";
                case "COLD" -> "heat_1";
                case "HOT" -> "heat_4";
                case "HEAT_STROKE" -> "heat_6";
                default -> "heat_2";
            };
            return new TemperaturePair(Component.literal(WordUtils.capitalizeFully(temperature)), -1, sprite);
        }
        var level = player.level
        //? if >1.20 {
        ()
        //?}
        ;
        return getBiomeTemperature(level.getBiome(player.blockPosition()).value());
    }

    public record TemperaturePair(Component component, Integer color, String texture) {

    }

    public static TemperaturePair getBiomeTemperature(Biome biome) {
        return getBiomeTemperature(biome.getBaseTemperature());
    }

    public static TemperaturePair getBiomeTemperature(float temperature) {
        if (temperature >= 2) {
            return new TemperaturePair(Component.translatable("gui.c.temperature.hot"), ModConfig.get().temperature_hot_colour, "heat_4");
        } else if (temperature <= 0.2) {
            return new TemperaturePair(Component.translatable("gui.c.temperature.cold"), ModConfig.get().temperature_cold_colour, "heat_0");
        } else return new TemperaturePair(Component.translatable("gui.c.temperature.temperate"), ModConfig.get().temperature_temperate_colour, "heat_1");
    }
}
