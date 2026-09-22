package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.MoonOverlay;
import dev.corgitaco.enhancedcelestials.EnhancedCelestials;
import dev.corgitaco.enhancedcelestials.api.lunarevent.LunarEvent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class EnhancedCelestials1Compat {
	public static MoonOverlay.MoonPhase get(ClientLevel level) {
		//? if <26 {
		var worldDataOptional = EnhancedCelestials.lunarForecastWorldData(level);
		if (worldDataOptional.isPresent()) {
			var worldData = worldDataOptional.get();
			Optional<ResourceKey<LunarEvent>> lunarEventResourceKey = worldData.currentLunarEventHolder().unwrapKey();
			if (lunarEventResourceKey.isEmpty()) return null;
			ResourceLocation location = lunarEventResourceKey.orElseThrow().mru$identifier();
			var path = location.getPath();
			if (!path.equals("default")) {
				LunarEvent lunarEvent = worldData.currentLunarEvent();
				return new MoonOverlay.MoonPhase(path, lunarEvent.getTextComponents().name().getComponent(), Optional.ofNullable(lunarEvent.getTextComponents().name().getStyle().getColor()).map(TextColor::getValue).orElse(MoonOverlay.MoonPhase.defaultColour()));
			}
		}
		//?}
		return null;
	}
}
