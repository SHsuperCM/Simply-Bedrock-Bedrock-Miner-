package SHCM.SHsuperCM.simplybedrock.client.bedrock_miner;

import SHCM.SHsuperCM.simplybedrock.Config;
import SHCM.SHsuperCM.simplybedrock.SimplyBedrock;
import SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner.ContainerBlockMiner;
import SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner.TEBlockMiner;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiBlockMiner extends GuiContainer{
    private static final ResourceLocation TEXTURE = new ResourceLocation(SimplyBedrock.MODID,"textures/gui/bedrock_miner.png");

    private TEBlockMiner tileEntity;
    private InventoryPlayer inventory;

    public GuiBlockMiner(TEBlockMiner tileEntity, InventoryPlayer inventory) {
        super(new ContainerBlockMiner(tileEntity,inventory));
        this.tileEntity = tileEntity;
        this.inventory = inventory;
        this.xSize = 176;
        this.ySize = 133;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        mc.getTextureManager().bindTexture(TEXTURE);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawCenteredString(fontRenderer,tileEntity.progress*100/Config.bedrock_hitpoints + "% Progress", width/2,height/2-80,new Color(255,255,255).getRGB());
        drawCenteredString(fontRenderer,tileEntity.fuelAmount + " Fuel", width/2,height/2-70,new Color(255,255,255).getRGB());

        String s = this.tileEntity.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 93, 4210752);

        mc.getTextureManager().bindTexture(TEXTURE);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if(Config.bedrock_hitpoints != 0) {
            int p = tileEntity.progress * 128 / Config.bedrock_hitpoints;
            drawTexturedModalRect(41 + p, 21, 41, 133, 128 - p, 16);
        }
        if(tileEntity.fuelBurnTime != 0) {
            int p = tileEntity.fuelAmount * 14 / tileEntity.fuelBurnTime;
            drawTexturedModalRect(26, 36 - p, 26, 134, 14, p);
        }

        this.renderHoveredToolTip(mouseX-guiLeft,mouseY-guiTop);
    }
}
