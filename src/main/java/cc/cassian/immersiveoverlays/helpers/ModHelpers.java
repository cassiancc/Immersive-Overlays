package cc.cassian.immersiveoverlays.helpers;

import cc.cassian.immersiveoverlays.config.ModConfig;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Consumer;

import static cc.cassian.immersiveoverlays.ModClient.MOD_ID;

public class ModHelpers {
    // Automatically generate translation keys for config options.
    public static Component fieldName(Field field) {
        return Component.translatable("config.%s.%s".formatted(MOD_ID, field.getName()));
    }

    // Get the current value of a config field.
    @SuppressWarnings("unchecked")
    public static <T> T fieldGet(ModConfig instance, Field field) {
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Set a config field.
    public static <T> Consumer<T> fieldSetter(ModConfig instance, Field field) {
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
        var key = "config.%s.%s.tooltip".formatted(MOD_ID, field.getName());
        if (exists("config.%s.%s.tooltip".formatted(MOD_ID, field.getName())))
            return Component.translatable(key);
        else return Component.empty();
    }

    public static boolean exists(String formatted) {
        return Language.getInstance().has(formatted);
    }
}
