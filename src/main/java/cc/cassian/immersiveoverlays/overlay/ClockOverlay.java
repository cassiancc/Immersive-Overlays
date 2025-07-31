package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.config.ModConfig;
//? if >1.21.5
/*import net.minecraft.client.renderer.RenderPipelines;*/
import net.minecraft.client.Minecraft;
//? if >1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import net.minecraft.client.gui.GuiComponent;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
 *///?}
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;

public class ClockOverlay {
    public static boolean showTime = false;
    public static boolean showWeather = false;


    //? if >1.20 {
    public static void renderGameOverlayEvent(GuiGraphics guiGraphics) {
    //?} else {
        /*public static void renderGameOverlayEvent(PoseStack guiGraphics) {
     *///?}
        if ((!showWeather && !showTime) || !ModConfig.get().clock_enable)
            return;
        var mc = Minecraft.getInstance();
        if (OverlayHelpers.debug(mc))
            return;
        if (mc.level == null) return;

        String time = "Hi! ";
        if (showTime) {
            if (mc.level.dimensionType().natural()) {
                time = getTime(mc.level.getDayTime());
            } else {
                time = "????";
            }

            if (time.length() == 4) {
                time = " " + time;
            }
        }

        int xOffset = 3;
        // The amount of offset needed to display the barometer icons, if visible.
        int iconOffset = 0;
        int textureOffset = 7;
        int tooltipSize = 16;
        int yPlacement = ModConfig.get().clock_vertical_position;
        int textYPlacement = yPlacement;
        if (showWeather) {
            if (showTime) {
                iconOffset = 20;
            }
            textureOffset = 111;
            tooltipSize = 21;
            textYPlacement += 2;
        }

        int fontWidth = mc.font.width(time)+iconOffset;

        if (mc.player == null) return;
        if (OverlayHelpers.playerHasPotions(mc.player, ModConfig.get().biome_horizontal_position_left)) {
            yPlacement += OverlayHelpers.moveBy(mc.player);
            textYPlacement += OverlayHelpers.moveBy(mc.player);
        }

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().clock_horizontal_position_left);
        OverlayHelpers.renderBackground(guiGraphics, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, textureOffset, tooltipSize, ModConfig.get().clock_horizontal_position_left);
        if (showTime) {
            // render text
            OverlayHelpers.drawString(guiGraphics, mc.font, time, xPlacement-xOffset+iconOffset, textYPlacement, 14737632);
        }
        if (showWeather) {
            var spriteOffset = getWeather(mc.player);
            OverlayHelpers.blit(guiGraphics, xPlacement-xOffset-1, yPlacement-1, spriteOffset, 95, 16, 16, OverlayHelpers.textureSize, OverlayHelpers.textureSize);
        }
    }

    public static int getWeather(Player player) {
        var level = player.level
        //? if >1.20 {
        ();
        //?} else {
        /*;
        *///?}
        var biome = level.getBiome(player.blockPosition()).value();
        var time = level.getDayTime() % 24000;
        //? if >1.21.2 {
        /*var precipitation = biome.getPrecipitationAt(player.blockPosition(), level.getSeaLevel());
        *///?} else if >1.20 {
        var precipitation = biome.getPrecipitationAt(player.blockPosition());
        //?} else {
         /*var precipitation = biome.getPrecipitation();
        *///?}
        //? if >1.21.2 {
        /*var snows = biome.coldEnoughToSnow(player.blockPosition(), level.getSeaLevel());
        *///?} else {
        var snows = biome.coldEnoughToSnow(player.blockPosition());
         //?}
        if (!level.dimensionType().natural()) return 124; // Netherlike
        else if (level.isThundering()) {
            if (snows) return 92; // Snowing
            if (precipitation.equals(Biome.Precipitation.NONE)) return 108; // Sandstorming
            return 76; // Thundering
        } else if (level.isRaining()) {
            if (snows) return 92; // Snowing
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
        StringBuilder currentTime = new StringBuilder();
        if (ModConfig.get().clock_day_count) {
            int day = (int) (dayTime/24000);
           currentTime.append(I18n.get("gui.c.day", day));
           if (ModConfig.get().clock_current_time) {
               currentTime.append(", ");
           }
        }
        if (ModConfig.get().clock_current_time) {
            int time = (int)(dayTime + 6000L) % 24000;
            int m = (int)((float)time % 1000.0F / 1000.0F * 60.0F);
            int hour = time / 1000;
            String a = "";
            if (!(Boolean) ModConfig.get().clock_24_hour) {
                a = time < 12000 ? " AM" : " PM";
                hour %= 12;
                if (hour == 0) {
                    hour = 12;
                }
            }
            currentTime.append(hour).append(":").append(m < 10 ? "0" : "").append(m).append(a);
        }
        return currentTime.toString();
    }
}
