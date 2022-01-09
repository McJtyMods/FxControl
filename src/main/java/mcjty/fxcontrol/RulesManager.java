package mcjty.fxcontrol;

import com.google.gson.*;
import mcjty.fxcontrol.rules.*;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RulesManager {

    private static String path;
    public static List<EffectRule> effectRules = new ArrayList<>();
    public static List<HarvestRule> harvestRules = new ArrayList<>();
    public static List<PlaceRule> placeRules = new ArrayList<>();
    public static List<RightClickRule> rightclickRules = new ArrayList<>();
    public static List<LeftClickRule> leftclickRules = new ArrayList<>();

    public static void reloadRules() {
        effectRules.clear();
        harvestRules.clear();
        placeRules.clear();
        rightclickRules.clear();
        leftclickRules.clear();
        readAllRules();
    }

    public static void setRulePath(Path path) {
        RulesManager.path = path.toString();
    }

    public static void readRules() {
        readAllRules();
    }

    private static boolean exists(String file) {
        File f = new File(file);
        return f.exists() && !f.isDirectory();
    }

    private static void readAllRules() {
        File directory = new File(path + File.separator + "fxcontrol");
        if (!directory.exists()) {
            directory.mkdir();
        }

        safeCall("effects.json", () -> readRules(path, "effects.json", EffectRule::parse, effectRules));
        safeCall("breakevents.json", () -> readRules(path, "breakevents.json", HarvestRule::parse, harvestRules));
        safeCall("placeevents.json", () -> readRules(path, "placeevents.json", PlaceRule::parse, placeRules));
        safeCall("rightclicks.json", () -> readRules(path, "rightclicks.json", RightClickRule::parse, rightclickRules));
        safeCall("leftclicks.json", () -> readRules(path, "leftclicks.json", LeftClickRule::parse, leftclickRules));
    }

    private static void safeCall(String name, Runnable code) {
        try {
            code.run();
        } catch (Exception e) {
            ErrorHandler.error("JSON error in '" + name + "': check log for details (" + e.getMessage() + ")");
            FxControl.setup.getLogger().log(Level.ERROR, "Error parsing '" + name + "'", e);
        }
    }

    private static <T> void readRules(String path, String filename, Function<JsonElement, T> parser, List<T> rules) {
        JsonElement element = getRootElement(path, filename);
        if (element == null) {
            return;
        }
        int i = 0;
        for (JsonElement entry : element.getAsJsonArray()) {
            T rule = parser.apply(entry);
            if (rule != null) {
                rules.add(rule);
            } else {
                FxControl.setup.getLogger().log(Level.ERROR, "Rule " + i + " in " + filename + " is invalid, skipping!");
            }
            i++;
        }
        FxControl.setup.getLogger().log(Level.INFO, "Loaded " + i + " rules!");
    }

    private static JsonElement getRootElement(String path, String filename) {
        File file;
        if (path == null) {
            file = new File(filename);
        } else {
            file = new File(path + File.separator + "fxcontrol", filename);
        }
        if (!file.exists()) {
            // Create an empty rule file
            makeEmptyRuleFile(file);
            return null;
        }

        FxControl.setup.getLogger().log(Level.INFO, "Reading rules from " + filename);
        InputStream inputstream = null;
        try {
            inputstream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            FxControl.setup.getLogger().log(Level.ERROR, "Error reading " + filename + "!");
            return null;
        }

        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            FxControl.setup.getLogger().log(Level.ERROR, "Error reading " + filename + "!");
            return null;
        }

        JsonParser parser = new JsonParser();

        return parser.parse(br);
    }

    private static void makeEmptyRuleFile(File file) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            FxControl.setup.getLogger().log(Level.ERROR, "Error writing " + file.getName() + "!");
            return;
        }
        JsonArray array = new JsonArray();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        writer.print(gson.toJson(array));
        writer.close();
    }


}
