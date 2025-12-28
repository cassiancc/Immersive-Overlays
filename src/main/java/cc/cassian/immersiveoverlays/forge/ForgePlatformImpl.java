package cc.cassian.immersiveoverlays.forge;

//? forge {
import cc.cassian.immersiveoverlays.Platform;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.LoadingModList;

import java.nio.file.Path;

public class ForgePlatformImpl implements Platform {

    public boolean isLoaded(String mod) {
        return ModList.get().isLoaded(mod);
    }

    public boolean isLoadingLoaded(String mod) {
        return LoadingModList.get().getModFileById(mod) != null;
    }

    @Override
    public String loader() {
        return "forge";
    }

    @Override
    public Path configPath() {
        return FMLPaths.CONFIGDIR.get();
    }


}
//?}