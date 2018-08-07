package SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner;

import SHCM.SHsuperCM.simplybedrock.SimplyBedrock;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockMiner extends BlockContainer implements ITileEntityProvider {
    public BlockMiner() {
        super(Material.ROCK, MapColor.QUARTZ);
        setHardness(0.75F);
        setCreativeTab(CreativeTabs.DECORATIONS);
        this.fullBlock = false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TEBlockMiner();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TEBlockMiner();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote)
            playerIn.openGui(SimplyBedrock.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        spawnAsEntity(worldIn,pos,((TEBlockMiner)te).getItem());
    }
}
