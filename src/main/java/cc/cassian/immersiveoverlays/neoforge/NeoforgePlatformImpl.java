package cc.cassian.immersiveoverlays.neoforge;

//? neoforge {
/*import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.Platform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.loading.LoadingModList;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class NeoforgePlatformImpl implements Platform {

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


}
*///?}