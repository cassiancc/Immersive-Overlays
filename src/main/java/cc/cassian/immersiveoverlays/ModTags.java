package cc.cassian.immersiveoverlays;

import net.fabricmc.fabric.impl.biome.modification.BuiltInRegistryKeys;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static cc.cassian.immersiveoverlays.ModClient.locate;

public class ModTags {
    public static final TagKey<Item> SHOWS_XZ = createItemTag("shows_xz");
    public static final TagKey<Item> SHOWS_Y = createItemTag("shows_y");
    public static final TagKey<Item> SHOWS_WEATHER = createItemTag("shows_weather");
    public static final TagKey<Item> SHOWS_TIME = createItemTag("shows_time");
    public static final TagKey<Item> CONTAINERS = createItemTag("container");


    private static TagKey<Item> createItemTag(String id) {
        return TagKey.create(Registry.ITEM.key(), locate(id));
    }
}
