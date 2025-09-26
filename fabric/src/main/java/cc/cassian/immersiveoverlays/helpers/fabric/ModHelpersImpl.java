package cc.cassian.immersiveoverlays.helpers.fabric;

import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.TemperatureOverlay;
import net.fabricmc.fabric.api.tag.client.v1.ClientTags;
//? if >1.21 {
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
//?} else {
/*import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
*///?}
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.biome.Biome;

public class ModHelpersImpl {
    public static boolean clothConfigInstalled() {
        return FabricLoader.getInstance().isModLoaded("cloth-config");
    }

    public static boolean isLoaded(String mod) {
        return FabricLoader.getInstance().isModLoaded(mod);
    }

    public static TemperatureOverlay.TemperaturePair getBiomeTemperatureFromTag(Holder<Biome> biomeHolder) {
        if (ClientTags.isInWithLocalFallback(
                //? if >1.21
                ConventionalBiomeTags.IS_HOT
                //? if <1.21
                /*ConventionalBiomeTags.CLIMATE_HOT*/
                ,biomeHolder)) {
            return new TemperatureOverlay.TemperaturePair(Component.translatable("gui.c.temperature.hot"), ModConfig.get().temperature_hot_colour, "heat_4");
        } else if (ClientTags.isInWithLocalFallback(
                //? if >1.21
                ConventionalBiomeTags.IS_COLD
                //? if <1.21
                /*ConventionalBiomeTags.CLIMATE_COLD*/
                , biomeHolder)) {
            return new TemperatureOverlay.TemperaturePair(Component.translatable("gui.c.temperature.cold"), ModConfig.get().temperature_cold_colour, "heat_0");
        } else return new TemperatureOverlay.TemperaturePair(Component.translatable("gui.c.temperature.temperate"), ModConfig.get().temperature_temperate_colour, "heat_1");
    }
}
