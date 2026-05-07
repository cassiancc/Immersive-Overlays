package cc.cassian.immersiveoverlays.helpers;

import cc.cassian.immersiveoverlays.config.ModConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModLists {
    public static ArrayList<Item> compass_x_items = new ArrayList<>();
    public static ArrayList<Item> compass_y_items = new ArrayList<>();
    public static ArrayList<Item> compass_z_items = new ArrayList<>();
    public static ArrayList<Item> compass_anchor_items = new ArrayList<>();
    public static ArrayList<Item> clock_items = new ArrayList<>();
    public static ArrayList<Item> weather_items = new ArrayList<>();
    public static ArrayList<Item> biome_items = new ArrayList<>();
    public static ArrayList<Item> season_items = new ArrayList<>();
    public static ArrayList<Item> day_count_items = new ArrayList<>();
    public static ArrayList<Item> temperature_items = new ArrayList<>();
    public static ArrayList<Item> speed_items = new ArrayList<>();
    public static ArrayList<Item> wind_items = new ArrayList<>();
    public static ArrayList<Item> waila_items = new ArrayList<>();

    public static ArrayList<Block> important_blocks = new ArrayList<>();
    public static ArrayList<Block> compass_x_blocks = new ArrayList<>();
    public static ArrayList<Block> compass_y_blocks = new ArrayList<>();
    public static ArrayList<Block> compass_z_blocks = new ArrayList<>();
    public static ArrayList<Block> clock_blocks = new ArrayList<>();
    public static ArrayList<Block> weather_blocks = new ArrayList<>();
    public static ArrayList<Block> biome_blocks = new ArrayList<>();
    public static ArrayList<Block> season_blocks = new ArrayList<>();
    public static ArrayList<Block> day_count_blocks = new ArrayList<>();
    public static ArrayList<Block> temperature_blocks = new ArrayList<>();
    public static ArrayList<Block> speed_blocks = new ArrayList<>();
    public static ArrayList<Block> wind_blocks = new ArrayList<>();
    public static ArrayList<Block> waila_blocks = new ArrayList<>();


    public static void loadLists() {
        loadItems(BuiltInRegistries.ITEM);
        loadBlocks(BuiltInRegistries.BLOCK);
    }

    private static void loadItems(DefaultedRegistry<Item> registry) {
        addAll(registry, ModConfig.get().compass_x_items, compass_x_items);
        addAll(registry, ModConfig.get().compass_y_items, compass_y_items, compass_x_items);
        addAll(registry, ModConfig.get().compass_z_items, compass_z_items, compass_x_items);
        addAll(registry, ModConfig.get().compass_anchor_items, compass_anchor_items, compass_anchor_items);
        addAll(registry, ModConfig.get().clock_items, clock_items);
        addAll(registry, ModConfig.get().biome_items, biome_items, compass_x_items);
        addAll(registry, ModConfig.get().clock_weather_items, weather_items, clock_items);
        addAll(registry, ModConfig.get().season_items, season_items);
        addAll(registry, ModConfig.get().clock_day_count_items, day_count_items);
        addAll(registry, ModConfig.get().temperature_items, temperature_items);
        addAll(registry, ModConfig.get().speed_items, speed_items);
        addAll(registry, ModConfig.get().wind_items, wind_items);
        addAll(registry, ModConfig.get().compat_waila_items, waila_items);
    }

    private static void loadBlocks(DefaultedRegistry<Block> registry) {
        important_blocks.clear();
        important_blocks.addAll(addAll(registry, ModConfig.get().compass_x_blocks, compass_x_blocks));
        important_blocks.addAll(addAll(registry, ModConfig.get().compass_y_blocks, compass_y_blocks, compass_x_blocks));
        important_blocks.addAll(addAll(registry, ModConfig.get().compass_z_blocks, compass_z_blocks, compass_x_blocks));
        important_blocks.addAll(addAll(registry, ModConfig.get().clock_blocks, clock_blocks));
        important_blocks.addAll(addAll(registry, ModConfig.get().biome_blocks, biome_blocks, compass_x_blocks));
        important_blocks.addAll(addAll(registry, ModConfig.get().clock_weather_blocks, weather_blocks, clock_blocks));
        important_blocks.addAll(addAll(registry, ModConfig.get().season_blocks, season_blocks));
        important_blocks.addAll(addAll(registry, ModConfig.get().clock_day_count_blocks, day_count_blocks));
        important_blocks.addAll(addAll(registry, ModConfig.get().temperature_blocks, temperature_blocks));
        important_blocks.addAll(addAll(registry, ModConfig.get().speed_blocks, speed_blocks));
        important_blocks.addAll(addAll(registry, ModConfig.get().wind_blocks, wind_blocks));
        important_blocks.addAll(addAll(registry, ModConfig.get().compat_waila_blocks, waila_blocks));
    }

    /**
     * @param registry The registry to check - usually {@link BuiltInRegistries#ITEM}
     * @param idList   - A list of strings to convert to registry objects.
     * @param itemList - A list of registry objects to fill with strings.
     * @return itemList - A list of registry objects to fill with strings.
     */
    private static <T> ArrayList<T> addAll(DefaultedRegistry<T> registry, List<String> idList, ArrayList<T> itemList) {
        itemList.clear();
        for (String itemId : idList) {
            Optional<T> item = registry.getOptional(ResourceLocation.tryParse(itemId));
            item.ifPresent(itemList::add);
        }
        return itemList;
    }

	/**
     * @param registry     The registry to check - usually {@link BuiltInRegistries#ITEM}
     * @param idList       - A list of strings to convert to registry objects.
     * @param itemList     - A list of registry objects to fill with strings.
     * @param fallbackList - A list of registry objects to use if the main list is empty.
     * @return itemList - A list of registry objects to fill with strings.
     */
    private static <T> ArrayList<T> addAll(DefaultedRegistry<T> registry, List<String> idList, ArrayList<T> itemList, ArrayList<T> fallbackList) {
        addAll(registry, idList, itemList);
        if (itemList.isEmpty()) {
            itemList.addAll(fallbackList);
        }
        return itemList;
    }

}
