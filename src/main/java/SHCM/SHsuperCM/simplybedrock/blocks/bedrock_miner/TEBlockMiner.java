package SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner;

import SHCM.SHsuperCM.simplybedrock.SimplyBedrock;
import SHCM.SHsuperCM.simplybedrock.blocks.ModBlocks;
import com.sun.org.apache.xml.internal.security.utils.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;

import javax.annotation.Nullable;

public class TEBlockMiner extends TileEntityLockable implements ITickable, IInventory {

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
        fuelItem = new ItemStack(tag.getCompoundTag("FuelItem"));
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
        NBTTagCompound nbtFuelItem = new NBTTagCompound();
        fuelItem.writeToNBT(nbtFuelItem);
        compound.setTag("FuelItem",nbtFuelItem);
        compound.setInteger("FuelAmount", fuelAmount);
        compound.setInteger("Progress", progress);
        return compound;
    }

    @Override
    public void update() {
        if(!world.isRemote) {
            if(progress >= 6000) {
                world.setBlockState(pos.down(),Blocks.AIR.getDefaultState());
                progress = 0;
                sync();
            } else if (world.getBlockState(getPos().down()).getBlock() == Blocks.BEDROCK) {
                if (fuelAmount > 0) {
                    progress++;
                    fuelAmount--;
                } else {
                    int fuel;
                    if((fuel = TileEntityFurnace.getItemBurnTime(fuelItem)) > 0) {
                        fuelAmount += fuel;
                        fuelItem.setCount(fuelItem.getCount()-1);
                        fuelBurnTime = fuelAmount;
                    }
                }

                sync();
            } else if (progress != 0) {
                progress = 0;
                sync();
            }
        }
    }

    private void sync() {
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
        markDirty();
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
        return fuelItem.splitStack(count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack item = fuelItem;
        fuelItem = ItemStack.EMPTY;
        return item;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        fuelItem = stack;
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
    public void clear() {fuelItem = ItemStack.EMPTY;}

    @Override
    public String getName() {
        return "tile.simplybedrock:bedrock_miner.name";
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