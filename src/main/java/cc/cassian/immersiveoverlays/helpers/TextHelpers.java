package cc.cassian.immersiveoverlays.helpers;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
//? if <1.19 {
/*import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
*///?}

public class TextHelpers {
    public static MutableComponent translatable(String key) {
        //? if >1.19 {
        return Component.translatable(key);
         //?} else {
        /*return new TranslatableComponent(key);
        *///?}
    }

    public static MutableComponent literal(String key) {
        //? if >1.19 {
        return Component.literal(key);
         //?} else {
        /*return new TextComponent(key);
        *///?}
    }

    public static Component empty() {
        //? if >1.19 {
        return Component.empty();
         //?} else {
        /*return new TextComponent("");
        *///?}
    }

}