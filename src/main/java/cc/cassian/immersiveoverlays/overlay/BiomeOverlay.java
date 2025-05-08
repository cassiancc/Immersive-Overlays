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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Objects;

import static cc.cassian.immersiveoverlays.ModClient.MOD_ID;

public class BiomeOverlay {
    public static boolean showBiome = false;


    //? if >1.20 {
    public static void renderGameOverlayEvent(GuiGraphics poseStack) {
    //?} else {
        /*public static void renderGameOverlayEvent(PoseStack poseStack) {
     *///?}
        if (!showBiome)
            return;
        if (ModConfig.get().biome_reduced_info) {
            return;
        }
        if (!ModConfig.get().biome_enable)
            return;
        var mc = Minecraft.getInstance();

        ResourceLocation biome = getBiome(mc.player);
        String biomeText = formatBiome(biome);

        int xOffset = 3;
        // The amount of offset needed to display the biome icons.
        int iconOffset = 20;
        int textureOffset = 111;
        int tooltipSize = 21;
        int yPlacement = ModConfig.get().biome_vertical_position;
        int textYPlacement = yPlacement+2;

        int fontWidth = mc.font.width(biomeText)+iconOffset;

        if (mc.player == null) return;
        if (!(ClockOverlay.showTime || ClockOverlay.showWeather) || !ModConfig.get().clock_enable) {
            yPlacement = yPlacement - 24;
            textYPlacement = textYPlacement - 24;
        }
        if (OverlayHelpers.playerHasPotions(mc.player)) {
            yPlacement += OverlayHelpers.moveBy(mc.player);
            textYPlacement += OverlayHelpers.moveBy(mc.player);
        }

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().biome_horizontal_position_left);
        //? if <1.20
        /*RenderSystem.setShaderTexture(0, OverlayHelpers.TEXTURE);*/
        OverlayHelpers.renderBackground(poseStack, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, textureOffset, tooltipSize, ModConfig.get().biome_horizontal_position_left);
        // render text
        //? if >1.20 {
        poseStack.drawString(mc.font, biomeText, xPlacement-xOffset+iconOffset, textYPlacement, 14737632);
        //?} else {
        /*GuiComponent.drawString(poseStack, mc.font, biomeText, xPlacement-xOffset+iconOffset, textYPlacement, 14737632);
         *///?}
        var sprite = getBiomeSprite(biome);

        //? if >1.21.2 {
        /*poseStack.blit(RenderType::guiTextured, sprite,
        *///?} else if >1.20 {
        poseStack.blit(sprite,
        //?} else {

        /*RenderSystem.setShaderTexture(0, sprite);
           GuiComponent.blit(poseStack,
         *///?}
                xPlacement-xOffset-1, yPlacement-2,
                0, 0,
                0, 16, 16,
                16, 16);
    }

    public static ResourceLocation getBiomeSprite(ResourceLocation biome) {
        var manager = Minecraft.getInstance().getResourceManager();
        var path = "textures/immersiveoverlays/"+ biome.getPath();
        var key = ResourceLocation.tryBuild(biome.getNamespace(), "%s.png".formatted(path));
        var redirect = ResourceLocation.tryBuild(biome.getNamespace(), "%s.txt".formatted(path));
        if (manager.getResource(key).isPresent())
            return key;
        else if (manager.getResource(redirect).isPresent()) {
            try {
                return getBiomeSprite(Objects.requireNonNull(ResourceLocation.tryParse(manager.openAsReader(redirect).lines().toList().get(0))));
            } catch (Exception e) {
                return ResourceLocation.tryBuild(MOD_ID, "textures/immersiveoverlays/undefined.png");
            }
        } else {
            return ResourceLocation.tryBuild(MOD_ID, "textures/immersiveoverlays/undefined.png");
        }
    }

    public static ResourceLocation getBiome(LocalPlayer player) {
        //? if >1.20 {
        var level = player.level();
        //?} else {
        /*var level = player.level;
         *///?}
        return level.getBiome(player.blockPosition()).unwrapKey().orElse(Biomes.THE_VOID).location();
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
