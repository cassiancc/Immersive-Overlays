package cc.cassian.immersiveoverlays;

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
    public static ArrayList<Item> compass_items = new ArrayList<>();
    public static ArrayList<Item> clock_items = new ArrayList<>();
    public static ArrayList<Item> weather_items = new ArrayList<>();
    public static ArrayList<Item> compass_depth_items = new ArrayList<>();


    public static void loadLists() {
        //? if >1.20 {
        var registry = BuiltInRegistries.ITEM;
        //?} else {
        /*var registry = Registry.ITEM;
         *///?}
        compass_items = new ArrayList<>();
        clock_items = new ArrayList<>();
        weather_items = new ArrayList<>();
        compass_depth_items = new ArrayList<>();
        for (String compassItem : ModConfig.get().compass_items) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(compassItem));
            item.ifPresent(value -> compass_items.add(value));
        }
        for (String clockItem : ModConfig.get().clock_items) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(clockItem));
            item.ifPresent(value -> clock_items.add(value));
        }
        for (String depthItem : ModConfig.get().compass_depth_items) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(depthItem));
            item.ifPresent(value -> compass_depth_items.add(value));
        }
        for (String weatherItem : ModConfig.get().clock_weather_items) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(weatherItem));
            item.ifPresent(value -> weather_items.add(value));
        }
        if (weather_items.isEmpty()) {
            weather_items.addAll(clock_items);
        }
        if (compass_depth_items.isEmpty()) {
            compass_depth_items.addAll(compass_items);
        }
    }

}
