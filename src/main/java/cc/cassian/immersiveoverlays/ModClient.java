package cc.cassian.immersiveoverlays;


import cc.cassian.immersiveoverlays.config.ModConfig;

import cc.cassian.immersiveoverlays.overlay.*;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
//? neoforge {
/*import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import cc.cassian.immersiveoverlays.neoforge.NeoforgePlatformImpl;
*///?} else forge {
/*import cc.cassian.immersiveoverlays.forge.ForgePlatformImpl;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
*///?}

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModClient {
    public static final String MOD_ID = "immersiveoverlays";
    public static final String MOD_NAME = "Immersive Overlays";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static final int CONFIG_VERSION = 4;
    public static boolean DEBUG = false;

    //? if >1.21.8 {
    /*public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(locate("keybinds")); // The category translation key used to categorize in the Controls screen
    *///?} else {
    public static final String CATEGORY = "key.category.immersiveoverlays.keybinds";
     //?}

    // A key mapping with keyboard as the default
    public static final KeyMapping overlayToggle = new KeyMapping(
            "key.immersiveoverlays.toggle", // The translation key of the name shown in the Controls screen
            InputConstants.Type.KEYSYM, // This key mapping is for Keyboards by default
            InputConstants.UNKNOWN.getValue(), // The default keycode
            CATEGORY
    );
    public static final KeyMapping overlaySettings = new KeyMapping(
            "key.immersiveoverlays.settings", // The translation key of the name shown in the Controls screen
            InputConstants.Type.KEYSYM, // This key mapping is for Keyboards by default
            InputConstants.UNKNOWN.getValue(), // The default keycode
            CATEGORY
    );

    public static void init() {
        ModConfig.load();
    }

    public static void registerOverlays(
            //? neoforge
            //RegisterGuiLayersEvent event
            //? forge
            //CustomizeGuiOverlayEvent.DebugText event
    ) {
        //? neoforge
        //NeoforgePlatformImpl.guiLayersEvent = event;
        //? forge
        //ForgePlatformImpl.guiLayersEvent = event;

        Platform.INSTANCE.registerOverlay(ModClient.locate("biome"), BiomeOverlay::renderGameOverlayEvent);
        Platform.INSTANCE.registerOverlay(ModClient.locate("clock"), ClockOverlay::renderGameOverlayEvent);
        Platform.INSTANCE.registerOverlay(ModClient.locate("compass"), CompassOverlay::renderGameOverlayEvent);
        Platform.INSTANCE.registerOverlay(ModClient.locate("speed"), SpeedOverlay::renderGameOverlayEvent);
        Platform.INSTANCE.registerOverlay(ModClient.locate("temperature"), TemperatureOverlay::renderGameOverlayEvent);
        Platform.INSTANCE.registerOverlay(ModClient.locate("wind"), WindOverlay::renderGameOverlayEvent);
    }

    public static ResourceLocation locate(String s) {
        return locate(MOD_ID, s);
    }

    public static ResourceLocation locate(String namespace, String path) {
        //? if >1.21 {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
         //?} else {
        /*return new ResourceLocation(namespace, path);
        *///?}
    }
}
