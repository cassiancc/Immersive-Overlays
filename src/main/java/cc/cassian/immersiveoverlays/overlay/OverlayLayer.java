//? if >1.21 && <=1.21.5 {
/*package cc.cassian.immersiveoverlays.overlay;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

public class OverlayLayer implements LayeredDraw.Layer {
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        CompassOverlay.renderGameOverlayEvent(guiGraphics);
        ClockOverlay.renderGameOverlayEvent(guiGraphics);
    }
}
*///?}