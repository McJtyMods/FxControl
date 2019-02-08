package mcjty.fxcontrol.rules.support;

import mcjty.tools.rules.CommonRuleKeys;
import mcjty.tools.typed.Key;
import mcjty.tools.typed.Type;

public interface RuleKeys extends CommonRuleKeys {

    // Inputs
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
