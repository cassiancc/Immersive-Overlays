package cc.cassian.immersiveoverlays;

import cc.cassian.immersiveoverlays.helpers.ModHelpers;

public class ModCompat {
    public static final boolean MAP_ATLASES;
    public static final boolean CAVERNS_AND_CHASMS = false;

    static {
        MAP_ATLASES = ModHelpers.isLoaded("map_atlases");
    }
}
