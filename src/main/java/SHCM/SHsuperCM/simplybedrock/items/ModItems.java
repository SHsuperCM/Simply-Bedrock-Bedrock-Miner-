package SHCM.SHsuperCM.simplybedrock.items;

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

    public static ItemBlock bedrock_miner = new ItemBlock(ModBlocks.bedrock_miner);


    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        for (Field f : ModItems.class.getFields()) {
            if(Item.class.isAssignableFrom(f.getType()))
                try {
                    Item item = (Item)f.get(null);
                    if(item == null)
                        item = (Item)f.getType().newInstance();

                    item.setRegistryName(f.getName());
                    item.setUnlocalizedName(item.getRegistryName().toString());

                    f.set(null, item);
                    event.getRegistry().register(item);
                } catch(Exception e) {e.printStackTrace();}
        }
    }

    public static void registerRenders() {
        for (Field f : ModItems.class.getFields()) {
            if(Item.class.isAssignableFrom(f.getType()))
                try {
                    Item item = (Item)f.get(null);

                    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item,0,new ModelResourceLocation(item.getRegistryName(),"inventory"));
                } catch(Exception e) {e.printStackTrace();}
        }
    }
}
