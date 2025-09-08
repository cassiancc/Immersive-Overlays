//? if >1.21 && <=1.21.5 {
package cc.cassian.immersiveoverlays.layers;

import cc.cassian.immersiveoverlays.overlay.ClockOverlay;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

public class ClockLayer implements LayeredDraw.Layer {
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        ClockOverlay.renderGameOverlayEvent(guiGraphics);
    }
}
//?}