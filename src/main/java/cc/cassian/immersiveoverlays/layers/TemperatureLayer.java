//? if >1.21 && <=1.21.5 {
package cc.cassian.immersiveoverlays.layers;

import cc.cassian.immersiveoverlays.overlay.TemperatureOverlay;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

public class TemperatureLayer implements LayeredDraw.Layer {
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        TemperatureOverlay.renderGameOverlayEvent(guiGraphics);
    }
}
//?}