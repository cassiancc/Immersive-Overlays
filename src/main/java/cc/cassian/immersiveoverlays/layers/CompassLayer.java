//? if >1.21 && <=1.21.5 {
package cc.cassian.immersiveoverlays.layers;

import cc.cassian.immersiveoverlays.overlay.CompassOverlay;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

public class CompassLayer implements LayeredDraw.Layer {
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        CompassOverlay.renderGameOverlayEvent(guiGraphics);
    }
}
//?}