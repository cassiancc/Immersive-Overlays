package cc.cassian.immersiveoverlays.neoforge;

//? if neoforge {

/*import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.compat.ModCompat;
import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import cc.cassian.immersiveoverlays.config.ModConfigFactory;
import cc.cassian.immersiveoverlays.overlay.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

import static cc.cassian.immersiveoverlays.ModClient.MOD_ID;

@Mod(value = MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = MOD_ID)
public class ImmersiveOverlaysNeoForgeClient {

    public ImmersiveOverlaysNeoForgeClient(IEventBus eventBus, ModContainer modContainer) {
        // Load config.
        ModClient.init();
        //Register config screen.
        registerModsPage();
    }

    @SubscribeEvent
    public static void loadComplete(FMLClientSetupEvent event) {
        ModLists.loadLists();
    }

    @SubscribeEvent
    public static void renderGameOverlayEvent(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ModClient.locate("biome"), BiomeOverlay::renderGameOverlayEvent);
        event.registerAboveAll(ModClient.locate("clock"), ClockOverlay::renderGameOverlayEvent);
        event.registerAboveAll(ModClient.locate("compass"), CompassOverlay::renderGameOverlayEvent);
        event.registerAboveAll(ModClient.locate("speed"), SpeedOverlay::renderGameOverlayEvent);
        event.registerAboveAll(ModClient.locate("temperature"), TemperatureOverlay::renderGameOverlayEvent);
    }

    @SubscribeEvent
    public static void checkInventoryForOverlays(ClientTickEvent.Post event){
        OverlayHelpers.checkInventoryForOverlays(Minecraft.getInstance());
        OverlayHelpers.checkKeybind();
    }

    @SubscribeEvent
    public static void registerKeybinds(RegisterKeyMappingsEvent event){
        event.register(ModClient.overlayToggle);
        event.register(ModClient.overlaySettings);
        //? if >1.21.8
        /^event.registerCategory(ModClient.CATEGORY);^/
    }


    //Integrate Cloth Config screen (if mod present) with NeoForge mod menu.
    public static void registerModsPage() {
        if (ModCompat.CLOTH_CONFIG) ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, ModConfigFactory::new);
    }
}

*///?}
