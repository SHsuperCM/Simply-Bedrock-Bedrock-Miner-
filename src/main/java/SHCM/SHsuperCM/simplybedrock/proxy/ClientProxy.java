package SHCM.SHsuperCM.simplybedrock.proxy;

import SHCM.SHsuperCM.simplybedrock.blocks.ModBlocks;
import SHCM.SHsuperCM.simplybedrock.items.ModItems;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ModItems.registerRenders();
        ModBlocks.registerRenders();
    }
}
