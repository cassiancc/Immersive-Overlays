package cc.cassian.immersiveoverlays;

import cc.cassian.immersiveoverlays.helpers.ModHelpers;

public class ModCompat {
    public static final boolean MAP_ATLASES;
    public static final boolean CAVERNS_AND_CHASMS;
    public static final boolean SPELUNKERY;
    public static final boolean SUPPLEMENTARIES;

    static {
        MAP_ATLASES = ModHelpers.isLoaded("map_atlases");
        SUPPLEMENTARIES = ModHelpers.isLoaded("supplementaries");
        CAVERNS_AND_CHASMS = ModHelpers.isLoaded("caverns_and_chasms");
        SPELUNKERY = ModHelpers.isLoaded("spelunkery");

    }
}
