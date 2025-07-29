package cc.cassian.immersiveoverlays.config;

import cc.cassian.immersiveoverlays.ModClient;
import cc.cassian.immersiveoverlays.helpers.ModLists;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.architectury.injectables.annotations.ExpectPlatform;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ModConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    private static ModConfig INSTANCE = new ModConfig();
    //General settings

    // coords
    public boolean compass_enable = true;
    public int compass_vertical_position = 125;
    public boolean compass_horizontal_position_left = false;
    public List<String> compass_x_items = List.of("minecraft:compass", "minecraft:recovery_compass", "spelunkery:magnetic_compass", "firmaciv:nav_clock", "firmaciv:firmaciv_compass");
    public List<String> compass_y_items = List.of("spelunkery:depth_gauge","caverns_and_chasms:depth_gauge","additionaladditions:depth_meter","supplementaries:altimeter","depthmeter:depthmeter");
    public List<String> compass_z_items = List.of("firmaciv:sextant");
    public boolean compass_hide_when_similar_mods_present = true;
    public String compass_x_colour = "Red";
    public String compass_y_colour = "Green";
    public String compass_z_colour = "Blue";
    // time
    public boolean clock_enable = true;
    public boolean clock_24_hour = true;
    public int clock_vertical_position = 90;
    public boolean clock_horizontal_position_left = false;
    public List<String> clock_items = List.of("minecraft:clock");
    public List<String> clock_weather_items = List.of("caverns_and_chasms:barometer", "firmaciv:barometer");
    // general
    public boolean hide_from_debug = true;
    public boolean require_item_in_hand = false;
    public boolean search_containers = true;
    public boolean search_containers_for_containers = true;
    public boolean render_background = true;
    public boolean moved_by_effects = true;
    // compat
    public boolean compat_playerlocatorplus = true;
    public boolean compat_xaeros = true;
    public boolean compat_map_atlases = false;
    public boolean compat_accessorify = true;
    public boolean compat_oreganized_temperature = true;
    public boolean compat_tough_as_nails_temperature = true;
    // biomes
    public boolean biome_enable = true;
    public boolean biome_reduced_info = false;
    public int biome_vertical_position = 112;
    public boolean biome_horizontal_position_left = false;
    public List<String> biome_items = List.of("minecraft:map", "minecraft:empty_map");
    // temperature
    public boolean temperature_enable = true;
    public boolean temperature_horizontal_position_left = true;
    public List<String> temperature_items = List.of("oreganized:thermometer");
    public int temperature_vertical_position = 112;
    // version
    public int version = ModClient.CONFIG_VERSION;


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

    @ExpectPlatform
    static Path configPath() {
        throw new AssertionError();
    }
}