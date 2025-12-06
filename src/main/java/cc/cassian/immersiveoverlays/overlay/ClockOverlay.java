package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.compat.*;
import cc.cassian.immersiveoverlays.config.ModConfig;
//? if >1.21.5
/*import net.minecraft.client.renderer.RenderPipelines;*/
import cc.cassian.immersiveoverlays.helpers.TextHelpers;
import net.minecraft.client.Minecraft;
//? if >1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import com.mojang.blaze3d.vertex.PoseStack;
*///?}
//? if >1.21 {
import net.minecraft.client.DeltaTracker;
//?}
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Locale;

public class ClockOverlay {
    public static boolean showTime = false;
    public static boolean showWeather = false;
    public static boolean showSeason = false;


    public static void renderGameOverlayEvent(
            //? if >1.20 {
            GuiGraphics guiGraphics
            //?} else {
            /*PoseStack guiGraphics*/
            //?}
            //? if >1.21 {
            , DeltaTracker deltaTracker
            //?} else {
            /*, float deltaTracker
             *///?}
    ) {
        if ((!showWeather && !showTime && !shouldShowSeasons()) || !ModConfig.get().clock_enable)
            return;
        var mc = Minecraft.getInstance();
        if (OverlayHelpers.shouldCancelRender(mc))
            return;
        if (mc.level == null || mc.player == null) return;

        String time = "Hi! ";
        if (showTime) {
            //? if >1.21.2 {
            /*if (mc.level.dimensionType().hasFixedTime()) {
            *///?} else {
            if (mc.level.dimensionType().natural()) {
            //?}
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
        int iconXOffset = 0;
        int tooltipSize = 16;
        int yPlacement = ModConfig.get().clock_vertical_position;
        int iconYPlacement = yPlacement;
        int textYPlacement = yPlacement;
        if (showWeather) {
            if (showTime) {
                iconXOffset = 20;
            }
            tooltipSize = 21;
            textYPlacement += 2;
        }
        if (shouldShowSeasons()) {
            if (showTime) {
                tooltipSize = 36;
            } else {
                tooltipSize = 21;
                textYPlacement += 2;
            }
            iconXOffset = 20;
        }

        int fontWidth = mc.font.width(time)+iconXOffset;
        Component seasonText = null;
        String seasonString = null;

        if (shouldShowSeasons()) {
            seasonString = ClockOverlay.getSeason(mc.level, mc.player.blockPosition());
            seasonText = TextHelpers.translatableWithFallback("gui.c.season."+seasonString, WordUtils.capitalizeFully(seasonString.replace("_", " ")));
            fontWidth = Integer.max(mc.font.width(time), mc.font.width(seasonText))+iconXOffset;
        }

        if (OverlayHelpers.playerHasPotions(mc.player, ModConfig.get().biome_horizontal_position_left)) {
            iconYPlacement += OverlayHelpers.moveBy(mc.player);
            textYPlacement += OverlayHelpers.moveBy(mc.player);
        }

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().clock_horizontal_position_left);
        OverlayHelpers.renderBackground(guiGraphics, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, tooltipSize, ModConfig.get().clock_horizontal_position_left);
        if (showTime) {
            // render text
            OverlayHelpers.drawString(guiGraphics, mc.font, time, xPlacement-xOffset+iconXOffset, textYPlacement, ModConfig.get().clock_text_colour);
        }
        if (showWeather) {
            var spriteOffset = getWeather(mc.player);
            OverlayHelpers.blit(guiGraphics, xPlacement-xOffset-1, iconYPlacement-1, spriteOffset, 95, 16, 16, OverlayHelpers.textureSize, OverlayHelpers.textureSize);
        }
        if (ClockOverlay.shouldShowSeasons()) {
            int seasonTextYPlacement = textYPlacement;
            if (showTime) {
                seasonTextYPlacement+=15;
            }
            OverlayHelpers.drawString(guiGraphics, mc.font, seasonText, xPlacement-xOffset+iconXOffset, seasonTextYPlacement, ModConfig.get().clock_text_colour);
            assert seasonString != null;
            var sprite = getSprite(seasonString.toLowerCase(Locale.ROOT));
            OverlayHelpers.blitSprite(guiGraphics, sprite, xPlacement-xOffset-1, seasonTextYPlacement-4);
        }
    }

    public static ResourceLocation getSprite(String season) {
        var spriteText = season.replace("early_", "").replace("mid_", "").replace("late_", "").replace("autumn", "fall");
        return ModClient.locate("textures/gui/"+spriteText+".png");
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
        //? if >1.21.10 {
        /*if (!level.dimension().equals(Level.OVERWORLD)) {
        *///?} else {
        if (!level.dimensionType().natural()) {
        //?}
            return 124; // Netherlike
        } else if (level.isThundering()) {
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

    public static boolean shouldShowSeasons() {
        if (ModConfig.get().clock_seasons && showSeason) {
            return ModCompat.SERENE_SEASONS || ModCompat.SIMPLE_SEASONS || ModCompat.FABRIC_SEASONS || ModCompat.TERRAFIRMACRAFT || ModCompat.ECLIPTIC_SEASONS;
        }
        return false;
    }

    public static String getSeason(ClientLevel level, BlockPos pos) {
        String season = "unknown";
        if (ModConfig.get().clock_seasons && showSeason) {
            //? if >1.20 {
            if (ModCompat.SERENE_SEASONS && ModConfig.get().compat_serene_seasons) {
                season = SereneSeasonsCompat.getSeason(level, pos);
            }
            //?}
            //? if fabric {
            if (ModCompat.FABRIC_SEASONS && ModConfig.get().compat_fabric_seasons) {
                season = FabricSeasonsCompat.getSeason(level);
            }
            if (ModCompat.SIMPLE_SEASONS && ModConfig.get().compat_simple_seasons) {
                season = SimpleSeasonsCompat.getSeason(level);
            }
            //?}
            //? if forge || neoforge {
            /*if (ModCompat.TERRAFIRMACRAFT && ModConfig.get().compat_tfc_seasons) {
                var tfcCompat = TerrafirmacraftCompat.getSeason(level);
                if (tfcCompat != null) season = tfcCompat;
            }
            *///?}
            //? if (forge || neoforge) && >1.20 {
             /*if (ModCompat.ECLIPTIC_SEASONS && ModConfig.get().compat_ecliptic_seasons) {
                var eclipticCompat = EclipticSeasonsCompat.getSeason(level, pos);
                if (eclipticCompat != null) season = eclipticCompat;
            }
            *///?}
        }
        return season;
    }
}
