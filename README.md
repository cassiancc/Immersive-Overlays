# Immersive Overlays

<a href='https://modrinth.com/mod/immersive-overlays/versions?l=fabric'><img alt="fabric" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/fabric_vector.svg"></a>
<a href='https://modrinth.com/mod/immersive-overlays/versions?l=neoforge&l=forge'><img alt="forge" height="56" src="https://raw.githubusercontent.com/cassiancc/Cassians-Badges/refs/heads/main/cozy/NeoForge.svg"></a>
<a href='https://www.curseforge.com/minecraft/modpacks/raspberry-flavoured'><img alt="Raspberry Flavoured" height="56" src="https://raw.githubusercontent.com/cassiancc/Cassians-Badges/refs/heads/main/cozy/Raspberry-Flavoured.svg?"></a>

This mod integrates a highly configurable coordinate overlay as an in-world mechanic, allowing it to only be visible as long as the player has a compass. In addition, it offers a similar overlay for clocks, showing the time of day.

## Coordinate Overlays

Coordinate overlays for the player's X and Z coordinates are visible whenever the player has a compass in their inventory, including in a bundle.

When a player additionally has an item that displays depth, like a Supplementaries Altimeter or a Depth Gauge from mods like Caverns and Chasms or Spelunkery, the Y coordinate value is shown as well.

## Time and Weather Overlays

An overlay displaying the current time of day is displayed whenever the player has a clcok in their inventory, including in a bundle.

When a player additionally has an item that displays the weather, like a Caverns and Chasms Barometer, the weather is shown as well.

## Mod Compatibility

The mod has support for Spelunkery, Caverns and Chasms, and Supplementaries items out of the box. For other mods, items can be added to the mod's various item tags.
- `immersiveoverlays:shows_xz` controls what items show X/Z overlays.
- `immersiveoverlays:shows_y` controls what items show Y overlays.
- `immersiveoverlays:shows_time` controls what items show time overlays.
- `immersiveoverlays:shows_weather` controls what items show weather overlays.
- `immersiveoverlays:containers` controls what items are treated as containers.

For consistency, the mod also allows atlases from [Map Atlases](https://github.com/MehVahdJukaar/mapatlases-neoforge) to be used inside of bundles.

## Credits

The concept for this mod came from a prototype by [nöelle](https://modrinth.com/user/noelledotjpg) for Raspberry Flavoured. I took this concept and iterated on it in my mod, [Raspberry Core](https://modrinth.com/mod/raspberry-core), before finalizing it here as Immersive Overlays.