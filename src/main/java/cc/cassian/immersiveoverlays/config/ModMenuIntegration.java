package cc.cassian.immersiveoverlays.config;

//? if fabric {
import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.compat.ModCompat;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;


public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModConfigFactory::create;
    }
}
//?}