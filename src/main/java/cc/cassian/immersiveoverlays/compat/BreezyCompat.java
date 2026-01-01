package cc.cassian.immersiveoverlays.compat;

//? if forge {

import cc.cassian.immersiveoverlays.helpers.TextHelpers;
import codyhuh.breezy.common.network.BreezyNetworking;
import codyhuh.breezy.common.network.NewWindSavedData;
import codyhuh.breezy.core.other.tags.BreezyBiomeTags;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;

public class BreezyCompat {
	private static final String[] WIND_DIRECTIONS = {
			"east",
			"southeast",
			"south",
			"southwest",
			"west",
			"northwest",
			"north",
			"northeast"
	};

	private static final double[] WIND_ANGLES = {
			22.5, 67.5, 112.5, 157.5, 202.5, 247.5, 292.5, 337.5
	};

	public static Component getWindDirection(LocalPlayer player) {
		NewWindSavedData windData = BreezyNetworking.CLIENT_CACHE;
		if (windData == null) {
			return Component.empty();
		}
		double wind = Math.round(windData.getWindAtHeight((int) player.getY(), player.level()) * 10) / 10.0;
		int idx = getWindDxnIndex(wind);
		return TextHelpers.translatable("gui.c.direction." + WIND_DIRECTIONS[idx]);
	}

	public static String getSprite(LocalPlayer player) {
		var level = player.level();
		var position = player.blockPosition();
		Holder<Biome> holder = level.getBiome(position);

		NewWindSavedData windData = BreezyNetworking.CLIENT_CACHE;
		if (windData == null) {
			return "unknown";
		}

		int windStrength = 2;

		if (holder.is(BreezyBiomeTags.NO_WIND)) {
			windStrength = 0;
		} else if (holder.is(BreezyBiomeTags.LESS_WIND)) {
			windStrength = 1;
		} else if (holder.is(BreezyBiomeTags.MORE_WIND)) {
			windStrength = 3;
		}

		if (!level.canSeeSky(position)) {
			windStrength--;
		}
		if (windData.getLayer(player.blockPosition().getY(), player.level()) > 6) {
			windStrength++;
		}

		windStrength = Mth.clamp(windStrength, 0, 3);
		return "wind_" + windStrength;
	}

	private static int getWindDxnIndex(double wind) {
		for (int i = 0; i < WIND_ANGLES.length; i++) {
			if (wind < WIND_ANGLES[i] || (i == WIND_ANGLES.length - 1 && wind >= WIND_ANGLES[i])) {
				return i;
			}
		}
		return 0;
	}
}
//?}