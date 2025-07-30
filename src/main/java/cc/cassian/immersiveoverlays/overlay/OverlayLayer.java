//? if >1.21 {
/*package cc.cassian.immersiveoverlays.overlay;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

public class OverlayLayer implements LayeredDraw.Layer {
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        CompassOverlay.renderGameOverlayEvent(guiGraphics);
        ClockOverlay.renderGameOverlayEvent(guiGraphics);
        BiomeOverlay.renderGameOverlayEvent(guiGraphics);
        TemperatureOverlay.renderGameOverlayEvent(guiGraphics);
    }
}
*///?}