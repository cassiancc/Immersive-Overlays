package cc.cassian.immersiveoverlays.neoforge;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import cc.cassian.immersiveoverlays.config.neoforge.ModConfigFactory;
import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
//? if >1.21.5 {
/*import cc.cassian.immersiveoverlays.overlay.neoforge.OverlayLayer;
*///?} else {
import cc.cassian.immersiveoverlays.overlay.OverlayLayer;
//?}
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

import static cc.cassian.immersiveoverlays.helpers.ModHelpers.clothConfigInstalled;

public class ImmersiveOverlaysNeoForgeClient {
    public static final OverlayLayer LAYER = new OverlayLayer();

    public static void init(IEventBus eventBus, ModContainer modContainer) {
        // Load config.
        ModClient.init("neoforge");
        //Register config screen.
        registerModsPage();
        NeoForge.EVENT_BUS.addListener(ImmersiveOverlaysNeoForgeClient::checkInventoryForOverlays);
        eventBus.addListener(ImmersiveOverlaysNeoForgeClient::renderGameOverlayEvent);
        eventBus.addListener(ImmersiveOverlaysNeoForgeClient::loadComplete);

    }

    @SubscribeEvent
    public static void loadComplete(FMLClientSetupEvent event) {
        ModLists.loadLists();
    }

    @SubscribeEvent
    public static void renderGameOverlayEvent(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ModClient.locate("overlay"), LAYER);
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
