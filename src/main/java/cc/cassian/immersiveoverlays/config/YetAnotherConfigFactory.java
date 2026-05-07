package cc.cassian.immersiveoverlays.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Locale;

import static cc.cassian.immersiveoverlays.helpers.ModHelpers.fieldGet;
import static cc.cassian.immersiveoverlays.helpers.ModHelpers.fieldName;
import static cc.cassian.immersiveoverlays.helpers.ModHelpers.fieldTooltip;

public class YetAnotherConfigFactory {

    private static ConfigCategory.Builder createCategory(String section, LinkedHashMap<String, ConfigCategory.Builder> categories) {
        if (categories.containsKey(section)) {
            return categories.get(section);
        } else {
            String sectionKey = section;
            if (section == null) {
                sectionKey = "";
            } else {
                sectionKey += "_";
            }
            ConfigCategory.Builder category = ConfigCategory.createBuilder().name(Component.translatable("config.immersiveoverlays.%stitle".formatted(sectionKey)));
            categories.put(section, category);
            return category;
        }
    }

    private static boolean is(Field field, String name) {
        return field.getName().toLowerCase(Locale.ROOT).contains(name);
    }

    private static final ModConfig DEFAULT_VALUES = new ModConfig();

    public static Screen create(Screen parent) {
        final YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.immersiveoverlays.title"));
        LinkedHashMap<String, ConfigCategory.Builder> categories = new LinkedHashMap<>();


        final var configInstance = ModConfig.get();
        final var generalCategory = createCategory(null, categories);
        final var compassCategory = createCategory("compass", categories);
        final var clockCategory = createCategory("clock", categories);
        final var biomeCategory = createCategory("biome", categories);
        final var temperatureCategory = createCategory("temperature", categories);
        final var speedCategory = createCategory("speed", categories);
        final var windCategory = createCategory("wind", categories);
        final var compatCategory = createCategory("compat", categories);

        for (var field : ModConfig.class.getFields()) {
            ConfigCategory.Builder category;
            if (is(field, "compass")) category = compassCategory;
            else if (is(field,"clock")) category = clockCategory;
            else if (is(field,"biome")) category = biomeCategory;
            else if (is(field,"season")) category = clockCategory;
            else if (is(field,"temperature")) category = temperatureCategory;
            else if (is(field,"speed")) category = speedCategory;
            else if (is(field,"wind")) category = windCategory;
            else if (is(field,"compat")) category = compatCategory;
            else category = generalCategory;
            if (is(field, "version")) {}
            else if (field.getType() == boolean.class) {
                category.option(Option.<Boolean>createBuilder()
                        .name(fieldName(field))
                        .description(OptionDescription.of(fieldTooltip(field)))
                        .binding(
                                fieldGet(DEFAULT_VALUES, field),
                                ()-> fieldGet(configInstance, field),
                                (value)->fieldSetter(configInstance, field, value)
                        )
                        .controller(option -> BooleanControllerBuilder.create(option).coloured(true))
                        .build());

            }
            else if (field.getType() == String.class) {
                category.option(Option.<String>createBuilder()
                        .name(fieldName(field))
                        .description(OptionDescription.of(fieldTooltip(field)))
                        .binding(
                                fieldGet(DEFAULT_VALUES, field),
                                ()-> fieldGet(configInstance, field),
                                (value)->fieldSetter(configInstance, field, value)
                        )
                        .controller(StringControllerBuilder::create)
                        .build());
            }
            else if (field.getType() == int.class && field.getName().contains("colour")) {
                category.option(Option.<Color>createBuilder()
                        .name(fieldName(field))
                        .description(OptionDescription.of(fieldTooltip(field)))
                        .binding(
                                new Color(fieldGet(DEFAULT_VALUES, field)),
                                ()-> new Color(fieldGet(configInstance, field)) ,
                                (value)-> colour(configInstance, field, value)
                        )
                        .controller(option -> ColorControllerBuilder.create(option).allowAlpha(false))
                        .build());
            }
            else if (field.getType() == int.class) {
                category.option(Option.<Integer>createBuilder()
                        .name(fieldName(field))
                        .description(OptionDescription.of(fieldTooltip(field)))
                        .binding(
                                fieldGet(DEFAULT_VALUES, field),
                                ()-> fieldGet(configInstance, field),
                                (value)->fieldSetter(configInstance, field, value)
                        )
                        .controller(IntegerFieldControllerBuilder::create)
                        .build());
            }
            else if (field.getType() == java.util.List.class) {
                category.group(ListOption.<String>createBuilder()
                        .name(fieldName(field))
                        .description(OptionDescription.of(fieldTooltip(field)))
                        .binding(
                                fieldGet(DEFAULT_VALUES, field),
                                ()-> fieldGet(configInstance, field),
                                (value)->fieldSetter(configInstance, field, value)
                )
                .controller(StringControllerBuilder::create)
                .initial("")
                .build());
            }
        }
        for (ConfigCategory.Builder s : categories.values()) {
            builder.category(s.build());
        }
        builder.save(ModConfig::save);
        return builder.build().generateScreen(parent);
    }

    // Set a config field.
    public static void colour(ModConfig instance, Field field, Color value) {
        fieldSetter(instance, field, value.getRGB());
    }

    // Set a config field.
    public static <T> void fieldSetter(ModConfig instance, Field field, T t) {
        try {
            field.set(instance, t);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}