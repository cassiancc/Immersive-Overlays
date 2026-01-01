package cc.cassian.immersiveoverlays.helpers;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.text.WordUtils;

public class TextHelpers {
    public static MutableComponent translatable(String key) {
        return Component.translatable(key);
    }

    public static MutableComponent translatableWithFallback(String key, String fallback) {
        return Component.translatableWithFallback(key, fallback);
    }

    public static MutableComponent literal(String key) {
        return Component.literal(key);
    }

    public static Component empty() {
        return Component.empty();
    }

}