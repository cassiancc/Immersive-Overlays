package cc.cassian.immersiveoverlays.config;


import cc.cassian.immersiveoverlays.helpers.TextHelpers;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

import static cc.cassian.immersiveoverlays.helpers.ModHelpers.*;


public class ClothConfigFactory {

    private static final ModConfig DEFAULT_VALUES = new ModConfig();

    private static ConfigCategory createCategory(String section, ConfigBuilder builder) {
        if (section == null) {
            section = "";
        } else {
            section += "_";
        }
        return builder.getOrCreateCategory(TextHelpers.translatable("config.immersiveoverlays.%stitle".formatted(section)));
    }

    private static boolean is(Field field, String name) {
        return field.getName().toLowerCase(Locale.ROOT).contains(name);
    }

    public static Screen create(Screen parent) {
        final var builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(TextHelpers.translatable("config.immersiveoverlays.title"));

        final var entryBuilder = builder.entryBuilder();
        final var configInstance = ModConfig.get();
        final var generalCategory = createCategory(null, builder);
        final var compassCategory = createCategory("compass", builder);
        final var clockCategory = createCategory("clock", builder);
        final var biomeCategory = createCategory("biome", builder);
        final var temperatureCategory = createCategory("temperature", builder);
        final var speedCategory = createCategory("speed", builder);
        final var windCategory = createCategory("wind", builder);
        final var compatCategory = createCategory("compat", builder);

        for (var field : ModConfig.class.getFields()) {
            ConfigCategory category;
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
                category.addEntry(entryBuilder.startBooleanToggle(fieldName(field), fieldGet(configInstance, field))
                        .setSaveConsumer(fieldSetter(configInstance, field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((boolean) fieldGet(DEFAULT_VALUES, field)).build());

            }
            else if (field.getType() == String.class) {
                category.addEntry(entryBuilder.startStrField(fieldName(field), fieldGet(configInstance, field))
                        .setSaveConsumer(fieldSetter(configInstance, field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((String) fieldGet(DEFAULT_VALUES, field)).build());
            }
            else if (field.getType() == int.class && field.getName().contains("colour")) {
                category.addEntry(entryBuilder.startColorField(fieldName(field), (int) fieldGet(configInstance, field))
                        .setSaveConsumer(fieldSetter(configInstance, field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((int) fieldGet(DEFAULT_VALUES, field)).build());
            }
            else if (field.getType() == int.class) {
                category.addEntry(entryBuilder.startIntField(fieldName(field), fieldGet(configInstance, field))
                        .setSaveConsumer(fieldSetter(configInstance, field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((int) fieldGet(DEFAULT_VALUES, field)).build());
            }
            else if (field.getType() == List.class) {
                category.addEntry(entryBuilder.startStrList(fieldName(field), fieldGet(configInstance, field))
                        .setSaveConsumer(fieldSetter(configInstance, field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((List<String>) fieldGet(DEFAULT_VALUES, field)).build());
            }
        }
        builder.setSavingRunnable(ModConfig::save);
        return builder.build();
    }
}