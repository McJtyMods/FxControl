package mcjty.fxcontrol;

import mcjty.fxcontrol.rules.EffectRule;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

public class ForgeEventHandlers {

    public static boolean debug = false;

    public static Map<Integer, Integer> tickCounters = new HashMap<>();

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (event.side != Side.SERVER) {
            return;
        }

        int id = event.player.getEntityId();
        if (!tickCounters.containsKey(id)) {
            tickCounters.put(id, 0);
        }
        int tickCounter = tickCounters.get(id) + 1;
        tickCounters.put(id, tickCounter);
        int i = 0;
        for (EffectRule rule : RulesManager.effectRules) {
            if (tickCounter % rule.getTimeout() == 0 && rule.match(event)) {
                if (debug) {
                    FxControl.logger.log(Level.INFO, "Join Rule " + i
                            + " entity: " + event.player.getName()
                            + " y: " + event.player.getPosition().getY());
                }
                rule.action(event);
                return;
            }
            i++;
        }
    }
}
