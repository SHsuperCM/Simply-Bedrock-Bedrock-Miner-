package SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TESRBlockMiner extends TileEntitySpecialRenderer<TEBlockMiner> {
    private static final EntityItem PICKAXE = new EntityItem(Minecraft.getMinecraft().world,0,0,0,new ItemStack(Items.DIAMOND_PICKAXE));
    private int angle = 0;

    @Override
    public void render(TEBlockMiner te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        if(te.fuelAmount > 0 && getWorld().getBlockState(te.getPos().down()).getBlock() == Blocks.BEDROCK)
            angle += 5;

        PICKAXE.hoverStart = 0;
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x,y+1,z);

            GlStateManager.rotate(angle,0,0,-1);

            Minecraft.getMinecraft().getRenderManager().renderEntity(PICKAXE, 0, 0, 0, 0, 0, true);

        }
        GlStateManager.popMatrix();
    }
}
