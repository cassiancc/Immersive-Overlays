package cc.cassian.immersiveoverlays.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.loading.FMLEnvironment;

import static cc.cassian.immersiveoverlays.ModClient.MOD_ID;

@Mod(MOD_ID)
public final class ImmersiveOverlaysNeoForge {
    public ImmersiveOverlaysNeoForge(IEventBus eventBus, ModContainer modContainer) {
        if (FMLEnvironment.dist.isClient()) {
            ImmersiveOverlaysNeoForgeClient.init(eventBus, modContainer);
        }
    }
}
