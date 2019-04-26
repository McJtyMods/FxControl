package mcjty.fxcontrol;


import mcjty.fxcontrol.commands.CmdDebug;
import mcjty.fxcontrol.commands.CmdDumpBlockNBT;
import mcjty.fxcontrol.commands.CmdDumpItemNBT;
import mcjty.fxcontrol.commands.CmdReload;
import mcjty.fxcontrol.setup.IProxy;
import mcjty.fxcontrol.setup.ModSetup;
import mcjty.tools.cache.StructureCache;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = FxControl.MODID, name = FxControl.MODNAME,
        dependencies =
                "after:forge@[" + FxControl.MIN_FORGE11_VER + ",)",
        version = FxControl.VERSION,
        acceptedMinecraftVersions = "[1.12,1.13)",
        acceptableRemoteVersions = "*")
public class FxControl {

    public static final String MODID = "fxcontrol";
    public static final String MODNAME = "FxControl";
    public static final String VERSION = "0.1.7";
    public static final String MIN_FORGE11_VER = "13.19.0.2176";

    @SidedProxy(clientSide = "mcjty.fxcontrol.setup.ClientProxy", serverSide = "mcjty.fxcontrol.setup.ServerProxy")
    public static IProxy proxy;
    public static ModSetup setup = new ModSetup();

    @Mod.Instance
    public static FxControl instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        setup.preInit(event);
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        setup.init(e);
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        setup.postInit(e);
        proxy.postInit(e);
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent e) {
        RulesManager.readRules();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CmdReload());
        event.registerServerCommand(new CmdDebug());
        event.registerServerCommand(new CmdDumpItemNBT());
        event.registerServerCommand(new CmdDumpBlockNBT());
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        StructureCache.CACHE.clean();
    }
}
