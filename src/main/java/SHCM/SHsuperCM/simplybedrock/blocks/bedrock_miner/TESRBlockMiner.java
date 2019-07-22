package SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner;

import SHCM.SHsuperCM.simplybedrock.SimplyBedrock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TESRBlockMiner extends TileEntitySpecialRenderer<TEBlockMiner> {
    private static EntityItem PICKAXE = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0, ItemStack.EMPTY);
    private static final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];

    public static void setPickaxeModelItem(ItemStack stack) {
        PICKAXE.setItem(stack);
    }

    public static void registerBlockBreaking(Minecraft mc) {
        TextureMap texturemap = mc.getTextureMapBlocks();

        for (int i = 0; i < destroyBlockIcons.length; ++i)
            destroyBlockIcons[i] = texturemap.getAtlasSprite("minecraft:blocks/destroy_stage_" + i);
    }

    @Override
    public void render(TEBlockMiner te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        float angle = ((int) te.getWorld().getTotalWorldTime() + partialTicks) * 25;
        boolean mining = te.fuelAmount > 0 && te.isAboveBedrock();

        PICKAXE.hoverStart = 0;
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x + 0.5f, y + 0.10f, z + 0.5f);

            drawPickaxe(mining, angle);

            GlStateManager.rotate(90, 0, 1, 0);
            drawPickaxe(mining, angle + 90);

            GlStateManager.rotate(90, 0, 1, 0);
            drawPickaxe(mining, angle + 180);

            GlStateManager.rotate(90, 0, 1, 0);
            drawPickaxe(mining, angle + 270);
        }
        GlStateManager.popMatrix();

        /*if(mining) {
            int progress = te.progress*10 / SimplyBedrock.Config.bedrock_hitpoints;
            if(progress >= 0 && progress < 10) {
                BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
                blockrendererdispatcher.renderBlockDamage(getWorld().getBlockState(te.getPos().down()), te.getPos().down(), destroyBlockIcons[progress], getWorld());
            }
        }*/
    }

    private void drawPickaxe(boolean mining, float angle) {
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(-0.3f,0,0);

            GlStateManager.translate(-0.15f, 0.35f, 0);
            GlStateManager.rotate(mining ? angle : 75, 0, 0, -1);
            GlStateManager.scale(1.5f, 1.5f, 1.5f);
            GlStateManager.translate(0.15f, -0.35f, 0);

            Minecraft.getMinecraft().getRenderManager().renderEntity(PICKAXE, 0, 0, 0, 0, 0, true);
        }
        GlStateManager.popMatrix();
    }
}

