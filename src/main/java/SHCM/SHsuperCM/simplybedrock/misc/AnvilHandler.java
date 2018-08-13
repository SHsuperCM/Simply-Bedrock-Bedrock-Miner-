package SHCM.SHsuperCM.simplybedrock.misc;

import SHCM.SHsuperCM.simplybedrock.SimplyBedrock;
import SHCM.SHsuperCM.simplybedrock.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class AnvilHandler {
    @SubscribeEvent
    public static void anvilEvent(AnvilUpdateEvent event) {
        if(SimplyBedrock.Config.orb_of_infinity && event.getRight().getItem() == ModItems.orb_of_infinity && !event.getLeft().isEmpty() && event.getLeft().isItemStackDamageable()) {
            ItemStack item = event.getLeft().copy();
            NBTTagCompound compound = item.getTagCompound();
            if(compound == null) compound = new NBTTagCompound();

            compound.setBoolean("Unbreakable", true);
            item.setTagCompound(compound);
            event.setOutput(item);
            event.setCost(SimplyBedrock.Config.orb_of_infinity_xp_cost);
            event.setMaterialCost(1);

        }
    }
}
