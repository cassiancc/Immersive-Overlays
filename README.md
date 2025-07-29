# Immersive Overlays

<a href='https://modrinth.com/mod/immersive-overlays/versions?l=fabric'><img alt="fabric" height="56" src="https://raw.githubusercontent.com/intergrav/devins-badges/refs/heads/v3/assets/cozy/supported/fabric_vector.svg"></a>
<a href='https://modrinth.com/mod/immersive-overlays/versions?l=neoforge&l=forge'><img alt="forge" height="56" src="https://raw.githubusercontent.com/cassiancc/Cassians-Badges/refs/heads/main/cozy/NeoForge.svg"></a>
<a href='https://www.curseforge.com/minecraft/modpacks/raspberry-flavoured'><img alt="Raspberry Flavoured" height="56" src="https://raw.githubusercontent.com/cassiancc/Cassians-Badges/refs/heads/main/cozy/Raspberry-Flavoured.svg?"></a>

This mod integrates a highly configurable coordinate overlay as an in-world mechanic, allowing it to only be visible as long as the player has a compass. In addition, it offers a similar overlay for clocks, showing the time of day.

## Coordinate Overlays

Coordinate overlays for the player's X and Z coordinates are visible whenever the player has a compass in their inventory, including in a bundle.

In 1.21.6 and above, the Player Locator Bar also requires a compass. This can be disabled in the config.

When a player additionally has an item that displays depth, like a [Supplementaries](https://modrinth.com/mod/supplementaries) Altimeter or a Depth Gauge from mods like [Caverns & Chasms](https://modrinth.com/mod/caverns-and-chasms) or [Spelunkery](https://modrinth.com/mod/spelunkery), the Y coordinate value is shown as well. If no item is available, a compass item is sufficient to show the overlay.

## Time and Weather Overlays

An overlay displaying the current time of day is displayed whenever the player has a clock in their inventory, including in a bundle.

When a player additionally has an item that displays the weather, like a [Caverns & Chasms](https://modrinth.com/mod/caverns-and-chasms) Barometer, the weather is shown as well. If no item is available, a clock item is sufficient to show the overlay.

## Temperature Overlays

An overlay displaying the current temperature is displayed whenever the player has an item that displays their current temperature in their inventory, including in a bundle.

- In vanilla, this data is sourced from the biome's temperature.
- With [Oreganized](https://modrinth.com/mod/oreganized) installed, this data is sourced from the Thermometer.

## Mod Compatibility

The mod has support for [Spelunkery](https://modrinth.com/mod/spelunkery), [Caverns & Chasms](https://modrinth.com/mod/caverns-and-chasms), [Oreganized](https://modrinth.com/mod/oreganized) and [Supplementaries](https://modrinth.com/mod/supplementaries) items out of the box. For other mods, items can be added to the mod's various config options.

In addition, the following features are present when compatible mods are installed:
- [Accessories](https://modrinth.com/mod/accessories): Items with overlays will work in accessory slots.
- [Accessorify](https://modrinth.com/mod/accessorify): This mod's overlays will replace the ones from Accessorify.
- [Player Locator Plus](https://modrinth.com/mod/player-locator-plus): The locator bar is now only displayed when the player has a compass.
- [Better Player Locator Bar](https://modrinth.com/mod/bplb): The locator bar is now only displayed when the player has a compass.
- [Xaero's Minimap](https://modrinth.com/mod/xaeros-minimap): When a map item is set, it can be used inside a bundle.
- [Map Atlases](https://modrinth.com/mod/map-atlases) (disabled by default, has issues): Atlases can be used inside a bundle.

## Credits

The concept for this mod came from a prototype by [nöelle](https://modrinth.com/user/noelledotjpg) for Raspberry Flavoured. I took this concept and iterated on it in my mod, [Raspberry Core](https://modrinth.com/mod/raspberry-core), before finalizing it here as Immersive Overlays.

Textures and mod icon are also by [nöelle](https://modrinth.com/user/noelledotjpg).