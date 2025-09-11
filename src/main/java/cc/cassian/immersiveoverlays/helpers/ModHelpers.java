package cc.cassian.immersiveoverlays.helpers;

import cc.cassian.immersiveoverlays.overlay.TemperatureOverlay;
import dev.architectury.injectables.annotations.ExpectPlatform;
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
     * Check if a mod is loaded
     */
    @ExpectPlatform
    public static boolean isLoaded(String mod) {
        throw new AssertionError();
    }

    /**
     * Check if Cloth Config is installed and its configuration can be used.
     */
    @ExpectPlatform
    public static boolean clothConfigInstalled() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static TemperatureOverlay.TemperaturePair getBiomeTemperatureFromTag(Holder<Biome> biome) {
        throw new AssertionError();
    }
}
