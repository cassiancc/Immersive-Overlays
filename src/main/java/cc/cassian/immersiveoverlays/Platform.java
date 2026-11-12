package cc.cassian.immersiveoverlays;

//? fabric
import cc.cassian.asphodel.client.fabric.ClientVersionedUtil;
import cc.cassian.asphodel.client.util.Overlay;
import net.minecraft.resources.ResourceLocation;
//? neoforge {
/*import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
 *///?} else forge {
/*import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import org.jetbrains.annotations.UnknownNullability;
*///?}


public class Platform {

	public static void registerOverlay(ResourceLocation id, Overlay overlay
									   //? neoforge
									   //,RegisterGuiLayersEvent event
									   //? forge
		   //, RenderGuiOverlayEvent event
									   //? fabric
									   ,Object event
			) {
		//? fabric {
		ClientVersionedUtil.registerOverlay(id, overlay);
		//?} else if neoforge {
		/*event.registerAboveAll(id, overlay::render);
		*///?} else if forge {
		/*overlay.render(event.getGuiGraphics(), event.getPartialTick());
		*///?}

	}

	@SuppressWarnings("false")
    public static boolean showDevInfo() {
        return ModClient.DEBUG && cc.cassian.asphodel.Platform.INSTANCE.isDeveloperEnvironment();
    }
}
