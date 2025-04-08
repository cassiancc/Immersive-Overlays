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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;

import static cc.cassian.immersiveoverlays.overlay.OverlayHelpers.TEXTURE;

public class ClockOverlay {
    public static boolean hasClock = false;
    public static boolean hasBarometer = false;


    //? if >1.20 {
    public static void renderGameOverlayEvent(GuiGraphics poseStack) {
    //?} else {
        /*public static void renderGameOverlayEvent(PoseStack poseStack) {
     *///?}
        if (!hasBarometer && !hasClock)
            return;
        if (!ModConfig.get().clock_enable)
            return;
        var mc = Minecraft.getInstance();

        var time = getTime(mc.level.getDayTime());
        if (time.length() == 4) {
            time = " " + time;
        }
        if (hasClock && !ModConfig.get().clock_require_weather_item) {
            hasBarometer = true;
        }

        int xOffset = 3;
        // The amount of offset needed to display the barometer icons, if visible.
        int iconOffset = 0;
        int textureOffset = 7;
        int tooltipSize = 16;
        int yPlacement = ModConfig.get().clock_vertical_position;
        int textYPlacement = yPlacement;
        if (hasBarometer) {
            if (hasClock) {
                iconOffset = 20;
            }
            textureOffset = 111;
            tooltipSize = 21;
            textYPlacement += 2;
        }

        int fontWidth = mc.font.width(time)+iconOffset;

        if (mc.player == null) return;
        if (OverlayHelpers.playerHasPotions(mc.player)) {
            yPlacement = yPlacement + 16;
        }

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth);
        //? if <1.20
        /*RenderSystem.setShaderTexture(0, OverlayHelpers.TEXTURE);*/
        OverlayHelpers.renderBackground(poseStack, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, textureOffset, tooltipSize);
        if (hasClock) {
            // render text
            //? if >1.20 {
            poseStack.drawString(mc.font, time, xPlacement-xOffset+iconOffset, textYPlacement, 14737632);
            //?} else {
            /*GuiComponent.drawString(poseStack, mc.font, time, xPlacement-xOffset+iconOffset, textYPlacement, 14737632);
             *///?}
        }
        if (hasBarometer) {
            var spriteOffset = getWeather(mc.player);

            //? if >1.20 {
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
    }

    public static int getWeather(Player player) {
        //? if >1.20 {
        var level = player.level();
        //?} else {
          /*var level = player.level;
        *///?}
        var biome = level.getBiome(player.blockPosition()).value();
        var time = level.getDayTime() % 24000;
        //? if >1.20 {
        var precipitation = biome.getPrecipitationAt(player.blockPosition());
        //?} else {
         /*var precipitation = biome.getPrecipitation();
        *///?}
        if (!level.dimensionType().natural()) return 124; // Netherlike
        else if (level.isThundering()) {
            if (biome.coldEnoughToSnow(player.blockPosition())) return 92; // Snowing
            if (precipitation.equals(Biome.Precipitation.NONE)) return 108; // Sandstorming
            return 76; // Thundering
        } else if (level.isRaining()) {
            if (biome.coldEnoughToSnow(player.blockPosition())) return 92; // Snowing
            if (precipitation.equals(Biome.Precipitation.NONE)) return 108; // Sandstorming
            return 60; // Raining
        }
        else if (time >= 12500 && time <= 13500) return 30; // Sunset
        else if (time >= 13500 && time <= 22500) return 46; // Night
        else if (time >= 23000 || time <= 300) return 15; // Morning
        return 0; // Sunny
    }

    // This code was originally authored by MehVadVukaar for Supplementaries.
    // It is adapted here for our clock overlay as authorized by the
    // Supplementaries Team License, as Immersive Overlays is not designed
    // to compete with Supplementaries.
    public static String getTime(float dayTime) {
        int time = (int)(dayTime + 6000L) % 24000;
        int m = (int)((float)time % 1000.0F / 1000.0F * 60.0F);
        int h = time / 1000;
        String a = "";
        if (!(Boolean) ModConfig.get().clock_24_hour) {
            a = time < 12000 ? " AM" : " PM";
            h %= 12;
            if (h == 0) {
                h = 12;
            }
        }
        return (h + ":" + (m < 10 ? "0" : "") + m + a);
    }
}
