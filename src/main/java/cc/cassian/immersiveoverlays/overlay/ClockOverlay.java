package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.mru.client.util.ClientUtil;
import cc.cassian.mru.client.util.HudUtils;
import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.compat.*;
import cc.cassian.immersiveoverlays.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Locale;

public class ClockOverlay {
    public static boolean showTime = false;
    public static boolean showWeather = false;
    public static boolean showSeason = false;
    public static boolean showDayCount = false;



    //~ if >1.21 'float'-> 'net.minecraft.client.DeltaTracker'
    public static void renderGameOverlayEvent(GuiGraphics guiGraphics, net.minecraft.client.DeltaTracker deltaTracker) {
        if (!isVisible() || !ModConfig.get().clock_enable)
            return;
        var mc = Minecraft.getInstance();
        if (OverlayHelpers.shouldCancelRender(mc))
            return;
        if (mc.level == null || mc.player == null) return;

        String time = "Hi! ";
        boolean showTimeOrDayCount = showTime || showDayCount;
        boolean showReducedMoonInfo = MoonOverlay.showMoon(mc) && ModConfig.get().moon_reduced_info;
        boolean showFirstLine = showTimeOrDayCount || showReducedMoonInfo;

        if (showTimeOrDayCount) {
            if (OverlayHelpers.timePassesNaturally(mc.level)) {
                time = getTime(ClientUtil.getOverworldTime());
                if (time.length() == 4) {
                    time = " " + time;
                }
            } else {
                time = "";
            }
        }

        int xOffset = 3;
        // The amount of offset needed to display the barometer icons, if visible.
        int iconXOffset = 0;
        int tooltipSize = 16;
        int yPlacement = ModConfig.get().clock_vertical_position;
        if (OverlayHelpers.playerHasPotions(mc.player, ModConfig.get().clock_horizontal_position_left)) {
            yPlacement += OverlayHelpers.moveBy(mc.player);
        }
        int iconYPlacement = yPlacement;
        int textYPlacement = yPlacement;
        if (showWeather || showReducedMoonInfo) {
            if (showFirstLine) {
                iconXOffset = 20;
            }
            tooltipSize = 21;
            textYPlacement += 2;
        }
        if (shouldShowSeasons()) {
            if (showFirstLine) {
                tooltipSize = 36;
            } else {
                tooltipSize = 21;
                textYPlacement += 2;
            }
            iconXOffset = 20;
        }

        int fontWidth = mc.font.width(time)+iconXOffset;
        Season season = Season.UNKNOWN;

        if (shouldShowSeasons()) {
            season = ClockOverlay.getSeason(mc.level, mc.player.blockPosition());
            fontWidth = Integer.max(mc.font.width(time), mc.font.width(season.name))+iconXOffset;
        }

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().clock_horizontal_position_left);
        OverlayHelpers.renderBackground(guiGraphics, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, tooltipSize, ModConfig.get().clock_horizontal_position_left);
        if (showTimeOrDayCount) {
            // render text
            HudUtils.drawString(guiGraphics, mc.font, time, xPlacement-xOffset+iconXOffset, textYPlacement, ModConfig.get().clock_text_colour);
        }
        if (showWeather || showReducedMoonInfo) {
            var weather = getWeather(mc.player);
            if (showReducedMoonInfo) {
                MoonOverlay.blitSprite(guiGraphics, MoonOverlay.getPhase(mc.level), xPlacement, xOffset, yPlacement);
            } else if (showWeather) {
                OverlayHelpers.blitSprite(guiGraphics, weather, xPlacement-xOffset-1, iconYPlacement-1);
            }
        }
        if (ClockOverlay.shouldShowSeasons()) {
            int seasonTextYPlacement = textYPlacement;
            if (showFirstLine) {
                seasonTextYPlacement+=15;
            }
            HudUtils.drawString(guiGraphics, mc.font, season.name, xPlacement-xOffset+iconXOffset, seasonTextYPlacement, ModConfig.get().clock_text_colour);
            var sprite = getSprite(season.sprite);
            HudUtils.blitSprite(guiGraphics, sprite, xPlacement-xOffset-1, seasonTextYPlacement-4);
        }
    }

    public static ResourceLocation getSprite(String season) {
        var spriteText = season.replace("early_", "").replace("mid_", "").replace("late_", "").replace("autumn", "fall");
        return ModClient.locate("textures/gui/sprites/"+spriteText+".png");
    }

    public static String getWeather(Player player) {
		Level level = player.level();
        var biome = level.getBiome(player.blockPosition()).value();
        var time = ClientUtil.getOverworldTime() % 24000;
        //? if >1.21.2 {
        /*var precipitation = biome.getPrecipitationAt(player.blockPosition(), level.getSeaLevel());
        var snows = biome.coldEnoughToSnow(player.blockPosition(), level.getSeaLevel());
        *///?} else {
        var precipitation = biome.getPrecipitationAt(player.blockPosition());
        var snows = biome.coldEnoughToSnow(player.blockPosition());
         //?}
        if (!OverlayHelpers.timePassesNaturally(level)) {
            return "nether"; // Netherlike
        } else if (level.isThundering()) {
            if (snows) return "snow"; // Snowing
            if (precipitation.equals(Biome.Precipitation.NONE)) return "sandstorm"; // Sandstorming
            return "storm"; // Thundering
        } else if (level.isRaining()) {
            if (snows) return "snow"; // Snowing
            if (precipitation.equals(Biome.Precipitation.NONE)) return "sandstorm"; // Sandstorming
            return "rain"; // Raining
        }
        else if (time >= 12500 && time <= 13500) return "moonrise"; // Sunset
        else if (time >= 13500 && time <= 23000) return "moon"; // Night
        else if (time >= 23000 || time <= 300) return "sunrise"; // Morning
        return "sun"; // Sunny
    }

    // This code was originally authored by MehVadVukaar for Supplementaries.
    // It is adapted here for our clock overlay as authorized by the
    // Supplementaries Team License, as Immersive Overlays is not designed
    // to compete with Supplementaries.
    public static String getTime(float dayTime) {
        StringBuilder currentTime = new StringBuilder();
        boolean showCurrentTime = ModConfig.get().clock_current_time && ClockOverlay.showTime;
        if ((ModConfig.get().clock_day_count && ClockOverlay.showTime) || showDayCount) {
            int day = (int) (dayTime/24000);
           currentTime.append(I18n.get("gui.c.day", day));
           if (showCurrentTime) {
               currentTime.append(", ");
           }
        }
        if (showCurrentTime) {
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

    public static Season getSeason(ClientLevel level, BlockPos pos) {
        Season season = Season.UNKNOWN;
        if (ModConfig.get().clock_seasons && showSeason) {
            if (ModCompat.SERENE_SEASONS && ModConfig.get().compat_serene_seasons) {
                season = SereneSeasonsCompat.getSeason(level, pos);
            }
            if (ModCompat.FABRIC_SEASONS && ModConfig.get().compat_fabric_seasons) {
                season = FabricSeasonsCompat.getSeason(level);
            }
            if (ModCompat.SIMPLE_SEASONS && ModConfig.get().compat_simple_seasons) {
                season = new Season(SimpleSeasonsCompat.getSeason(level));
            }
            //? if forge || neoforge {
            /*if (ModCompat.TERRAFIRMACRAFT && ModConfig.get().compat_tfc_seasons) {
                var tfcCompat = TerrafirmacraftCompat.getSeason(level);
                if (tfcCompat != null) season = new Season(tfcCompat);
            }
            *///?}
            //? if (forge || neoforge || (fabric && >26)) {
             /*if (ModCompat.ECLIPTIC_SEASONS && ModConfig.get().compat_ecliptic_seasons) {
                season = EclipticSeasonsCompat.getSeason(level, pos);
            }
            *///?}
        }
        return season;
    }

    public static boolean isVisible() {
        return ClockOverlay.showTime || ClockOverlay.showWeather || ClockOverlay.showDayCount || shouldShowSeasons() || (ModConfig.get().moon_reduced_info && MoonOverlay.showMoon(Minecraft.getInstance()));
    }

    public record Season(String sprite, Component name) {
        public Season(String sprite, Component name) {
            this.sprite = sprite.toLowerCase(Locale.ROOT);
            this.name = name;
        }

        public Season(String sprite) {
            this(sprite, createSeasonText(sprite.toLowerCase(Locale.ROOT)));
        }

        public static Component createSeasonText(String sprite) {
            return Component.translatableWithFallback("gui.c.season."+sprite, WordUtils.capitalizeFully(sprite.replace("_", " ")));
        }

        public static final Season UNKNOWN = new Season("unknown");
    }
}
