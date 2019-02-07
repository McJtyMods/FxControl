package mcjty.fxcontrol.rules;

import com.google.gson.JsonElement;
import mcjty.fxcontrol.FxControl;
import mcjty.fxcontrol.rules.support.GenericRuleEvaluator;
import mcjty.fxcontrol.rules.support.IEventQuery;
import mcjty.typed.Attribute;
import mcjty.typed.AttributeMap;
import mcjty.typed.GenericAttributeMapFactory;
import mcjty.varia.Tools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Level;

import java.util.*;
import java.util.function.Consumer;

import static mcjty.fxcontrol.rules.support.RuleKeys.*;

public class EffectRule {

    private static final GenericAttributeMapFactory FACTORY = new GenericAttributeMapFactory();
    public static final IEventQuery<TickEvent.PlayerTickEvent> EVENT_QUERY = new IEventQuery<TickEvent.PlayerTickEvent>() {
        @Override
        public World getWorld(TickEvent.PlayerTickEvent o) {
            return o.player.getEntityWorld();
        }

        @Override
        public BlockPos getPos(TickEvent.PlayerTickEvent o) {
            return o.player.getPosition();
        }

        @Override
        public int getY(TickEvent.PlayerTickEvent o) {
            return o.player.getPosition().getY();
        }

        @Override
        public Entity getEntity(TickEvent.PlayerTickEvent o) {
            return o.player;
        }

        @Override
        public DamageSource getSource(TickEvent.PlayerTickEvent o) {
            return null;
        }

        @Override
        public Entity getAttacker(TickEvent.PlayerTickEvent o) {
            return null;
        }
    };

    public static final IEventQuery<EntityJoinWorldEvent> EVENT_QUERY_JOIN = new IEventQuery<EntityJoinWorldEvent>() {
        @Override
        public World getWorld(EntityJoinWorldEvent o) {
            return o.getWorld();
        }

        @Override
        public BlockPos getPos(EntityJoinWorldEvent o) {
            EntityJoinWorldEvent s = o;
            return s.getEntity().getPosition();
        }

        @Override
        public int getY(EntityJoinWorldEvent o) {
            return (int) o.getEntity().getPosition().getY();
        }

        @Override
        public Entity getEntity(EntityJoinWorldEvent o) {
            return o.getEntity();
        }

        @Override
        public DamageSource getSource(EntityJoinWorldEvent o) {
            return null;
        }

        @Override
        public Entity getAttacker(EntityJoinWorldEvent o) {
            return null;
        }
    };

    static {
        FACTORY
                .attribute(Attribute.create(MINTIME))
                .attribute(Attribute.create(MAXTIME))
                .attribute(Attribute.create(MINLIGHT))
                .attribute(Attribute.create(MAXLIGHT))
                .attribute(Attribute.create(MINHEIGHT))
                .attribute(Attribute.create(MAXHEIGHT))
                .attribute(Attribute.create(MINDIFFICULTY))
                .attribute(Attribute.create(MAXDIFFICULTY))
                .attribute(Attribute.create(MINSPAWNDIST))
                .attribute(Attribute.create(MAXSPAWNDIST))
                .attribute(Attribute.create(RANDOM))
                .attribute(Attribute.create(SEESKY))
                .attribute(Attribute.create(WEATHER))
                .attribute(Attribute.create(TEMPCATEGORY))
                .attribute(Attribute.create(DIFFICULTY))
                .attribute(Attribute.create(STRUCTURE))

                .attribute(Attribute.create(GAMESTAGE))

                .attribute(Attribute.create(WINTER))
                .attribute(Attribute.create(SUMMER))
                .attribute(Attribute.create(SPRING))
                .attribute(Attribute.create(AUTUMN))

                .attribute(Attribute.create(INBUILDING))
                .attribute(Attribute.create(INCITY))
                .attribute(Attribute.create(INSTREET))
                .attribute(Attribute.create(INSPHERE))

                .attribute(Attribute.createMulti(AMULET))
                .attribute(Attribute.createMulti(RING))
                .attribute(Attribute.createMulti(BELT))
                .attribute(Attribute.createMulti(TRINKET))
                .attribute(Attribute.createMulti(HEAD))
                .attribute(Attribute.createMulti(BODY))
                .attribute(Attribute.createMulti(CHARM))

                .attribute(Attribute.createMulti(BLOCK))
                .attribute(Attribute.createMulti(HELMET))
                .attribute(Attribute.createMulti(CHESTPLATE))
                .attribute(Attribute.createMulti(LEGGINGS))
                .attribute(Attribute.createMulti(BOOTS))
                .attribute(Attribute.createMulti(HELDITEM))
                .attribute(Attribute.createMulti(OFFHANDITEM))
                .attribute(Attribute.createMulti(BOTHHANDSITEM))
                .attribute(Attribute.createMulti(BIOME))
                .attribute(Attribute.createMulti(BIOMETYPE))
                .attribute(Attribute.createMulti(DIMENSION))

                .attribute(Attribute.createMulti(ACTION_POTION))
                .attribute(Attribute.create(ACTION_FIRE))
                .attribute(Attribute.create(ACTION_CLEAR))
                .attribute(Attribute.create(ACTION_DAMAGE))
        ;
    }

    private final GenericRuleEvaluator ruleEvaluator;
    private final List<Consumer<EventGetter>> actions = new ArrayList<>();
    private final int timeout;

    private interface EventGetter {
        EntityLivingBase getEntityLiving();
        World getWorld();
    }

    private EffectRule(AttributeMap map, int time) {
        ruleEvaluator = new GenericRuleEvaluator(map);
        this.timeout = time > 0 ? time : 1;
        addActions(map);
    }

    public int getTimeout() {
        return timeout;
    }

    private void addActions(AttributeMap map) {
        if (map.has(ACTION_POTION)) {
            addPotionsAction(map);
        }
        if (map.has(ACTION_FIRE)) {
            addFireAction(map);
        }
        if (map.has(ACTION_CLEAR)) {
            addClearAction(map);
        }
        if (map.has(ACTION_DAMAGE)) {
            addDamageAction(map);
        }
    }

    private static Map<String, DamageSource> damageMap = null;
    private static void addSource(DamageSource source) {
        damageMap.put(source.getDamageType(), source);
    }

    private void createDamageMap() {
        if (damageMap == null) {
            damageMap = new HashMap<>();
            addSource(DamageSource.IN_FIRE);
            addSource(DamageSource.LIGHTNING_BOLT);
            addSource(DamageSource.ON_FIRE);
            addSource(DamageSource.LAVA);
            addSource(DamageSource.HOT_FLOOR);
            addSource(DamageSource.IN_WALL);
            addSource(DamageSource.CRAMMING);
            addSource(DamageSource.DROWN);
            addSource(DamageSource.STARVE);
            addSource(DamageSource.CACTUS);
            addSource(DamageSource.FALL);
            addSource(DamageSource.FLY_INTO_WALL);
            addSource(DamageSource.OUT_OF_WORLD);
            addSource(DamageSource.GENERIC);
            addSource(DamageSource.MAGIC);
            addSource(DamageSource.WITHER);
            addSource(DamageSource.ANVIL);
            addSource(DamageSource.FALLING_BLOCK);
            addSource(DamageSource.DRAGON_BREATH);
            addSource(DamageSource.FIREWORKS);
        }
    }

    private void addDamageAction(AttributeMap map) {
        String damage = map.get(ACTION_DAMAGE);
        createDamageMap();
        String[] split = StringUtils.split(damage, "=");
        DamageSource source = damageMap.get(split[0]);
        if (source == null) {
            FxControl.logger.log(Level.ERROR, "Can't find damage source '" + split[0] + "'!");
            return;
        }
        float amount = 1.0f;
        if (split.length > 1) {
            amount = Float.parseFloat(split[1]);
        }

        float finalAmount = amount;
        actions.add(event -> {
            EntityLivingBase living = event.getEntityLiving();
            if (living != null) {
                living.attackEntityFrom(source, finalAmount);
            }
        });
    }


    private void addClearAction(AttributeMap map) {
        Boolean clear = map.get(ACTION_CLEAR);
        if (clear) {
            actions.add(event -> {
                EntityLivingBase living = event.getEntityLiving();
                if (living != null) {
                    living.clearActivePotions();
                }
            });
        }
    }

    private void addFireAction(AttributeMap map) {
        Integer fireAction = map.get(ACTION_FIRE);
        actions.add(event -> {
            EntityLivingBase living = event.getEntityLiving();
            if (living != null) {
                living.attackEntityFrom(DamageSource.ON_FIRE, 0.1f);
                living.setFire(fireAction);
            }
        });
    }

    private void addPotionsAction(AttributeMap map) {
        List<PotionEffect> effects = new ArrayList<>();
        for (String p : map.getList(ACTION_POTION)) {
            String[] splitted = StringUtils.split(p, ',');
            if (splitted == null || splitted.length != 3) {
                FxControl.logger.log(Level.ERROR, "Bad potion specifier '" + p + "'! Use <potion>,<duration>,<amplifier>");
                continue;
            }
            Potion potion = ForgeRegistries.POTIONS.getValue(new ResourceLocation(splitted[0]));
            if (potion == null) {
                FxControl.logger.log(Level.ERROR, "Can't find potion '" + p + "'!");
                continue;
            }
            int duration = 0;
            int amplifier = 0;
            try {
                duration = Integer.parseInt(splitted[1]);
                amplifier = Integer.parseInt(splitted[2]);
            } catch (NumberFormatException e) {
                FxControl.logger.log(Level.ERROR, "Bad duration or amplifier integer for '" + p + "'!");
                continue;
            }
            effects.add(new PotionEffect(potion, duration, amplifier));
        }
        if (!effects.isEmpty()) {
            actions.add(event -> {
                EntityLivingBase living = event.getEntityLiving();
                if (living != null) {
                    for (PotionEffect effect : effects) {
                        PotionEffect neweffect = new PotionEffect(effect.getPotion(), effect.getDuration(), effect.getAmplifier());
                        living.addPotionEffect(neweffect);
                    }
                }
            });
        }
    }

    private List<Pair<Float, ItemStack>> getItems(List<String> itemNames) {
        List<Pair<Float, ItemStack>> items = new ArrayList<>();
        for (String name : itemNames) {
            Pair<Float, ItemStack> pair = Tools.parseStackWithFactor(name, FxControl.logger);
            if (pair.getValue().isEmpty()) {
                FxControl.logger.log(Level.ERROR, "Unknown item '" + name + "'!");
            } else {
                items.add(pair);
            }
        }
        return items;
    }

    private ItemStack getRandomItem(List<Pair<Float, ItemStack>> items, float total) {
        float r = rnd.nextFloat() * total;
        for (Pair<Float, ItemStack> pair : items) {
            if (r <= pair.getLeft()) {
                return pair.getRight().copy();
            }
            r -= pair.getLeft();
        }
        return ItemStack.EMPTY;
    }

    private float getTotal(List<Pair<Float, ItemStack>> items) {
        float total = 0.0f;
        for (Pair<Float, ItemStack> pair : items) {
            total += pair.getLeft();
        }
        return total;
    }

    private static Random rnd = new Random();

    public boolean match(TickEvent.PlayerTickEvent event) {
        return ruleEvaluator.match(event, EVENT_QUERY);
    }

    public boolean match(EntityJoinWorldEvent event) {
        return ruleEvaluator.match(event, EVENT_QUERY_JOIN);
    }

    public void action(TickEvent.PlayerTickEvent event) {
        EventGetter getter = new EventGetter() {
            @Override
            public EntityLivingBase getEntityLiving() {
                return event.player;
            }

            @Override
            public World getWorld() {
                return event.player.getEntityWorld();
            }
        };
        for (Consumer<EventGetter> action : actions) {
            action.accept(getter);
        }
    }


    public static EffectRule parse(JsonElement element) {
        if (element == null) {
            return null;
        } else {
            AttributeMap map = null;
            try {
                map = FACTORY.parse(element);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            int time = element.getAsJsonObject().has("timeout") ? element.getAsJsonObject().get("timeout").getAsInt() : 20;
            return new EffectRule(map, time);
        }
    }
}
