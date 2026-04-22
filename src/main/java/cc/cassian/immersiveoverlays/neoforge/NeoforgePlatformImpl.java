package cc.cassian.immersiveoverlays.neoforge;

//? neoforge {
/*import cc.cassian.immersiveoverlays.Overlay;
import cc.cassian.immersiveoverlays.Platform;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import java.nio.file.Path;

public class NeoforgePlatformImpl implements Platform {
    public static RegisterGuiLayersEvent guiLayersEvent;

    public boolean isLoaded(String mod) {
        return ModList.get().isLoaded(mod);
    }

    public boolean isLoadingLoaded(String mod) {
        return LoadingModList.get().getModFileById(mod) != null;
    }

    @Override
    public String loader() {
        return "neoforge";
    }

    @Override
    public Path configPath() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public void registerOverlay(ResourceLocation id, Overlay overlay) {
        guiLayersEvent.registerAboveAll(id, overlay::render);
    }

    @Override
    public boolean isDeveloperEnvironment() {
        //? if >1.21.9 {
        /^return !FMLEnvironment.isProduction();
        ^///?} else {
        return !FMLEnvironment.production;
        //?}

    }
}
*///?}