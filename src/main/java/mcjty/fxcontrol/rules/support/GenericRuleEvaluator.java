package mcjty.fxcontrol.rules.support;

import mcjty.fxcontrol.FxControl;
import mcjty.fxcontrol.compat.ModRuleCompatibilityLayer;
import mcjty.fxcontrol.tools.rules.CommonRuleEvaluator;
import mcjty.fxcontrol.tools.typed.AttributeMap;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static mcjty.fxcontrol.rules.support.RuleKeys.MOD;

public class GenericRuleEvaluator extends CommonRuleEvaluator {

    public GenericRuleEvaluator(AttributeMap map) {
        super(map, FxControl.setup.getLogger(), new ModRuleCompatibilityLayer());
    }

    @Override
    protected void addChecks(AttributeMap map) {
        super.addChecks(map);
        if (map.has(MOD)) {
            addModsCheck(map);
        }

    }

    private void addModsCheck(AttributeMap map) {
        List<String> mods = map.getList(MOD);
        if (mods.size() == 1) {
            String modid = mods.get(0);
            checks.add((event, query) -> {
                ItemStack item = query.getItem(event);
                return modid.equals(item.getItem().getRegistryName().getNamespace());
            });
        } else {
            Set<String> modids = new HashSet<>();
            for (String modid : mods) {
                modids.add(modid);
            }
            checks.add((event, query) -> {
                ItemStack item = query.getItem(event);
                String mod = item.getItem().getRegistryName().getNamespace();
                return modids.contains(mod);
            });
        }
    }


}
