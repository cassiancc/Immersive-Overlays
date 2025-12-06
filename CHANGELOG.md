# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.4.6]

### Added
- Keybind for opening mod configurations.
- Support for Sophisticated Backpacks on 1.21.10 NeoForge.

### Fixed
- Legendary Survival Overhaul compat on NeoForge.
- Clock overlay placement when weather icon is not visible.

## [1.4.5]

### Added
- Debug option to disable end cap rendering (might improve the appearance of some custom resource packs)

### Fixed
- Filled maps now show biome overlays.
- Basalt Deltas now show their biome icon.
- Crash on newly released Traveler's Backpacks for 1.21.10.

## [1.4.4]

### Added
- Aliases for the Sparse Jungle biome and some Wilder Wild biomes.

### Changed
- Accessorify support has been removed as Accessorify now handles Immersive Overlays support itself.

### Fixed
- Ecliptic Seasons and Curios support now works correctly on 1.21.1 NeoForge.


## [1.4.3] - 2025-10-16

### Fixed

- Late season overlays not working correctly.

## [1.4.2] - 2025-10-16

### Added
- River, Frozen River, Ice Spikes, and Snowy Beach biome overlay textures. (@noelledotjpg)
- Biome overlay textures for Clifftree (kelpiesaurus)

### Changed
- Season, Temperature, and Speed mod compat options are now in their individual sections instead of the general compat category.

### Fixed
- Clock Overlay placement being off.
- Oreganized 1.21.1 integration not working correctly.
- Fallback text on biome overlays.

## [1.4.1] - 2025-10-14

### Added
- Support for detecting [Antique Atlas 4](https://modrinth.com/mod/antique-atlas-4) items in containers. This requires the mod to be configured to require an item, and will work when the mod is remapped by Sinytra Connector.
- A global "on/off" setting has been added to the config, which can be changed via a keybind (default binding is disabled).

### Fixed
- Locator bar toggle now uses the correct config.
- Issues with mod loading on NeoForge.

## [1.4.0] - 2025-10-13

### Added
- Reworked buildscript to temporarily restore 1.19.2, 1.20.1 and 1.21.5 targets. This includes support for Ecliptic Seasons and TerraFirmaCraft on 1.20.1.
- Clock, Compass, and Biome overlays can now have their text colour changed.
- Added support for detecting items in [Trinkets](https://modrinth.com/mod/trinkets) slots.
- Jade overlay can now have the activation item changed, the new default config also includes Goggles from Create.
- Compatibility with [Thermoo](https://modrinth.com/mod/thermoo), displaying temperature.
- Support for detecting temperature based off biome client tags from [Forgified Fabric API](https://modrinth.com/mod/forgified-fabric-api).
- Season Overlays now shows sub-season and tropical season information from Serene Seasons.

### Changed
- Various compatibility checks for mods are now loader specific where necessary (e.g. you will no longer see a nonfunctional Fabric Seasons toggle as an option when running Immersive Overlays for Forge).

### Fixed
- Temperature overlay now shows correct information when Cold Sweat is configured to Celsius.
- Reduced Biome Info overlapping with coordinates.
- Time and Season overlay overlapping.

## [1.3.2] - 2025-10-01

### Added
- Support for seasons from Ecliptic Seasons and TerraFirmaCraft.

### Fixed
- Missing config translation key.

## [1.3.1] - 2025-09-26

### Added
- Support for biomes from No Man's Land, Abundant Atmosphere, and New World.
- Support for detecting temperature based off biome tags on Fabric.
- Support for disabling Jade's overlay until a Spyglass is the inventory.
- Added Map Atlases to the list of items that trigger Biome Overlays.

## Changed
- Updated Accessorify support to 2.2.1.

### Fixed
- Issues with locator bar support on NeoForge 1.21.8.


## [1.3.0] - 2025-09-08

### Added
- Biome overlays, displayed when a map is in the inventory, that show the current biome. Additional biomes can be added via resource packs, with support for all of Minecraft's biomes included.
  - Mod support is in the works - Atmospheric, Quark, and Nature's Spirit have biomes included in this release. Always looking for contributions, and more can be added by modpackers.
  - Adding more biomes is simple. As an example, to add a texture to Dunes from Atmospheric (`atmospheric:dunes`), your resource pack should have a texture with the path `assets/atmospheric/textures/immersiveoverlays/dunes.png`.
  - Immersive Overlays also has a system for redirects - allowing multiple biomes to use the same texture. To add a redirect to Flourishing Dunes from Atmospheric (`atmospheric:flourishing_dunes`) so that it reuses the texture of Dunes, your resource pack should have a text file with the path `assets/atmospheric/textures/immersiveoverlays/dunes.txt`, with the contents `atmospheric:dunes`.
- Temperature overlays, displayed when a thermometer is in the inventory, that show the current temperature.
  - In vanilla, this data is sourced from the biome's temperature.
  - With [Oreganized](https://modrinth.com/mod/oreganized) installed, this data is sourced from the Thermometer.
  - With [Tough as Nails](https://modrinth.com/mod/tough-as-nails) installed, this data is sourced from the player's temperature.
  - With [Cold Sweat](https://modrinth.com/mod/cold-sweat/) installed, this data is sourced from the player's temperature.
  - With [Legendary Survival Overhaul](https://www.curseforge.com/minecraft/mc-mods/legendary-survival-overhaul) installed, this data is sourced from the player's temperature.
- Season overlays, displayed when a calendar is in the inventory, that show the current season.
  - Supports [Serene Seasons](https://modrinth.com/mod/serene-seasons), [Fabric Seasons](https://modrinth.com/mod/fabric-seasons), and [Simple Seasons](https://modrinth.com/mod/simple-seasons).
- Speed overlays, displayed when a speedometer is in the inventory, that display the current speed.
  - Supports [Oreganized](https://modrinth.com/mod/oreganized).
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

## [1.2.0] - 2025-04-30

### Added
- Support for overlay items in backpacks and other armour slots.
- Support for overlay items in accessory slots from Accessories.
- When Accessorify is present, Immersive Overlays will replace Accessorify overlays with Immersive Overlays. This can be disabled in the config.

## [1.1.1] - 2025-04-24

### Fixed
- Rendering issued with Raised on NeoForge.

## [1.1] - 2025-04-25

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

## [1.0.1] - 2025-04-20

### Added
- Config option to disable effects moving overlays.

### Fixed
- No longer clips with negative effects when Map Atlases is installed.
- Dyed sacks now show overlays correctly.
- Registry code loading too early on Forge.

## [1.0.0] - 2025-04-17

Initial release.