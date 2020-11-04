package tfar.tfcinfo.clent;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiCheckBox;

import java.io.IOException;
import java.lang.reflect.Field;

public class CheckboxScreen extends GuiScreen {

    protected int xSize = 250;
    /** The Y size of the inventory window in pixels. */
    protected int ySize = 170;
    /** Starting X position for the Gui. Inconsistent use for Gui backgrounds. */
    protected int guiLeft;
    /** Starting Y position for the Gui. Inconsistent use for Gui backgrounds. */
    protected int guiTop;

    protected static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation("textures/gui/demo_background.png");

    protected static final int spacing = 11;

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        drawBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawForeground();
    }

    public void drawBackground() {
        this.mc.getTextureManager().bindTexture(CRAFTING_TABLE_GUI_TEXTURES);
        int i = this.guiLeft;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(i, j, 0, 0, 200, 200);
    }

    protected void drawForeground() {
        int xPos = guiLeft + 24;
        int yPos = guiTop + 2;

        this.fontRenderer.drawString("Current Temp", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Average Temp", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Max Temp", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Rainfall", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Spawn Protection Timer", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Date", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Time", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Slime Chunks", xPos, yPos += spacing, 0x404040);

        this.fontRenderer.drawString("X Position", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Y Position", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Z Position", xPos, yPos += spacing, 0x404040);


    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        int xPos = guiLeft + 10;
        int yPos = guiTop;
        for (int i = 0; i < TFCInfoClientConfig.class.getFields().length - 7;i++) {

            if (i > 0 && i % 12 == 0) {
                xPos += 135;
                yPos = guiTop;
            }

            try {
                addButton(new GuiCheckBox(i, xPos, yPos += spacing, "", TFCInfoClientConfig.class.getFields()[i].getBoolean(null)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        int id = button.id;
        Field field = TFCInfoClientConfig.class.getFields()[id];
        try {
            field.setBoolean(null,!field.getBoolean(null));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
