package mcjty.fxcontrol.setup;

import mcjty.fxcontrol.ForgeEventHandlers;
import mcjty.fxcontrol.FxControl;
import mcjty.fxcontrol.RulesManager;
import mcjty.fxcontrol.compat.EnigmaSupport;
import mcjty.fxcontrol.compat.LostCitySupport;
import mcjty.fxcontrol.config.ConfigSetup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class ModSetup {

    public static boolean lostcities = false;
    public static boolean gamestages = false;
    public static boolean sereneSeasons = false;
    public static boolean baubles = false;
    public static boolean enigma = false;

    private Logger logger;

    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();

        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());

        setupModCompat();

        ConfigSetup.init(e);
        RulesManager.setRulePath(e.getModConfigurationDirectory());
    }

    private void setupModCompat() {
        lostcities = Loader.isModLoaded("lostcities");
        gamestages = Loader.isModLoaded("gamestages");
        sereneSeasons = Loader.isModLoaded("sereneseasons");
        baubles = Loader.isModLoaded("baubles");
        enigma = Loader.isModLoaded("enigma");

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

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
        ConfigSetup.postInit();
    }

    public Logger getLogger() {
        return logger;
    }
}
