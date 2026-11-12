package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.compat.EnhancedCelestials2Compat;
import cc.cassian.immersiveoverlays.compat.EnhancedCelestials1Compat;
import cc.cassian.immersiveoverlays.compat.LunarCompat;
import cc.cassian.immersiveoverlays.compat.ModCompat;
import cc.cassian.immersiveoverlays.config.ModConfig;
import cc.cassian.asphodel.client.util.HudUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class MoonOverlay {
    public record MoonPhase(String sprite, Component text, int color) {

        public static MoonPhase FULL_MOON = new MoonPhase("full_moon");
        public static MoonPhase WANING_GIBBOUS = new MoonPhase("waning_gibbous");
        public static MoonPhase THIRD_QUARTER = new MoonPhase("third_quarter");
        public static MoonPhase WANING_CRESCENT = new MoonPhase("waning_crescent");
        public static MoonPhase NEW_MOON = new MoonPhase("new_moon");
        public static MoonPhase WAXING_CRESCENT = new MoonPhase("waxing_crescent");
        public static MoonPhase FIRST_QUARTER = new MoonPhase("first_quarter");
        public static MoonPhase WAXING_GIBBOUS = new MoonPhase("waxing_gibbous");

        public static MoonPhase[] PHASES = new MoonPhase[]{FULL_MOON, WANING_GIBBOUS, THIRD_QUARTER, WANING_CRESCENT, NEW_MOON, WAXING_CRESCENT, FIRST_QUARTER, WAXING_GIBBOUS};

		public MoonPhase(String sprite, Component text, int color) {
            this.sprite = "moon_phase/" + sprite.toLowerCase(Locale.ROOT);
            this.text = text;
            if (ModConfig.get().moon_text_colour_from_events)
                this.color = color;
            else
                this.color = defaultColour();
		}

        public static int defaultColour() {
            return ModConfig.get().moon_text_colour;
        }

        public MoonPhase(String sprite) {
			this(sprite, getText(sprite), defaultColour());
		}

        public static MutableComponent getText(String sprite) {
            return Component.translatableWithFallback("gui.c.moon_phase." + sprite, WordUtils.capitalize(sprite.replace("_", " ")));
        }

        public static MutableComponent getText(ResourceLocation sprite) {
            String formatted = "gui.%s.moon_phase.%s".formatted(sprite.getNamespace(), sprite.getPath());
            if (Language.getInstance().has(formatted))
                return Component.translatable(formatted);
            return getText(sprite.getPath());
        }

        public static MoonPhase getPhase(ClientLevel level) {
            if (ModCompat.ENHANCED_CELESTIALS) {
                MoonPhase phase = EnhancedCelestials1Compat.get(level);
                if (phase != null) {
                    return phase;
                }
            }
            if (ModCompat.ENHANCED_CELESTIALS_2) {
                MoonPhase phase = EnhancedCelestials2Compat.get(level);
                if (phase != null) {
                    return phase;
                }
            }
            if (ModCompat.LUNAR) {
                MoonPhase phase = LunarCompat.get(level);
                if (phase != null) {
                    return phase;
                }
            }
            //? if >26 {
            /*int phase = level.environmentAttributes().getValue(net.minecraft.world.attribute.EnvironmentAttributes.MOON_PHASE, Minecraft.getInstance().player.position()).index();
            *///?} else {
            int phase = level.getMoonPhase();
            //?}
			return PHASES[phase];
		}
	}


    public static boolean showMoon = false;

    //~ if >1.21 'float'-> 'net.minecraft.client.DeltaTracker'
    public static void renderGameOverlayEvent(GuiGraphics guiGraphics, net.minecraft.client.DeltaTracker deltaTracker) {
        var mc = Minecraft.getInstance();
        if (!isVisible() || !showMoon(mc))
            return;
        if (OverlayHelpers.shouldCancelRender(mc))
            return;
        if (mc.level == null || mc.player == null || ModConfig.get().moon_reduced_info) return;

        MoonPhase moonPhase = getPhase(mc.level);
        if (moonPhase == null) return;

        int xOffset = 3;
        // The amount of offset needed to display the moon icons.
        int iconXOffset = 20;
        int tooltipSize = 21;
        int yPlacement = ModConfig.get().moon_vertical_position;
        if (OverlayHelpers.playerHasPotions(mc.player, ModConfig.get().moon_horizontal_position_left)) {
            yPlacement += OverlayHelpers.moveBy(mc.player);
        }

        int fontWidth = mc.font.width(moonPhase.text)+iconXOffset;

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().moon_horizontal_position_left);
        OverlayHelpers.renderBackground(guiGraphics, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, tooltipSize, ModConfig.get().moon_horizontal_position_left);
        // render text
        HudUtils.drawString(guiGraphics, mc.font, moonPhase.text, xPlacement-xOffset+iconXOffset, yPlacement+2, moonPhase.color);
        blitSprite(guiGraphics, moonPhase, xPlacement, xOffset, yPlacement);
    }

    public static boolean showMoon(Minecraft mc) {
        return ModConfig.get().moon_enable && MoonOverlay.showMoon && (!ModConfig.get().moon_only_at_night || ClockOverlay.getWeather(mc.player).contains("moon"));
    }

    static @Nullable MoonPhase getPhase(ClientLevel level) {
        if (OverlayHelpers.timePassesNaturally(level)) {
            return MoonPhase.getPhase(level);
        } else {
            return null;
        }
    }

    protected static void blitSprite(GuiGraphics guiGraphics, @Nullable MoonPhase moonPhase, int xPlacement, int xOffset, int yPlacement) {
        if (moonPhase == null) return;
        if (Minecraft.getInstance().getResourceManager().getResource(ModClient.locate("textures/gui/sprites/%s.png".formatted(moonPhase.sprite))).isPresent()) {
            OverlayHelpers.blitSprite(guiGraphics, moonPhase.sprite(), xPlacement - xOffset -1, yPlacement -1);
        } else {
            OverlayHelpers.blitSprite(guiGraphics, "moon_phase/event_moon", xPlacement - xOffset -1, yPlacement -1);
        }
    }

    public static boolean isVisible() {
        return MoonOverlay.showMoon;
    }
}
