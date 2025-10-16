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

## Biome Overlays

An overlay displaying the current biome is displayed whenever the player has a clock in their map, including in a bundle.

This feature supports all vanilla biomes, as well as some modded biomes. Mod support can be added via resource packs by adding a 16x16 sprite to `assets/modname/textures/immersiveoverlays/biomename.png`.

## Season Overlays

An overlay displaying the current season is displayed whenever the player has a calendar in their inventory, including in a bundle.

This requires a compatible seasons mod, with support for [Serene Seasons](https://modrinth.com/mod/serene-seasons), [Fabric Seasons](https://modrinth.com/mod/fabric-seasons), [Ecliptic Seasons](https://www.curseforge.com/minecraft/mc-mods/ecliptic-seasons) and [Simple Seasons](https://modrinth.com/mod/simple-seasons).

## Temperature Overlays

An overlay displaying the current temperature is displayed whenever the player has a thermometer in their inventory, including in a bundle.

- In vanilla, this data is sourced from the biome's temperature.
- With [Oreganized](https://modrinth.com/mod/oreganized) installed, this data is sourced from the Thermometer.
- With [Tough as Nails](https://modrinth.com/mod/tough-as-nails) installed, this data is sourced from the player's temperature.
- With [Cold Sweat](https://modrinth.com/mod/cold-sweat/) installed, this data is sourced from the player's temperature.
- With [Legendary Survival Overhaul](https://www.curseforge.com/minecraft/mc-mods/legendary-survival-overhaul) installed, this data is sourced from the player's temperature.
- With [Thermoo](https://modrinth.com/mod/thermoo) installed, this data is sourced from the player's temperature.

## Speed Overlays

An overlay displaying the current temperature is displayed whenever the player has a speedometer in their inventory, including in a bundle.

- In vanilla, this data is sourced from the player's speed.
- With [Oreganized](https://modrinth.com/mod/oreganized) installed, this data is sourced from the Speedometer.

## Mod Compatibility

The mod has support for [Spelunkery](https://modrinth.com/mod/spelunkery), [Caverns & Chasms](https://modrinth.com/mod/caverns-and-chasms), and [Supplementaries](https://modrinth.com/mod/supplementaries) items out of the box. For other mods, items can be added to the mod's various config options.

In addition, the following features are present when compatible mods are installed:
- [Accessories](https://modrinth.com/mod/accessories): Items with overlays will work in accessory slots.
- [Accessorify](https://modrinth.com/mod/accessorify): This mod's overlays will replace the ones from Accessorify.
- [Curios](https://modrinth.com/mod/curios): Items with overlays will work in accessory slots.
- [Trinkets](https://modrinth.com/mod/trinkets): Items with overlays will work in accessory slots.
- [Traveler's Backpack](https://modrinth.com/mod/travelersbackpack/): Items with overlays will work in backpacks.
- [Sophisticated Backpacks](https://modrinth.com/mod/sophisticated-backpacks/versions): Items with overlays will work in backpacks.
- [Player Locator Plus](https://modrinth.com/mod/player-locator-plus): The locator bar is now only displayed when the player has a compass.
- [Better Player Locator Bar](https://modrinth.com/mod/bplb): The locator bar is now only displayed when the player has a compass.
- [Xaero's Minimap](https://modrinth.com/mod/xaeros-minimap): When a map item is set, it can be used inside a bundle.
- [Xaero's World Map](https://modrinth.com/mod/xaeros-world-map): When a map item is set, it can be used inside a bundle.
- [Map Atlases](https://modrinth.com/mod/map-atlases) (disabled by default, has issues): Atlases can be used inside a bundle.
- [Antique Atlas 4](https://modrinth.com/mod/antique-atlas-4): When a map item is required, it can be used inside a bundle.

## Credits

The concept for this mod came from a prototype by [nöelle](https://modrinth.com/user/noelledotjpg) for Raspberry Flavoured. I took this concept and iterated on it in my mod, [Raspberry Core](https://modrinth.com/mod/raspberry-core), before finalizing it here as Immersive Overlays.

Textures and mod icon are also by [nöelle](https://modrinth.com/user/noelledotjpg).

Textures for Temperature Overlays and Season Overlays by [ProbablyEkho](https://modrinth.com/user/ProbablyEkho).

Textures for No Man's Land, Abundant Atmosphere, and New World biome overlays by KevBo.

This repository contains remapped copies of [Antique Atlas 4](https://modrinth.com/mod/antique-atlas-4), as the Fabric version cannot be remapped by ModDevGradle.