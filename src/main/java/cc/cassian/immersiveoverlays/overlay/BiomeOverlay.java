package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.apache.commons.lang3.text.WordUtils;

import static cc.cassian.immersiveoverlays.overlay.OverlayHelpers.TEXTURE;

public class BiomeOverlay {
    public static boolean showBiome = true;


    //? if >1.20 {
    public static void renderGameOverlayEvent(GuiGraphics poseStack) {
    //?} else {
        /*public static void renderGameOverlayEvent(PoseStack poseStack) {
     *///?}
        if (!showBiome)
            return;
        if (!ModConfig.get().biome_enable)
            return;
        var mc = Minecraft.getInstance();

        var time = getBiome(mc.player);

        int xOffset = 3;
        // The amount of offset needed to display the biome icons.
        int iconOffset = 20;
        int textureOffset = 111;
        int tooltipSize = 21;
        int yPlacement = ModConfig.get().biome_vertical_position;
        int textYPlacement = yPlacement+2;

        int fontWidth = mc.font.width(time)+iconOffset;

        if (mc.player == null) return;
        if (OverlayHelpers.playerHasPotions(mc.player)) {
            yPlacement += OverlayHelpers.moveBy(mc.player);
            textYPlacement += OverlayHelpers.moveBy(mc.player);
        }

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth);
        //? if <1.20
        /*RenderSystem.setShaderTexture(0, OverlayHelpers.TEXTURE);*/
        OverlayHelpers.renderBackground(poseStack, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, textureOffset, tooltipSize);
        // render text
        //? if >1.20 {
        poseStack.drawString(mc.font, time, xPlacement-xOffset+iconOffset, textYPlacement, 14737632);
        //?} else {
        /*GuiComponent.drawString(poseStack, mc.font, time, xPlacement-xOffset+iconOffset, textYPlacement, 14737632);
         *///?}
        var spriteOffset = getBiomeOffset(mc.player);

        //? if >1.21.2 {
        /*poseStack.blit(RenderType::guiTextured, TEXTURE,
        *///?} else if >1.20 {
        poseStack.blit(TEXTURE,
        //?} else {

        /*RenderSystem.setShaderTexture(0, TEXTURE);
           GuiComponent.blit(poseStack,
         *///?}
                xPlacement-xOffset-1, yPlacement-1,
                0, spriteOffset,
                95, 16, 16,
                OverlayHelpers.textureSize, OverlayHelpers.textureSize);
    }

    private static int getBiomeOffset(LocalPlayer player) {
//        Minecraft.getInstance().getResourceManager().getResource(TEXTURE);
        return 0;
    }

    private static String getBiome(LocalPlayer player) {
        //? if >1.20 {
        var level = player.level();
        //?} else {
        /*var level = player.level;
         *///?}
        ResourceLocation biome = level.getBiome(player.blockPosition()).unwrapKey().orElse(Biomes.THE_VOID).location();
        var path = biome.getPath();
        var namespace = biome.getNamespace();
        var key = "biome." + namespace + path;
        if (I18n.exists(key)) {
            return I18n.get(key);
        } else return WordUtils.capitalize(path);
    }
}
