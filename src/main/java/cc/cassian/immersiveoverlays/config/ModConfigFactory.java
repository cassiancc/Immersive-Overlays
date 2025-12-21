package cc.cassian.immersiveoverlays.config;


import cc.cassian.immersiveoverlays.compat.ModCompat;
import cc.cassian.immersiveoverlays.config.ClothConfigFactory;
//? if fabric && <26
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
//? if neoforge {
/*import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
*///?}
//? if forge
/*import net.minecraftforge.fml.ModContainer;*/
import org.jetbrains.annotations.NotNull;

public class ModConfigFactory
        //? if neoforge
        //implements IConfigScreenFactory
        //? if fabric && <26
        implements ConfigScreenFactory<Screen>
{


    public ModConfigFactory() {
    }

    //? if fabric
    public Screen create(Screen parent) {
        //? if neoforge
        //public @NotNull Screen createScreen(@NotNull ModContainer modContainer, @NotNull Screen parent) {
        //? if forge
        /*public static @NotNull Screen createScreen(Minecraft modContainer, @NotNull Screen parent) {*/
        if (ModCompat.CLOTH_CONFIG) {
            return ClothConfigFactory.create(parent);
        }
        return parent;
    }
}