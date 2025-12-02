package cc.cassian.immersiveoverlays.fabric;

//? if fabric {

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import cc.cassian.immersiveoverlays.overlay.*;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
//? if >1.21.5 {
/*import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
*///?} else if >1.21.2 {
/*
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
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
        //? if >1.21.5 {
        /*HudElementRegistry.addFirst(ModClient.locate("biome"), BiomeOverlay::renderGameOverlayEvent);
        HudElementRegistry.addFirst(ModClient.locate("clock"), ClockOverlay::renderGameOverlayEvent);
        HudElementRegistry.addFirst(ModClient.locate("compass"), CompassOverlay::renderGameOverlayEvent);
        HudElementRegistry.addFirst(ModClient.locate("speed"), SpeedOverlay::renderGameOverlayEvent);
        HudElementRegistry.addFirst(ModClient.locate("temperature"), TemperatureOverlay::renderGameOverlayEvent);
        *///?} else if >1.21.2 {
        /*HudLayerRegistrationCallback.EVENT.register((layeredDrawer -> {
            layeredDrawer.addLayer(IdentifiedLayer.of(ModClient.locate("biome"), BiomeOverlay::renderGameOverlayEvent));
            layeredDrawer.addLayer(IdentifiedLayer.of(ModClient.locate("clock"), ClockOverlay::renderGameOverlayEvent));
            layeredDrawer.addLayer(IdentifiedLayer.of(ModClient.locate("compass"), CompassOverlay::renderGameOverlayEvent));
            layeredDrawer.addLayer(IdentifiedLayer.of(ModClient.locate("speed"), SpeedOverlay::renderGameOverlayEvent));
            layeredDrawer.addLayer(IdentifiedLayer.of(ModClient.locate("temperature"), TemperatureOverlay::renderGameOverlayEvent));
        }));
         *///?} else {
        HudRenderCallback.EVENT.register((OverlayHelpers::renderOverlays));
        //?}
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            OverlayHelpers.checkInventoryForOverlays(minecraft);
            OverlayHelpers.checkKeybind();
        });
        ClientLifecycleEvents.CLIENT_STARTED.register((client -> {
            ModLists.loadLists();
        }));
        KeyBindingHelper.registerKeyBinding(ModClient.overlayToggle);
    }

}
//?}