package cc.cassian.immersiveoverlays.fabric;

//? if fabric {

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.Platform;
import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import cc.cassian.immersiveoverlays.overlay.*;
//? if <26 {
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
//?} else {
/*import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
*///?}
//? if >1.21.5 {
/*import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
*///?}
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;


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
        //? if <26 {
        KeyBindingHelper.registerKeyBinding(ModClient.overlayToggle);
        KeyBindingHelper.registerKeyBinding(ModClient.overlaySettings);
        //?} else {
        /*KeyMappingHelper.registerKeyMapping(ModClient.overlayToggle);
        KeyMappingHelper.registerKeyMapping(ModClient.overlaySettings);
        *///?}
    }

}
//?}