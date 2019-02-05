package SHCM.SHsuperCM.simplybedrock.items;

import SHCM.SHsuperCM.simplybedrock.SimplyBedrock;
import SHCM.SHsuperCM.simplybedrock.blocks.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber
public class ModItems {

    public static Item bedrock_dust = new Item().setCreativeTab(CreativeTabs.MISC);
    public static Item orb_of_infinity = new Item().setCreativeTab(CreativeTabs.MISC);

    public static ItemBlock bedrock_miner = new ItemBlock(ModBlocks.bedrock_miner);

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        bedrock_dust.setRegistryName("bedrock_dust").setUnlocalizedName(bedrock_dust.getRegistryName().toString());
        event.getRegistry().register(bedrock_dust);

        orb_of_infinity.setRegistryName("orb_of_infinity").setUnlocalizedName(orb_of_infinity.getRegistryName().toString());
        event.getRegistry().register(orb_of_infinity);

        bedrock_miner.setRegistryName("bedrock_miner").setUnlocalizedName(bedrock_miner.getRegistryName().toString());
        event.getRegistry().register(bedrock_miner);

    }

    public static void registerRenders() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(bedrock_dust,0,new ModelResourceLocation(bedrock_dust.getRegistryName(),"inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(orb_of_infinity,0,new ModelResourceLocation(orb_of_infinity.getRegistryName(),"inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(bedrock_miner,0,new ModelResourceLocation(bedrock_miner.getRegistryName(),"inventory"));
    }
}
