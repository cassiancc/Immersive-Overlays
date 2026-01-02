package cc.cassian.immersiveoverlays.fabric;

//? fabric {

/*import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.Overlay;
import cc.cassian.immersiveoverlays.Platform;
import cc.cassian.immersiveoverlays.overlay.*;
//? if >1.21.2 {

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
//?} else {
/^import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
^///?}
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class FabricPlatformImpl implements Platform {

    @Override
    public boolean isLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }

    @Override
    public String loader() {
        return "fabric";
    }

    public boolean isLoadingLoaded(String mod) {
        return isLoaded(mod);
    }

    @Override
    public Path configPath() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public void registerOverlay(Identifier id, Overlay overlay) {
        //? if >1.21.5 {
        HudElementRegistry.addFirst(id, overlay::render);
        //?} else {
        /^HudRenderCallback.EVENT.register(overlay::render);
        ^///?}
    }

    @Override
	public boolean isDeveloperEnvironment() {
		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}
}
*///?}