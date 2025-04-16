package cc.cassian.immersiveoverlays;


import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModClient {
    public static final String MOD_ID = "immersiveoverlays";
    public static final String MOD_NAME = "Immersive Overlays";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static void init() {
        ModConfig.load();
        ModLists.loadLists();
    }

    public static ResourceLocation locate(String s) {
        return ResourceLocation.tryBuild(MOD_ID, s);
    }
}
