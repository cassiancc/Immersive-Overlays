package cc.cassian.immersiveoverlays.config;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.Platform;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static cc.cassian.immersiveoverlays.ModClient.MOD_ID;

public class ModConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    private static ModConfig INSTANCE = new ModConfig();
    // version
    public int version = ModClient.CONFIG_VERSION;
    //General settings
    public boolean enabled = true;
    //? if >1.21.5
    /*public boolean locator_bar = true;*/
    public boolean avoid_overlapping = true;
    public boolean moved_by_effects = true;
    public boolean require_item = true;
    public boolean require_item_in_hand = false;
    public boolean search_containers = true;
    public boolean search_containers_for_containers = true;
    public boolean render_background = true;
    public boolean hide_from_debug = true;
    // coords
    public boolean compass_enable = true;
    public int compass_vertical_position = 140;
    public boolean compass_horizontal_position_left = false;
    public int compass_text_colour = 14737632;
    public String compass_x_colour = "Red";
    public String compass_y_colour = "Green";
    public String compass_z_colour = "Blue";
    public List<String> compass_x_items = List.of("minecraft:compass", "minecraft:recovery_compass", "spelunkery:magnetic_compass", "firmaciv:nav_clock", "firmaciv:firmaciv_compass");
    public List<String> compass_y_items = List.of("spelunkery:depth_gauge","caverns_and_chasms:depth_gauge","additionaladditions:depth_meter","supplementaries:altimeter","depthmeter:depthmeter");
    public List<String> compass_z_items = List.of("firmaciv:sextant");

    // time
    public boolean clock_enable = true;
    public boolean clock_current_time = true;
    public boolean clock_day_count = false;
    public boolean clock_24_hour = true;
    public boolean clock_seasons = true;
    public int clock_text_colour = 14737632;
    public int clock_vertical_position = 85;
    public boolean clock_horizontal_position_left = false;
    public List<String> clock_items = List.of("minecraft:clock");
    public List<String> clock_weather_items = List.of("caverns_and_chasms:barometer", "firmaciv:barometer");
    // temperature
    public boolean temperature_enable = true;
    public boolean temperature_icons = true;
    public int temperature_hot_colour = 16759694;
    public int temperature_cold_colour = 9371647;
    public int temperature_temperate_colour = 16777215;
    public int temperature_vertical_position = 112;
    public boolean temperature_horizontal_position_left = true;
    public List<String> temperature_items = List.of("oreganized:thermometer", "toughasnails:thermometer", "legendarysurvivaloverhaul:thermometer", "cold_sweat:thermometer");
    // compat
    //? if fabric
    public boolean compat_playerlocatorplus = true;
    public boolean compat_xaeros = true;
    public boolean compat_antique_atlas = true;
    public boolean compat_accessorify = true;
    //? if (forge && =1.20.1) || (neoforge && =1.21.1) {
    /*public boolean compat_oreganized_temperature = true;
    *///?}
    public boolean compat_tough_as_nails_temperature = true;
    //? if forge || neoforge {
    /*public boolean compat_cold_sweat_temperature = true;
    *///?}
    //? if forge {
    /*public boolean compat_legendary_survival_overhaul_temperature = true;
     *///?}
    //? if fabric
    public boolean compat_thermoo_temperature = true;
    // biomes
    public boolean biome_enable = true;
    public boolean biome_icons = true;
    public boolean biome_reduced_info = false;
    public boolean biome_text_tinted = true;
    public int biome_text_colour = 14737632;
    public int biome_vertical_position = 112;
    public boolean biome_horizontal_position_left = false;
    public List<String> biome_items = List.of("minecraft:map", "minecraft:empty_map", "map_atlases:atlas", "map_atlases:end_atlas", "map_atlases:nether_atlas",  "naturescompass:naturescompass", "antiqueatlas:antique_atlas");
    // seasons
    public List<String> season_items = List.of("sereneseasons:calendar", "seasonsextras:season_calendar", "eclipticseasons:calendar");
    //speed
    public boolean speed_enable = true;
    public int speed_vertical_position = 140;
    public boolean speed_horizontal_position_left = true;
    public int speed_colour = 0xc7bf81;
    public List<String> speed_items = List.of("oreganized:speedometer", "speedometer:speedometer");
    public boolean compat_oreganized_speed = true;
    public boolean compat_jade = false;
    public List<String> compat_waila_items = List.of("minecraft:spyglass", "create:goggles");
    public boolean compat_map_atlases = false;
    public boolean compat_serene_seasons = true;
    //? if fabric {
    public boolean compat_fabric_seasons = true;
    public boolean compat_simple_seasons = true;
    //?}
    //? if forge || neoforge {
    /*public boolean compat_tfc_seasons = true;
    public boolean compat_ecliptic_seasons = true;
    *///?}


    public static void load() {
        if (!Files.exists(configPath())) {
            save();
            return;
        }
        try {
            String s = Files.readString(configPath(), Charset.defaultCharset());
            if (!s.contains("version")) {
                save();
                return;
            }
        } catch (IOException e) {
            ModClient.LOGGER.warn("Upgrading config file to version: " + ModClient.CONFIG_VERSION);
        }

        try (var input = Files.newInputStream(configPath())) {
            INSTANCE = GSON.fromJson(new InputStreamReader(input, StandardCharsets.UTF_8), ModConfig.class);
            if (INSTANCE.version != ModClient.CONFIG_VERSION) {
                INSTANCE = new ModConfig();
                save();
            }
        } catch (IOException e) {
            ModClient.LOGGER.warn("Unable to load config file!");
        }
    }

    public static void save() {
        try (var output = Files.newOutputStream(configPath()); var writer = new OutputStreamWriter(output, StandardCharsets.UTF_8)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            ModClient.LOGGER.warn("Unable to save config file!");
        }
        ModLists.loadLists();
    }

    public static ModConfig get() {
        if (INSTANCE == null) INSTANCE = new ModConfig();
        return INSTANCE;
    }


    static Path configPath() {
        return Platform.INSTANCE.configPath().resolve(MOD_ID + ".json");
    }
}