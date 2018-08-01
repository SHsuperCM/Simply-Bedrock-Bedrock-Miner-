package SHCM.SHsuperCM.simplybedrock;

import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@net.minecraftforge.common.config.Config(modid = SimplyBedrock.MODID)
public class Config {
    @SubscribeEvent
    public void config(ConfigChangedEvent event) {
        ConfigManager.sync(SimplyBedrock.MODID, net.minecraftforge.common.config.Config.Type.INSTANCE);
    }

}
