package cc.cassian.immersiveoverlays.helpers;

import cc.cassian.immersiveoverlays.config.ModConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
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
    public static ArrayList<Item> wind_items = new ArrayList<>();
    public static ArrayList<Item> waila_items = new ArrayList<>();


    public static void loadLists() {
        var registry = BuiltInRegistries.ITEM;
        addAll(registry, ModConfig.get().compass_x_items, compass_x_items);
        addAll(registry, ModConfig.get().compass_y_items, compass_y_items, compass_x_items);
        addAll(registry, ModConfig.get().compass_z_items, compass_z_items, compass_x_items);
        addAll(registry, ModConfig.get().clock_items, clock_items);
        addAll(registry, ModConfig.get().biome_items, biome_items, compass_x_items);
        addAll(registry, ModConfig.get().clock_weather_items, weather_items, clock_items);
        addAll(registry, ModConfig.get().season_items, season_items);
        addAll(registry, ModConfig.get().temperature_items, temperature_items);
        addAll(registry, ModConfig.get().speed_items, speed_items);
        addAll(registry, ModConfig.get().wind_items, wind_items);
        addAll(registry, ModConfig.get().compat_waila_items, waila_items);
    }

    private static void addAll(DefaultedRegistry<Item> registry, List<String> idList, ArrayList<Item> itemList) {
        itemList.clear();
        for (String itemId : idList) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(itemId));
            item.ifPresent(itemList::add);
        }
    }

    private static void addAll(DefaultedRegistry<Item> registry, List<String> idList, ArrayList<Item> itemList, ArrayList<Item> fallbackList) {
        addAll(registry, idList, itemList);
        if (itemList.isEmpty()) {
            itemList.addAll(fallbackList);
        }
    }

}
