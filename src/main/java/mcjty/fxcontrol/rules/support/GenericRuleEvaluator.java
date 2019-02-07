package mcjty.fxcontrol.rules.support;

import mcjty.fxcontrol.FxControl;
import mcjty.fxcontrol.cache.StructureCache;
import mcjty.fxcontrol.compat.GameStageSupport;
import mcjty.fxcontrol.compat.LostCitySupport;
import mcjty.fxcontrol.compat.SereneSeasonsSupport;
import mcjty.fxcontrol.typed.AttributeMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Level;

import java.util.*;
import java.util.function.BiFunction;

import static mcjty.fxcontrol.rules.support.RuleKeys.*;

public class GenericRuleEvaluator {

    private final List<BiFunction<Event, IEventQuery, Boolean>> checks = new ArrayList<>();

    public GenericRuleEvaluator(AttributeMap map) {
        addChecks(map);
    }

    private void addChecks(AttributeMap map) {
        if (map.has(RANDOM)) {
            addRandomCheck(map);
        }
        if (map.has(MINTIME)) {
            addMinTimeCheck(map);
        }
        if (map.has(MAXTIME)) {
            addMaxTimeCheck(map);
        }

        if (map.has(MINHEIGHT)) {
            addMinHeightCheck(map);
        }
        if (map.has(MAXHEIGHT)) {
            addMaxHeightCheck(map);
        }

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

        if (map.has(WEATHER)) {
            addWeatherCheck(map);
        }
        if (map.has(TEMPCATEGORY)) {
            addTempCategoryCheck(map);
        }
        if (map.has(DIFFICULTY)) {
            addDifficultyCheck(map);
        }
        if (map.has(DIMENSION)) {
            addDimensionCheck(map);
        }

        if (map.has(MINSPAWNDIST)) {
            addMinSpawnDistCheck(map);
        }
        if (map.has(MAXSPAWNDIST)) {
            addMaxSpawnDistCheck(map);
        }

        if (map.has(MINLIGHT)) {
            addMinLightCheck(map);
        }
        if (map.has(MAXLIGHT)) {
            addMaxLightCheck(map);
        }

        if (map.has(MINDIFFICULTY)) {
            addMinAdditionalDifficultyCheck(map);
        }
        if (map.has(MAXDIFFICULTY)) {
            addMaxAdditionalDifficultyCheck(map);
        }

        if (map.has(SEESKY)) {
            addSeeSkyCheck(map);
        }
        if (map.has(BLOCK)) {
            addBlocksCheck(map);
        }
        if (map.has(BIOME)) {
            addBiomesCheck(map);
        }
        if (map.has(BIOMETYPE)) {
            addBiomeTypesCheck(map);
        }
        if (map.has(HELMET)) {
            addHelmetCheck(map);
        }
        if (map.has(CHESTPLATE)) {
            addChestplateCheck(map);
        }
        if (map.has(LEGGINGS)) {
            addLeggingsCheck(map);
        }
        if (map.has(BOOTS)) {
            addBootsCheck(map);
        }
        if (map.has(HELDITEM)) {
            addHeldItemCheck(map);
        }
        if (map.has(OFFHANDITEM)) {
            addOffHandItemCheck(map);
        }
        if (map.has(BOTHHANDSITEM)) {
            addBothHandsItemCheck(map);
        }

        if (map.has(STRUCTURE)) {
            addStructureCheck(map);
        }
    }

    private static Random rnd = new Random();

    private void addRandomCheck(AttributeMap map) {
        final float r = map.get(RANDOM);
        checks.add((event,query) -> rnd.nextFloat() < r);
    }

    private void addSeeSkyCheck(AttributeMap map) {
        if (map.get(SEESKY)) {
            checks.add((event,query) -> {
                return query.getWorld(event).canBlockSeeSky(query.getPos(event));
            });
        } else {
            checks.add((event,query) -> {
                return !query.getWorld(event).canBlockSeeSky(query.getPos(event));
            });
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
        checks.add((event, query) -> FxControl.gamestages && GameStageSupport.hasGameStage(query.getEntity(event), stage));
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

    private void addDimensionCheck(AttributeMap map) {
        List<Integer> dimensions = map.getList(DIMENSION);
        if (dimensions.size() == 1) {
            Integer dim = dimensions.get(0);
            checks.add((event,query) -> query.getWorld(event).provider.getDimension() == dim);
        } else {
            Set<Integer> dims = new HashSet<>(dimensions);
            checks.add((event,query) -> dims.contains(query.getWorld(event).provider.getDimension()));
        }
    }

    private void addDifficultyCheck(AttributeMap map) {
        String difficulty = map.get(DIFFICULTY).toLowerCase();
        EnumDifficulty diff = null;
        for (EnumDifficulty d : EnumDifficulty.values()) {
            if (d.getDifficultyResourceKey().endsWith("." + difficulty)) {
                diff = d;
                break;
            }
        }
        if (diff != null) {
            EnumDifficulty finalDiff = diff;
            checks.add((event,query) -> query.getWorld(event).getDifficulty() == finalDiff);
        } else {
            FxControl.logger.log(Level.ERROR, "Unknown difficulty '" + difficulty + "'! Use one of 'easy', 'normal', 'hard',  or 'peaceful'");
        }
    }

    private void addWeatherCheck(AttributeMap map) {
        String weather = map.get(WEATHER);
        boolean raining = weather.toLowerCase().startsWith("rain");
        boolean thunder = weather.toLowerCase().startsWith("thunder");
        if (raining) {
            checks.add((event,query) -> query.getWorld(event).isRaining());
        } else if (thunder) {
            checks.add((event,query) -> query.getWorld(event).isThundering());
        } else {
            FxControl.logger.log(Level.ERROR, "Unknown weather '" + weather + "'! Use 'rain' or 'thunder'");
        }
    }

    private void addTempCategoryCheck(AttributeMap map) {
        String tempcategory = map.get(TEMPCATEGORY).toLowerCase();
        Biome.TempCategory cat = null;
        if ("cold".equals(tempcategory)) {
            cat = Biome.TempCategory.COLD;
        } else if ("medium".equals(tempcategory)) {
            cat = Biome.TempCategory.MEDIUM;
        } else if ("warm".equals(tempcategory)) {
            cat = Biome.TempCategory.WARM;
        } else if ("ocean".equals(tempcategory)) {
            cat = Biome.TempCategory.OCEAN;
        } else {
            FxControl.logger.log(Level.ERROR, "Unknown tempcategory '" + tempcategory + "'! Use one of 'cold', 'medium', 'warm',  or 'ocean'");
            return;
        }

        Biome.TempCategory finalCat = cat;
        checks.add((event,query) -> {
            Biome biome = query.getWorld(event).getBiome(query.getPos(event));
            return biome.getTempCategory() == finalCat;
        });
    }

    private void addStructureCheck(AttributeMap map) {
        String structure = map.get(STRUCTURE);
        checks.add((event,query) -> {
            return StructureCache.CACHE.isInStructure(query.getWorld(event), structure, query.getPos(event));
        });
    }

    private void addBiomesCheck(AttributeMap map) {
        List<String> biomes = map.getList(BIOME);
        if (biomes.size() == 1) {
            String biomename = biomes.get(0);
            checks.add((event,query) -> {
                Biome biome = query.getWorld(event).getBiome(query.getPos(event));
                return biomename.equals(biome.biomeName);
            });
        } else {
            Set<String> biomenames = new HashSet<>(biomes);
            checks.add((event,query) -> {
                Biome biome = query.getWorld(event).getBiome(query.getPos(event));
                return biomenames.contains(biome.biomeName);
            });
        }
    }

    private void addBiomeTypesCheck(AttributeMap map) {
        List<String> biomeTypes = map.getList(BIOMETYPE);
        if (biomeTypes.size() == 1) {
            String biometype = biomeTypes.get(0);
            BiomeDictionary.Type type = BiomeDictionary.Type.getType(biometype);
            checks.add((event,query) -> {
                Biome biome = query.getWorld(event).getBiome(query.getPos(event));
                return BiomeDictionary.getTypes(biome).contains(type);
            });
        } else {
            Set<BiomeDictionary.Type> types = new HashSet<>();
            for (String s : biomeTypes) {
                types.add(BiomeDictionary.Type.getType(s));
            }

            checks.add((event,query) -> {
                Biome biome = query.getWorld(event).getBiome(query.getPos(event));
                return BiomeDictionary.getTypes(biome).stream().anyMatch(s -> types.contains(s));
            });
        }
    }

    private static final int[] EMPTYINTS = new int[0];

    private void addBlocksCheck(AttributeMap map) {
        List<String> blocks = map.getList(BLOCK);
        if (blocks.size() == 1) {
            String blockname = blocks.get(0);
            if (blockname.startsWith("ore:")) {
                int oreId = OreDictionary.getOreID(blockname.substring(4));
                checks.add((event, query) -> {
                    BlockPos pos = query.getPos(event);
                    Block block = query.getWorld(event).getBlockState(pos.down()).getBlock();
                    ItemStack stack = new ItemStack(block);
                    int[] oreIDs = stack.isEmpty() ? EMPTYINTS : OreDictionary.getOreIDs(stack);
                    if (isMatchingOreId(oreIDs, oreId)) {
                        return true;
                    }
                    return false;
                });
            } else {
                checks.add((event, query) -> {
                    BlockPos pos = query.getPos(event);
                    ResourceLocation registryName = query.getWorld(event).getBlockState(pos.down()).getBlock().getRegistryName();
                    if (registryName == null) {
                        return false;
                    }
                    String name = registryName.toString();
                    return blockname.equals(name);
                });
            }
        } else {
            Set<String> blocknames = new HashSet<>(blocks);
            checks.add((event,query) -> {
                BlockPos pos = query.getPos(event);
                Block block = query.getWorld(event).getBlockState(pos.down()).getBlock();
                ItemStack stack = new ItemStack(block);
                int[] oreIDs = stack.isEmpty() ? EMPTYINTS : OreDictionary.getOreIDs(stack);
                ResourceLocation registryName = block.getRegistryName();
                if (registryName == null) {
                    return false;
                }
                String name = registryName.toString();
                for (String blockname : blocknames) {
                    if (blockname.startsWith("ore:")) {
                        int oreId = OreDictionary.getOreID(blockname.substring(4));
                        if (isMatchingOreId(oreIDs, oreId)) {
                            return true;
                        }
                    } else {
                        if (blockname.equals(name)) {
                            return true;
                        }
                    }
                }

                return false;
            });
        }
    }

    private boolean isMatchingOreId(int[] oreIDs, int oreId) {
        if (oreIDs.length > 0) {
            for (int id : oreIDs) {
                if (id == oreId) {
                    return true;
                }
            }
        }
        return false;
    }


    private void addMinTimeCheck(AttributeMap map) {
        final int mintime = map.get(MINTIME);
        checks.add((event,query) -> {
            int time = (int) query.getWorld(event).getWorldTime();
            return time >= mintime;
        });
    }

    private void addMaxTimeCheck(AttributeMap map) {
        final int maxtime = map.get(MAXTIME);
        checks.add((event,query) -> {
            int time = (int) query.getWorld(event).getWorldTime();
            return time <= maxtime;
        });
    }

    private void addMinSpawnDistCheck(AttributeMap map) {
        final Float d = map.get(MINSPAWNDIST) * map.get(MINSPAWNDIST);
        checks.add((event,query) -> {
            BlockPos pos = query.getPos(event);
            double sqdist = pos.distanceSq(query.getWorld(event).getSpawnPoint());
            return sqdist >= d;
        });
    }

    private void addMaxSpawnDistCheck(AttributeMap map) {
        final Float d = map.get(MAXSPAWNDIST) * map.get(MAXSPAWNDIST);
        checks.add((event,query) -> {
            BlockPos pos = query.getPos(event);
            double sqdist = pos.distanceSq(query.getWorld(event).getSpawnPoint());
            return sqdist <= d;
        });
    }


    private void addMinLightCheck(AttributeMap map) {
        final int minlight = map.get(MINLIGHT);
        checks.add((event,query) -> {
            BlockPos pos = query.getPos(event);
            return query.getWorld(event).getLight(pos, true) >= minlight;
        });
    }

    private void addMaxLightCheck(AttributeMap map) {
        final int maxlight = map.get(MAXLIGHT);
        checks.add((event,query) -> {
            BlockPos pos = query.getPos(event);
            return query.getWorld(event).getLight(pos, true) <= maxlight;
        });
    }

    private void addMinAdditionalDifficultyCheck(AttributeMap map) {
        final Float mindifficulty = map.get(MINDIFFICULTY);
        checks.add((event,query) -> query.getWorld(event).getDifficultyForLocation(query.getPos(event)).getAdditionalDifficulty() >= mindifficulty);
    }

    private void addMaxAdditionalDifficultyCheck(AttributeMap map) {
        final Float maxdifficulty = map.get(MAXDIFFICULTY);
        checks.add((event,query) -> query.getWorld(event).getDifficultyForLocation(query.getPos(event)).getAdditionalDifficulty() <= maxdifficulty);
    }

    private void addMaxHeightCheck(AttributeMap map) {
        final int maxheight = map.get(MAXHEIGHT);
        checks.add((event,query) -> query.getY(event) <= maxheight);
    }

    private void addMinHeightCheck(AttributeMap map) {
        final int minheight = map.get(MINHEIGHT);
        checks.add((event,query) -> query.getY(event) >= minheight);
    }


    public boolean match(Event event, IEventQuery query) {
        for (BiFunction<Event, IEventQuery, Boolean> rule : checks) {
            if (!rule.apply(event, query)) {
                return false;
            }
        }
        return true;
    }

    private List<Item> getItems(List<String> itemNames) {
        List<Item> items = new ArrayList<>();
        for (String name : itemNames) {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
            if (item == null) {
                FxControl.logger.log(Level.ERROR, "Unknown item '" + name + "'!");
            } else {
                items.add(item);
            }
        }
        return items;
    }

    public void addHelmetCheck(AttributeMap map) {
        List<Item> items = getItems(map.getList(HELMET));
        addArmorCheck(items, EntityEquipmentSlot.HEAD);
    }

    public void addChestplateCheck(AttributeMap map) {
        List<Item> items = getItems(map.getList(CHESTPLATE));
        addArmorCheck(items, EntityEquipmentSlot.CHEST);
    }

    public void addLeggingsCheck(AttributeMap map) {
        List<Item> items = getItems(map.getList(LEGGINGS));
        addArmorCheck(items, EntityEquipmentSlot.LEGS);
    }

    public void addBootsCheck(AttributeMap map) {
        List<Item> items = getItems(map.getList(BOOTS));
        addArmorCheck(items, EntityEquipmentSlot.FEET);
    }

    private void addArmorCheck(List<Item> items, EntityEquipmentSlot slot) {
        checks.add((event,query) -> {
            Entity entity = query.getEntity(event);
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                ItemStack armorItem = player.getItemStackFromSlot(slot);
                if (!armorItem.isEmpty()) {
                    for (Item item : items) {
                        if (armorItem.getItem() == item) {
                            return true;
                        }
                    }
                }
            }
            return false;
        });
    }

    public void addHeldItemCheck(AttributeMap map) {
        List<Item> items = getItems(map.getList(HELDITEM));
        checks.add((event,query) -> {
            Entity entity = query.getEntity(event);
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                ItemStack mainhand = player.getHeldItemMainhand();
                if (!mainhand.isEmpty()) {
                    for (Item item : items) {
                        if (mainhand.getItem() == item) {
                            return true;
                        }
                    }
                }
            }
            return false;
        });
    }

    public void addOffHandItemCheck(AttributeMap map) {
        List<Item> items = getItems(map.getList(OFFHANDITEM));
        checks.add((event,query) -> {
            Entity entity = query.getEntity(event);
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                ItemStack offhand = player.getHeldItemOffhand();
                if (!offhand.isEmpty()) {
                    for (Item item : items) {
                        if (offhand.getItem() == item) {
                            return true;
                        }
                    }
                }
            }
            return false;
        });
    }

    public void addBothHandsItemCheck(AttributeMap map) {
        List<Item> items = getItems(map.getList(BOTHHANDSITEM));
        checks.add((event,query) -> {
            Entity entity = query.getEntity(event);
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                ItemStack offhand = player.getHeldItemOffhand();
                if (!offhand.isEmpty()) {
                    for (Item item : items) {
                        if (offhand.getItem() == item) {
                            return true;
                        }
                    }
                }
                ItemStack mainhand = player.getHeldItemMainhand();
                if (!mainhand.isEmpty()) {
                    for (Item item : items) {
                        if (mainhand.getItem() == item) {
                            return true;
                        }
                    }
                }
            }
            return false;
        });
    }


}
