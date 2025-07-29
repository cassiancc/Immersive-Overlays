package cc.cassian.immersiveoverlays.fabric;


import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import cc.cassian.immersiveoverlays.overlay.*;
//? if >1.21.2 {
/*import cc.cassian.immersiveoverlays.overlay.OverlayLayer;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.resources.ResourceLocation;
*///?}
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public final class ImmersiveOverlaysFabricClient implements ClientModInitializer {
    //? if >1.21.2 {
    /*public static final OverlayLayer LAYER = new OverlayLayer();
    *///?}
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ModClient.init("fabric");
        //? if >1.21.2 {
        /*HudLayerRegistrationCallback.EVENT.register((layeredDrawer -> layeredDrawer.addLayer(IdentifiedLayer.of(ResourceLocation.fromNamespaceAndPath(ModClient.MOD_ID, "overlay"), LAYER))));
        *///?} else {
        HudRenderCallback.EVENT.register(((matrixStack, tickDelta) -> {
            CompassOverlay.renderGameOverlayEvent(matrixStack);
            ClockOverlay.renderGameOverlayEvent(matrixStack);
            BiomeOverlay.renderGameOverlayEvent(matrixStack);
            TemperatureOverlay.renderGameOverlayEvent(matrixStack);
        }));
        //?}
        ClientTickEvents.END_CLIENT_TICK.register(OverlayHelpers::checkInventoryForOverlays);
        ClientLifecycleEvents.CLIENT_STARTED.register((client -> {
            ModLists.loadLists();
        }));
    }

}
