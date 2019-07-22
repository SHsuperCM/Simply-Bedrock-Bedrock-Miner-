package SHCM.SHsuperCM.simplybedrock.proxy;

import SHCM.SHsuperCM.simplybedrock.SimplyBedrock;
import SHCM.SHsuperCM.simplybedrock.client.GuiHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(SimplyBedrock.instance,new GuiHandler());
    }

    public void setModelPickaxe(NBTTagCompound itemNBT) {}
}
