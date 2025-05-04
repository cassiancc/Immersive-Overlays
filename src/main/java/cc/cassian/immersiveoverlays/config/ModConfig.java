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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ModConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    private static ModConfig INSTANCE = new ModConfig();
    //General settings
    public boolean compass_enable = true;
    public boolean hide_from_debug = true;
    public int compass_vertical_position = 105;
    public List<String> compass_items = List.of("minecraft:compass", "minecraft:recovery_compass", "spelunkery:magnetic_compass");
    public List<String> compass_depth_items = List.of("spelunkery:depth_gauge","caverns_and_chasms:depth_gauge","additionaladditions:depth_meter","supplementaries:altimeter","depthmeter:depthmeter");
    public boolean compass_hide_when_similar_mods_present = true;
    public String compass_x_colour = "Red";
    public String compass_y_colour = "Green";
    public String compass_z_colour = "Blue";
    public boolean clock_enable = true;
    public int clock_vertical_position = 90;
    public List<String> clock_items = List.of("minecraft:clock");
    public List<String> clock_weather_items = List.of("caverns_and_chasms:barometer");
    public boolean clock_24_hour = true;
    public boolean require_item_in_hand = false;
    public boolean search_containers = true;
    public boolean render_background = true;
    public boolean align_left = false;
    public boolean moved_by_effects = true;
    public boolean compat_playerlocatorplus = true;
    public boolean compat_xaeros = true;
    public boolean compat_map_atlases = false;
    public boolean compat_accessorify = true;
    public boolean biome_enable = true;
    public int biome_vertical_position = 150;


    public static void load() {
        if (!Files.exists(configPath())) {
            save();
            return;
        }

        try (var input = Files.newInputStream(configPath())) {
            INSTANCE = GSON.fromJson(new InputStreamReader(input, StandardCharsets.UTF_8), ModConfig.class);
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