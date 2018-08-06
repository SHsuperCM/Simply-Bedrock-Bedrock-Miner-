package SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;

public class ContainerBlockMiner extends Container {

    private final TEBlockMiner te;

    public ContainerBlockMiner(TEBlockMiner te, InventoryPlayer playerInventory) {
        this.te = te;

        this.addSlotToContainer(new SlotFurnaceFuel(te, 0, 56, 53));

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.isUsableByPlayer(playerIn);
    }
}
