package mcjty.fxcontrol.compat;

import mcjty.fxcontrol.setup.ModSetup;
import mcjty.tools.rules.IEventQuery;
import mcjty.tools.rules.IModRuleCompatibilityLayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

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
    public ItemStack getBaubleStack(EntityPlayer player, int slot) {
        return BaublesSupport.getStack(player, slot);
    }

    @Override
    public boolean hasGameStages() {
        return ModSetup.gamestages;
    }

    @Override
    public boolean hasGameStage(EntityPlayer player, String stage) {
        return GameStageSupport.hasGameStage(player, stage);
    }

    @Override
    public void addGameStage(EntityPlayer player, String stage) {
        GameStageSupport.addGameStage(player, stage);
    }

    @Override
    public void removeGameStage(EntityPlayer player, String stage) {
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
    public boolean isSpring(World world) {
        return SereneSeasonsSupport.isSpring(world);
    }

    @Override
    public boolean isSummer(World world) {
        return SereneSeasonsSupport.isSummer(world);
    }

    @Override
    public boolean isWinter(World world) {
        return SereneSeasonsSupport.isWinter(world);
    }

    @Override
    public boolean isAutumn(World world) {
        return SereneSeasonsSupport.isAutumn(world);
    }

    @Override
    public boolean hasEnigmaScript() {
        return ModSetup.enigma;
    }

    @Override
    public String getPlayerState(EntityPlayer player, String statename) {
        return EnigmaSupport.getPlayerState(player, statename);
    }

    @Override
    public String getState(World world, String statename) {
        return EnigmaSupport.getState(world, statename);
    }

    @Override
    public void setPlayerState(EntityPlayer player, String statename, String statevalue) {
        EnigmaSupport.setPlayerState(player, statename, statevalue);
    }

    @Override
    public void setState(World world, String statename, String statevalue) {
        EnigmaSupport.setState(world, statename, statevalue);
    }

    @Override
    public String getBiomeName(Biome biome) {
        return biome.biomeName;
    }
}
