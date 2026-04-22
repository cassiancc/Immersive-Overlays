package cc.cassian.immersiveoverlays;

//? if >1.21
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public interface Overlay {
	void render(GuiGraphics guiGraphics
				//? if >1.21 {
				, DeltaTracker deltaTracker
				//?} else {
				/*, float deltaTracker
				 *///?}
	);
}
