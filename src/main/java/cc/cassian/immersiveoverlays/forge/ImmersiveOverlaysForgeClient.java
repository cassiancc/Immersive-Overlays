package cc.cassian.immersiveoverlays.forge;
//? if forge {
/*import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import cc.cassian.immersiveoverlays.config.ModConfigFactory;
import cc.cassian.immersiveoverlays.overlay.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ImmersiveOverlaysForgeClient {

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Load config.
        ModClient.init();
        //Register config screen.
        registerModsPage();
        MinecraftForge.EVENT_BUS.addListener(ImmersiveOverlaysForgeClient::checkInventoryForOverlays);
        MinecraftForge.EVENT_BUS.addListener(ImmersiveOverlaysForgeClient::renderGameOverlayEvent);
        eventBus.addListener(ImmersiveOverlaysForgeClient::loadComplete);
        eventBus.addListener(ImmersiveOverlaysForgeClient::registerKeybinds);
    }


    public static void loadComplete(FMLClientSetupEvent event) {
        ModLists.loadLists();
    }


    public static void renderGameOverlayEvent(CustomizeGuiOverlayEvent.DebugText event) {
        //? if >1.20 {
        var hud = event.getGuiGraphics();
        //?} else {
        /^var hud = event.getPoseStack();
         ^///?}
        OverlayHelpers.renderOverlays(hud, event.getPartialTick());
    }


    public static void registerKeybinds(RegisterKeyMappingsEvent event){
        event.register(ModClient.overlayToggle);
        event.register(ModClient.overlaySettings);
    }


    public static void checkInventoryForOverlays(TickEvent.ClientTickEvent event){
        OverlayHelpers.checkInventoryForOverlays(Minecraft.getInstance());
    }

    //Integrate Cloth Config screen (if mod present) with Forge mod menu.
    public static void registerModsPage() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(ModConfigFactory::createScreen));
    }
}
*///?}