package cc.cassian.immersiveoverlays.config;

//? if fabric {
/*import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.compat.ModCompat;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;


public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        //Display Cloth Config screen if mod present, else error.
        if (ModCompat.CLOTH_CONFIG) return new ModConfigFactory();
        else {
            return parent -> null;
        }
    }
}
*///?}