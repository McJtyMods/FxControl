package mcjty.fxcontrol.setup;

import mcjty.fxcontrol.ForgeEventHandlers;
import mcjty.fxcontrol.FxControl;
import mcjty.fxcontrol.RulesManager;
import mcjty.fxcontrol.compat.EnigmaSupport;
import mcjty.fxcontrol.compat.LostCitySupport;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModSetup {

    public static boolean lostcities = false;
    public static boolean gamestages = false;
    public static boolean sereneSeasons = false;
    public static boolean baubles = false;
    public static boolean enigma = false;

    private Logger logger;

    public void init() {
        logger = LogManager.getLogger(FxControl.MODID);
        setupModCompat();

        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
        RulesManager.setRulePath(FMLPaths.CONFIGDIR.get());
        RulesManager.readRules();

        // @todo 1.15
//        ConfigSetup.init(e);
    }

    public Logger getLogger() {
        return logger;
    }

    private void setupModCompat() {
        lostcities = ModList.get().isLoaded("lostcities");
        gamestages = ModList.get().isLoaded("gamestages");
        sereneSeasons = ModList.get().isLoaded("sereneseasons");
        baubles = ModList.get().isLoaded("baubles");
        enigma = ModList.get().isLoaded("enigma");

        if (ModSetup.lostcities) {
            LostCitySupport.register();
            FxControl.setup.getLogger().log(Level.INFO, "Enabling support for Lost Cities");
        }
        if (ModSetup.gamestages) {
            FxControl.setup.getLogger().log(Level.INFO, "Enabling support for Game Stages");
        }
        if (ModSetup.sereneSeasons) {
            FxControl.setup.getLogger().log(Level.INFO, "Enabling support for Serene Seasons");
        }
        if (ModSetup.baubles) {
            FxControl.setup.getLogger().log(Level.INFO, "Enabling support for Baubles");
        }
        if (ModSetup.enigma) {
            EnigmaSupport.register();
            FxControl.setup.getLogger().log(Level.INFO, "Enabling support for EnigmaScript");
        }
    }
}
