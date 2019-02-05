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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber
public class ModBlocks {

    public static BlockMiner bedrock_miner = new BlockMiner();

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Block> event) {
        bedrock_miner.setRegistryName("bedrock_miner").setUnlocalizedName(bedrock_miner.getRegistryName().toString());

        GameRegistry.registerTileEntity(TEBlockMiner.class,new ResourceLocation(bedrock_miner.getRegistryName() + "_tileentity"));

        event.getRegistry().register(bedrock_miner);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(bedrock_miner),0,new ModelResourceLocation(bedrock_miner.getRegistryName(),"normal"));

        ClientRegistry.bindTileEntitySpecialRenderer(TEBlockMiner.class,new TESRBlockMiner());
    }
}
