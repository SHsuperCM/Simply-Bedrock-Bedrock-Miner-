package SHCM.SHsuperCM.simplybedrock;

import SHCM.SHsuperCM.simplybedrock.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = SimplyBedrock.MODID, useMetadata = true)
public class SimplyBedrock {
    public static final String MODID = "simplybedrock";

    @Mod.Instance
    public static SimplyBedrock instance;

    @SidedProxy(clientSide = "SHCM.SHsuperCM.simplybedrock.proxy.ClientProxy", serverSide = "SHCM.SHsuperCM.simplybedrock.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @SubscribeEvent
    public void syncConfig(ConfigChangedEvent event) { ConfigManager.sync(MODID, net.minecraftforge.common.config.Config.Type.INSTANCE); }
    @net.minecraftforge.common.config.Config(modid = MODID)
    public static class Config {

        @net.minecraftforge.common.config.Config.Name("Bedrock Hitpoints")
        @net.minecraftforge.common.config.Config.Comment({"Determines how many hits bedrock requires by a Bedrock Miner to be broken.","Controls the both the time and fuel it takes in ticks(20th of a second)"})
        public static int bedrock_hitpoints = 6000;
    }
}
