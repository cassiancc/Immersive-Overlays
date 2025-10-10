package cc.cassian.immersiveoverlays.layers;

//? if >1.21 {

import cc.cassian.immersiveoverlays.overlay.SpeedOverlay;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
//? if >1.21 && <=1.21.5 {
import net.minecraft.client.gui.LayeredDraw;
 //?} else fabric && >1.21.5 {
/*import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
*///?} else neoforge && >1.21.5 {
/*import net.neoforged.neoforge.client.gui.GuiLayer;
 *///?}

public class SpeedLayer
//? if >1.21 && <=1.21.5 {
        implements LayeredDraw.Layer
         //?} else fabric && >1.21.5 {
        /*implements HudElement
*///?} else neoforge && >1.21.5 {
        /*implements GuiLayer
         *///?}
{
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        SpeedOverlay.renderGameOverlayEvent(guiGraphics);
    }
}
//?}