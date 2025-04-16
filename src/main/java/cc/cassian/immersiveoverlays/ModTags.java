package cc.cassian.immersiveoverlays;


//? if >1.20 {
import net.minecraft.core.registries.BuiltInRegistries;
 //?} else {
/*import net.minecraft.core.Registry;
*///?}
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static cc.cassian.immersiveoverlays.ModClient.locate;

public class ModTags {
    public static final TagKey<Item> CONTAINERS = createItemTag("container");


    private static TagKey<Item> createItemTag(String id) {
        //? if >1.20 {
        var registry = BuiltInRegistries.ITEM.key();
        //?} else {
         /*var registry = Registry.ITEM.key();
        *///?}
        return TagKey.create(registry, locate(id));
    }
}
