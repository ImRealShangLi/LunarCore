package emu.lunarcore.data;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.reflections.Reflections;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import emu.lunarcore.LunarRail;
import emu.lunarcore.data.ResourceDeserializers.LunarRailDoubleDeserializer;
import emu.lunarcore.data.ResourceDeserializers.LunarRailHashDeserializer;
import emu.lunarcore.data.config.FloorInfo;
import emu.lunarcore.data.config.FloorInfo.FloorGroupSimpleInfo;
import emu.lunarcore.data.config.GroupInfo;
import emu.lunarcore.data.config.SkillAbilityInfo;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class ResourceLoader {
    private static boolean loaded = false;

    // Special gson factory we create for loading resources
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(double.class, new LunarRailDoubleDeserializer())
            .registerTypeAdapter(long.class, new LunarRailHashDeserializer())
            .create();

    // Load all resources
    public static void loadAll() {
        // Make sure we don't load more than once
        if (loaded) return;

        // Start loading resources
        loadResources();
        // Load floor infos after resources
        loadFloorInfos();
        // Load maze abilities
        loadMazeAbilities();

        // Done
        loaded = true;
    }

    private static List<Class<?>> getResourceDefClasses() {
        Reflections reflections = new Reflections(ResourceLoader.class.getPackage().getName());
        Set<?> classes = reflections.getSubTypesOf(GameResource.class);

        List<Class<?>> classList = new ArrayList<>(classes.size());
        classes.forEach(o -> {
            Class<?> c = (Class<?>) o;
            if (c.getAnnotation(ResourceType.class) != null) {
                classList.add(c);
            }
        });

        classList.sort((a, b) -> b.getAnnotation(ResourceType.class).loadPriority().value() - a.getAnnotation(ResourceType.class).loadPriority().value());

        return classList;
    }

    private static void loadResources() {
        for (Class<?> resourceDefinition : getResourceDefClasses()) {
            ResourceType type = resourceDefinition.getAnnotation(ResourceType.class);

            if (type == null) {
                continue;
            }

            @SuppressWarnings("rawtypes")
            Int2ObjectMap map = GameData.getMapForExcel(resourceDefinition);

            try {
                loadFromResource(resourceDefinition, type, map);
            } catch (Exception e) {
                LunarRail.getLogger().error("Error loading resource file: " + Arrays.toString(type.name()), e);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private static void loadFromResource(Class<?> c, ResourceType type, Int2ObjectMap map) throws Exception {
        int count = 0;

        for (String name : type.name()) {
            count += loadFromResource(c, type, name, map);
        }

        LunarRail.getLogger().info("Loaded " + count + " " + c.getSimpleName() + "s.");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <T> int loadFromResource(Class<T> c, ResourceType type, String fileName, Int2ObjectMap map) throws Exception {
        String file = LunarRail.getConfig().getResourceDir() + "/ExcelOutput/" + fileName;

        // Load reader from file
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            // Setup variables
            Stream<T> stream = null;

            // Determine format of json
            JsonElement json = JsonParser.parseReader(fileReader);

            if (json.isJsonArray()) {
                // Parse list
                List<T> excels = gson.fromJson(json, TypeToken.getParameterized(List.class, c).getType());
                stream = excels.stream();
            } else if (json.isJsonObject()) {
                // Check if object is map or a nested map
                boolean isMap = true;

                var it = json.getAsJsonObject().asMap().entrySet().iterator();
                if (it.hasNext()) {
                    var it2 = it.next().getValue().getAsJsonObject().asMap().entrySet().iterator();
                    String key = it2.next().getKey();
                    try {
                        Integer.parseInt(key);
                        isMap = false;
                    } catch (Exception ex) {

                    }
                }

                // Parse json
                if (isMap) {
                    // Map
                    Map<Integer, T> excels = gson.fromJson(json, TypeToken.getParameterized(Map.class, Integer.class, c).getType());
                    stream = excels.values().stream();
                } else {
                    // Nested Map
                    Map<Integer, Map<Integer, T>> excels = gson.fromJson(json, TypeToken.getParameterized(Map.class, Integer.class, TypeToken.getParameterized(Map.class, Integer.class, c).getType()).getType());
                    stream = excels.values().stream().flatMap(m -> m.values().stream());
                }
            } else {
                throw new Exception("Invalid excel file: " + fileName);
            }

            // Sanity check
            if (stream == null) return 0;

            // Mutable integer
            AtomicInteger count = new AtomicInteger();

            stream.forEach(o -> {
                GameResource res = (GameResource) o;
                res.onLoad();

                count.getAndIncrement();

                if (map != null) {
                    map.put(res.getId(), res);
                }
            });

            return count.get();
        }
    }
    
    // Might be better to cache
    private static void loadFloorInfos() {
        // Load floor infos
        File floorDir = new File(LunarRail.getConfig().getResourceDir() + "/Config/LevelOutput/Floor/");
        
        if (!floorDir.exists()) {
            LunarRail.getLogger().warn("Floor infos are missing, please check your resources.");
            return;
        }
        
        // Dump
        for (File file : floorDir.listFiles()) {
            try (FileReader reader = new FileReader(file)) {
                FloorInfo floor = gson.fromJson(reader, FloorInfo.class);
                String name = file.getName().substring(0, file.getName().indexOf('.'));
                GameData.getFloorInfos().put(name, floor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Load group infos
        for (FloorInfo floor : GameData.getFloorInfos().values()) {
            for (FloorGroupSimpleInfo simpleGroup : floor.getSimpleGroupList()) {
                File file = new File(LunarRail.getConfig().getResourceDir() + "/" + simpleGroup.getGroupPath());
                
                if (!file.exists()) {
                    continue;
                }
                
                // TODO optimize
                try (FileReader reader = new FileReader(file)) {
                    GroupInfo group = gson.fromJson(reader, GroupInfo.class);
                    group.setId(simpleGroup.getID());
                    floor.getGroups().put(simpleGroup.getID(), group);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            // Post load callback to cache floor info
            floor.onLoad();
        }
        
        // Done
        LunarRail.getLogger().info("Loaded " + GameData.getFloorInfos().size() + " FloorInfos.");
    }
    
    // Might be better to cache
    private static void loadMazeAbilities() {
        int count = 0;
        
        for (var avatarExcel : GameData.getAvatarExcelMap().values()) {
            // Get file
            File file = new File(LunarRail.getConfig().getResourceDir() + "/Config/ConfigAdventureAbility/LocalPlayer/LocalPlayer_" + avatarExcel.getNameKey() + "_Ability.json");
            if (!file.exists()) continue;
            
            try (FileReader reader = new FileReader(file)) {
                SkillAbilityInfo avatarSkills = gson.fromJson(reader, SkillAbilityInfo.class);
                
                if (avatarSkills.parse(avatarExcel)) {
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Done
        LunarRail.getLogger().info("Loaded " + count + " maze abilities for avatars");
    }
}
