package cc.cassian.immersiveoverlays.overlay;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.compat.ModCompat;
import cc.cassian.immersiveoverlays.compat.OreganizedCompat;
import cc.cassian.immersiveoverlays.config.ModConfig;
//? if >1.21 {
/*import net.minecraft.client.DeltaTracker;
*///?}
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.gui.GuiGraphics;

public class SpeedOverlay {
    public static boolean showSpeed = false;


    public static void renderGameOverlayEvent(GuiGraphics guiGraphics
            //? if >1.21 {
            /*, DeltaTracker deltaTracker
            *///?} else {
            , float deltaTracker
             //?}
    ) {
        if (!showSpeed || !ModConfig.get().speed_enable)
            return;
        var mc = Minecraft.getInstance();
        if (OverlayHelpers.shouldCancelRender(mc))
            return;

        if (mc.player == null) return;
        double speed = getSpeed(mc.player);
        String speedText = String.format("%.2f", speed);

        int xOffset = 3;
        int tooltipSize = 21;
        int yPlacement = ModConfig.get().speed_vertical_position;
        int textYPlacement = yPlacement+2;

        int fontWidth = 40;

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int xPlacement = OverlayHelpers.getPlacement(windowWidth, fontWidth, ModConfig.get().speed_horizontal_position_left);
        // render background
        OverlayHelpers.renderBackground(guiGraphics, windowWidth, fontWidth, xPlacement, xOffset, yPlacement, tooltipSize, ModConfig.get().speed_horizontal_position_left);
        // render text
        OverlayHelpers.drawString(guiGraphics, mc.font, speedText, xPlacement-xOffset+10, textYPlacement, ModConfig.get().speed_colour);
        var spriteX = xPlacement-4;
        var spriteY = textYPlacement+10;
        var fast = ModClient.locate("textures/gui/fast.png");
        var slow = ModClient.locate("textures/gui/slow.png");
        ResourceLocation sprite;
        if (speed>=0.05) sprite = fast; else sprite = slow;
        OverlayHelpers.blitSprite(guiGraphics, sprite, spriteX, spriteY, 7);
        if (speed>=0.08) sprite = fast; else sprite = slow;
        OverlayHelpers.blitSprite(guiGraphics, sprite, spriteX+7, spriteY, 7);
        if (speed>=0.14) sprite = fast; else sprite = slow;
        OverlayHelpers.blitSprite(guiGraphics, sprite, spriteX+14, spriteY, 7);
        if (speed>=0.28) sprite = fast; else sprite = slow;
        OverlayHelpers.blitSprite(guiGraphics, sprite, spriteX+21, spriteY, 7);
        if (speed>0.56) sprite = fast; else sprite = slow;
        OverlayHelpers.blitSprite(guiGraphics, sprite, spriteX+28, spriteY, 7);
        if (speed>1) sprite = fast; else sprite = slow;
        OverlayHelpers.blitSprite(guiGraphics, sprite, spriteX+35, spriteY, 7);
    }

    public static double getSpeed(LocalPlayer player) {
        double speed;
        if (ModCompat.OREGANIZED && ModConfig.get().compat_oreganized_speed) {
            speed = OreganizedCompat.getSpeed(player);
        } else {
            Vec3 deltaMovement = player.getRootVehicle().getDeltaMovement();
            speed = Math.max(Math.max(deltaMovement.x, deltaMovement.y), deltaMovement.z);
        }
        return speed;
    }
}
