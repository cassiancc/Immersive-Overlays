package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.ModClient;
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

import java.io.IOException;
import java.util.Objects;

public class BiomeOverlay {
    public static boolean showBiome = false;
    public static ResourceLocation UNDEFINED = ModClient.locate("textures/immersiveoverlays/undefined.png");


    //? if >1.20 {
    public static void renderGameOverlayEvent(GuiGraphics guiGraphics) {
    //?} else {
        /*public static void renderGameOverlayEvent(PoseStack guiGraphics) {
     *///?}
        if (!showBiome || !ModConfig.get().biome_enable)
            return;
        if (ModConfig.get().biome_reduced_info) {
            return;
        }
        var mc = Minecraft.getInstance();
        if (OverlayHelpers.shouldCancelRender(mc))
            return;

        if (mc.player == null) return;
        Holder<Biome> biome = getBiome(mc.player);
        ResourceLocation biomeId = getId(biome);
        String biomeText = formatBiome(biomeId);

        int yPlacement = ModConfig.get().biome_vertical_position;
        int textYPlacement = yPlacement+2;
        int xOffset = 3;
        // The amount of offset needed to display the biome icons.
        int iconXOffset = 0;
        ResourceLocation sprite = UNDEFINED;
        if (ModConfig.get().biome_icons) {
            sprite = getBiomeSprite(biomeId, true);
            if (!sprite.equals(UNDEFINED)) {
                iconXOffset = 20;
            }
        }
        int tooltipSize = 21;

        int fontWidth = mc.font.width(biomeText)+iconXOffset;

        if (ModConfig.get().avoid_overlapping) {
            if (!(ClockOverlay.showTime || ClockOverlay.showWeather) || !ModConfig.get().clock_enable) {
                yPlacement = yPlacement - 24;
                textYPlacement = textYPlacement - 24;
            }
            if (ClockOverlay.shouldShowSeasons()) {
                yPlacement += 15;
                textYPlacement += 15;
            }
        }
        if (OverlayHelpers.playerHasPotions(mc.player, ModConfig.get().biome_horizontal_position_left)) {
            yPlacement += OverlayHelpers.moveBy(mc.player);
            textYPlacement += OverlayHelpers.moveBy(mc.player);
        }

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().biome_horizontal_position_left);
        // render text
        if (showBiome) {
            OverlayHelpers.renderBackground(guiGraphics, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, tooltipSize, ModConfig.get().biome_horizontal_position_left);
            OverlayHelpers.drawString(guiGraphics, mc.font, biomeText, xPlacement-xOffset+iconXOffset, textYPlacement, getTextColour(biome));
            if (ModConfig.get().biome_icons && !sprite.equals(UNDEFINED))
                OverlayHelpers.blitSprite(guiGraphics, sprite, xPlacement-xOffset-1, textYPlacement-4);
        }
    }

    private static Integer getTextColour(Holder<Biome> biome) {
        if (ModConfig.get().biome_text_tinted)
            return TemperatureOverlay.getBiomeTemperature(biome).color();
        return ModConfig.get().biome_text_colour;
    }

    public static ResourceLocation getBiomeSprite(ResourceLocation biome, boolean allowRedirect) {
        var manager = Minecraft.getInstance().getResourceManager();
        var path = "textures/immersiveoverlays/"+ biome.getPath();
        var key = ModClient.locate(biome.getNamespace(), "%s.png".formatted(path));
        var redirect = ModClient.locate(biome.getNamespace(), "%s.txt".formatted(path));
        //? if >1.19 {
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
        //?} else {
        /*if (allowRedirect) {
            try {
                return getBiomeSprite(Objects.requireNonNull(ResourceLocation.tryParse(manager.getResource(redirect).getInputStream().toString())), false);
            } catch (Exception e) {
                return UNDEFINED;
            }
        } else {
            return UNDEFINED;
        }
        *///?}
    }

    public static Holder<Biome> getBiome(LocalPlayer player) {
        return player.level
        //? if >1.20 {
        ()
        //?}
        .getBiome(player.blockPosition());
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
        } else return WordUtils.capitalize(path.replace("_"," "));
    }

}
