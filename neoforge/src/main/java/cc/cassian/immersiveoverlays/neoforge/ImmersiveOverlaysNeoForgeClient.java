package cc.cassian.immersiveoverlays.neoforge;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.ModLists;
import cc.cassian.immersiveoverlays.config.neoforge.ModConfigFactory;
import cc.cassian.immersiveoverlays.overlay.ClockOverlay;
import cc.cassian.immersiveoverlays.overlay.CompassOverlay;
import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

import static cc.cassian.immersiveoverlays.helpers.ModHelpers.clothConfigInstalled;

public class ImmersiveOverlaysNeoForgeClient {
    public static void init(IEventBus eventBus, ModContainer modContainer) {
        // Load config.
        ModClient.init();
        //Register config screen.
        registerModsPage();
        NeoForge.EVENT_BUS.addListener(ImmersiveOverlaysNeoForgeClient::checkInventoryForOverlays);
        NeoForge.EVENT_BUS.addListener(ImmersiveOverlaysNeoForgeClient::renderGameOverlayEvent);
        eventBus.addListener(ImmersiveOverlaysNeoForgeClient::loadComplete);

    }

    @SubscribeEvent
    public static void loadComplete(FMLClientSetupEvent event) {
        ModLists.loadLists();
    }

    @SubscribeEvent
    public static void renderGameOverlayEvent(RenderGuiLayerEvent.Post event) {
        CompassOverlay.renderGameOverlayEvent(event.getGuiGraphics());
        ClockOverlay.renderGameOverlayEvent(event.getGuiGraphics());
    }

    @SubscribeEvent
    public static void checkInventoryForOverlays(ClientTickEvent.Post event){
        OverlayHelpers.checkInventoryForOverlays(Minecraft.getInstance());
    }

    //Integrate Cloth Config screen (if mod present) with NeoForge mod menu.
    public static void registerModsPage() {
        if (clothConfigInstalled()) ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, ModConfigFactory::new);
    }
}
