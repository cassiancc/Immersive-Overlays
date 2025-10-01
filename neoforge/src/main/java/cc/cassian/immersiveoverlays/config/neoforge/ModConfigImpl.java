package cc.cassian.immersiveoverlays.config.neoforge;

import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

import static cc.cassian.immersiveoverlays.ModClient.MOD_ID;

public class ModConfigImpl {
    public static Path configPath() {
        return FMLPaths.CONFIGDIR.get().resolve(MOD_ID + ".json");
    }
}
