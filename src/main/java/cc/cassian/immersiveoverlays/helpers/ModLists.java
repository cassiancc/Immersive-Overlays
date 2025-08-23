package cc.cassian.immersiveoverlays.helpers;

import cc.cassian.immersiveoverlays.config.ModConfig;
//? if >1.20 {
import net.minecraft.core.registries.BuiltInRegistries;
//?} else {
/*import net.minecraft.core.Registry;
 *///?}
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Optional;

public class ModLists {
    public static ArrayList<Item> compass_x_items = new ArrayList<>();
    public static ArrayList<Item> compass_y_items = new ArrayList<>();
    public static ArrayList<Item> compass_z_items = new ArrayList<>();
    public static ArrayList<Item> clock_items = new ArrayList<>();
    public static ArrayList<Item> weather_items = new ArrayList<>();
    public static ArrayList<Item> biome_items = new ArrayList<>();
    public static ArrayList<Item> season_items = new ArrayList<>();
    public static ArrayList<Item> temperature_items = new ArrayList<>();
    public static ArrayList<Item> speed_items = new ArrayList<>();


    public static void loadLists() {
        //? if >1.20 {
        var registry = BuiltInRegistries.ITEM;
        //?} else {
        /*var registry = Registry.ITEM;
         *///?}
        compass_x_items = new ArrayList<>();
        clock_items = new ArrayList<>();
        biome_items = new ArrayList<>();
        season_items = new ArrayList<>();
        weather_items = new ArrayList<>();
        compass_y_items = new ArrayList<>();
        temperature_items = new ArrayList<>();
        speed_items = new ArrayList<>();
        for (String compassItem : ModConfig.get().compass_x_items) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(compassItem));
            item.ifPresent(value -> compass_x_items.add(value));
        }
        for (String depthItem : ModConfig.get().compass_y_items) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(depthItem));
            item.ifPresent(value -> compass_y_items.add(value));
        }
        for (String depthItem : ModConfig.get().compass_z_items) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(depthItem));
            item.ifPresent(value -> compass_z_items.add(value));
        }
        for (String clockItem : ModConfig.get().clock_items) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(clockItem));
            item.ifPresent(value -> clock_items.add(value));
        }
        for (String biomeItem : ModConfig.get().biome_items) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(biomeItem));
            item.ifPresent(value -> biome_items.add(value));
        }
        for (String weatherItem : ModConfig.get().clock_weather_items) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(weatherItem));
            item.ifPresent(value -> weather_items.add(value));
        }
        for (String seasonItem : ModConfig.get().season_items) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(seasonItem));
            item.ifPresent(value -> season_items.add(value));
        }
        for (String temperatureItem : ModConfig.get().temperature_items) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(temperatureItem));
            item.ifPresent(value -> temperature_items.add(value));
        }
        for (String speedItem : ModConfig.get().speed_items) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(speedItem));
            item.ifPresent(value -> speed_items.add(value));
        }
        if (weather_items.isEmpty()) {
            weather_items.addAll(clock_items);
        }
        if (compass_y_items.isEmpty()) {
            compass_y_items.addAll(compass_x_items);
        }
        if (compass_z_items.isEmpty()) {
            compass_z_items.addAll(compass_x_items);
        }
        if (biome_items.isEmpty()) {
            biome_items.addAll(compass_x_items);
        }
    }

}
