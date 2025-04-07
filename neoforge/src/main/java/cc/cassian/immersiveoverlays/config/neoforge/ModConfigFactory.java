package cc.cassian.immersiveoverlays.config.neoforge;



import cc.cassian.immersiveoverlays.config.ClothConfigFactory;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

public class ModConfigFactory implements IConfigScreenFactory {

    @Override
    public @NotNull Screen createScreen(@NotNull ModContainer modContainer, @NotNull Screen parent) {
        return ClothConfigFactory.create(parent);
    }
}