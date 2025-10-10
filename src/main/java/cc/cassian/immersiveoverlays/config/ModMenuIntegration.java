package cc.cassian.immersiveoverlays.config;

//? if fabric {
import cc.cassian.immersiveoverlays.ModClient;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import static cc.cassian.immersiveoverlays.helpers.ModHelpers.clothConfigInstalled;


public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        //Display Cloth Config screen if mod present, else error.
        if (clothConfigInstalled()) return new ModConfigFactory();
        else {
            ModClient.LOGGER.warn("User attempted to edit config, but Cloth Config is not present!");
            return parent -> null;
        }
    }
}
//?}