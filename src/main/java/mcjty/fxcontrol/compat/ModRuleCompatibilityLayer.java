package mcjty.fxcontrol.compat;

import mcjty.fxcontrol.setup.ModSetup;
import mcjty.fxcontrol.tools.rules.IEventQuery;
import mcjty.fxcontrol.tools.rules.IModRuleCompatibilityLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRuleCompatibilityLayer implements IModRuleCompatibilityLayer {

    @Override
    public boolean hasBaubles() {
        return ModSetup.baubles;
    }

    @Override
    public int[] getAmuletSlots() {
        return BaublesSupport.getAmuletSlots();
    }

    @Override
    public int[] getBeltSlots() {
        return BaublesSupport.getBeltSlots();
    }

    @Override
    public int[] getBodySlots() {
        return BaublesSupport.getBodySlots();
    }

    @Override
    public int[] getCharmSlots() {
        return BaublesSupport.getCharmSlots();
    }

    @Override
    public int[] getHeadSlots() {
        return BaublesSupport.getHeadSlots();
    }

    @Override
    public int[] getRingSlots() {
        return BaublesSupport.getRingSlots();
    }

    @Override
    public int[] getTrinketSlots() {
        return BaublesSupport.getTrinketSlots();
    }

    @Override
    public ItemStack getBaubleStack(Player player, int slot) {
        return BaublesSupport.getStack(player, slot);
    }

    @Override
    public boolean hasGameStages() {
        return ModSetup.gamestages;
    }

    @Override
    public boolean hasGameStage(Player player, String stage) {
        return GameStageSupport.hasGameStage(player, stage);
    }

    @Override
    public void addGameStage(Player player, String stage) {
        GameStageSupport.addGameStage(player, stage);
    }

    @Override
    public void removeGameStage(Player player, String stage) {
        GameStageSupport.removeGameStage(player, stage);
    }

    @Override
    public boolean hasLostCities() {
        return ModSetup.lostcities;
    }

    @Override
    public <T> boolean isCity(IEventQuery<T> query, T event) {
        return LostCitySupport.isCity(query, event);
    }

    @Override
    public <T> boolean isStreet(IEventQuery<T> query, T event) {
        return LostCitySupport.isStreet(query, event);
    }

    @Override
    public <T> boolean inSphere(IEventQuery<T> query, T event) {
        return LostCitySupport.inSphere(query, event);
    }

    @Override
    public <T> boolean isBuilding(IEventQuery<T> query, T event) {
        return LostCitySupport.isBuilding(query, event);
    }

    @Override
    public boolean hasSereneSeasons() {
        return ModSetup.sereneSeasons;
    }

    @Override
    public boolean isSpring(LevelAccessor world) {
        return SereneSeasonsSupport.isSpring(world);
    }

    @Override
    public boolean isSummer(LevelAccessor world) {
        return SereneSeasonsSupport.isSummer(world);
    }

    @Override
    public boolean isWinter(LevelAccessor world) {
        return SereneSeasonsSupport.isWinter(world);
    }

    @Override
    public boolean isAutumn(LevelAccessor world) {
        return SereneSeasonsSupport.isAutumn(world);
    }

    @Override
    public boolean hasEnigmaScript() {
        return ModSetup.enigma;
    }

    @Override
    public String getPlayerState(Player player, String statename) {
        // @todo 1.15
//        return EnigmaSupport.getPlayerState(player, statename);
        return null;
    }

    @Override
    public String getState(LevelAccessor world, String statename) {
        // @todo 1.15
//        return EnigmaSupport.getState(world, statename);
        return null;
    }

    @Override
    public void setPlayerState(Player player, String statename, String statevalue) {
        // @todo 1.15
//        EnigmaSupport.setPlayerState(player, statename, statevalue);
    }

    @Override
    public void setState(LevelAccessor world, String statename, String statevalue) {
        // @todo 1.15
//        EnigmaSupport.setState(world, statename, statevalue);
    }

    @Override
    public String getBiomeName(Biome biome) {
        ResourceLocation resourceLocation = ForgeRegistries.BIOMES.getKey(biome);
        String s = "biome." + resourceLocation.getNamespace() + "." + resourceLocation.getPath();
        return Component.translatable(s).getString();
    }
}
