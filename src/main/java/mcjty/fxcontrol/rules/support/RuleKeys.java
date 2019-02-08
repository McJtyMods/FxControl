package mcjty.fxcontrol.rules.support;

import mcjty.tools.rules.CommonRuleKeys;
import mcjty.tools.typed.Key;
import mcjty.tools.typed.Type;

public interface RuleKeys extends CommonRuleKeys {

    // Outputs
    Key<Integer> ACTION_FIRE = Key.create(Type.INTEGER, "fire");
    Key<Boolean> ACTION_CLEAR = Key.create(Type.BOOLEAN, "clear");
    Key<String> ACTION_DAMAGE = Key.create(Type.STRING, "damage");
}
