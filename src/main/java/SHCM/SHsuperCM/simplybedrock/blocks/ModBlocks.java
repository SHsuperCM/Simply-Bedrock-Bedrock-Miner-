package SHCM.SHsuperCM.simplybedrock.blocks;

import SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner.BlockMiner;
import SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner.TEBlockMiner;
import SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner.TESRBlockMiner;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber
public class ModBlocks {

    public static BlockMiner bedrock_miner;




    @SubscribeEvent
    public static void register(RegistryEvent.Register<Block> event) {
        for (Field f : ModBlocks.class.getFields()) {
            if(Block.class.isAssignableFrom(f.getType()))
                try {
                    Block block = (Block)f.get(null);
                    if(block == null)
                        block = (Block)f.getType().newInstance();

                    block.setRegistryName(f.getName());
                    block.setUnlocalizedName(block.getRegistryName().toString());

                    if(block.hasTileEntity(null))
                        GameRegistry.registerTileEntity(block.createTileEntity(null,null).getClass(),new ResourceLocation(block.getRegistryName().toString() + "_tileentity"));

                    f.set(null, block);
                    event.getRegistry().register(block);
                } catch(Exception e) {e.printStackTrace();}
        }
    }

    public static void registerRenders() {
        for (Field f : ModBlocks.class.getFields()) {
            if(Block.class.isAssignableFrom(f.getType()))
                try {
                    Block block = (Block)f.get(null);

                    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),0,new ModelResourceLocation(block.getRegistryName(),"normal"));

                } catch(Exception e) {e.printStackTrace();}
        }

        ClientRegistry.bindTileEntitySpecialRenderer(TEBlockMiner.class,new TESRBlockMiner());
    }
}
