package mcjty.fxcontrol.rules.support;

import mcjty.fxcontrol.typed.Key;
import mcjty.fxcontrol.typed.Type;

public interface RuleKeys {

    // Inputs
    Key<Integer> MINTIME = Key.create(Type.INTEGER, "mintime");
    Key<Integer> MAXTIME = Key.create(Type.INTEGER, "maxtime");
    Key<Integer> MINLIGHT = Key.create(Type.INTEGER, "minlight");
    Key<Integer> MAXLIGHT = Key.create(Type.INTEGER, "maxlight");
    Key<Integer> MINHEIGHT = Key.create(Type.INTEGER, "minheight");
    Key<Integer> MAXHEIGHT = Key.create(Type.INTEGER, "maxheight");
    Key<Float> MINDIFFICULTY = Key.create(Type.FLOAT, "mindifficulty");
    Key<Float> MAXDIFFICULTY = Key.create(Type.FLOAT, "maxdifficulty");
    Key<Float> MINSPAWNDIST = Key.create(Type.FLOAT, "minspawndist");
    Key<Float> MAXSPAWNDIST = Key.create(Type.FLOAT, "maxspawndist");
    Key<Float> RANDOM = Key.create(Type.FLOAT, "random");
    Key<Boolean> SEESKY = Key.create(Type.BOOLEAN, "seesky");
    Key<String> WEATHER = Key.create(Type.STRING, "weather");
    Key<String> TEMPCATEGORY = Key.create(Type.STRING, "tempcategory");
    Key<String> DIFFICULTY = Key.create(Type.STRING, "difficulty");
    Key<String> BLOCK = Key.create(Type.STRING, "block");
    Key<String> BIOME = Key.create(Type.STRING, "biome");
    Key<String> BIOMETYPE = Key.create(Type.STRING, "biometype");
    Key<String> STRUCTURE = Key.create(Type.STRING, "structure");
    Key<Integer> DIMENSION = Key.create(Type.INTEGER, "dimension");

    Key<String> HELMET = Key.create(Type.STRING, "helmet");
    Key<String> CHESTPLATE = Key.create(Type.STRING, "chestplate");
    Key<String> LEGGINGS = Key.create(Type.STRING, "leggings");
    Key<String> BOOTS = Key.create(Type.STRING, "boots");
    Key<String> HELDITEM = Key.create(Type.STRING, "helditem");
    Key<String> OFFHANDITEM = Key.create(Type.STRING, "offhanditem");
    Key<String> BOTHHANDSITEM = Key.create(Type.STRING, "bothhandsitem");

    Key<Boolean> INCITY = Key.create(Type.BOOLEAN, "incity");
    Key<Boolean> INBUILDING = Key.create(Type.BOOLEAN, "inbuilding");
    Key<Boolean> INSTREET = Key.create(Type.BOOLEAN, "instreet");
    Key<Boolean> INSPHERE = Key.create(Type.BOOLEAN, "insphere");

    Key<String> GAMESTAGE = Key.create(Type.STRING, "gamestage");

    Key<Boolean> SUMMER = Key.create(Type.BOOLEAN, "summer");
    Key<Boolean> WINTER = Key.create(Type.BOOLEAN, "winter");
    Key<Boolean> SPRING = Key.create(Type.BOOLEAN, "spring");
    Key<Boolean> AUTUMN = Key.create(Type.BOOLEAN, "autumn");

    Key<String> AMULET = Key.create(Type.STRING, "amulet");
    Key<String> RING = Key.create(Type.STRING, "ring");
    Key<String> BELT = Key.create(Type.STRING, "belt");
    Key<String> TRINKET = Key.create(Type.STRING, "trinket");
    Key<String> HEAD = Key.create(Type.STRING, "head");
    Key<String> BODY = Key.create(Type.STRING, "body");
    Key<String> CHARM = Key.create(Type.STRING, "charm");

    // Outputs
    Key<String> ACTION_POTION = Key.create(Type.STRING, "potion");
    Key<Integer> ACTION_FIRE = Key.create(Type.INTEGER, "fire");
    Key<Boolean> ACTION_CLEAR = Key.create(Type.BOOLEAN, "clear");
    Key<String> ACTION_DAMAGE = Key.create(Type.STRING, "damage");
}
