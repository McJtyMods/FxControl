package mcjty.fxcontrol;

import mcjty.fxcontrol.commands.ModCommands;
import mcjty.fxcontrol.rules.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

public class ForgeEventHandlers {

    public static boolean debug = false;

    @SubscribeEvent
    public void serverLoad(FMLServerStartingEvent event) {
        ModCommands.register(event.getCommandDispatcher());
    }

    public static Map<Integer, Integer> tickCounters = new HashMap<>();

    @SubscribeEvent
    public void onRightClickEvent(PlayerInteractEvent.RightClickBlock event) {
        if (event.getWorld().isRemote) {
            return;
        }
        int i = 0;
        for (RightClickRule rule : RulesManager.rightclickRules) {
            if (rule.match(event)) {
                Event.Result result = rule.getResult();
                if (debug) {
                    FxControl.setup.getLogger().log(Level.INFO, "Rule " + i + ": "+ result
                            + " entity: " + event.getPlayer().getName()
                            + " y: " + event.getPos().getY()
                            + " biome: " + event.getWorld().getBiome(event.getPos()).getDisplayName().getFormattedText());
                }
                rule.action(event);
                event.setUseBlock(result);
                if (result == Event.Result.DENY) {
                    event.setCanceled(true);
                }
                return;
            }
            i++;
        }
    }

    @SubscribeEvent
    public void onLeftClickEvent(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getWorld().isRemote) {
            return;
        }
        int i = 0;
        for (LeftClickRule rule : RulesManager.leftclickRules) {
            if (rule.match(event)) {
                Event.Result result = rule.getResult();
                if (debug) {
                    FxControl.setup.getLogger().log(Level.INFO, "Rule " + i + ": "+ result
                            + " entity: " + event.getPlayer().getName()
                            + " y: " + event.getPos().getY()
                            + " biome: " + event.getWorld().getBiome(event.getPos()).getDisplayName().getFormattedText());
                }
                rule.action(event);
                event.setUseBlock(result);
                if (result == Event.Result.DENY) {
                    event.setCanceled(true);
                }
                return;
            }
            i++;
        }
    }


    @SubscribeEvent
    public void onBlockPaceEvent(BlockEvent.EntityPlaceEvent event) {
        if (event.getWorld().isRemote()) {
            return;
        }
        int i = 0;
        for (PlaceRule rule : RulesManager.placeRules) {
            if (rule.match(event)) {
                Event.Result result = rule.getResult();
                if (debug) {
                    FxControl.setup.getLogger().log(Level.INFO, "Rule " + i + ": "+ result
                            + " entity: " + event.getEntity().getName()
                            + " y: " + event.getPos().getY()
                            + " biome: " + event.getWorld().getBiome(event.getPos()).getDisplayName().getFormattedText());
                }
                rule.action(event);
                if (result == Event.Result.DENY) {
                    event.setCanceled(true);
                }
                return;
            }
            i++;
        }
    }

    @SubscribeEvent
    public void onBlockBreakEvent(BlockEvent.BreakEvent event) {
        if (event.getWorld().isRemote()) {
            return;
        }
        int i = 0;
        for (HarvestRule rule : RulesManager.harvestRules) {
            if (rule.match(event)) {
                Event.Result result = rule.getResult();
                if (debug) {
                    FxControl.setup.getLogger().log(Level.INFO, "Rule " + i + ": "+ result
                            + " entity: " + event.getPlayer().getName()
                            + " y: " + event.getPos().getY()
                            + " biome: " + event.getWorld().getBiome(event.getPos()).getDisplayName().getFormattedText());
                }
                rule.action(event);
                if (result == Event.Result.DENY) {
                    event.setCanceled(true);
                }
                return;
            }
            i++;
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (event.side != LogicalSide.SERVER) {
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
                    FxControl.setup.getLogger().log(Level.INFO, "Join Rule " + i
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
