package mcjty.fxcontrol.rules;

import com.google.gson.JsonElement;
import mcjty.fxcontrol.FxControl;
import mcjty.fxcontrol.compat.ModRuleCompatibilityLayer;
import mcjty.fxcontrol.rules.support.GenericRuleEvaluator;
import mcjty.tools.rules.IEventQuery;
import mcjty.tools.rules.RuleBase;
import mcjty.tools.typed.Attribute;
import mcjty.tools.typed.AttributeMap;
import mcjty.tools.typed.GenericAttributeMapFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;

import java.util.function.Consumer;

import static mcjty.fxcontrol.rules.support.RuleKeys.*;

import mcjty.tools.rules.RuleBase.EventGetter;

public class EffectRule extends RuleBase<RuleBase.EventGetter> {

    private static final GenericAttributeMapFactory FACTORY = new GenericAttributeMapFactory();
    public static final IEventQuery<TickEvent.PlayerTickEvent> EVENT_QUERY = new IEventQuery<TickEvent.PlayerTickEvent>() {
        @Override
        public World getWorld(TickEvent.PlayerTickEvent o) {
            return o.player.getCommandSenderWorld();
        }

        @Override
        public BlockPos getPos(TickEvent.PlayerTickEvent o) {
            return o.player.blockPosition();
        }

        @Override
        public BlockPos getValidBlockPos(TickEvent.PlayerTickEvent o) {
            return o.player.blockPosition().below();
        }

        @Override
        public int getY(TickEvent.PlayerTickEvent o) {
            return o.player.blockPosition().getY();
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

        @Override
        public PlayerEntity getPlayer(TickEvent.PlayerTickEvent o) {
            return o.player;
        }

        @Override
        public ItemStack getItem(TickEvent.PlayerTickEvent o) {
            return ItemStack.EMPTY;
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
                .attribute(Attribute.createMulti(CATEGORY))
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
                .attribute(Attribute.create(BLOCKOFFSET))
                .attribute(Attribute.createMulti(HELMET))
                .attribute(Attribute.createMulti(CHESTPLATE))
                .attribute(Attribute.createMulti(LEGGINGS))
                .attribute(Attribute.createMulti(BOOTS))
                .attribute(Attribute.createMulti(HELDITEM))
                .attribute(Attribute.createMulti(PLAYER_HELDITEM))
                .attribute(Attribute.createMulti(OFFHANDITEM))
                .attribute(Attribute.createMulti(BOTHHANDSITEM))
                .attribute(Attribute.createMulti(BIOME))
                .attribute(Attribute.createMulti(BIOMETYPE))
                .attribute(Attribute.createMulti(DIMENSION))
                .attribute(Attribute.createMulti(DIMENSION_MOD))

                .attribute(Attribute.create(ACTION_COMMAND))
                .attribute(Attribute.create(ACTION_ADDSTAGE))
                .attribute(Attribute.create(ACTION_REMOVESTAGE))
                .attribute(Attribute.create(ACTION_MESSAGE))
                .attribute(Attribute.create(ACTION_FIRE))
                .attribute(Attribute.create(ACTION_EXPLOSION))
                .attribute(Attribute.create(ACTION_CLEAR))
                .attribute(Attribute.create(ACTION_DAMAGE))
                .attribute(Attribute.create(ACTION_SETBLOCK))
                .attribute(Attribute.create(ACTION_SETHELDITEM))
                .attribute(Attribute.create(ACTION_SETHELDAMOUNT))
                .attribute(Attribute.create(ACTION_SETSTATE))
                .attribute(Attribute.create(ACTION_SETPSTATE))
                .attribute(Attribute.createMulti(ACTION_POTION))
                .attribute(Attribute.createMulti(ACTION_GIVE))
                .attribute(Attribute.createMulti(ACTION_DROP))
        ;
    }

    private final GenericRuleEvaluator ruleEvaluator;
    private final int timeout;

    private EffectRule(AttributeMap map, int time) {
        super(FxControl.setup.getLogger());
        ruleEvaluator = new GenericRuleEvaluator(map);
        this.timeout = time > 0 ? time : 1;
        addActions(map, new ModRuleCompatibilityLayer());
    }

    public int getTimeout() {
        return timeout;
    }

    public boolean match(TickEvent.PlayerTickEvent event) {
        return ruleEvaluator.match(event, EVENT_QUERY);
    }

    public void action(TickEvent.PlayerTickEvent event) {
        EventGetter getter = new EventGetter() {
            @Override
            public LivingEntity getEntityLiving() {
                return event.player;
            }

            @Override
            public PlayerEntity getPlayer() {
                return event.player;
            }

            @Override
            public World getWorld() {
                return event.player.getCommandSenderWorld();
            }

            @Override
            public BlockPos getPosition() {
                return event.player.blockPosition();
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
                FxControl.setup.getLogger().error(e);
                return null;
            }
            int time = element.getAsJsonObject().has("timeout") ? element.getAsJsonObject().get("timeout").getAsInt() : 20;
            return new EffectRule(map, time);
        }
    }
}
