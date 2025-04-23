package cc.cassian.immersiveoverlays.fabric;


import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.ModLists;
import cc.cassian.immersiveoverlays.overlay.ClockOverlay;
import cc.cassian.immersiveoverlays.overlay.CompassOverlay;
import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public final class ImmersiveOverlaysFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ModClient.init();
        HudRenderCallback.EVENT.register(((matrixStack, tickDelta) -> {
            CompassOverlay.renderGameOverlayEvent(matrixStack);
            ClockOverlay.renderGameOverlayEvent(matrixStack);
        }));
        ClientTickEvents.END_CLIENT_TICK.register(OverlayHelpers::checkInventoryForOverlays);
        ClientLifecycleEvents.CLIENT_STARTED.register((client -> {
            ModLists.loadLists();
        }));
    }

}
