package SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner;

import SHCM.SHsuperCM.simplybedrock.SimplyBedrock;
import SHCM.SHsuperCM.simplybedrock.blocks.ModBlocks;
import SHCM.SHsuperCM.simplybedrock.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.block.BlockObserver.POWERED;

public class TEBlockMiner extends TileEntityLockable implements ITickable, IInventory {
    private static List<Block> bedrockBlocks = new ArrayList<>();

    public static void setBedrockBlocks(List<Block> bedrockBlocks) {
        TEBlockMiner.bedrockBlocks = bedrockBlocks;
    }

    public ItemStack fuelItem = ItemStack.EMPTY;
    public int fuelAmount = 0;
    public int fuelBurnTime = 0;
    public int progress = 0;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        fuelItem = new ItemStack(compound.getCompoundTag("FuelItem"));
        fuelAmount = compound.getInteger("FuelAmount");
        fuelBurnTime = compound.getInteger("FuelBurnTime");
        progress = compound.getInteger("Progress");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound nbtFuelItem = new NBTTagCompound();
        fuelItem.writeToNBT(nbtFuelItem);
        compound.setTag("FuelItem",nbtFuelItem);
        compound.setInteger("FuelAmount", fuelAmount);
        compound.setInteger("FuelBurnTime", fuelBurnTime);
        compound.setInteger("Progress", progress);
        return super.writeToNBT(compound);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos,999,getUpdateTag());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
        fuelAmount = tag.getInteger("FuelAmount");
        progress = tag.getInteger("Progress");
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        handleUpdateTag(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = super.getUpdateTag();
        compound.setInteger("FuelAmount", fuelAmount);
        compound.setInteger("Progress", progress);
        return compound;
    }

    public boolean isAboveBedrock() {
        return bedrockBlocks.contains(world.getBlockState(getPos().down()).getBlock());
    }

    @Override
    public void update() {
        if(!world.isRemote) {
            if(progress >= SimplyBedrock.Config.bedrock_hitpoints) {
                world.destroyBlock(pos.down(),false);
                progress = 0;
                world.getPlayers(EntityPlayerMP.class,input -> input.getDistance(pos.getX(),pos.getY(),pos.getZ()) < 10).forEach(player -> player.connection.sendPacket(new SPacketCustomSound("minecraft:block.stone.break", SoundCategory.BLOCKS,pos.getX(),pos.getY(),pos.getZ(),1f,0.5f)));
                world.spawnEntity(new EntityItem(world,getPos().getX() + 0.5d, pos.getY() + 0.7d, pos.getZ() + 0.5d,new ItemStack(ModItems.bedrock_dust)));

                world.notifyNeighborsOfStateChange(pos, ModBlocks.bedrock_miner, true);
            } else if (isAboveBedrock()) {
                if (fuelAmount > 0) {
                    progress++;
                    fuelAmount--;
                } else {
                    int fuel;
                    if((fuel = TileEntityFurnace.getItemBurnTime(fuelItem)) > 0) {
                        fuelAmount += fuel;

                        if(fuelItem.getItem() == Items.LAVA_BUCKET)
                            fuelItem = new ItemStack(Items.BUCKET);
                        else
                            fuelItem.setCount(fuelItem.getCount()-1);

                        fuelBurnTime = fuelAmount;
                    }
                }
            } else if (progress != 0)
                progress = 0;
            else
                return;

            world.markBlockRangeForRenderUpdate(pos, pos);
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
            markDirty();
        }
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return fuelItem.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return fuelItem;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack item = fuelItem.splitStack(count);
        markDirty();
        return item;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack item = fuelItem;
        fuelItem = ItemStack.EMPTY;
        markDirty();
        return item;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        fuelItem = stack;
        markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {}
    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return stack.getItem().getItemBurnTime(stack) > 0;
    }

    @Override
    public int getField(int id) {
        if (id == 0)
            return fuelBurnTime;
        return 0;
    }
    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                fuelBurnTime = value;
        }
    }
    @Override
    public int getFieldCount() {
        return 1;
    }

    @Override
    public void clear() {
        fuelItem = ItemStack.EMPTY;
        markDirty();
    }

    @Override
    public String getName() {
        return "tile." + SimplyBedrock.MODID + ":bedrock_miner.name";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerBlockMiner(this, playerInventory);
    }

    @Override
    public String getGuiID() {
        return SimplyBedrock.MODID + ":bedrock_miner_container";
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(ModBlocks.bedrock_miner,1);
        if(fuelAmount > 0 || !fuelItem.isEmpty()) {
            NBTTagCompound teInfo = new NBTTagCompound();

            NBTTagCompound nbtFuelItem = new NBTTagCompound();
            fuelItem.writeToNBT(nbtFuelItem);
            teInfo.setTag("FuelItem", nbtFuelItem);
            teInfo.setInteger("FuelAmount", fuelAmount);
            teInfo.setInteger("FuelBurnTime", fuelBurnTime);

            item.setTagInfo("BlockEntityTag", teInfo);
        }
        return item;
    }
}