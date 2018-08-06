package SHCM.SHsuperCM.simplybedrock.client;

import SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner.ContainerBlockMiner;
import SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner.TEBlockMiner;
import SHCM.SHsuperCM.simplybedrock.client.bedrock_miner.GuiBlockMiner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

        if (tileEntity != null && tileEntity instanceof TEBlockMiner && ID == 0)
            return new ContainerBlockMiner((TEBlockMiner) tileEntity, player.inventory);

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

        if (tileEntity != null && tileEntity instanceof TEBlockMiner && ID == 0)
            return new GuiBlockMiner((TEBlockMiner) tileEntity, player.inventory);

        return null;
    }
}
