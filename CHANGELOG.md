# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased

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