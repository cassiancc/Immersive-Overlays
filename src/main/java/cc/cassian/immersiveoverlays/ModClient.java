package cc.cassian.immersiveoverlays;


import cc.cassian.immersiveoverlays.config.ModConfig;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModClient {
    public static final String MOD_ID = "immersiveoverlays";
    public static final String MOD_NAME = "Immersive Overlays";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static final int CONFIG_VERSION = 2;
    public static String loader;
    public static void init(String loader) {
        ModConfig.load();
        ModClient.loader = loader;
    }

    public static ResourceLocation locate(String s) {
        return ResourceLocation.tryBuild(MOD_ID, s);
    }
}
