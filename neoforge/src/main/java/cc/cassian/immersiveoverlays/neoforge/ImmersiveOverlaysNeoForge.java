package cc.cassian.immersiveoverlays.neoforge;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.config.neoforge.ModConfigFactory;
import cc.cassian.immersiveoverlays.overlay.ClockOverlay;
import cc.cassian.immersiveoverlays.overlay.CompassOverlay;
import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

import static cc.cassian.immersiveoverlays.ModClient.MOD_ID;
import static cc.cassian.immersiveoverlays.helpers.ModHelpers.clothConfigInstalled;

@Mod(MOD_ID)
public final class ImmersiveOverlaysNeoForge {
    public ImmersiveOverlaysNeoForge(IEventBus eventBus, ModContainer modContainer) {
        // Load config.
        ModClient.init();
        //Register config screen.
        registerModsPage();
        NeoForge.EVENT_BUS.addListener(ImmersiveOverlaysNeoForge::checkInventoryForOverlays);
        NeoForge.EVENT_BUS.addListener(ImmersiveOverlaysNeoForge::renderGameOverlayEvent);

    }

    @SubscribeEvent
    public static void renderGameOverlayEvent(CustomizeGuiOverlayEvent.Chat event) {
        CompassOverlay.renderGameOverlayEvent(event.getGuiGraphics());
        ClockOverlay.renderGameOverlayEvent(event.getGuiGraphics());
    }

    @SubscribeEvent
    public static void checkInventoryForOverlays(ClientTickEvent.Post event){
        OverlayHelpers.checkInventoryForOverlays(Minecraft.getInstance());
    }

    //Integrate Cloth Config screen (if mod present) with NeoForge mod menu.
    public void registerModsPage() {
        if (clothConfigInstalled()) ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, ModConfigFactory::new);
    }
}
