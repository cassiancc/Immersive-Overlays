package cc.cassian.immersiveoverlays.compat;

import cc.cassian.asphodel.Platform;

public class ModCompat {
    /**
     * Breezy - used for Wind Direction Overlays.
     * Forge
     */
    public static boolean BREEZY = Platform.INSTANCE.isLoaded("breezy");
    /**
     * Cold Sweat - used for Temperature Overlays.
     * Forge/NeoForge
     */
    public static final boolean COLD_SWEAT = Platform.INSTANCE.isLoaded("cold_sweat");
    /**
     * Ecliptic Seasons - used for Season Overlays.
     * Forge/NeoForge
     */
    public static final boolean ECLIPTIC_SEASONS = Platform.INSTANCE.isLoaded("eclipticseasons");
    /**
	 * Enhanced Celestials - used for Moon Overlays.
     * Multiplatform
	 */
    public static final boolean ENHANCED_CELESTIALS = Platform.INSTANCE.isLoaded("enhancedcelestials");
    /**
     * Enhanced Celestials 2 - used for Moon Overlays.
     * Multiplatform
     */
    public static final boolean ENHANCED_CELESTIALS_2 = Platform.INSTANCE.isLoaded("enhancedcelestials2core");
    /**
     * Fabric API - used for its client tag API.
     * Multiplatform (Forgified Fabric API)
     */
    public static final boolean FABRIC_API = Platform.INSTANCE.isLoaded("fabric-api") || Platform.INSTANCE.isLoaded("fabric_api");
    /**
     * Fabric Seasons - used for Season Overlays.
     * Fabric
     */
    public static final boolean FABRIC_SEASONS = Platform.INSTANCE.isLoaded("seasons");
    /**
     * Legendary Survival Overhaul - used for Temperature Overlays.
     * Forge
     */
    public static final boolean LEGENDARY_SURVIVAL_OVERHAUL = Platform.INSTANCE.isLoaded("legendarysurvivaloverhaul");
    /**
     * Lunar - used for Moon Overlays.
     * Multiplatform
     */
    public static final boolean LUNAR = Platform.INSTANCE.isLoaded("lunar");
    /**
     * Map Atlases - a minimap mod modified to check for items inside containers.
     * Multiplatform
     */
    public static final boolean MAP_ATLASES = Platform.INSTANCE.isLoaded("map_atlases");
    /**
     * Oreganized - used for Temperature and Speed Overlays.
     * Forge/NeoForge
     */
    public static final boolean OREGANIZED = Platform.INSTANCE.isLoaded("oreganized");
    /**
     * Serene Seasons - used for Season Overlays.
     */
    public static final boolean SERENE_SEASONS = Platform.INSTANCE.isLoaded("sereneseasons");
    /**
     * Simple Seasons - used for Season Overlays.
     * Fabric
     */
    public static final boolean SIMPLE_SEASONS = Platform.INSTANCE.isLoaded("simple-seasons");
    /**
     * TerraFirmaCraft - used for Season Overlays.
     * Forge/NeoForge
     */
    public static final boolean TERRAFIRMACRAFT = Platform.INSTANCE.isLoaded("tfc");
    /**
     * Thermoo - used for Temperature Overlays.
     * Fabric
     */
    public static final boolean THERMOO = Platform.INSTANCE.isLoaded("thermoo");
    /**
     * Tough as Nails - used for Temperature Overlays.
     */
    public static final boolean TOUGH_AS_NAILS = Platform.INSTANCE.isLoaded("toughasnails");
}
