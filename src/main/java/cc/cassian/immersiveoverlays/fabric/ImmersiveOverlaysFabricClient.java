package cc.cassian.immersiveoverlays.fabric;

//? if fabric {

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import cc.cassian.immersiveoverlays.overlay.*;
//? if >1.21.5 {
/*import cc.cassian.immersiveoverlays.layers.*;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
*///?} else if >1.21.2 {

/*import cc.cassian.immersiveoverlays.layers.*;
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
        ModClient.init("fabric");
        //? if >1.21.5 {
        /*HudElementRegistry.addFirst(ModClient.locate("biome"), new BiomeLayer());
        HudElementRegistry.addFirst(ModClient.locate("clock"), new ClockLayer());
        HudElementRegistry.addFirst(ModClient.locate("compass"), new CompassLayer());
        HudElementRegistry.addFirst(ModClient.locate("speed"), new SpeedLayer());
        HudElementRegistry.addFirst(ModClient.locate("temperature"), new TemperatureLayer());
        *///?} else if >1.21.2 {
        /*HudLayerRegistrationCallback.EVENT.register((layeredDrawer -> {
            layeredDrawer.addLayer(IdentifiedLayer.of(ModClient.locate("biome"), new BiomeLayer()));
            layeredDrawer.addLayer(IdentifiedLayer.of(ModClient.locate("clock"), new ClockLayer()));
            layeredDrawer.addLayer(IdentifiedLayer.of(ModClient.locate("compass"), new CompassLayer()));
            layeredDrawer.addLayer(IdentifiedLayer.of(ModClient.locate("speed"), new SpeedLayer()));
            layeredDrawer.addLayer(IdentifiedLayer.of(ModClient.locate("temperature"), new TemperatureLayer()));
        }));
         *///?} else {
        HudRenderCallback.EVENT.register(((matrixStack, tickDelta) -> {
            OverlayHelpers.renderOverlays(matrixStack);
        }));
        //?}
        ClientTickEvents.END_CLIENT_TICK.register(OverlayHelpers::checkInventoryForOverlays);
        ClientLifecycleEvents.CLIENT_STARTED.register((client -> {
            ModLists.loadLists();
        }));
    }

}
//?}