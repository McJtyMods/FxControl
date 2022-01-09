package mcjty.fxcontrol;


import mcjty.fxcontrol.setup.ModSetup;
import mcjty.fxcontrol.tools.cache.StructureCache;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FxControl.MODID)
public class FxControl {

    public static final String MODID = "fxcontrol";

    public static ModSetup setup = new ModSetup();

    public FxControl() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent event) -> setup.init());
        MinecraftForge.EVENT_BUS.addListener((ServerStoppedEvent event) -> StructureCache.CACHE.clean());
        MinecraftForge.EVENT_BUS.addListener(ErrorHandler::onPlayerJoinWorld);
    }
}
