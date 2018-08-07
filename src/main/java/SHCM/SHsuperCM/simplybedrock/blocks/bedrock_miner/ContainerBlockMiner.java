package SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;

public class ContainerBlockMiner extends Container {

    private final TEBlockMiner te;

    private SlotFurnaceFuel fuelSlot;

    public ContainerBlockMiner(TEBlockMiner te, InventoryPlayer playerInventory) {
        this.te = te;

        for (int x = 0; x < 9; ++x)
            this.addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 109));

        for (int y = 0; y < 3; ++y)
            for (int x = 0; x < 9; ++x)
                this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 51 + y * 18));

        this.addSlotToContainer(fuelSlot = new SlotFurnaceFuel(te, 36, 8, 21));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.isUsableByPlayer(playerIn);
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this,te);
        listener.sendSlotContents(this,36,te.fuelItem);
        //TODO sort out slot issues
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener icontainerlistener = this.listeners.get(i);

            icontainerlistener.sendWindowProperty(this,0,te.fuelBurnTime);
        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        te.setField(id,data);
    }

    @Override // From MBE https://github.com/TheGreyGhost/MinecraftByExample/blob/master/src/main/java/minecraftbyexample/mbe30_inventory_basic/ContainerBasic.java
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int sourceSlotIndex) {
        Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (sourceSlotIndex < 36) {
            if (!mergeItemStack(sourceStack, 36, 37, false)){
                return ItemStack.EMPTY;
            }
        } else if (sourceSlotIndex == 36) {
            if (!mergeItemStack(sourceStack, 0, 36, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.err.print("Invalid slotIndex:" + sourceSlotIndex);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.putStack(ItemStack.EMPTY);
        } else {
            sourceSlot.onSlotChanged();
        }

        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }


}