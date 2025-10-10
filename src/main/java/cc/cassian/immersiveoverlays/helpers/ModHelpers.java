package cc.cassian.immersiveoverlays.helpers;

import cc.cassian.immersiveoverlays.Platform;
import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.TemperatureOverlay;
//? if fabric {
import net.fabricmc.fabric.api.tag.client.v1.ClientTags;
//? if >1.21 {
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
//?} else {
/*import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
 *///?}
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.biome.Biome;
//?}
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.biome.Biome;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Consumer;

import static cc.cassian.immersiveoverlays.ModClient.MOD_ID;

public class ModHelpers {
    // Automatically generate translation keys for config options.
    public static Component fieldName(Field field) {
        return Component.translatable("config."+MOD_ID+".config." + field.getName());
    }

    // Get the current value of a config field.
    @SuppressWarnings("unchecked")
    public static <T> T fieldGet(Object instance, Field field) {
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Set a config field.
    public static <T> Consumer<T> fieldSetter(Object instance, Field field) {
        return t -> {
            try {
                field.set(instance, t);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Automatically generate translation keys for config options.
     */
    public static Component fieldTooltip(Field field) {
        var key = "config.%s.config.%s.tooltip".formatted(MOD_ID, field.getName());
        if (I18n.exists("config.%s.config.%s.tooltip".formatted(MOD_ID, field.getName())))
            return Component.translatable(key);
        else return Component.empty();
    }

    /**
     * Used to check what colour the text should be.
     * Adapted from Item Descriptions.
     */
    public static char getColour(String colour) {
        int length = colour.length();
        if (length == 1) {
            return colour.charAt(0);
        }
        else {
            String replacedColour = colour.toLowerCase().replace(" ", "_");
            return switch (replacedColour) {
                case "black", "dark_blue", "dark_green", "dark_red", "dark_purple",
                     "blue", "green", "aqua", "red", "yellow", "white" ->
                        Objects.requireNonNullElse(ChatFormatting.getByName(colour), ChatFormatting.GRAY).getChar();
                case "pink", "light_purple" ->
                        Objects.requireNonNullElse(ChatFormatting.getByName("light_purple"), ChatFormatting.GRAY).getChar();
                case "dark_gray", "dark_grey" ->
                        Objects.requireNonNullElse(ChatFormatting.getByName("dark_gray"), ChatFormatting.GRAY).getChar();
                case "cyan", "dark_aqua" ->
                        Objects.requireNonNullElse(ChatFormatting.getByName("dark_aqua"), ChatFormatting.GRAY).getChar();
                case "orange", "gold", "dark_yellow" ->
                        Objects.requireNonNullElse(ChatFormatting.getByName("gold"), ChatFormatting.GRAY).getChar();
                default -> ChatFormatting.GRAY.getChar();
            };

        }
    }
    /**
     * Check if Cloth Config is installed and its configuration can be used.
     */
    public static boolean clothConfigInstalled() {
        return Platform.INSTANCE.isLoaded("cloth-config") || Platform.INSTANCE.isLoaded("cloth_config");
    }

    public static TemperatureOverlay.TemperaturePair getBiomeTemperatureFromTag(Holder<Biome> biomeHolder) {
        //? if fabric {
        if (ClientTags.isInWithLocalFallback(
                //? if >1.21
                ConventionalBiomeTags.IS_HOT
                //? if <1.21
                /*ConventionalBiomeTags.CLIMATE_HOT*/
                ,biomeHolder)) {
            return new TemperatureOverlay.TemperaturePair(Component.translatable("gui.c.temperature.hot"), ModConfig.get().temperature_hot_colour, "heat_4");
        } else if (ClientTags.isInWithLocalFallback(
                //? if >1.21
                ConventionalBiomeTags.IS_COLD
                //? if <1.21
                /*ConventionalBiomeTags.CLIMATE_COLD*/
                , biomeHolder)) {
            return new TemperatureOverlay.TemperaturePair(Component.translatable("gui.c.temperature.cold"), ModConfig.get().temperature_cold_colour, "heat_0");
        } else return new TemperatureOverlay.TemperaturePair(Component.translatable("gui.c.temperature.temperate"), ModConfig.get().temperature_temperate_colour, "heat_1");
        //?} else {
        /*return null;
        *///?}
    }
}
