package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.Platform;
import cc.cassian.immersiveoverlays.compat.*;
import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.helpers.TextHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.lang3.text.WordUtils;

public class TemperatureOverlay {
    public static boolean showTemperature = false;


    public static void renderGameOverlayEvent(GuiGraphics guiGraphics
            //? if >1.21 {
            /*, net.minecraft.client.DeltaTracker deltaTracker
            *///?} else {
            , float deltaTracker
             //?}
    ) {
        if (!showTemperature || !ModConfig.get().temperature_enable)
            return;
        var mc = Minecraft.getInstance();
        if (OverlayHelpers.shouldCancelRender(mc))
            return;

        if (mc.player == null) return;
        TemperaturePair temperature = getTemperature(mc.player, guiGraphics);

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
        OverlayHelpers.renderBackground(guiGraphics, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, tooltipSize, ModConfig.get().temperature_horizontal_position_left);
        // render text
        OverlayHelpers.drawString(guiGraphics, mc.font, temperature.component(), xPlacement-xOffset+iconOffset, textYPlacement, temperature.color());
        // render sprite
        if (showTemperature && ModConfig.get().temperature_icons) {
            OverlayHelpers.blitSprite(guiGraphics, ModClient.locate("textures/gui/" + temperature.texture() + ".png"), xPlacement-xOffset-1, textYPlacement-3);
        }
    }

    public static TemperaturePair getTemperature(LocalPlayer player, GuiGraphics guiGraphics) {
        if (ModCompat.TOUGH_AS_NAILS && ModConfig.get().compat_tough_as_nails_temperature) {
            if (ToughAsNailsCompat.isTemperatureEnabled()) {
                if (Platform.showDevInfo()) {
                    OverlayHelpers.drawString(guiGraphics, Minecraft.getInstance().font, Component.literal("Using Tough as Nails for Temperature!"), 0, 0, -1);
                }
                String temperature = ToughAsNailsCompat.getAmbientTemperature(player);
                var sprite = switch (temperature) {
                    case "ICY" -> "heat_0";
                    case "COLD" -> "heat_1";
                    case "WARM" -> "heat_4";
                    case "HOT" -> "heat_6";
                    default -> "heat_2";
                };
                return new TemperaturePair(TextHelpers.literal(WordUtils.capitalizeFully(temperature)), -1, sprite);
            }
        }
        //? if forge || neoforge {
        if (ModCompat.COLD_SWEAT && ModConfig.get().compat_cold_sweat_temperature) {
            if (Platform.showDevInfo()) {
                OverlayHelpers.drawString(guiGraphics, Minecraft.getInstance().font, Component.literal("Using Cold Sweat for Temperature!"), 0, 0, -1);
            }
            return ColdSweatCompat.getTemperaturePair(player);
        }
        //?}
        //? if forge || neoforge {
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
        //?}
        //? if fabric || 1.21.1 {
        /*if (ModCompat.THERMOO && ModConfig.get().compat_thermoo_temperature) {
            TemperaturePair temperaturePair = ThermooCompat.getTemperaturePair(player);
            if (temperaturePair != null)
                return temperaturePair;
        }
        *///?}
        //? if (forge) || (neoforge && =1.21.1) {
        if (ModCompat.OREGANIZED && ModConfig.get().compat_oreganized_temperature) {
            int temperature =  OreganizedCompat.getAmbientTemperatureFromThermometer(player);
            if (Platform.showDevInfo()) {
                OverlayHelpers.drawString(guiGraphics, Minecraft.getInstance().font, Component.literal("Oreganized: " + temperature), 0, 0, -1);
            }
            return new TemperaturePair(Component.translatable("tooltip.oreganized.heat_"+temperature), OreganizedCompat.getTemperatureColourFromThermometer(temperature), "heat_"+temperature);
        }
        //?}
        var level = player.level();
        return getBiomeTemperature(level.getBiome(player.blockPosition()));
    }

    public record TemperaturePair(Component component, Integer color, String texture) {

    }

    public static TemperaturePair getBiomeTemperature(Holder<Biome> biome) {
        //? if >1.19 {
        if (ModCompat.FABRIC_API)
            return FabricCompat.getBiomeTemperatureFromTag(biome);
        //?}
        return getBiomeTemperature(biome.value().getBaseTemperature());
    }

    public static TemperaturePair getBiomeTemperature(float temperature) {
        if (temperature >= 2) {
            return new TemperaturePair(TextHelpers.translatable("gui.c.temperature.hot"), ModConfig.get().temperature_hot_colour, "heat_4");
        } else if (temperature <= 0.2) {
            return new TemperaturePair(TextHelpers.translatable("gui.c.temperature.cold"), ModConfig.get().temperature_cold_colour, "heat_0");
        } else return new TemperaturePair(TextHelpers.translatable("gui.c.temperature.temperate"), ModConfig.get().temperature_temperate_colour, "heat_1");
    }
}
