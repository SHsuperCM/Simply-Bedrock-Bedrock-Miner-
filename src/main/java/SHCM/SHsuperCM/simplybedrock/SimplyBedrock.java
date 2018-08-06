package SHCM.SHsuperCM.simplybedrock;

import SHCM.SHsuperCM.simplybedrock.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SimplyBedrock.MODID, useMetadata = true)
public class SimplyBedrock {
    public static final String MODID = "simplybedrock";

    @Mod.Instance
    public static SimplyBedrock instance;

    @SidedProxy(clientSide = "SHCM.SHsuperCM.simplybedrock.proxy.ClientProxy", serverSide = "SHCM.SHsuperCM.simplybedrock.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
