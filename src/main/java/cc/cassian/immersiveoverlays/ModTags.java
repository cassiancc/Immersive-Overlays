package cc.cassian.immersiveoverlays;

import net.minecraft.core.Registry;
//? if >1.20 {
/*import net.minecraft.core.registries.BuiltInRegistries;
 */
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static cc.cassian.immersiveoverlays.ModClient.locate;

public class ModTags {
    public static final TagKey<Item> SHOWS_XZ = createItemTag("shows_xz");
    public static final TagKey<Item> SHOWS_Y = createItemTag("shows_y");
    public static final TagKey<Item> SHOWS_WEATHER = createItemTag("shows_weather");
    public static final TagKey<Item> SHOWS_TIME = createItemTag("shows_time");
    public static final TagKey<Item> CONTAINERS = createItemTag("container");


    private static TagKey<Item> createItemTag(String id) {
        //? if >1.20 {
        /*var registry = BuiltInRegistries.ITEM.key();
        *///?} else {
         var registry = Registry.ITEM.key();
        //?}
        return TagKey.create(registry, locate(id));
    }
}
