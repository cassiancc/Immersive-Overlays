### Added
- Day count has been modified to be a separate item list.

### Changed
- Overlay background rendering has been adjusted on 1.21+ to no longer require drawing an endcap.
- Textures in `immersiveoverlays/textures/gui` have been moved to `immersiveoverlays/textures/gui/sprites`. The exception to this is `overlay.png`, which is now only used for background rendering on 1.20.
- Weather sprites are now located in `immersiveoverlays/textures/gui/sprites` allowing them to be more easily modified with resource packs.

### Fixed
- Crash on release 26.1.
- Offscreen coordinates with single line coordinate rendering and biome overlay.

### Removed
- 1.21.11 support.