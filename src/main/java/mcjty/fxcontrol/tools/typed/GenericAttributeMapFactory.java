package mcjty.fxcontrol.tools.typed;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import mcjty.fxcontrol.ErrorHandler;
import mcjty.fxcontrol.tools.varia.JSonTools;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GenericAttributeMapFactory {

    private final List<Attribute> attributes = new ArrayList<>();

    public GenericAttributeMapFactory attribute(@Nonnull Attribute a) {
        attributes.add(a);
        return this;
    }

    @Nonnull
    public AttributeMap parse(@Nonnull JsonElement element) {
        JsonObject jsonObject = element.getAsJsonObject();
        AttributeMap map = new AttributeMap();

        for (Attribute attribute : attributes) {
            Key key = attribute.key();
            Type type = key.type();

            if (attribute.multi()) {
                Function<JsonElement, Object> transformer;
                if (type == Type.INTEGER) {
                    transformer = JsonElement::getAsInt;
                } else if (type == Type.FLOAT) {
                    transformer = JsonElement::getAsFloat;
                } else if (type == Type.BOOLEAN) {
                    transformer = JsonElement::getAsBoolean;
                } else if (type == Type.STRING) {
                    transformer = JsonElement::getAsString;
                } else if (type == Type.JSON) {
                    transformer = JsonElement::toString;
                } else if (type == Type.DIMENSION_TYPE) {
                    transformer = jsonElement -> {
                        ResourceKey<Level> worldkey = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(jsonElement.getAsString()));
                        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                        if (server != null) {
                            if (!server.levelKeys().contains(worldkey)) {
                                ErrorHandler.error("Dimension '" + jsonElement.getAsString() + "' not found!");
                            }
                        }
                        return worldkey;
                    };
                } else {
                    transformer = e -> "INVALID";
                }

                JSonTools.getElement(jsonObject, key.name())
                        .ifPresent(e -> {
                            JSonTools.asArrayOrSingle(e)
                                    .map(transformer)
                                    .forEach(s -> {
                                        map.addListNonnull(key, s);
                                    });
                        });
            } else {
                if (type == Type.INTEGER) {
                    map.setNonnull(key, JSonTools.parseInt(jsonObject, key.name()));
                } else if (type == Type.FLOAT) {
                    map.setNonnull(key, JSonTools.parseFloat(jsonObject, key.name()));
                } else if (type == Type.BOOLEAN) {
                    map.setNonnull(key, JSonTools.parseBool(jsonObject, key.name()));
                } else if (type == Type.STRING) {
                    if (jsonObject.has(key.name())) {
                        map.setNonnull(key, jsonObject.get(key.name()).getAsString());
                    }
                } else if (type == Type.DIMENSION_TYPE) {
                    if (jsonObject.has(key.name())) {
                        JsonElement jsonElement = jsonObject.get(key.name());
                        map.setNonnull(key, ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(jsonElement.getAsString())));
                    }
                } else if (type == Type.JSON) {
                    if (jsonObject.has(key.name())) {
                        JsonElement el = jsonObject.get(key.name());
                        if (el.isJsonObject()) {
                            JsonObject obj = el.getAsJsonObject();
                            map.setNonnull(key, obj.toString());
                        } else {
                            if (el.isJsonPrimitive()) {
                                JsonPrimitive prim = el.getAsJsonPrimitive();
                                if (prim.isString()) {
                                    map.setNonnull(key, prim.getAsString());
                                } else if (prim.isNumber()) {
                                    map.setNonnull(key, "" + prim.getAsInt());
                                } else {
                                    throw new RuntimeException("Bad type for key '" + key.name() + "'!");
                                }
                            }
                        }
                    }
                }
            }
        }

        return map;
    }
}