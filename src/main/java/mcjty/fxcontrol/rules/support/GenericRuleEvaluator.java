package mcjty.fxcontrol.rules.support;

import mcjty.fxcontrol.FxControl;
import mcjty.fxcontrol.compat.BaublesSupport;
import mcjty.fxcontrol.compat.GameStageSupport;
import mcjty.fxcontrol.compat.LostCitySupport;
import mcjty.fxcontrol.compat.SereneSeasonsSupport;
import mcjty.tools.rules.CommonRuleEvaluator;
import mcjty.tools.rules.IEventQuery;
import mcjty.tools.typed.AttributeMap;
import mcjty.tools.typed.Key;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static mcjty.fxcontrol.rules.support.RuleKeys.*;

public class GenericRuleEvaluator extends CommonRuleEvaluator {

    private final List<BiFunction<Event, IEventQuery, Boolean>> checks = new ArrayList<>();

    public GenericRuleEvaluator(AttributeMap map) {
        super(map, FxControl.logger);
    }

    @Override
    protected void addChecks(AttributeMap map) {
        super.addChecks(map);

        if (map.has(SUMMER)) {
            if (FxControl.sereneSeasons) {
                addSummerCheck(map);
            } else {
                FxControl.logger.warn("Serene Seaons is missing: this test cannot work!");
            }
        }
        if (map.has(WINTER)) {
            if (FxControl.sereneSeasons) {
                addWinterCheck(map);
            } else {
                FxControl.logger.warn("Serene Seaons is missing: this test cannot work!");
            }
        }
        if (map.has(SPRING)) {
            if (FxControl.sereneSeasons) {
                addSpringCheck(map);
            } else {
                FxControl.logger.warn("Serene Seaons is missing: this test cannot work!");
            }
        }
        if (map.has(AUTUMN)) {
            if (FxControl.sereneSeasons) {
                addAutumnCheck(map);
            } else {
                FxControl.logger.warn("Serene Seaons is missing: this test cannot work!");
            }
        }
        if (map.has(GAMESTAGE)) {
            if (FxControl.gamestages) {
                addGameStageCheck(map);
            } else {
                FxControl.logger.warn("Game Stages is missing: the 'gamestage' test cannot work!");
            }
        }
        if (map.has(INCITY)) {
            if (FxControl.lostcities) {
                addInCityCheck(map);
            } else {
                FxControl.logger.warn("The Lost Cities is missing: the 'incity' test cannot work!");
            }
        }
        if (map.has(INSTREET)) {
            if (FxControl.lostcities) {
                addInStreetCheck(map);
            } else {
                FxControl.logger.warn("The Lost Cities is missing: the 'instreet' test cannot work!");
            }
        }
        if (map.has(INSPHERE)) {
            if (FxControl.lostcities) {
                addInSphereCheck(map);
            } else {
                FxControl.logger.warn("The Lost Cities is missing: the 'insphere' test cannot work!");
            }
        }
        if (map.has(INBUILDING)) {
            if (FxControl.lostcities) {
                addInBuildingCheck(map);
            } else {
                FxControl.logger.warn("The Lost Cities is missing: the 'inbuilding' test cannot work!");
            }
        }

        if (map.has(AMULET)) {
            if (FxControl.baubles) {
                addBaubleCheck(map, AMULET, BaublesSupport::getAmuletSlots);
            } else {
                FxControl.logger.warn("Baubles is missing: this test cannot work!");
            }
        }
        if (map.has(RING)) {
            if (FxControl.baubles) {
                addBaubleCheck(map, RING, BaublesSupport::getRingSlots);
            } else {
                FxControl.logger.warn("Baubles is missing: this test cannot work!");
            }
        }
        if (map.has(BELT)) {
            if (FxControl.baubles) {
                addBaubleCheck(map, BELT, BaublesSupport::getBeltSlots);
            } else {
                FxControl.logger.warn("Baubles is missing: this test cannot work!");
            }
        }
        if (map.has(TRINKET)) {
            if (FxControl.baubles) {
                addBaubleCheck(map, TRINKET, BaublesSupport::getTrinketSlots);
            } else {
                FxControl.logger.warn("Baubles is missing: this test cannot work!");
            }
        }
        if (map.has(HEAD)) {
            if (FxControl.baubles) {
                addBaubleCheck(map, HEAD, BaublesSupport::getHeadSlots);
            } else {
                FxControl.logger.warn("Baubles is missing: this test cannot work!");
            }
        }
        if (map.has(BODY)) {
            if (FxControl.baubles) {
                addBaubleCheck(map, BODY, BaublesSupport::getBodySlots);
            } else {
                FxControl.logger.warn("Baubles is missing: this test cannot work!");
            }
        }
        if (map.has(CHARM)) {
            if (FxControl.baubles) {
                addBaubleCheck(map, CHARM, BaublesSupport::getCharmSlots);
            } else {
                FxControl.logger.warn("Baubles is missing: this test cannot work!");
            }
        }
    }

    private void addSummerCheck(AttributeMap map) {
        Boolean s = map.get(SUMMER);
        checks.add((event, query) -> s == FxControl.sereneSeasons && SereneSeasonsSupport.isSummer(query.getWorld(event)));
    }

    private void addWinterCheck(AttributeMap map) {
        Boolean s = map.get(WINTER);
        checks.add((event, query) -> s == FxControl.sereneSeasons && SereneSeasonsSupport.isWinter(query.getWorld(event)));
    }

    private void addSpringCheck(AttributeMap map) {
        Boolean s = map.get(SPRING);
        checks.add((event, query) -> s == FxControl.sereneSeasons && SereneSeasonsSupport.isSpring(query.getWorld(event)));
    }

    private void addAutumnCheck(AttributeMap map) {
        Boolean s = map.get(AUTUMN);
        checks.add((event, query) -> s == FxControl.sereneSeasons && SereneSeasonsSupport.isAutumn(query.getWorld(event)));
    }

    private void addGameStageCheck(AttributeMap map) {
        String stage = map.get(GAMESTAGE);
        checks.add((event, query) -> FxControl.gamestages && GameStageSupport.hasGameStage(query.getPlayer(event), stage));
    }

    private void addInCityCheck(AttributeMap map) {
        if (map.get(INCITY)) {
            checks.add((event,query) -> FxControl.lostcities && LostCitySupport.isCity(query, event));
        } else {
            checks.add((event,query) -> FxControl.lostcities && !LostCitySupport.isCity(query, event));
        }
    }

    private void addInStreetCheck(AttributeMap map) {
        if (map.get(INSTREET)) {
            checks.add((event,query) -> FxControl.lostcities && LostCitySupport.isStreet(query, event));
        } else {
            checks.add((event,query) -> FxControl.lostcities && !LostCitySupport.isStreet(query, event));
        }
    }

    private void addInSphereCheck(AttributeMap map) {
        if (map.get(INSPHERE)) {
            checks.add((event,query) -> FxControl.lostcities && LostCitySupport.inSphere(query, event));
        } else {
            checks.add((event,query) -> FxControl.lostcities && !LostCitySupport.inSphere(query, event));
        }
    }

    private void addInBuildingCheck(AttributeMap map) {
        if (map.get(INBUILDING)) {
            checks.add((event,query) -> FxControl.lostcities && LostCitySupport.isBuilding(query, event));
        } else {
            checks.add((event,query) -> FxControl.lostcities && !LostCitySupport.isBuilding(query, event));
        }
    }

    public void addBaubleCheck(AttributeMap map, Key<String> key, Supplier<int[]> slotSupplier) {
        List<Item> items = getItems(map.getList(key));
        checks.add((event,query) -> {
            EntityPlayer player = query.getPlayer(event);
            if (player != null) {
                for (int slot : slotSupplier.get()) {
                    ItemStack stack = BaublesSupport.getStack(player, slot);
                    if (!stack.isEmpty()) {
                        for (Item item : items) {
                            if (stack.getItem() == item) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        });
    }
}
