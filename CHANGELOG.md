# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased

### Added
- Biome overlays, displayed when a map is in the inventory, that show the current biome. Additional biomes can be added via resource packs, with support for all of Minecraft's biomes included.
  - Mod support is in the works - Atmospheric, Quark, and Nature's Spirit have biomes included in this release. Always looking for contributions, and more can be added by modpackers.
  - Adding more biomes is simple. As an example, to add a texture to Dunes from Atmospheric (`atmospheric:dunes`), your resource pack should have a texture with the path `assets/atmospheric/textures/immersiveoverlays/dunes.png`.
  - Immersive Overlays also has a system for redirects - allowing multiple biomes to use the same texture. To add a redirect to Flourishing Dunes from Atmospheric (`atmospheric:flourishing_dunes`) so that it reuses the texture of Dunes, your resource pack should have a text file with the path `assets/atmospheric/textures/immersiveoverlays/dunes.txt`, with the contents `atmospheric:dunes`.
- Temperature overlays, displayed when a thermometer is in the inventory, that show the current temperature.
  - In vanilla, this data is sourced from the biome's temperature.
  - With [Oreganized](https://modrinth.com/mod/oreganized) installed, this data is sourced from the Thermometer.
  - With [Tough as Nails](https://modrinth.com/mod/tough-as-nails) installed, this data is sourced from the player's temperature.
- Season overlays, displayed when a calendar is in the inventory, that show the current season.
  - Supports Serene Seasons, Fabric Seasons, and Simple Seasons.
- Added support for detecting overlay items in Curios slots (1.20+).
- Added support for detecting overlay items in Traveler's Backpacks.
- Added support for detecting overlay items in Sophisticated Backpacks.
- Added support for Xaero's World Map in parity with the existing support for Xaero's Minimap.
- Option to show the current day in the clock's overlay.
- Option to show overlays without an item required.
- Option to show overlays when F3 is open.
- Option to fix overlays in a specific position.
- Individual options to left align particular overlays.
- X/Z overlay items can now be set independently.
- New config option to allow compasses in bundles in Shulkers to function again.

### Changed
- Clock overlays no longer function in the Nether.
- Overlays are now placed on individual layers, helping compatibility with mods like Raised.

### Fixed
- Overlays are now properly hidden by F1.
- Weather overlays no longer move randomly.
- Backpacks function again (1.21.5).

## [1.2.0] - 04-30-2025

### Added
- Support for overlay items in backpacks and other armour slots.
- Support for overlay items in accessory slots from Accessories.
- When Accessorify is present, Immersive Overlays will replace Accessorify overlays with Immersive Overlays. This can be disabled in the config.

## [1.1.1] - 04-24-2025

### Fixed
- Rendering issued with Raised on NeoForge.

## [1.1] - 04-25-2025

### Added
- Players with [Player Locator Plus](https://modrinth.com/mod/player-locator-plus) or [Better Player Locator Bar](https://modrinth.com/mod/bplb) installed now require a compass item in the inventory to display the bar. This can be disabled in the config.

### Changed
- Compat now has its own section in the config.
- Xaero's Minimap compat can now be disabled.

### Fixed
- Registry code loading too early on Fabric.
- No longer crashes on Forge and NeoForge servers.
- Clipping issues with weather-only and coordinate overlays.
- Map Atlases being installed no longer causes coordinate overlays to not display.

## [1.0.1] - 04-20-2025

### Added
- Config option to disable effects moving overlays.

### Fixed
- No longer clips with negative effects when Map Atlases is installed.
- Dyed sacks now show overlays correctly.
- Registry code loading too early on Forge.

## [1.0.0] - 04-17-2025

Initial release.