package cc.cassian.immersiveoverlays.config;

import cc.cassian.mru.compat.ModCompat;
import cc.cassian.immersiveoverlays.config.ClothConfigFactory;
//? if fabric
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
//? if neoforge {
/*import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
*///?}
//? if forge
//import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class ModConfigFactory {

    public static Screen create(Screen parent) {
        if (ModCompat.YET_ANOTHER_CONFIG_LIB) {
            return YetAnotherConfigFactory.create(parent);
        }
        if (ModCompat.CLOTH_CONFIG) {
            return ClothConfigFactory.create(parent);
        }
        return parent;
    }
}