package mcjty.fxcontrol;

import com.google.gson.*;
import mcjty.fxcontrol.rules.EffectRule;
import mcjty.fxcontrol.rules.HarvestRule;
import mcjty.fxcontrol.rules.PlaceRule;
import mcjty.fxcontrol.rules.RightClickRule;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RulesManager {

    private static String path;
    public static List<EffectRule> effectRules = new ArrayList<>();
    public static List<HarvestRule> harvestRules = new ArrayList<>();
    public static List<PlaceRule> placeRules = new ArrayList<>();
    public static List<RightClickRule> rightclickRules = new ArrayList<>();

    public static void reloadRules() {
        effectRules.clear();
        harvestRules.clear();
        placeRules.clear();
        rightclickRules.clear();
        readAllRules();
    }

    public static void setRulePath(File directory) {
        path = directory.getPath();
    }

    public static void readRules() {
        readAllRules();
    }

    private static boolean exists(String file) {
        File f = new File(file);
        return f.exists() && !f.isDirectory();
    }

    private static void readAllRules() {
        readRules(path, "effects.json", EffectRule::parse, effectRules);
        readRules(path, "breakevents.json", HarvestRule::parse, harvestRules);
        readRules(path, "placeevents.json", PlaceRule::parse, placeRules);
        readRules(path, "rightclicks.json", RightClickRule::parse, rightclickRules);
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
                FxControl.logger.log(Level.ERROR, "Rule " + i + " in " + filename + " is invalid, skipping!");
            }
            i++;
        }
        FxControl.logger.log(Level.INFO, "Loaded " + i + " rules!");
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

        FxControl.logger.log(Level.INFO, "Reading rules from " + filename);
        InputStream inputstream = null;
        try {
            inputstream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            FxControl.logger.log(Level.ERROR, "Error reading " + filename + "!");
            return null;
        }

        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            FxControl.logger.log(Level.ERROR, "Error reading " + filename + "!");
            return null;
        }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(br);

        return element;
    }

    private static void makeEmptyRuleFile(File file) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            FxControl.logger.log(Level.ERROR, "Error writing " + file.getName() + "!");
            return;
        }
        JsonArray array = new JsonArray();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        writer.print(gson.toJson(array));
        writer.close();
    }


}
