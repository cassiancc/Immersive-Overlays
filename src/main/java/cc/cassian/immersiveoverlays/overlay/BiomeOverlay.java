package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.config.ModConfig;
import net.minecraft.client.Minecraft;
//? if >1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import net.minecraft.client.gui.GuiComponent;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
 *///?}
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Objects;

import static cc.cassian.immersiveoverlays.ModClient.MOD_ID;

public class BiomeOverlay {
    public static boolean showBiome = false;
    public static ResourceLocation UNDEFINED = ResourceLocation.tryBuild(MOD_ID, "textures/immersiveoverlays/undefined.png");


    //? if >1.20 {
    public static void renderGameOverlayEvent(GuiGraphics guiGraphics) {
    //?} else {
        /*public static void renderGameOverlayEvent(PoseStack guiGraphics) {
     *///?}
        if (!showBiome || ModConfig.get().biome_reduced_info || !ModConfig.get().biome_enable)
            return;
        var mc = Minecraft.getInstance();
        if (OverlayHelpers.debug(mc))
            return;

        if (mc.player == null) return;
        Holder<Biome> biome = getBiome(mc.player);
        ResourceLocation biomeId = getId(biome);
        String biomeText = formatBiome(biomeId);

        int xOffset = 3;
        // The amount of offset needed to display the biome icons.
        int iconOffset = 0;
        ResourceLocation sprite = UNDEFINED;
        if (ModConfig.get().biome_icons) {
            sprite = getBiomeSprite(biomeId, true);
            if (!sprite.equals(UNDEFINED)) {
                iconOffset = 20;
            }
        }
        int textureOffset = 111;
        int tooltipSize = 21;
        int yPlacement = ModConfig.get().biome_vertical_position;
        int textYPlacement = yPlacement+2;

        int fontWidth = mc.font.width(biomeText)+iconOffset;

        if (ModConfig.get().avoid_overlapping) {
            if (!(ClockOverlay.showTime || ClockOverlay.showWeather) || !ModConfig.get().clock_enable) {
                yPlacement = yPlacement - 24;
                textYPlacement = textYPlacement - 24;
            }
        }
        if (OverlayHelpers.playerHasPotions(mc.player, ModConfig.get().biome_horizontal_position_left)) {
            yPlacement += OverlayHelpers.moveBy(mc.player);
            textYPlacement += OverlayHelpers.moveBy(mc.player);
        }

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().biome_horizontal_position_left);
        OverlayHelpers.renderBackground(guiGraphics, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, textureOffset, tooltipSize, ModConfig.get().biome_horizontal_position_left);
        // render text
        OverlayHelpers.drawString(guiGraphics, mc.font, biomeText, xPlacement-xOffset+iconOffset, textYPlacement, getTextColour(biome));
        // render biome icon
        if (ModConfig.get().biome_icons && !sprite.equals(UNDEFINED)) {
            OverlayHelpers.blit(guiGraphics, sprite, xPlacement-xOffset-1, yPlacement-2, 0, 0, 16, 16, 16, 16);
        }
    }

    private static Integer getTextColour(Holder<Biome> biome) {
        if (ModConfig.get().biome_text_tinted)
            return TemperatureOverlay.getBiomeTemperature(biome.value()).getB();
        return -1;
    }

    public static ResourceLocation getBiomeSprite(ResourceLocation biome, boolean allowRedirect) {
        var manager = Minecraft.getInstance().getResourceManager();
        var path = "textures/immersiveoverlays/"+ biome.getPath();
        var key = ResourceLocation.tryBuild(biome.getNamespace(), "%s.png".formatted(path));
        var redirect = ResourceLocation.tryBuild(biome.getNamespace(), "%s.txt".formatted(path));
        if (manager.getResource(key).isPresent())
            return key;
        else {
            if (manager.getResource(redirect).isPresent() && allowRedirect) {
                try {
                    return getBiomeSprite(Objects.requireNonNull(ResourceLocation.tryParse(manager.openAsReader(redirect).lines().toList().get(0))), false);
                } catch (Exception e) {
                    return UNDEFINED;
                }
            } else {
                return UNDEFINED;
            }
        }
    }

    public static Holder<Biome> getBiome(LocalPlayer player) {
        //? if >1.20 {
        var level = player.level();
        //?} else {
        /*var level = player.level;
         *///?}
        return level.getBiome(player.blockPosition());
    }

    public static ResourceLocation getId(Holder<Biome> biome) {
        return biome.unwrapKey().orElse(Biomes.THE_VOID).location();
    }

    public static String formatBiome(ResourceLocation biome) {
        var path = biome.getPath();
        var namespace = biome.getNamespace();
        var key = "biome.%s.%s".formatted(namespace, path);
        if (I18n.exists(key)) {
            return I18n.get(key);
        } else return WordUtils.capitalize(path);
    }
}
