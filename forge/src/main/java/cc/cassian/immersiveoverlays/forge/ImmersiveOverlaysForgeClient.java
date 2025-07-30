package cc.cassian.immersiveoverlays.forge;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import cc.cassian.immersiveoverlays.config.forge.ModConfigFactory;
import cc.cassian.immersiveoverlays.overlay.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
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
        ModClient.init("forge");
        //Register config screen.
        registerModsPage();
        MinecraftForge.EVENT_BUS.addListener(ImmersiveOverlaysForgeClient::checkInventoryForOverlays);
        MinecraftForge.EVENT_BUS.addListener(ImmersiveOverlaysForgeClient::renderGameOverlayEvent);
        eventBus.addListener(ImmersiveOverlaysForgeClient::loadComplete);
    }

    @SubscribeEvent
    public static void loadComplete(FMLClientSetupEvent event) {
        ModLists.loadLists();
    }

    @SubscribeEvent
    public static void renderGameOverlayEvent(CustomizeGuiOverlayEvent.DebugText event) {
        //? if >1.20 {
        var hud = event.getGuiGraphics();
        //?} else {
        /*var hud = event.getPoseStack();
         *///?}
        OverlayHelpers.renderOverlays(hud);
    }

    @SubscribeEvent
    public static void checkInventoryForOverlays(TickEvent.ClientTickEvent event){
        OverlayHelpers.checkInventoryForOverlays(Minecraft.getInstance());
    }

    //Integrate Cloth Config screen (if mod present) with Forge mod menu.
    public static void registerModsPage() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(ModConfigFactory::createScreen));
    }
}
