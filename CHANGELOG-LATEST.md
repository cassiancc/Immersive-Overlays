### Added
- Reworked buildscript to restore 1.20.1 and 1.21.5 targets. This includes support for Ecliptic Seasons and TerraFirmaCraft on 1.20.1.
- Clock, Compass, and Biome overlays can now have their text colour changed.
- Added support for detecting items in [Trinkets](https://modrinth.com/mod/trinkets) slots.
- Jade overlay can now have the activation item changed, the new default config also includes Goggles from Create.
- Compatibility with [Thermoo](https://modrinth.com/mod/thermoo), displaying temperature.

### Changed
- Various compatibility checks for mods are now loader specific where necessary (e.g. you will no longer see a nonfunctional Fabric Seasons toggle as an option when running Immersive Overlays for Forge).

### Fixed
- Temperature overlay now shows correct information when Cold Sweat is configured to Celsius.