package cc.cassian.immersiveoverlays;

//? fabric {
import cc.cassian.immersiveoverlays.fabric.FabricPlatformImpl;
//?}
import net.minecraft.world.item.ItemStack;
import java.io.File;
import java.nio.file.Path;
//? neoforge {
/*import cc.cassian.immersiveoverlays.neoforge.NeoforgePlatformImpl;
*///?}
//? forge
/*import cc.cassian.immersiveoverlays.forge.ForgePlatformImpl;*/

public interface Platform {

    //? fabric {
    Platform INSTANCE = new FabricPlatformImpl();
    //?}
    //? neoforge {
    /*Platform INSTANCE = new NeoforgePlatformImpl();
    *///?}
    //? forge {
    /*Platform INSTANCE = new ForgePlatformImpl();
     *///?}


    boolean isLoaded(String modid);
    boolean isLoadingLoaded(String mod);
    String loader();
    Path configPath();
}
