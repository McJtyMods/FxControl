package mcjty.fxcontrol.rules.support;

import mcjty.fxcontrol.FxControl;
import mcjty.fxcontrol.compat.ModRuleCompatibilityLayer;
import mcjty.tools.rules.CommonRuleEvaluator;
import mcjty.tools.typed.AttributeMap;

public class GenericRuleEvaluator extends CommonRuleEvaluator {

    public GenericRuleEvaluator(AttributeMap map) {
        super(map, FxControl.logger, new ModRuleCompatibilityLayer());
    }

    @Override
    protected void addChecks(AttributeMap map) {
        super.addChecks(map);
    }

}
