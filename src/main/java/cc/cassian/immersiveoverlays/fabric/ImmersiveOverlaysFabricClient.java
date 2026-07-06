package cc.cassian.immersiveoverlays.fabric;

//? if fabric {

import cc.cassian.mru.client.util.ClientVersionedUtil;
import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import cc.cassian.immersiveoverlays.overlay.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public final class ImmersiveOverlaysFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ModClient.init();
        ModClient.registerOverlays();
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            OverlayHelpers.checkInventoryForOverlays(minecraft);
            OverlayHelpers.checkKeybind();
        });
        ClientLifecycleEvents.CLIENT_STARTED.register((client -> {
            ModLists.loadLists();
        }));
        ClientVersionedUtil.registerKeyMapping(ModClient.overlayToggle);
        ClientVersionedUtil.registerKeyMapping(ModClient.overlaySettings);
    }

}
//?}