package SHCM.SHsuperCM.simplybedrock.proxy;

import SHCM.SHsuperCM.simplybedrock.blocks.ModBlocks;
import SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner.TEBlockMiner;
import SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner.TESRBlockMiner;
import SHCM.SHsuperCM.simplybedrock.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ModItems.registerRenders();
        ModBlocks.registerRenders();
        TESRBlockMiner.registerBlockBreaking(Minecraft.getMinecraft());
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        World world = Minecraft.getMinecraft().world;
        if(world != null && !(Minecraft.getMinecraft().currentScreen instanceof GuiIngameMenu) && world.getTotalWorldTime() % 5 == 0)
            for(TileEntity te : world.loadedTileEntityList)
                if(te instanceof TEBlockMiner && ((TEBlockMiner)te).fuelAmount > 0 && world.getBlockState(te.getPos().down()).getBlock() == Blocks.BEDROCK)
                    Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSoundRecord(SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS,1f, -0.5f,te.getPos()));
    }
}
