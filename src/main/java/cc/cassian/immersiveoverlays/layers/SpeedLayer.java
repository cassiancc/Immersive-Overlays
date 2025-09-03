//? if >1.21 && <=1.21.5 {
package cc.cassian.immersiveoverlays.layers;

import cc.cassian.immersiveoverlays.overlay.SpeedOverlay;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

public class SpeedLayer implements LayeredDraw.Layer {
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        SpeedOverlay.renderGameOverlayEvent(guiGraphics);
    }
}
//?}